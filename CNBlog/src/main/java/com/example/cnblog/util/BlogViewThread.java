package com.example.cnblog.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.News;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BlogViewThread extends Thread {
	private String urlstr;
	private Handler handler;
	public BlogViewThread(String url,Handler handler){
		this.urlstr=url;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL url;
		try {
			
			url = new URL(urlstr);
			
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(in);
			NodeList n1=doc.getElementsByTagName("string");
			
			String s=n1.item(0).getFirstChild().getNodeValue();
			
			Message msg=Message.obtain();
			msg.what=2;
			msg.obj=s;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.run();
	}
}
