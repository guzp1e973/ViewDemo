package com.example.cnblog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.cnblog.adapter.RemenAdapter;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Bloger;
import com.example.cnblog.util.BlogThread;
import com.example.cnblog.util.DownAvatar;
import com.example.cnblog.util.RefreshableView;
import com.example.cnblog.util.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BlogerAct extends Activity implements OnItemClickListener {
	private ListView lv;
	private RemenAdapter adapter;
	private RefreshableView refresh;
	private ProgressBar bar;
	private TextView name;
	private TextView count;
	private ImageView blogerimg;
	private String url;
	private int page = 1;
	private int lastindex;
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// dia.dismiss();
			bar.setVisibility(View.GONE);
			refresh.setVisibility(View.VISIBLE);
			refresh.finishRefreshing();
			lv.setDivider(null);
			if (msg.what == 5) {
				blogerimg.setImageBitmap(BlogViewAct.bit);
			}

			if (page == 1) {
				adapter = new RemenAdapter(BlogerAct.this);
				lv.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.blogeract);
		refresh = (RefreshableView) findViewById(R.id.refresh);
		lv = (ListView) findViewById(R.id.remen_list);
		name = (TextView) findViewById(R.id.blog_blogerName);
		count = (TextView) findViewById(R.id.blog_blogCount);
		blogerimg = (ImageView) findViewById(R.id.blog_blogerImg);

		View footer = View.inflate(BlogerAct.this, R.layout.comment_footer,
				null);
		Button more = (Button) footer.findViewById(R.id.more);
		ProgressBar moreing = (ProgressBar) footer.findViewById(R.id.moreing);
		more.setVisibility(View.GONE);
		moreing.setVisibility(View.VISIBLE);
		url = getIntent().getStringExtra("url");
		Bloger bloger = AppStatic.bloger;
		name.setText("博主：" + bloger.getTitle());
		count.setText("共" + bloger.getPostcount() + "篇博文");

		new DownAvatar(bloger.getAvatar(), handler).start();

		// lv.addFooterView(footer);
		lv.setOnItemClickListener(this);
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

				if ((lastindex == adapter.getCount())
						&& (scrollState == OnScrollListener.SCROLL_STATE_IDLE)) {

					new BlogThread(url + (++page) + "/10", handler, true)
							.start();

				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastindex = firstVisibleItem + visibleItemCount - 1;
			}
		});
		bar = (ProgressBar) findViewById(R.id.zuixin_bar);
		new BlogThread(url + (page) + "/10", handler, false).start();
		refresh.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// AppStatic.articles.clear();
				page = 1;
				BlogThread t = new BlogThread(url + (page) + "/10", handler,
						false);
				t.start();

			}
		}, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, BlogViewAct.class);
		it.putExtra("title", AppStatic.articles.get(position).getTitle());
		it.putExtra("pos", position);
		it.putExtra("url", AppStatic.articles.get(position).getUrl());
		startActivity(it);
	}

}