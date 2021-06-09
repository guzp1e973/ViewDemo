package com.example.cnblog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.example.cnblog.database.SqlArticlesHelper;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Bloger;
import com.example.cnblog.util.BlogViewThread;
import com.example.cnblog.util.BlogerThread;
import com.example.cnblog.util.DownAvatar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ZoomControls;

public class BlogViewAct extends Activity implements OnClickListener {
	private WebView web;
	private TextView tv;
	private TextView bloger;
	private TextView blogscount;
	private TextView blogslist;
	private RelativeLayout rl;
	private RelativeLayout rlb;
	private ImageView comment;
	private ImageView to_top;
	private ImageView save_blog;
	private ImageView man;
	private ImageView touxiang;
	private String blogApp;
	private String url;
	private String title;
	private SqlArticlesHelper dbHelper;
	private int pos;
	private Bloger blogeobj;
	private String avatar;
	public static Bitmap bit;
	private String text;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				rl.setVisibility(View.GONE);
				break;
			case 3:
				rlb.setVisibility(View.GONE);
				rl.setVisibility(View.GONE);
				break;
			case 2:
				text = (String) msg.obj;
				web.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
				
				break;
			case 4:
				blogeobj=(Bloger) msg.obj;
				
				blogApp=blogeobj.getBlogapp();
				AppStatic.articles.get(pos).setBlogApp(blogApp);
				blogscount.setText("博客："+blogeobj.getPostcount()+"篇");
				break;
			case 5:
				touxiang.setImageBitmap(bit);
			default:
				break;
			}

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.blogwebact);
		dbHelper = new SqlArticlesHelper(this, "cnblog.db");
		web = (WebView) findViewById(R.id.web);
		tv = (TextView) findViewById(R.id.web_tv);
		bloger=(TextView) findViewById(R.id.panel_bloger_name);
		blogscount=(TextView) findViewById(R.id.panel_blog_num);
		blogslist=(TextView) findViewById(R.id.panel_blog_list);
		touxiang = (ImageView) findViewById(R.id.panel_bloger_img);
		comment = (ImageView) findViewById(R.id.blog_comment);
		to_top = (ImageView) findViewById(R.id.to_top);
		man = (ImageView) findViewById(R.id.man);
		save_blog = (ImageView) findViewById(R.id.save_blog);
		comment.setOnClickListener(this);
		to_top.setOnClickListener(this);
		save_blog.setOnClickListener(this);
		blogslist.setOnClickListener(this);
		man.setOnClickListener(this);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(true);
		web.getSettings().setBuiltInZoomControls(true);
		web.clearCache(true);
		rl = (RelativeLayout) findViewById(R.id.right_panel);
		rlb = (RelativeLayout) findViewById(R.id.bloger_panel);
		Intent it = getIntent();
		pos = it.getIntExtra("pos", -1);
		url = it.getStringExtra("url");
		title = it.getStringExtra("title");
		tv.setText(title);
		avatar=AppStatic.articles.get(pos).getAvatar();
		new DownAvatar(avatar, handler).start();
		String name=AppStatic.articles.get(pos).getSourceName();
		bloger.setText("博主："+name);
		String n = null;
		try {
			n = "http://wcf.open.cnblogs.com/blog/bloggers/search?t="+URLEncoder.encode(name,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppStatic.articles.get(pos).setAuthorurl(n);
		new BlogViewThread(url, handler).start();
		new BlogerThread(n,handler).start();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
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
			String id= url.substring(url.lastIndexOf("/"));
			it.putExtra("url", "http://wcf.open.cnblogs.com/blog/post/"+id);
			it.putExtra("title", title);
			startActivity(it);
			break;
		case R.id.to_top:
			web.scrollTo(0, 0);
			break;
		case R.id.man:
			rlb.setVisibility(View.VISIBLE);
			
			Message msg = handler.obtainMessage(3);
			handler.removeMessages(3);
			handler.sendMessageDelayed(msg, 3000);
			
			break;
		case R.id.save_blog:
			

			if (dbHelper.insertBlog(pos,text)!=-1) {

				
				Toast.makeText(this, "储存成功", Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(this, "内容已存在", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.panel_blog_list:
			Intent intent=new Intent(this,BlogerAct.class);
			intent.putExtra("url","http://wcf.open.cnblogs.com/blog/u/"+blogApp+"/posts/" );
			startActivity(intent);
			
			break;
		default:
			break;
		}
	}

}
