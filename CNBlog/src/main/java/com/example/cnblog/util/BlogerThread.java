package com.example.cnblog.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.instance.Bloger;
import com.example.cnblog.instance.News;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BlogerThread extends Thread {
	private String urlstr;
	private Handler handler;
	public BlogerThread(String url, Handler handler) {
		this.urlstr = url;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL url;
		try {
			
			url = new URL(urlstr);
			//Log.i("msg", urlstr);
			Bloger bloger=new Bloger();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in = conn.getInputStream();
//			BufferedReader reader=new BufferedReader(new InputStreamReader(in)) ;
//			String s;
//			while((s=reader.readLine())!=null){
//				Log.i("msg", s);
//			}
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=dbf.newDocumentBuilder();
			Document doc=builder.parse(in);
			NodeList nl=doc.getElementsByTagName("entry");
			
			NodeList child=nl.item(0).getChildNodes();
			bloger.setId(child.item(0).getFirstChild().getNodeValue());
			
			bloger.setTitle(child.item(1).getFirstChild().getNodeValue());
			bloger.setUpdated(child.item(2).getFirstChild().getNodeValue());
			bloger.setBlogapp(child.item(4).getFirstChild().getNodeValue());
			try {
				bloger.setAvatar(child.item(5).getFirstChild().getNodeValue());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//Log.i("msg", bloger.getAvatar());
			bloger.setPostcount(Integer.parseInt(child.item(6).getFirstChild().getNodeValue()));
			AppStatic.bloger=bloger;
			Message msg = Message.obtain();
			msg.what = 4;
			msg.obj=bloger;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.run();
	}
}
