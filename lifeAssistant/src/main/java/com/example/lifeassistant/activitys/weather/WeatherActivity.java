package com.example.lifeassistant.activitys.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.WeatherAdapter;
import com.example.lifeassistant.bean.DayWeather;
import com.example.lifeassistant.bean.Weather;
import com.example.lifeassistant.util.CheckNetWork;
import com.example.lifeassistant.util.Constants;
import com.example.lifeassistant.util.DownWeather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener {
	private EditText edit_search;
	private Button btnYes;
	private ImageView img_weather;
	private TextView text_taday_city, text_taday_weather, text_weather_details,
			text_taday_wet, text_clody_leaver, text_clody_month;
	private ListView weather_list;
	private WeatherAdapter adapter;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Weather weather = (Weather) msg.obj;
				List<DayWeather> evday = weather.getEvday();
				// Log.i("msg", "sssa"+weather.getEvday().size());
				adapter = new WeatherAdapter(WeatherActivity.this, evday);
				proDialog.dismiss();
				text_taday_city.setText(weather.getCity());
				text_taday_weather.setText(weather.getTemp());
				text_weather_details.setText(weather.getWeather());
				text_taday_wet.setText(weather.getIndex());
				text_clody_leaver.setText("风力：" + weather.getWind1());
				text_clody_month.setText(weather.getDate_y());
				weather_list.setAdapter(adapter);
				Constants.setWeatherImg(img_weather, weather.getWeather());
			}

			if (msg.what == 0) {
				String code = (String) msg.obj;
				if (!code.equals("")) {
					SharedPreferences share = WeatherActivity.this
							.getSharedPreferences("city", Activity.MODE_PRIVATE);
					SharedPreferences.Editor edit = share.edit();
					edit.putString("city", code);
					edit.commit();
					edit_search.setText("");
					getWeather(code);
				} else {
					Toast.makeText(WeatherActivity.this, "输入城市无效!",
							Toast.LENGTH_SHORT).show();
				}

			}
		};
	};
	private ProgressDialog proDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// http://m.weather.com.cn/atad/101010100.html
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_layout);
		init();

		SharedPreferences share = getSharedPreferences("city",
				Activity.MODE_PRIVATE);
		String url = share.getString("city", "");
		if (url.equals("")) {
			Toast.makeText(WeatherActivity.this, "请输入您所在的城市",
					Toast.LENGTH_SHORT).show();
		} else {
			getWeather(url);
		}

	}

	private void getWeather(String url) {
		// TODO Auto-generated method stub

		showProgressDialog();
		CheckNetWork chknet = new CheckNetWork();
		boolean netinfo = chknet.isNetWorkConnected(WeatherActivity.this);
		if (netinfo == true) {
			Toast.makeText(WeatherActivity.this, "网络正常，正在解析...",
					Toast.LENGTH_SHORT).show();
			new DownWeather(handler, url).start();
		} else {
			Toast.makeText(WeatherActivity.this, "请检查您的网络状态",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void init() {
		edit_search = (EditText) findViewById(R.id.editw_search);
		btnYes = (Button) findViewById(R.id.btnYes);
		img_weather = (ImageView) findViewById(R.id.img_weather);
		text_taday_city = (TextView) findViewById(R.id.text_taday_city);
		text_taday_weather = (TextView) findViewById(R.id.text_taday_weather);
		text_weather_details = (TextView) findViewById(R.id.text_weather_details);
		text_taday_wet = (TextView) findViewById(R.id.text_taday_wet);
		text_clody_leaver = (TextView) findViewById(R.id.text_clody_leaver);
		text_clody_month = (TextView) findViewById(R.id.text_clody_month);
		weather_list = (ListView) findViewById(R.id.weather_list);
		btnYes.setOnClickListener(this);
	}

	public void showProgressDialog() {
		proDialog = new ProgressDialog(WeatherActivity.this);
		proDialog.setTitle("天气正在加载中......");
		proDialog.setMessage("请稍等...");
		proDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnYes:
			praseCitys();

			break;
		default:
			break;
		}

	}

	private void praseCitys() {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String s = edit_search.getText().toString();
				String code = "";
				XmlPullParser xpp = Xml.newPullParser();
				try {
					xpp.setInput(getAssets().open("citylist.xml"), "utf-8");
					int event = xpp.getEventType();
					boolean flag = false;
					while (event != XmlPullParser.END_DOCUMENT
							&& code.equals("")) {
						switch (event) {
						case XmlPullParser.START_DOCUMENT:

							break;
						case XmlPullParser.START_TAG:
							if ("name".equals(xpp.getName())) {

								if (xpp.nextText().equals(s)) {
									flag = true;
								} else {
									flag = false;
								}

							}
							if ("code".equals(xpp.getName()) && flag == true) {
								code = xpp.nextText();
							}
							break;
						case XmlPullParser.END_TAG:

							break;

						default:
							break;
						}
						event = xpp.next();
					}
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				msg.what = 0;
				msg.obj = code;
				handler.sendMessage(msg);

				super.run();
			}
		}.start();
	}
}
