package com.example.cnblog.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;

import android.os.Handler;
import android.util.Log;

public class BlogThread extends Thread{

	private String urlstr;
	private Handler handler;
	private boolean isappend;
	private boolean flag=false;
	private List<Article> arts=new ArrayList<Article>();
	public BlogThread(String url,Handler handler,boolean isappend){
		this.urlstr=url;
		this.handler=handler;
		this.isappend=isappend;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL url=new URL(urlstr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			XmlPullParserFactory xpf=XmlPullParserFactory.newInstance();
			XmlPullParser xpp=xpf.newPullParser();
			xpp.setInput(in, "utf-8");
			Article article=null;
			
			int event=xpp.getEventType();
			while(event!=XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					if("entry".equals(xpp.getName())){
						article=new Article();
						flag=true;
					}
					if("feed".equals(xpp.getName())){
						flag=false;
					}
					if("id".equals(xpp.getName())&&flag==true){
						article.setId(Integer.parseInt(xpp.nextText()));
					}
					if("title".equals(xpp.getName())&&flag==true){
						article.setTitle(xpp.nextText());
					}
					if("summary".equals(xpp.getName())&&flag==true){
						article.setSummary(xpp.nextText());
					}
					if("published".equals(xpp.getName())&&flag==true){
						article.setPublished(xpp.nextText());
					}
					if("updated".equals(xpp.getName())&&flag==true){
						article.setUpdated(xpp.nextText());
					}
					if("views".equals(xpp.getName())&&flag==true){
						article.setViews(xpp.nextText());
					}
					if("comments".equals(xpp.getName())&&flag==true){
						article.setComments(xpp.nextText());
					}
					if("name".equals(xpp.getName())&&flag==true){
						article.setSourceName(xpp.nextText());
					}
					if("uri".equals(xpp.getName())&&flag==true){
						article.setUri(xpp.nextText());
					}
					if("avatar".equals(xpp.getName())&&flag==true){
						article.setAvatar(xpp.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if("entry".equals(xpp.getName())){
						article.setUrl("http://wcf.open.cnblogs.com/blog/post/body/"+article.getId());
						arts.add(article);
					}
					break;

				default:
					break;
				}
				event=xpp.next();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isappend){
			AppStatic.articles.addAll(arts);
		}else{
			AppStatic.articles=arts;
		}
		handler.sendEmptyMessage(1);
		super.run();
	}
}
