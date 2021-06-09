package com.example.cnblog.adapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import com.example.cnblog.BlogViewAct;
import com.example.cnblog.BlogerAct;
import com.example.cnblog.OfflineWebView;
import com.example.cnblog.R;
import com.example.cnblog.database.SqlArticlesHelper;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.BlogOff;
import com.example.cnblog.instance.Bloger;
import com.example.cnblog.util.BlogerThread;
import com.example.cnblog.util.ConvertDate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OffLineAdapter extends BaseAdapter {
	private Context context;
	private List<BlogOff> list;
	private Intent it;
	private SqlArticlesHelper helper;
	public OffLineAdapter(Context context,List<BlogOff> list){
		this.context=context;
		this.list=list;
	}
	public Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			context.startActivity(it);
		};
	};
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.offline_item, null);
			holder=new ViewHolder();
			holder.blog_title=(TextView) convertView.findViewById(R.id.blog_title);
			holder.blog_bloger=(TextView) convertView.findViewById(R.id.blog_bloger);
			holder.blog_uptime=(TextView) convertView.findViewById(R.id.blog_uptime);
			holder.blog_storetime=(TextView) convertView.findViewById(R.id.blog_storetime);
			holder.blog_summary=(TextView) convertView.findViewById(R.id.blog_summary);
			holder.blogImg=(ImageView) convertView.findViewById(R.id.blogImg);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.blog_title.setText(list.get(position).getTitle());
		holder.blog_bloger.setText("博主："+list.get(position).getBloger());
		String s="获取失败";
		try {
			s=ConvertDate.updateToDate(list.get(position).getUpdateTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.blog_uptime.setText("更新时间："+s);
		s=ConvertDate.CurrentTimeToDate(list.get(position).getStoreTime());
		holder.blog_storetime.setText("存储时间："+s);
		holder.blog_summary.setText(list.get(position).getSummary());
		holder.blogImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog=new AlertDialog.Builder(context);
				dialog.setTitle("请选择您要的操作：");
				String data[]={"查看","跳转至网页查看","查看博主其他博文","删除此项","全部删除","返回"};
				ListAdapter adapter=new ArrayAdapter<String>(context, R.layout.offline_dialog, data);
				dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							it=new Intent(context,OfflineWebView.class);
							it.putExtra("id", list.get(position).getBlogId());
							context.startActivity(it);
							break;
						case 1:
							it=new Intent(context,BlogViewAct.class);
							it.putExtra("title",list.get(position).getTitle());
							it.putExtra("pos", position);
							it.putExtra("url", list.get(position).getBlogLink());
							context.startActivity(it);
							break;
						case 2:
							
							String n = list.get(position).getBloger();
							try {
								n = "http://wcf.open.cnblogs.com/blog/bloggers/search?t="+URLEncoder.encode(n,"utf-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							new BlogerThread(n,handler).start();
							String blogApp=list.get(position).getBlogApp();
							it=new Intent(context,BlogerAct.class);
							it.putExtra("url","http://wcf.open.cnblogs.com/blog/u/"+blogApp+"/posts/" );
							
							break;
						case 3:
							helper=new SqlArticlesHelper(context, "cnblog.db");
							helper.deleteBlogbyId(list.get(position).getBlogId());
							list.remove(position);
							OffLineAdapter.this.notifyDataSetChanged();
							break;
						case 4:
							helper=new SqlArticlesHelper(context, "cnblog.db");
							helper.deleteAllBlog();
							list.clear();
							OffLineAdapter.this.notifyDataSetChanged();
							break;
						case 5:
							dialog.dismiss();
							break;

						default:
							break;
						}
					}
				});
				dialog.show();
			}
			
		});
		
		if(position%2==0){
			convertView.setBackgroundColor(0xffCDDDFA);
		}else{
			convertView.setBackgroundColor(0xffF5FFFA);
		}
		return convertView;
	}
	private class ViewHolder{
		TextView blog_title;
		TextView blog_bloger;
		TextView blog_uptime;
		TextView blog_storetime;
		TextView blog_summary;
		ImageView blogImg;
		
	}
	private class DialogAdapter extends BaseAdapter{
		private String data[]={"查看","跳转至网页查看"};
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v=View.inflate(context, R.layout.offline_dialog, null);
			TextView t=(TextView) v.findViewById(R.id.dia_item);
			t.setText(data[position]);
			return v;
		}
		
	}

}
