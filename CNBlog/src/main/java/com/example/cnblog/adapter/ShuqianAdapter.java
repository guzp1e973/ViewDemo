package com.example.cnblog.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.cnblog.R;
import com.example.cnblog.instance.Article;
import com.example.cnblog.instance.Mark;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ShuqianAdapter extends BaseAdapter {
	private Context context;
	private List<Mark> list;
	
	public ShuqianAdapter(Context context,List<Mark> list){
		this.context=context;
		this.list=list;
		
	}
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
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.shuqian_list_item, null);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.markers_title);
			holder.url=(TextView) convertView.findViewById(R.id.markers_link);
			holder.check=(CheckBox) convertView.findViewById(R.id.check_button);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.title.setText(list.get(position).getTitle());
		holder.url.setText(list.get(position).getUrl());
		holder.check.setChecked(list.get(position).isCheck());
		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				list.get(position).setCheck(isChecked);
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
		TextView title;
		TextView url;
		CheckBox check;
		
	}
}
