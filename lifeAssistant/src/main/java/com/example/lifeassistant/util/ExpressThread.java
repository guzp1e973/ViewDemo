package com.example.lifeassistant.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lifeassistant.bean.DayWeather;
import com.example.lifeassistant.bean.ExpressInfo;
import com.example.lifeassistant.bean.Weather;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ExpressThread extends Thread {
	private Handler handler;
	private String url;
	public ExpressThread(Handler handler, String url) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		this.url=url;
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
				
				ExpressInfo express=parserJson(sb.toString());
				Message msg = Message.obtain();
				msg.obj=express;
				msg.what = 1;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(2);
			}

		} catch (Exception e) {
			// TODO: handle exception
			handler.sendEmptyMessage(0);
		}
		super.run();
	}
	private ExpressInfo parserJson(String s) throws JSONException {
		// TODO Auto-generated method stub
		ExpressInfo express=new ExpressInfo();
		JSONObject jsonObject = new JSONObject(s);
		JSONObject result = jsonObject
				.getJSONObject("result");
		express.setCompany(result.getString("company"));
		express.setNo(result.getString("no"));
		JSONArray array=result.getJSONArray("list");
		StringBuffer sbu=new StringBuffer();
		for (int i = 0; i < array.length(); i++) {
			JSONObject ob=array.getJSONObject(i);
			String t=ob.getString("datetime");
			String r=ob.getString("remark");
			sbu.append(t+":"+r+";\n");
		}
		express.setInfo(sbu.toString());
		return express;
	}
}
