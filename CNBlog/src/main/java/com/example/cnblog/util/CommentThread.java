package com.example.cnblog.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.os.Handler;
import android.util.Log;

public class CommentThread extends Thread {
	private String url;
	private Handler handler;
	public CommentThread(String url, Handler handler){
		this.url=url;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL u=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) u.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			SAXParserFactory spf=SAXParserFactory.newInstance();
			SAXParser sp=spf.newSAXParser();
			
			sp.parse(in, new CommentSaxHandler());
			handler.sendEmptyMessage(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}
}
