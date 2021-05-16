package com.yeepay.myrefresh;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SwipeRefreshLayout refreshView;
	private ListView listView;
	private ListView leftlist;
	private List<Integer> data;
	private DoubleAdapter adapter;
	private ArrayAdapter<Integer> leftadapter;
	private Button but;
	private DrawerLayout draw;
	private int lastindex;
	private View foot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<Integer>();
		changeData();
		initView();

		refreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		draw.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				if (drawerView.equals(leftlist))
					but.setText("ClOSE");
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				if (drawerView.equals(leftlist))
					but.setText("SHOW");
			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}
		});
		but.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (but.getText().equals("SHOW")) {
					draw.openDrawer(leftlist);
				} else {
					draw.closeDrawer(Gravity.START);
				}
			}
		});

		adapter = new DoubleAdapter(this, data);
		leftadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, data);
		leftlist.setAdapter(leftadapter);
		foot = View.inflate(this, R.layout.footeract, null);
		foot.setVisibility(View.GONE);
		listView.addFooterView(foot);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
			                        int position, long id) {
				//ComponentName cn = new ComponentName("com.example.demo","com.example.demo.MainActivity");
				Intent it = new Intent();
				it.putExtra("data", data.get(position));
				it.setClassName("com.example.demo",
						"com.example.demo.MainActivity");
				// it.setComponent(cn);
				if (getPackageManager().resolveActivity(it, 0) == null) {
					// 说明系统中不存在这个activity
					Toast.makeText(MainActivity.this,"未安装插件，请上www.xxx.com",Toast.LENGTH_SHORT).show();
					return;
				} else {
					startActivity(it);
				}

			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				//ssssssaassssaaazzzzzzaaaasdasdas
				Log.i("msg", lastindex + "sss--sss" + adapter.getCount());

				if ((lastindex >= adapter.getCount())
						&& (scrollState == OnScrollListener.SCROLL_STATE_IDLE)) {
					foot.setVisibility(View.VISIBLE);
					changeData();
					adapter.notifyDataSetChanged();
					Log.i("msg", "onscroll");
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			                     int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastindex = firstVisibleItem + visibleItemCount;
			}
		});
		refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {//运行在主线程中

				Log.i("msg","11");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Log.i("msg","22");
						for (int i = 0; i < data.size(); i++) {
							data.set(i, data.get(i) * 2);
						}
						adapter.notifyDataSetChanged();
						refreshView.setRefreshing(false);
					}
				},3000);

			}


		});
	}

	private void initView() {
		refreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
		listView = (ListView) findViewById(R.id.listview);
		leftlist = (ListView) findViewById(R.id.left_list);
		draw = (DrawerLayout) findViewById(R.id.draw_layout);
		but = (Button) findViewById(R.id.but);
	}

	private void changeData() {
		for (int i = 0; i < 10; i++) {
			data.add(i + 1);
		}
	}

}
