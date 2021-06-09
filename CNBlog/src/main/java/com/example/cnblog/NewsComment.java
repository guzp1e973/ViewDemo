package com.example.cnblog;

import com.example.cnblog.adapter.CommentAdapter;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.util.CommentThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class NewsComment extends Activity implements OnClickListener{
	private TextView title;
	private ListView lv;
	private String url;
	private Button  more;
	private String s;
	private int page=1;
	private CommentAdapter adp;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				more.setText("查看更多...");
				if(page==2){
					adp=new CommentAdapter(NewsComment.this);
					lv.setAdapter(adp);
				}else{
					adp.notifyDataSetChanged();
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blogcomment);
		title=(TextView) findViewById(R.id.comments_list_item_title);
		title.setText(getIntent().getStringExtra("title"));
		AppStatic.comments.clear();
		lv=(ListView) findViewById(R.id.comments_listview);
		//lv.setAdapter(new CommentAdapter(this));
		Intent it=getIntent();
		url=it.getStringExtra("url");
		s=url+"/comments/"+(page++)+"/10";
		Log.i("msg", s);
		new CommentThread(s,handler).start();
		View footer=View.inflate(this, R.layout.comment_footer, null);
		more=(Button) footer.findViewById(R.id.more);
		lv.addFooterView(footer);
		more.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		more.setText("正在加载中...");
		s=url+"/comments/"+(page++)+"/10";
		
		new CommentThread(s,handler).start();
	}
}
