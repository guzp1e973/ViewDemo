package com.example.cnblog;

import com.example.cnblog.database.SqlArticlesHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class OfflineWebView extends Activity {
	private TextView title;
	private TextView bloger;
	private WebView web;
	private SqlArticlesHelper helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline_content);
		helper=new SqlArticlesHelper(this, "cnblog.db");
		title=(TextView) findViewById(R.id.offline_content_title);
		bloger=(TextView) findViewById(R.id.offline_content_bloger);
		web=(WebView) findViewById(R.id.offline_content_text);
		int id=getIntent().getIntExtra("id", -1);
		
		Cursor c=helper.queryBlogById(id);
		c.moveToFirst();
		//Log.i("msg",c.getCount()+"sss");
		//Log.i("msg",c.getString(2));
		title.setText(c.getString(c.getColumnIndex("blogTitle")));
		bloger.setText("博主："+c.getString(c.getColumnIndex("bloger")));
		String text=c.getString(c.getColumnIndex("blogText"));
		//web.loadData(text, "text/html", "utf-8");
		web.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
	}
}
