package com.example.lifeassistant.activitys.notepad;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.lifeassistant.R;
import com.example.lifeassistant.database.NotePadUpdate;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditNotePadActivity extends Activity implements OnClickListener{
	private ImageView img_return,edit,save;
	private TextView tv_time;
	private EditText tv_content;
	private int mId;
	private String something;
	private String currentdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editnotepad);
		img_return=(ImageView) findViewById(R.id.img_return);
		edit=(ImageView) findViewById(R.id.edit);
		save=(ImageView) findViewById(R.id.save);
		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_content=(EditText) findViewById(R.id.tv_content);
		img_return.setOnClickListener(this);
		edit.setOnClickListener(this);
		save.setOnClickListener(this);
		
		tv_time.setText(getIntent().getStringExtra("title"));
		tv_content.setText(getIntent().getStringExtra("body"));
		mId=getIntent().getIntExtra("mid", -1);
		tv_content.setEnabled(false);
		
/*		进入到该Activity先得到刚才intent中的值 包括标题 以及正文
		1、点击的是返回  不作任何事情 关闭自己 直接返回到列表中
		2、如果点了编辑图片
			弹出软键盘 进行编辑
		3、如果点了保存图片
			隐藏掉软键盘
			执行数据库的更新
			关闭自己
			给列表一个返回值*/
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_return:
			this.finish();
			break;
		case R.id.save:
			NotePadUpdate notepadDao = new NotePadUpdate(EditNotePadActivity.this);
			ContentValues values = new ContentValues();
			something = tv_content.getText().toString();
			values.put("body", something);
			//currentdate =String.valueOf(System.currentTimeMillis());
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			currentdate = sdf.format(d);
			values.put("title", currentdate);//时间格式有问题！！！
			if (!"".equals(something)) {
				notepadDao.updateData(values, String.valueOf(mId));
				finish();
			}else {
				Toast.makeText(EditNotePadActivity.this, "文本不能为空！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.edit:
			tv_content.setEnabled(true);//可编辑状态
			tv_content.setSelection(tv_content.length());//将光标设置到文本的最后
			edit.setVisibility(View.INVISIBLE);//编辑图标不可见
			save.setVisibility(View.VISIBLE);//保存图标可见
			//弹出软键盘
			//需要使用的系统提供的服务
//			getSystemService(name);//通知 闹钟   Inflater 定位 开关WIFI   GPRS 
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(tv_content, 0);//点击后显示软键盘
			break;
		

		default:
			break;
		}
	}
}
