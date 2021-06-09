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

import android.os.Handler;
import android.provider.MediaStore.Audio.Artists;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;

public class NetThread extends Thread{
	private String urlstr;
	private Handler handler;
	private boolean isappend;
	private List<Article> arts=new ArrayList<Article>();
	public NetThread(String url,Handler handler,boolean isappend){
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
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(in);
			NodeList entry=doc.getElementsByTagName("entry");
			for (int i = 0; i < entry.getLength(); i++) {
				NodeList child=entry.item(i).getChildNodes();
				//Log.i("msg", child.item(5).getAttributes().item(1).getNodeValue());
				Article art=new Article();
				int id=Integer.parseInt(child.item(0).getFirstChild().getNodeValue());
				art.setId(id);
				art.setTitle(child.item(1).getFirstChild().getNodeValue());
				art.setSummary(child.item(2).getFirstChild().getNodeValue());
				art.setPublished(child.item(3).getFirstChild().getNodeValue());
				art.setUpdated(child.item(4).getFirstChild().getNodeValue());
				art.setLink(child.item(5).getAttributes().item(1).getNodeValue());
				art.setViews(child.item(7).getFirstChild().getNodeValue());
				art.setComments(child.item(8).getFirstChild().getNodeValue());
				art.setSourceName(child.item(11).getFirstChild().getNodeValue());
				art.setUrl("http://wcf.open.cnblogs.com/news/item/"+id);
				arts.add(art);
			}
			if(isappend){
				AppStatic.articles.addAll(arts);
			}else{
				AppStatic.articles=arts;
			}
			handler.sendEmptyMessage(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.run();
	}
}