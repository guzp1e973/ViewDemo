package com.example.lifeassistant.activitys;

import com.example.lifeassistant.R;
import com.example.lifeassistant.R.id;
import com.example.lifeassistant.R.layout;
import com.example.lifeassistant.activitys.contacts.ContactsAct;
import com.example.lifeassistant.activitys.contacts.ContactsPager;
import com.example.lifeassistant.activitys.notepad.NotePadActivity;
import com.example.lifeassistant.activitys.score.ScoreActivity;
import com.example.lifeassistant.activitys.weather.WeatherActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.m_about:
		case R.id.about:
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.m_notepad:
		case R.id.notepad:
			Intent it = new Intent(MainActivity.this, NotePadActivity.class);
			startActivity(it);
			break;
		case R.id.m_weather:
		case R.id.weather:
			Intent it2 = new Intent(MainActivity.this, WeatherActivity.class);
			startActivity(it2);
			break;
		case R.id.m_express:
		case R.id.express:
			Intent ite = new Intent(MainActivity.this, Express.class);
			startActivity(ite);
			break;
		case R.id.m_calculator:
		case R.id.calculator:
			Intent itc = new Intent(MainActivity.this, Caculator.class);
			startActivity(itc);
			break;
		case R.id.m_contacts:
		case R.id.contacts:
			Intent itco = new Intent(MainActivity.this, ContactsPager.class);
			startActivity(itco);
			break;
		case R.id.m_calendar:
		case R.id.calendar:
			Intent itca = new Intent(MainActivity.this, CalendarActivity.class);
			startActivity(itca);
			break;
		}
	}
}
