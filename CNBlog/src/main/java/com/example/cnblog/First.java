package com.example.cnblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class First extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.splash);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent it=new Intent(First.this,MainActivity.class);
				startActivity(it);
				First.this.finish();
			};
		}.start();
	}
}
