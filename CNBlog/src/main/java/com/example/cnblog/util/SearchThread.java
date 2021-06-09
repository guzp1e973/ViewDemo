package com.example.cnblog.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.instance.Bloger;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SearchThread extends Thread {
	private String urlstr;
	private Handler handler;
	public SearchThread(String url, Handler handler) {
		this.urlstr = url;
		this.handler = handler;
	}
	private List<Bloger> blogers=new ArrayList<Bloger>();
	private boolean flag;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL url;
		try {
			
			url = new URL(urlstr);
			//Log.i("msg", urlstr);
			Bloger bloger=null;
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in = conn.getInputStream();
//			BufferedReader reader=new BufferedReader(new InputStreamReader(in)) ;
//			String s;
//			while((s=reader.readLine())!=null){
//				Log.i("msg", s);
//			}
			XmlPullParserFactory xpf=XmlPullParserFactory.newInstance();
			XmlPullParser xpp=xpf.newPullParser();
			xpp.setInput(in, "utf-8");
			int event=xpp.getEventType();
			while(event!=XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_TAG:
					if("entry".equals(xpp.getName())){
						bloger=new Bloger();
						flag=true;
					}
					if("feed".equals(xpp.getName())){
						flag=false;
					}
					if("id".equals(xpp.getName())&&flag==true){
						bloger.setId(xpp.nextText());
					}
					if("title".equals(xpp.getName())&&flag==true){
						bloger.setTitle(xpp.nextText());
					}
					if("updated".equals(xpp.getName())&&flag==true){
						bloger.setUpdated(xpp.nextText());
					}
					
					if("blogapp".equals(xpp.getName())&&flag==true){
						bloger.setBlogapp(xpp.nextText());
					}
					if("postcount".equals(xpp.getName())&&flag==true){
						bloger.setPostcount(Integer.parseInt(xpp.nextText()));
					}
					
					if("avatar".equals(xpp.getName())&&flag==true){
						bloger.setAvatar(xpp.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if("entry".equals(xpp.getName())){
						blogers.add(bloger);
					}
					break;

				default:
					break;
				}
				event=xpp.next();
			}
			
			//Log.i("msg", blogers.size()+"");
			AppStatic.blogers=blogers;
			Message msg = Message.obtain();
			msg.what = 1;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.run();
	}
}


