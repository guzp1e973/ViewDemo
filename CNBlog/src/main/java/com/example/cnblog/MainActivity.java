package com.example.cnblog;

import com.example.cnblog.instance.AppStatic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActivityGroup implements OnClickListener {
	private ScrollView menu;
	private LinearLayout body;
	private TextView title;
	private TextView remen;
	private TextView zuixin;
	private TextView tuijian;
	private TextView suoyou;
	private TextView paihang_48;
	private TextView paihang_10;
	private TextView shuqian;
	private TextView lixian;
	private TextView sousuo;
	private TextView shezhi;
	private TextView exit;
	private float startX;
	private float begin;
	private float endX;
	private float oldY;
	private float newY;
	private boolean isopen = false;

	private long exittime = System.currentTimeMillis();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Change_back change = new Change_back();
		IntentFilter infi = new IntentFilter();
		infi.addAction("background_changed");
		registerReceiver(change, infi);
		init();
		SharedPreferences share=MainActivity.this.getSharedPreferences("avatar", Activity.MODE_PRIVATE);
		AppStatic.down=share.getBoolean("ava", false);
	}

	@SuppressWarnings("deprecation")
	private void init() {
		menu = (ScrollView) findViewById(R.id.meun);
		body = (LinearLayout) findViewById(R.id.body);
		title = (TextView) findViewById(R.id.title);
		remen = (TextView) findViewById(R.id.remen);
		zuixin = (TextView) findViewById(R.id.zuixin);
		tuijian = (TextView) findViewById(R.id.tuijian);
		suoyou = (TextView) findViewById(R.id.suoyou);
		paihang_48 = (TextView) findViewById(R.id.paihang);
		paihang_10 = (TextView) findViewById(R.id.tuijian_10);
		shuqian = (TextView) findViewById(R.id.shuqian);
		lixian = (TextView) findViewById(R.id.lixian);
		sousuo = (TextView) findViewById(R.id.sousuo);
		shezhi = (TextView) findViewById(R.id.shezhi);
		exit = (TextView) findViewById(R.id.exit);
		WindowManager wm = getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams menuparams = (LayoutParams) menu
				.getLayoutParams();
		menuparams.width = (int) (width * 0.8);
		menuparams.leftMargin = (int) (-width * 0.8);
		setBack(menu);
		menu.setLayoutParams(menuparams);
		ViewGroup.LayoutParams bodyParams = body.getLayoutParams();// 区别
		bodyParams.width = width;
		body.setLayoutParams(bodyParams);
		ViewGroup.LayoutParams titleParams = title.getLayoutParams();
		titleParams.width = width;
		title.setLayoutParams(titleParams);
		body.addView(getLocalActivityManager().startActivity("a1",
				new Intent(MainActivity.this, Remen.class)).getDecorView());
		remen.setOnClickListener(this);
		zuixin.setOnClickListener(this);
		tuijian.setOnClickListener(this);
		suoyou.setOnClickListener(this);
		paihang_48.setOnClickListener(this);
		paihang_10.setOnClickListener(this);
		shuqian.setOnClickListener(this);
		lixian.setOnClickListener(this);
		sousuo.setOnClickListener(this);
		shezhi.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	private void setBack(ScrollView menu) {
		// TODO Auto-generated method stub
		SharedPreferences share = super
				.getSharedPreferences("bg", MODE_PRIVATE);
		int pos = share.getInt("bg", 0);
		switch (pos) {
		case 0:
			menu.setBackgroundColor(0xff1F2020);
			break;
		case 1:
			menu.setBackgroundResource(R.raw.left_menu_bg3);
			break;
		case 2:
			menu.setBackgroundResource(R.raw.left_menu_bg4);
			break;
		case 3:
			menu.setBackgroundResource(R.raw.left_menu_bg5);
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		title.setText(((TextView) v).getText());
		body.removeAllViews();
		switch (v.getId()) {
		case R.id.remen:
			body.addView(getLocalActivityManager().startActivity(
					"a1",
					new Intent(MainActivity.this, Remen.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.zuixin:
			body.addView(getLocalActivityManager()
					.startActivity(
							"a1",
							new Intent(MainActivity.this, Zuixin.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("url",
											"http://wcf.open.cnblogs.com/news/recommend/paged/"))
					.getDecorView());
			break;
		case R.id.tuijian:
			body.addView(getLocalActivityManager()
					.startActivity(
							"a1",
							new Intent(MainActivity.this, Zuixin.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("url",
											"http://wcf.open.cnblogs.com/news/recommend/paged/"))
					.getDecorView());
			break;
		case R.id.suoyou:
			body.addView(getLocalActivityManager()
					.startActivity(
							"a1",
							new Intent(MainActivity.this, Suoyou.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("url",
											"http://wcf.open.cnblogs.com/blog/sitehome/paged/"))
					.getDecorView());
			break;
		case R.id.paihang:
			body.addView(getLocalActivityManager()
					.startActivity(
							"a1",
							new Intent(MainActivity.this, Paihang48.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("url",
											"http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/50"))
					.getDecorView());
			break;
		case R.id.tuijian_10:
			body.addView(getLocalActivityManager()
					.startActivity(
							"a1",
							new Intent(MainActivity.this, Paihang48.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("url",
											"http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/50"))
					.getDecorView());
			break;
		case R.id.shuqian:
			body.addView(getLocalActivityManager().startActivity(
					"a1",
					new Intent(MainActivity.this, Shuqian.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.lixian:
			body.addView(getLocalActivityManager().startActivity(
					"a1",
					new Intent(MainActivity.this, OffLine.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.sousuo:
			body.addView(getLocalActivityManager().startActivity(
					"a1",
					new Intent(MainActivity.this, Sousuo.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.shezhi:
			body.addView(getLocalActivityManager().startActivity(
					"a1",
					new Intent(MainActivity.this, Shezhi.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.exit:
			finish();
			break;

		default:
			break;
		}

		isopen = false;
		new MoveMeun().execute();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int dis = 0;

		LinearLayout.LayoutParams lp = (LayoutParams) menu.getLayoutParams();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldY = event.getRawY();
			begin = event.getRawX();
			startX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			newY = event.getRawY();
			float moveX = event.getRawX();
			if (Math.abs(newY - oldY) < 100 && Math.abs(moveX - begin) > 100) {

				dis = (int) (moveX - startX);
				if (dis > 0 && lp.leftMargin < 0) {

					lp.leftMargin += dis;

					if (lp.leftMargin > 0) {
						lp.leftMargin = 0;
					}
					menu.setLayoutParams(lp);
				}
				if (dis < 0 && lp.leftMargin > -lp.width) {

					lp.leftMargin += dis;
					if (lp.leftMargin < -lp.width) {
						lp.leftMargin = -lp.width;
					}
					menu.setLayoutParams(lp);
				}
				startX = moveX;
			}

			break;
		case MotionEvent.ACTION_UP:

			if (lp.leftMargin > -lp.width / 2) {
				// if (event.getRawX() - begin > 150
				// || ((begin - event.getRawX() < 150) && (begin
				// - event.getRawX() > 0))) {
				isopen = true;

				new MoveMeun().execute();
			} else {
				isopen = false;
				new MoveMeun().execute();
			}
			if (Math.abs(event.getRawY() - oldY) < 100
					&& Math.abs(event.getRawX() - begin) > 100) {
				return true;
			}
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {

			if (isopen != true) {
				isopen = true;
				new MoveMeun().execute();
			} else {
				if ((System.currentTimeMillis() - exittime) > 2000) {
					Toast.makeText(MainActivity.this, "再按一次退出",
							Toast.LENGTH_SHORT).show();
					exittime = System.currentTimeMillis();
				} else {
					finish();

				}
			}
			return true;
		}
		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU
				&& event.getAction() == KeyEvent.ACTION_UP) {
			if (isopen != true) {
				isopen = true;
			} else {
				isopen = false;
			}
			new MoveMeun().execute();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	public class MoveMeun extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			LinearLayout.LayoutParams lp = (LayoutParams) menu
					.getLayoutParams();
			int lenth = lp.leftMargin;
			if (isopen == false) {
				while (lenth > -lp.width) {
					lenth -= 20;
					if (lenth < -lp.width) {
						lenth = -lp.width;
					}
					publishProgress(lenth);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				while (lenth < 0) {
					lenth += 20;
					if (lenth > 0) {
						lenth = 0;
					}
					publishProgress(lenth);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			LinearLayout.LayoutParams lp = (LayoutParams) menu
					.getLayoutParams();
			lp.leftMargin = values[0];
			menu.setLayoutParams(lp);

		}
	}

	private class Change_back extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			setBack(menu);
		}

	}
}
