package com.example.allviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
	private ListView left_list;
	private SwipeRefreshLayout refresh;
	private RecyclerView recycler;
	private Button but;
	private DrawerLayout draw_layout;
	private SlidingPaneLayout slidingpanel;
	private List<Integer> data;
	private RecyclerAdapter adapter;
	private Button but_slid;
	private LinearLayoutManager manager;
	private int lastVisibleItem;
	private String title;
	private ArrayAdapter listadapter;

	@Override
	protected void onResume() {
		super.onResume();
		int pos = RecyclerAdapter.position;

		if (pos != -1) {
			Log.i("msg", getIntent().getIntExtra("data", -1) + "aa");
			data.set(pos, getIntent().getIntExtra("data", data.get(pos)));
			notifyDataChanged();

		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		filldata();

		manager = new LinearLayoutManager(this);
		//manager.setOrientation(LinearLayoutManager.HORIZONTAL);


		recycler.setLayoutManager(manager);
		recycler.setAdapter(adapter);
		recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				int totalItemCount = manager.getItemCount();
				if (lastVisibleItem >= totalItemCount - 1 && newState == 0) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							filldata();
							notifyDataChanged();
							refresh.setRefreshing(false);
						}
					}, 2000);

				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = manager.findLastVisibleItemPosition();
			}
		});
		refresh.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < data.size(); i++) {
							data.set(i, data.get(i) * 2);
						}
						notifyDataChanged();
						refresh.setRefreshing(false);
					}
				}, 2000);
			}
		});
		left_list.setAdapter(listadapter);
		left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				MainActivity.this.setTitle(title + data.get(position));
			}
		});
		slidingpanel.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
			@Override
			public void onPanelSlide(View view, float v) {
				//but.setText("MOVING:"+v);
			}

			@Override
			public void onPanelOpened(View view) {
				but.setText("CLOSE");
			}

			@Override
			public void onPanelClosed(View view) {
				but.setText("SHOW");
			}
		});
		but.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("SHOW".equals(but.getText())) {
					slidingpanel.openPane();
					but.setText("CLOSE");
				} else {
					slidingpanel.closePane();
					but.setText("SHOW");
				}
			}
		});
		draw_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View view, float v) {
				but_slid.setText("MOVING:" + v);
			}

			@Override
			public void onDrawerOpened(View view) {
				but_slid.setText("CLOSE");
			}

			@Override
			public void onDrawerClosed(View view) {
				but_slid.setText("SHOW");
			}

			@Override
			public void onDrawerStateChanged(int i) {

			}
		});
		but_slid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("SHOW".equals(but_slid.getText())) {
					draw_layout.openDrawer(Gravity.END);
					but_slid.setText("CLOSE");
				} else {
					draw_layout.closeDrawer(Gravity.END);
					but_slid.setText("SHOW");
				}
			}
		});
	}

	private void initView() {
		left_list = (ListView) findViewById(R.id.left_list);
		refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
		recycler = (RecyclerView) findViewById(R.id.recycler);
		but = (Button) findViewById(R.id.but);
		draw_layout = (DrawerLayout) findViewById(R.id.draw_layout);
		slidingpanel = (SlidingPaneLayout) findViewById(R.id.slidingpanel);
		data = new ArrayList<Integer>();
		adapter = new RecyclerAdapter(data);
		listadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, data);
		but_slid = (Button) findViewById(R.id.but_slid);
		title = MainActivity.this.getTitle().toString();
	}

	private void notifyDataChanged() {
		adapter.notifyDataSetChanged();
		listadapter.notifyDataSetChanged();
	}

	void filldata() {
		for (int i = 0; i < 15; i++) {
			data.add(i + 1);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (slidingpanel.isOpen()) {
				slidingpanel.closePane();
			} else {
				slidingpanel.openPane();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//Log.i("msg","menu");
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
