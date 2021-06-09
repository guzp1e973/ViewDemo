package com.example.cnblog;

import java.util.ArrayList;
import java.util.List;

import com.example.cnblog.adapter.ShuqianAdapter;
import com.example.cnblog.database.SqlArticlesHelper;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.instance.Mark;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Shuqian extends Activity implements OnClickListener {
	private SqlArticlesHelper helper;
	private ListView listview;
	private TextView count;
	private Button check_all;
	private Button delete;
	private ShuqianAdapter adapter;
	private List<Mark> list;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.shuqian);
		helper=new SqlArticlesHelper(this, "cnblog.db");
		Cursor c=helper.queryAllNews();
		list=new ArrayList<Mark>();
		while(c.moveToNext()){
			Mark mark=new Mark();
			mark.setTitle(c.getString(c.getColumnIndex("title")));
			mark.setUrl(c.getString(c.getColumnIndex("url")));
			mark.setId(c.getInt(c.getColumnIndex("newsId")));
			mark.setCheck(false);
			list.add(mark);
		}
		count=(TextView) findViewById(R.id.count);
		count.setText("共"+c.getCount()+"条");
		check_all=(Button) findViewById(R.id.check_all);
		delete=(Button) findViewById(R.id.check_delete);
		check_all.setOnClickListener(this);
		delete.setOnClickListener(this);
		listview=(ListView) findViewById(R.id.mark_list);
		adapter=new ShuqianAdapter(this,list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Shuqian.this, WebViewAct.class);
				it.putExtra("title", list.get(position).getTitle());
				it.putExtra("url", list.get(position).getUrl());
				startActivity(it);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.check_all:
			if("全选".equals(check_all.getText())){
				for(int i=0;i<list.size();i++){
					list.get(i).setCheck(true);
				}
				check_all.setText("取消");
			}else{
				for(int i=0;i<list.size();i++){
					list.get(i).setCheck(false);
				}
				check_all.setText("全选");
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.check_delete:

			int i=0;
			while(i<list.size()){
				if(list.get(i).isCheck()){
					helper.deletebyId(list.get(i).getId());
					list.remove(i);
				}else{
					i++;
				}
			}
			adapter.notifyDataSetChanged();
			count.setText("共"+list.size()+"条");
			break;
		case R.id.check_button:
			
			break;

		default:
			break;
		}
	}
}
