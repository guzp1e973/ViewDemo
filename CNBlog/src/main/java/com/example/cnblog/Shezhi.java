package com.example.cnblog;

import com.example.cnblog.adapter.SheZhiAdapter;
import com.example.cnblog.instance.AppStatic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.TextView;

public class Shezhi extends Activity implements OnItemClickListener, OnClickListener {
	private GridView grid;
	private CheckBox cBox_huyan;
	private CheckBox cBox_avatar;
	private TextView fankui;
	private TextView guanyu;
	private float bright=0.8f;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.shezhi);
		grid = (GridView) findViewById(R.id.shezhi_gridview);
		grid.setAdapter(new SheZhiAdapter(this));
		grid.setOnItemClickListener(this);
		fankui=(TextView) findViewById(R.id.shezhi_tv_fankui);
		guanyu=(TextView) findViewById(R.id.shezhi_tv_guanyu);
		fankui.setOnClickListener(this);
		guanyu.setOnClickListener(this);
		cBox_huyan=(CheckBox) findViewById(R.id.shezhi_cb_huyan);
		cBox_huyan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					setScreenBrightness(0.5f);
				}else{
					setScreenBrightness(bright);
				}
			}
		});
		cBox_avatar=(CheckBox) findViewById(R.id.shezhi_cb_touxiang);
		cBox_avatar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences share=Shezhi.this.getSharedPreferences("avatar", Activity.MODE_PRIVATE);
				
				SharedPreferences.Editor edit=share.edit();
				edit.putBoolean("ava", isChecked);
				AppStatic.down=isChecked;
				edit.commit();
			}
		});
		SharedPreferences share=Shezhi.this.getSharedPreferences("avatar", Activity.MODE_PRIVATE);
		boolean isava=share.getBoolean("ava", false);
		cBox_avatar.setChecked(isava);
		
	}

	private View check;
	private void setScreenBrightness(float num){
		WindowManager.LayoutParams params=super.getWindow().getAttributes();
		bright=params.screenBrightness;
		params.screenBrightness=num;
		super.getWindow().setAttributes(params);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		SharedPreferences share=super.getSharedPreferences("bg", Activity.MODE_PRIVATE);
		int pos=share.getInt("bg", 0);
		SharedPreferences.Editor edit=share.edit();
		edit.putInt("bg", position);
		edit.commit();
		if(check==null){
			check=(View)parent.getItemAtPosition(pos);
			check.setVisibility(View.INVISIBLE);
		}else{
			check.setVisibility(View.INVISIBLE);
		}
		check=view.findViewById(R.id.yes);
		check.setVisibility(View.VISIBLE);
		Intent it=new Intent("background_changed");
		sendBroadcast(it);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shezhi_tv_fankui:
			AlertDialog dia=new AlertDialog.Builder(this).create();
			dia.setTitle("发送邮件...");
			dia.setMessage("没有应用可执行此操作。");
			dia.show();
			break;
		case R.id.shezhi_tv_guanyu:
			AlertDialog dia2=new AlertDialog.Builder(this).create();
			View view=View.inflate(this, R.layout.about, null);
			dia2.setView(view,0,0,0,0);
			dia2.show();
			break;

		default:
			break;
		}
	}
}
