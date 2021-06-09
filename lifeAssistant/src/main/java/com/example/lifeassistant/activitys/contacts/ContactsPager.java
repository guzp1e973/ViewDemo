package com.example.lifeassistant.activitys.contacts;

import java.util.ArrayList;
import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.ContactAdapter;
import com.example.lifeassistant.adapter.ContactsPagerAdapter;
import com.example.lifeassistant.util.ContactsBiz;

import android.os.Bundle;
import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsPager extends ActivityGroup {
	private ViewPager pager = null;
	private TextView t1, t2, t3;
	private View v1, v2, v3;
	ListView lvContacts;
	ContactsBiz biz;
	ContactAdapter adapter;
	private List<View> views = new ArrayList<View>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_contacts);

		

		pager = (ViewPager) findViewById(R.id.viewpager);
		views.add(getLocalActivityManager().startActivity(
				"a1",
				new Intent(this, Contacts.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		views.add(getLocalActivityManager().startActivity(
				"a2",
				new Intent(this, ContactsAct.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());

		pager.setAdapter(new ContactsPagerAdapter(views));
		
	

	}

}
