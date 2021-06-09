package com.example.lifeassistant.activitys.contacts;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.lifeassistant.R;
import com.example.lifeassistant.bean.ContactsInfo;
import com.example.lifeassistant.util.Constants;
import com.example.lifeassistant.util.ContactsBiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ContactsDetailsActivity extends Activity {
	ContactsInfo contact;
	ContactsBiz biz;
	ImageView ivhead;//头像
	TextView tvName;
	ListView lvDetails;
	Button call_contact;
	ArrayList<HashMap<String, Object>> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);
		biz = new ContactsBiz(ContactsDetailsActivity.this);
		contact = (ContactsInfo) getIntent().getSerializableExtra("contact");
		setUpView();
		call_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = (String) data.get(0).get(
						"data");
				Log.i("msg", number);
				Uri uri = Uri.parse("tel:" + number);
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				startActivity(intent);
			}
		});
	}
	
	public void setUpView(){
		call_contact=(Button) findViewById(R.id.call_contact);
		ivhead = (ImageView) findViewById(R.id.ivHead_Details);
		
		Bitmap bm = biz.getPhoto(contact.getId());
		if (bm==null) 
			ivhead.setImageResource(R.drawable.default_inco);
		else 
			ivhead.setImageBitmap(bm);
		
		tvName = (TextView) findViewById(R.id.tvName_Details);
		tvName.setText(contact.getName());
		
		lvDetails = (ListView) findViewById(R.id.lvDetails);
		data = new ArrayList<HashMap<String,Object>>();
		//取出电话信息 加入集合
		ArrayList<HashMap<String, Object>> phones = biz.getPhoes(contact.getId());
		if (phones!=null) {
			data.addAll(phones);
		}
		
		//取出邮箱信息 加入集合
		ArrayList<HashMap<String, Object>> emails = biz.getEmails(contact.getId());
		if (emails!=null) {
			data.addAll(emails);
		}
		SimpleAdapter adapter=  new SimpleAdapter(ContactsDetailsActivity.this, data, R.layout.details_layout_item, new String[]{"type","data"}, new int[]{R.id.tvType,R.id.tvData});
		lvDetails.setAdapter(adapter);
	}
}
