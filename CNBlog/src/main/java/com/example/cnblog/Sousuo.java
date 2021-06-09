package com.example.cnblog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.cnblog.adapter.SearchAdapter;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.util.SearchThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Sousuo extends Activity implements OnClickListener {
	private EditText edit;
	private Button go;
	private Button clear;
	private ListView lv;
	private ProgressBar probar;
	private TextView search_null;
	private String url;
	private SearchAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				probar.setVisibility(View.GONE);
				adapter = new SearchAdapter(Sousuo.this);
				if (AppStatic.blogers.size() == 0) {
					search_null.setText("没有可显示内容");
					search_null.setVisibility(View.VISIBLE);
				}
				lv.setAdapter(adapter);
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.sousuo);
		init();

	}

	private void init() {
		edit = (EditText) findViewById(R.id.search_text);
		go = (Button) findViewById(R.id.search_go);
		clear = (Button) findViewById(R.id.search_clear);
		lv = (ListView) findViewById(R.id.resultList);
		search_null = (TextView) findViewById(R.id.searchNull);
		probar = (ProgressBar) findViewById(R.id.blog_progress);
		go.setOnClickListener(this);
		clear.setOnClickListener(this);
		lv.setOnItemClickListener(new ItemClickListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubIt
		switch (v.getId()) {
		case R.id.search_go:
			String text = edit.getText().toString();
			try {
				text = URLEncoder.encode(text, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url = "http://wcf.open.cnblogs.com/blog/bloggers/search?t=" + text;
			search_null.setVisibility(View.GONE);
			probar.setVisibility(View.VISIBLE);
			new SearchThread(url, handler).start();
			break;
		case R.id.search_clear:
			AppStatic.blogers.clear();
			adapter.notifyDataSetChanged();
			search_null.setText("没有可显示内容");
			search_null.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	private class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			AppStatic.bloger= AppStatic.blogers.get(position);
			Intent intent = new Intent(Sousuo.this, BlogerAct.class);
			intent.putExtra("url", "http://wcf.open.cnblogs.com/blog/u/"
					+ AppStatic.bloger.getBlogapp() + "/posts/");
			startActivity(intent);
		}

	}
}
