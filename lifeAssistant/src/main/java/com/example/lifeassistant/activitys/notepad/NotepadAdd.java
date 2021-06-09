package com.example.lifeassistant.activitys.notepad;

import java.text.SimpleDateFormat;
import java.util.Date;




import com.example.lifeassistant.R;
import com.example.lifeassistant.database.NotePadUpdate;
import com.example.lifeassistant.util.Constants;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NotepadAdd extends Activity implements OnClickListener {
	private Button back;
	private Button save;
	private EditText body;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notepadadd);
		back=(Button) findViewById(R.id.btn_no_save);
		save=(Button) findViewById(R.id.btn_save);
		body=(EditText) findViewById(R.id.edt_body);
		save.setOnClickListener(this);
		body.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_no_save:
			finish();
			break;

		case R.id.btn_save:
			//标题 当前时间
			//正文 body
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			String mTitle = sdf.format(d);
			String mBody = body.getText().toString();
			ContentValues values = new ContentValues();
			values.put("title", mTitle);
			values.put("body", mBody);
			
			NotePadUpdate up = new NotePadUpdate(this);
			up.saveDate(values,null);
			setResult(Constants.NOTEPAD_ADD_RESULT);
			
			finish();
			break;
		}
	}
}
