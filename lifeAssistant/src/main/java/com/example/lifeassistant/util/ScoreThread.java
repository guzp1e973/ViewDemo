package com.example.lifeassistant.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.lifeassistant.activitys.contacts.Contacts;
import com.example.lifeassistant.bean.Course;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ScoreThread extends Thread {
	private Handler handler;
	private String url;

	public ScoreThread(Handler handler, String url) {
		this.handler = handler;
		this.url = url;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			
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
				Log.i("msg", sb.toString());
				reader.close();
				in.close();
				conn.disconnect();
				Document doc=Jsoup.parse(sb.toString());
				Elements elms = doc.getElementsByTag("table");
				Element nametable = elms.get(1);
				Elements trname=nametable.getElementsByTag("tr");
				Elements tdname=trname.get(0).getElementsByTag("td");
				Constants.name=tdname.get(1).text();
				Element table = elms.get(2);
				Elements trs=table.getElementsByTag("tr");
				List<Course> courses=new ArrayList<Course>();
				for (int i = 1; i < trs.size(); i++) {
					Elements td=trs.get(i).getElementsByTag("td");
					Course c=new Course();
					c.setName(td.get(1).text());
					c.setScore(Float.parseFloat(td.get(2).text()));
					courses.add(c);
				}
				Message msg=Message.obtain();
				msg.obj=courses;
				
				msg.what=0;
				handler.sendMessage(msg);
			}else{
				handler.sendEmptyMessage(1);
			}
//			Document doc = Jsoup.connect(url).data("query", "Java")
//					.userAgent("Mozilla").cookie("auth", "token").timeout(15000)
//					.get();
			
				
		} catch (Exception e) {
			// TODO: handle exception
			handler.sendEmptyMessage(1);
		}
		super.run();
	}
}
