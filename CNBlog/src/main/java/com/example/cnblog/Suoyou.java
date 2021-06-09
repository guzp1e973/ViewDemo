package com.example.cnblog;


import java.util.List;

import com.example.cnblog.adapter.RemenAdapter;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.util.BlogThread;
import com.example.cnblog.util.RefreshableView;
import com.example.cnblog.util.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Suoyou extends Activity implements OnItemClickListener {
	private ListView lv;
	private RemenAdapter adapter;
	private RefreshableView refresh;
	private ProgressBar bar;
	private String url;
	private int page=1;
	private int lastindex;
	private List<Article> articles;
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//dia.dismiss();
			bar.setVisibility(View.GONE);
			refresh.setVisibility(View.VISIBLE);
			refresh.finishRefreshing();
			lv.setDivider(null);
			
			
			if(page==1){
				adapter = new RemenAdapter(Suoyou.this);
				lv.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}
		};
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.zuixin);
		refresh=(RefreshableView) findViewById(R.id.refresh);
		lv = (ListView) findViewById(R.id.remen_list);
		View footer=View.inflate(Suoyou.this, R.layout.comment_footer, null);
		Button more=(Button) footer.findViewById(R.id.more);
		ProgressBar moreing=(ProgressBar) footer.findViewById(R.id.moreing);
		more.setVisibility(View.GONE);
		moreing.setVisibility(View.VISIBLE);
		url=getIntent().getStringExtra("url");
		lv.addFooterView(footer);
		lv.setOnItemClickListener(this);
		lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
				if((lastindex==adapter.getCount())&&(scrollState==OnScrollListener.SCROLL_STATE_IDLE)){
					
					new BlogThread(url+(++page)+"/10",
							handler,true).start();
					Log.i("msg", "onscroll");
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastindex=firstVisibleItem+visibleItemCount-1;
			}
		});
		bar=(ProgressBar) findViewById(R.id.zuixin_bar);
		//dia=ProgressDialog.show(this, null, "数据加载中请稍候...");
		new BlogThread(url+(page)+"/10",
				handler,false).start();
		refresh.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				//AppStatic.articles.clear();
				page=1;
				BlogThread t=new BlogThread(url+(page)+"/10",
						handler,false);
				t.start();
				
					
					
				
			}
		}, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent it=new Intent(this,BlogViewAct.class);
		it.putExtra("title", AppStatic.articles.get(position).getTitle());
		it.putExtra("pos", position);
		it.putExtra("url", AppStatic.articles.get(position).getUrl());
		startActivity(it);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		this.articles=AppStatic.articles;
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		
		
		AppStatic.articles=this.articles;
		super.onResume();
	}
}