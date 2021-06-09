package com.example.cnblog;

import com.example.cnblog.database.SqlArticlesHelper;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.News;
import com.example.cnblog.util.WebViewThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewAct extends Activity implements OnClickListener {
	private WebView web;
	private TextView tv;
	private RelativeLayout rl;
	private ImageView comment;
	private ImageView to_top;
	private ImageView save_blog;
	private String url;
	private String title;
	private SqlArticlesHelper dbHelper;
	private int pos;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				rl.setVisibility(View.GONE);
				break;
			case 2:
				String s = ((News) msg.obj).getContent();
				web.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);

				break;

			default:
				break;
			}

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.webact);
		dbHelper = new SqlArticlesHelper(this, "cnblog.db");
		web = (WebView) findViewById(R.id.web);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(true);
		web.getSettings().setBuiltInZoomControls(true);
		web.clearCache(true);
		tv = (TextView) findViewById(R.id.web_tv);
		comment = (ImageView) findViewById(R.id.blog_comment);
		to_top = (ImageView) findViewById(R.id.to_top);
		save_blog = (ImageView) findViewById(R.id.save_blog);
		comment.setOnClickListener(this);
		to_top.setOnClickListener(this);
		save_blog.setOnClickListener(this);
		rl = (RelativeLayout) findViewById(R.id.right_panel);
		Intent it = getIntent();
		pos = it.getIntExtra("pos", -1);
		url = it.getStringExtra("url");
		title = it.getStringExtra("title");
		tv.setText(title);

		new WebViewThread(url, handler).start();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		rl.setVisibility(View.VISIBLE);
		Message msg = handler.obtainMessage(1);
		handler.removeMessages(1);
		handler.sendMessageDelayed(msg, 3000);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.blog_comment:
			Intent it = new Intent(this, NewsComment.class);
			it.putExtra("url", url);
			it.putExtra("title", title);
			startActivity(it);
			break;
		case R.id.to_top:
			web.scrollTo(0, 0);
			break;
		case R.id.save_blog:
			// Log.i("msg",
			// dbHelper.queryById(AppStatic.articles.get(pos).getId()).getCount()+"");

			if ((pos != -1)
					&&(dbHelper.queryById(AppStatic.articles.get(pos).getId())
							.getCount() == 0)) {

				dbHelper.insertNews(pos);
				Toast.makeText(this, "储存成功", Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(this, "内容已存在", Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}
	}

}
