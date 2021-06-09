package com.example.cnblog.adapter;

import java.text.ParseException;
import java.util.List;

import com.example.cnblog.R;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;
import com.example.cnblog.util.ConvertDate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RemenAdapter extends BaseAdapter{
	private List<Article> articles;
	private Context context;
	public RemenAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		articles=AppStatic.articles;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return articles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			View view=View.inflate(context, R.layout.remen_list_item, null);
			convertView=view;
			holder.title=(TextView) view.findViewById(R.id.blog_list_item_title);
			holder. author =(TextView) view.findViewById(R.id.blog_list_item_authorname);
			holder. summary=(TextView) view.findViewById(R.id.blog_list_item_summary);
			holder. updated=(TextView) view.findViewById(R.id.blog_list_item_updated);
			holder. v_num=(TextView) view.findViewById(R.id.blog_list_item_views_num);
			holder. c_num=(TextView) view.findViewById(R.id.blog_list_item_comments_num);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(articles.get(position).getTitle());
		holder.author.setText(articles.get(position).getSourceName());
		holder.summary.setText(articles.get(position).getSummary());
		
		try {
			holder.updated.setText(ConvertDate.updateToDate(articles.get(position).getUpdated()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.v_num.setText(articles.get(position).getViews());
		holder.c_num.setText(articles.get(position).getComments());
		if(position%2==0){
			convertView.setBackgroundColor(0xffCDDDFA);
		}else{
			convertView.setBackgroundColor(0xffffffff);
		}
		return convertView;
	}
	private class ViewHolder{
		TextView title;
		TextView author ;
		TextView summary;
		TextView updated;
		TextView v_num;
		TextView c_num;
	}
}
