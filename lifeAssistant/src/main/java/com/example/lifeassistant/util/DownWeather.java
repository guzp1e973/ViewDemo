package com.example.lifeassistant.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.lifeassistant.bean.DayWeather;
import com.example.lifeassistant.bean.Weather;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownWeather extends Thread {
	private Handler handler;
	private String url;

	public DownWeather(Handler handler, String url) {
		this.handler = handler;
		this.url = "http://m.weather.com.cn/atad/" + url + ".html";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String s;
				StringBuffer sb = new StringBuffer();
				while ((s = reader.readLine()) != null) {
					sb.append(s);
				}
				reader.close();
				in.close();
				conn.disconnect();
				Weather w = new Weather();
				JSONObject jsonObject = new JSONObject(sb.toString());
				JSONObject weatherinfo = jsonObject
						.getJSONObject("weatherinfo");
				w.setCity(weatherinfo.getString("city"));
				String date = weatherinfo.getString("date_y");
				w.setDate_y(date);
				w.setIndex(weatherinfo.getString("index"));
				w.setWind1(weatherinfo.getString("wind1"));
				String week = weatherinfo.getString("week");
				w.setWeek(week);
				w.setTemp(weatherinfo.getString("temp1"));
				w.setWeather(weatherinfo.getString("weather1"));
				List<DayWeather> evday = new ArrayList<DayWeather>();
				for (int i = 2; i < 7; i++) {
					DayWeather d = new DayWeather();
					String temp = weatherinfo.getString("temp" + i);
					String weather = weatherinfo.getString("weather" + i);
					String weekn = getNextWeek(week);
					week = weekn;
					String daten = getNextDate(date);
					date=daten;
					d.setDate_y(daten);
					d.setWeek(weekn);
					d.setTemp(temp);
					d.setWeather(weather);
					evday.add(d);
				}
				w.setEvday(evday);

				Message msg = Message.obtain();
				msg.obj = w;
				msg.what = 1;
				handler.sendMessage(msg);
			} else {
				Log.i("msg", "无法连接");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		super.run();
	}

	private String getNextDate(String date) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date d = sdf.parse(date);
		long t=d.getTime();
		t=t+86400000;
		d.setTime(t);
		String next=sdf.format(d);
		return next;
	}

	private String getNextWeek(String week) {
		// TODO Auto-generated method stub
		if ("星期一".equals(week)) {
			return "星期二";
		}
		if ("星期二".equals(week)) {
			return "星期三";
		}
		if ("星期三".equals(week)) {
			return "星期四";
		}
		if ("星期四".equals(week)) {
			return "星期五";
		}
		if ("星期五".equals(week)) {
			return "星期六";
		}
		if ("星期六".equals(week)) {
			return "星期日";
		}
		if ("星期日".equals(week)) {
			return "星期一";
		}
		return null;
	}

}
