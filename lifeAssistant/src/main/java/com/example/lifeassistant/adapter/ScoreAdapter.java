package com.example.lifeassistant.adapter;

import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.WeatherAdapter.ViewHolder;
import com.example.lifeassistant.bean.Course;
import com.example.lifeassistant.bean.NotePad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter {
	private Context context;
	private List<Course> list;

	public ScoreAdapter(Context context, List<Course> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,R.layout.score_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.s_name);
			holder.score = (TextView) convertView.findViewById(R.id.s_score);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		if (list.get(position).getScore()<60) {
			holder.name.setTextColor(0xffff0000);
			holder.score.setTextColor(0xffff0000);
		}
		holder.score.setText(list.get(position).getScore()+"");
		return convertView;
	}
	static class ViewHolder {
		TextView name, score;

	}
}
