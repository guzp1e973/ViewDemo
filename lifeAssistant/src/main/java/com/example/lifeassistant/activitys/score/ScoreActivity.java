package com.example.lifeassistant.activitys.score;

import java.util.ArrayList;
import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.activitys.weather.WeatherActivity;
import com.example.lifeassistant.adapter.ScoreAdapter;
import com.example.lifeassistant.bean.Course;
import com.example.lifeassistant.util.Constants;
import com.example.lifeassistant.util.ScoreThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity implements OnClickListener {
	private EditText edit_search;
	private Button btnYes;
	private ListView scorelist;
	private ScoreAdapter adapter;
	private Spinner sp_year, sp_xueqi;
	private TextView score_name,score_count;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				List<Course> courses = (List<Course>) msg.obj;
				score_name.setText("姓名："+Constants.name);
				score_count.setText("课程数量："+courses.size());
				adapter = new ScoreAdapter(ScoreActivity.this, courses);
				proDialog.dismiss();
				scorelist.setAdapter(adapter);
			}else if(msg.what == 1){
				proDialog.dismiss();
				Toast.makeText(ScoreActivity.this, "服务器繁忙，请稍后重试。。", Toast.LENGTH_SHORT).show();;
			}

		};
	};
	private ProgressDialog proDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_layout);
		init();
		SharedPreferences share = getSharedPreferences("num",
				Activity.MODE_PRIVATE);
		edit_search.setText(share.getString("number", ""));
		
	}

	public void showProgressDialog() {
		proDialog = new ProgressDialog(ScoreActivity.this);
		proDialog.setTitle("正在查询成绩......");
		proDialog.setMessage("请稍等...");
		proDialog.show();
	}

	private void init() {
		// TODO Auto-generated method stub
		edit_search = (EditText) findViewById(R.id.edit_search);
		btnYes = (Button) findViewById(R.id.btnYes);
		scorelist = (ListView) findViewById(R.id.score_list);
		sp_xueqi = (Spinner) findViewById(R.id.sp_xueqi);
		sp_year = (Spinner) findViewById(R.id.sp_year);
		btnYes.setOnClickListener(this);
		score_name=(TextView) findViewById(R.id.score_name);
		score_count=(TextView) findViewById(R.id.score_count);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnYes) {
			String num = edit_search.getText().toString();// 111304011079
			SharedPreferences share = ScoreActivity.this
					.getSharedPreferences("num", Activity.MODE_PRIVATE);
			SharedPreferences.Editor edit = share.edit();
			edit.putString("number", num);
			edit.commit();
			String xuenian_s = sp_year.getSelectedItem().toString();
			String xueqi_s = sp_xueqi.getSelectedItem().toString();
			String url = "http://liren.3is3.com/number/" + xuenian_s + "/"
					+ xueqi_s + "/" + num;
			showProgressDialog();
			new ScoreThread(handler, url).start();
			;
		}
	}
}
