package com.example.cnblog;

import java.util.ArrayList;
import java.util.List;

import com.example.cnblog.adapter.OffLineAdapter;
import com.example.cnblog.database.SqlArticlesHelper;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.instance.BlogOff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class OffLine extends Activity implements OnItemClickListener {
	private TextView no;
	private ListView lv;
	private List<BlogOff> list;
	private SqlArticlesHelper helper;
	private OffLineAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.offline);
		no=(TextView) findViewById(R.id.offline_null);
		lv=(ListView) findViewById(R.id.offline_list);
		helper=new SqlArticlesHelper(this, "cnblog.db");
		Cursor c=helper.queryAllBlogs();
		list=new ArrayList<BlogOff>();
		//BlogOff blog=null;
		AppStatic.articles.clear();
		while(c.moveToNext()){
			BlogOff blog=new BlogOff();
			blog.setBloger(c.getString(c.getColumnIndex("bloger")));
			blog.setBlogerUrl(c.getString(c.getColumnIndex("blogerUrl")));
			blog.setBlogId(Integer.parseInt(c.getString(c.getColumnIndex("blogId"))));
			blog.setBlogLink(c.getString(c.getColumnIndex("blogUrl")));
			blog.setStoreTime(c.getLong(c.getColumnIndex("storeTime")));
			blog.setSummary(c.getString(c.getColumnIndex("blogSummary")));
			blog.setTitle(c.getString(c.getColumnIndex("blogTitle")));
			blog.setBlogApp(c.getString(c.getColumnIndex("blogApp")));
			blog.setUpdateTime(c.getString(c.getColumnIndex("updateTime")));
			list.add(blog);
			Article art=new Article();
			art.setSourceName(blog.getBloger());
			art.setId(blog.getBlogId());
			art.setAvatar(c.getString(c.getColumnIndex("avatar")));
			AppStatic.articles.add(art);
		} 
		if(list.size()!=0){
			no.setVisibility(View.GONE);
		}
		adapter=new OffLineAdapter(this,list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent it=new Intent(this,OfflineWebView.class);
		it.putExtra("id", list.get(position).getBlogId());
		startActivity(it);
	}
	
}
