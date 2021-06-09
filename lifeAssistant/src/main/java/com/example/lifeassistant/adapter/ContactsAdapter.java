package com.example.lifeassistant.adapter;

import java.util.ArrayList;
import java.util.HashMap;




import com.example.lifeassistant.R;
import com.example.lifeassistant.util.Constants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter {
	ArrayList<HashMap<String, String>> list;
	LayoutInflater inflater;

	public ContactsAdapter(Context context,
			ArrayList<HashMap<String, String>> list) {
		this.list = list;
		this.inflater = LayoutInflater.from(context);

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
		return position;
	}

	static class ViewHolder {
		TextView contactsName;
		TextView contactsNumber;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.contacts_item, null);
			holder.contactsName = (TextView) convertView
					.findViewById(R.id.contacts_name_item);
			holder.contactsNumber = (TextView) convertView
					.findViewById(R.id.contacts_number_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		holder.contactsName.setText(list.get(position).get(
				Constants.C_CONTACTS_NAME));
		holder.contactsNumber.setText(list.get(position).get(
				Constants.C_CONTACTS_NUMBER));
		return convertView;
	}

}
