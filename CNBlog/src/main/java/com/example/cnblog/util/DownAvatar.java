package com.example.cnblog.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.cnblog.BlogViewAct;
import com.example.cnblog.instance.AppStatic;

import android.graphics.BitmapFactory;
import android.os.Handler;

public class DownAvatar extends Thread {
	private String url;
	private Handler handler;
	public DownAvatar(String url, Handler handler) {
		this.url = url;
		this.handler = handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(AppStatic.down==false){
			return;
		}
		try {
			URL ur=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) ur.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			BlogViewAct.bit=BitmapFactory.decodeStream(in);
			handler.sendEmptyMessage(5);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		super.run();
	}
}
