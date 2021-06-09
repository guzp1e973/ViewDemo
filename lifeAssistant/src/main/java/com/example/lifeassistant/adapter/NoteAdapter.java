package com.example.lifeassistant.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.bean.NotePad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	private Context context;
	private List<NotePad> list;

	public NoteAdapter(Context context, List<NotePad> list) {
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
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getmID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,R.layout.notepadactivity_item, null);
			holder.mTVTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.mTVBody = (TextView) convertView.findViewById(R.id.tvBody);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(list.get(position).getmTitle().toString());
			sdf.applyPattern("MM月dd日");
			holder.mTVTitle.setText(sdf.format(d) + ",");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.mTVBody.setText(list.get(position).getmBody().toString());

		return convertView;
	}

	public void addItem(List<NotePad> nodeList) {
		this.list = nodeList;
	}

	static class ViewHolder {
		TextView mTVTitle, mTVBody;

	}
}
