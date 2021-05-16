package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (EditText) findViewById(R.id.tx);
        tv.setText(getIntent().getIntExtra("data", -1)+"");
	    Button but= (Button) findViewById(R.id.but);
	    but.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Intent it=new Intent();
			    it.setClassName("com.example.allviewdemo","com.example.allviewdemo.MainActivity");
			    try {
				    it.putExtra("data",Integer.parseInt(tv.getText().toString()));
			    }catch (Exception e){}

			    startActivity(it);
			    MainActivity.this.finish();
		    }
	    });
    }

}
