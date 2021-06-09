package com.example.cnblog.adapter;

import java.text.ParseException;
import java.util.List;

import com.example.cnblog.R;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Comment;
import com.example.cnblog.util.ConvertDate;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	private Context context;
	private List<Comment> comments;
	public CommentAdapter(Context context){
		this.context=context;
		this.comments=AppStatic.comments;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
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
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.comment_list_item, null);
			holder=new ViewHolder();
			holder.name=(TextView) convertView.findViewById(R.id.comments_list_item_authorName);
			holder.time=(TextView) convertView.findViewById(R.id.comments_list_item_updated);
			holder.comm= (WebView) convertView.findViewById(R.id.comments_list_item_content);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.name.setText(comments.get(position).getName());
		//Log.i("msg", comments.get(position).getContent());
		
		try {
			holder.time.setText(ConvertDate.publishedToDate(comments.get(position).getPublish()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.comm.loadDataWithBaseURL(null, comments.get(position).getContent(), "text/html", "utf-8",null);
		return convertView;
	}
	private class ViewHolder{
		TextView time;
		TextView name;
		WebView comm;
	}
}
