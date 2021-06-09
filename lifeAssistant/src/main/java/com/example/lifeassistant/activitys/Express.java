package com.example.lifeassistant.activitys;

import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.activitys.score.ScoreActivity;
import com.example.lifeassistant.adapter.ScoreAdapter;
import com.example.lifeassistant.bean.Course;
import com.example.lifeassistant.bean.ExpressInfo;
import com.example.lifeassistant.util.Constants;
import com.example.lifeassistant.util.ExpressThread;
import com.example.lifeassistant.util.ScoreThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Express extends Activity implements OnClickListener {
	private TextView express_company_info,express_id_info,express_message_info;
	private Spinner ems_com_list;
	private EditText ems_express_number;
	private Button btn_about_back,search_express_but;
	private RelativeLayout express_info;
	Handler handler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			proDialog.dismiss();
			if(msg.what==1){
				ExpressInfo e=(ExpressInfo) msg.obj;
				express_info.setVisibility(View.VISIBLE);
				express_company_info.setText(e.getCompany());
				express_id_info.setText(e.getNo());
				express_message_info.setText(e.getInfo());
			}else if(msg.what==2){
				Toast.makeText(Express.this, "对不起，网络出现问题了", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(Express.this, "对不起，暂时无法为您查询", Toast.LENGTH_SHORT).show();
			}
			
		};
	};
	private ProgressDialog proDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// http://v.juhe.cn/exp/index?key=91904cdf74040d19eada2fe8949523cd&com=sto&no=868018383640
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expressactivity);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		express_company_info=(TextView) findViewById(R.id.express_company_info);
		express_id_info=(TextView) findViewById(R.id.express_id_info);
		express_message_info=(TextView) findViewById(R.id.express_message_info);;
		ems_com_list=(Spinner) findViewById(R.id.ems_com_list);;
		ems_express_number=(EditText) findViewById(R.id.ems_express_number);
		btn_about_back=(Button) findViewById(R.id.btn_express_back);
		express_info=(RelativeLayout) findViewById(R.id.express_info);
		search_express_but=(Button) findViewById(R.id.search_express_but);
		btn_about_back.setOnClickListener(this);
		search_express_but.setOnClickListener(this);
	}
	public void showProgressDialog() {
		proDialog = new ProgressDialog(Express.this);
		proDialog.setTitle("正在查询快递......");
		proDialog.setMessage("请稍等...");
		proDialog.show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_express_back:
			this.finish();
			break;
		case R.id.search_express_but:
			String com=getCompanyCode();
			String num=ems_express_number.getText().toString();
			String url=Constants.EXPRESS_URL;
			url=url.replace("company", com);
			url=url.replace("postid", num);
			showProgressDialog();
			new ExpressThread(handler,url).start();;
			break;

		default:
			break;
		}
	}
	private String getCompanyCode() {
		// TODO Auto-generated method stub
		String s=ems_com_list.getSelectedItem().toString();
		if(s.equals("顺丰")){
			return "sf"; 
		}
		if(s.equals("申通")){
			return "sto"; 
		}
		if(s.equals("圆通")){
			return "yt"; 
		}
		if(s.equals("韵达")){
			return "yd"; 
		}
		if(s.equals("天天")){
			return "tt"; 
		}
		if(s.equals("EMS")){
			return "ems"; 
		}
		if(s.equals("中通")){
			return "zto"; 
		}
		if(s.equals("汇通")){
			return "ht"; 
		}
		return "";
	}
}
