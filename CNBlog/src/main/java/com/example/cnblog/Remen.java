package com.example.cnblog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.example.cnblog.adapter.RemenAdapter;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;

import com.example.cnblog.util.NetThread;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Remen extends Activity implements OnItemClickListener {
	private ListView lv;
	private RemenAdapter adapter;
	private List<Article> articles;
	private ProgressBar bar;

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			bar.setVisibility(View.GONE);
			lv.setDivider(null);
			adapter = new RemenAdapter(Remen.this);
			lv.setAdapter(adapter);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.remen);
		lv = (ListView) findViewById(R.id.remen_list);
		bar = (ProgressBar) findViewById(R.id.remen_bar);
		// dia=ProgressDialog.show(this, null, "数据加载中请稍候...");
		articles = AppStatic.articles;
		articles.clear();
		new NetThread("http://wcf.open.cnblogs.com/news/hot/30", handler, false)
				.start();
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, WebViewAct.class);
		it.putExtra("title", AppStatic.articles.get(position).getTitle());
		it.putExtra("pos", position);
		it.putExtra("url", AppStatic.articles.get(position).getUrl());
		startActivity(it);
	}

}
