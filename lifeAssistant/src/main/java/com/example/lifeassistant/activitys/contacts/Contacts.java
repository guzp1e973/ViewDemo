package com.example.lifeassistant.activitys.contacts;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.ContactAdapter;
import com.example.lifeassistant.bean.ContactsInfo;
import com.example.lifeassistant.util.ContactsBiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Contacts extends Activity {
	ListView lvContacts;
	ContactsBiz biz;
	ContactAdapter adapter;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				lvContacts.setAdapter(adapter);
				break;

			}

		}

	};
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_act);
		lvContacts = (ListView) findViewById(R.id.lvContacts);
		new MyThread().start();
		//item的点击事件 点击某个人的item进入详情页 在详情页显示该联系人的头像  名字 以及号码列表
		lvContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContactsInfo contact = (ContactsInfo) adapter.getItem(position);
				Intent intent = new Intent(Contacts.this, ContactsDetailsActivity.class);
				intent.putExtra("contact", contact);
				startActivity(intent);
			}
		});
		back=(Button) findViewById(R.id.btn_contacts_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Contacts.this.finish();
			}
		});
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			biz = new ContactsBiz(Contacts.this);
			adapter = new ContactAdapter(Contacts.this, biz.getContacts());
			handler.sendEmptyMessage(0);
		}
	}

}
