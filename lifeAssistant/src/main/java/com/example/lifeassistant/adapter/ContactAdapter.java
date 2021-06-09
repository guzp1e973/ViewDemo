package com.example.lifeassistant.adapter;

import java.util.ArrayList;

import com.example.lifeassistant.R;
import com.example.lifeassistant.bean.ContactsInfo;
import com.example.lifeassistant.util.ContactsBiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	ArrayList<ContactsInfo> infos;
	LayoutInflater inflater;
	ContactsBiz biz;

	public void setInfos(ArrayList<ContactsInfo> infos) {
		if (infos != null) {
			this.infos = infos;
		} else {
			this.infos = new ArrayList<ContactsInfo>();
		}
	}

	public ContactAdapter(Context context, ArrayList<ContactsInfo> infos) {
		this.setInfos(infos);
		this.inflater = LayoutInflater.from(context);
		this.biz = new ContactsBiz(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return infos.get(position).getId();
	}

	static class ViewHolder {
		private ImageView ivHead;
		private TextView tvname;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_contact, null);
			holder = new ViewHolder();
			holder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
			holder.tvname = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ContactsInfo info = infos.get(position);
		holder.tvname.setText(info.getName());
		// 设置头像
		Bitmap bm = biz.getPhoto(info.getId());
		if (bm == null) {// 如果查询到的联系人头像为空 此处可以添加一个默认头像
			holder.ivHead.setImageResource(R.drawable.default_inco);
		} else {// 如果头像存在 那就讲bm作为该联系人的头像
			holder.ivHead.setImageBitmap(bm);
		}
		return convertView;
	}

}
