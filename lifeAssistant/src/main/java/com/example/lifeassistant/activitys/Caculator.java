package com.example.lifeassistant.activitys;

import com.example.lifeassistant.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Caculator extends Activity implements OnClickListener {
	private Button b_c, b_back, b_1, b_2, b_3, b_4, b_5, b_6, b_7, b_8, b_9,
			b_0, b_jia, b_jian, b_cheng, b_chu, b_point, b_deng;
	private EditText edt_calc;
	private TextView txt_calc;
	private boolean flag = true;
	StringBuffer s1 = new StringBuffer("0");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caculator_layout);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		b_c = (Button) findViewById(R.id.btn_clear);
		b_back = (Button) findViewById(R.id.btn_del);
		b_1 = (Button) findViewById(R.id.btn_1);
		b_2 = (Button) findViewById(R.id.btn_2);
		b_3 = (Button) findViewById(R.id.btn_3);
		b_4 = (Button) findViewById(R.id.btn_4);
		b_5 = (Button) findViewById(R.id.btn_5);
		b_6 = (Button) findViewById(R.id.btn_6);
		b_7 = (Button) findViewById(R.id.btn_7);
		b_8 = (Button) findViewById(R.id.btn_8);
		b_9 = (Button) findViewById(R.id.btn_9);
		b_0 = (Button) findViewById(R.id.btn_0);
		b_jia = (Button) findViewById(R.id.btn_calc_add);
		b_jian = (Button) findViewById(R.id.btn_sub);
		b_cheng = (Button) findViewById(R.id.btn_multiply);
		b_chu = (Button) findViewById(R.id.btn_divide);
		b_point = (Button) findViewById(R.id.btn_point);
		b_deng = (Button) findViewById(R.id.btn_equal);
		edt_calc = (EditText) findViewById(R.id.edt_calc);
		txt_calc = (TextView) findViewById(R.id.txt_calc);
		b_c.setOnClickListener(this);
		b_back.setOnClickListener(this);
		b_1.setOnClickListener(this);
		b_2.setOnClickListener(this);
		b_3.setOnClickListener(this);
		b_4.setOnClickListener(this);
		b_5.setOnClickListener(this);
		b_6.setOnClickListener(this);
		b_7.setOnClickListener(this);
		b_8.setOnClickListener(this);
		b_9.setOnClickListener(this);
		b_0.setOnClickListener(this);
		b_jia.setOnClickListener(this);
		b_jian.setOnClickListener(this);
		b_cheng.setOnClickListener(this);
		b_chu.setOnClickListener(this);
		b_point.setOnClickListener(this);
		b_deng.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_clear:
			clear();
			break;
		case R.id.btn_del:
			back();
			break;
		case R.id.btn_1:
			b_1();
			break;
		case R.id.btn_2:
			b_2();
			break;
		case R.id.btn_3:
			b_3();
			break;
		case R.id.btn_4:
			b_4();
			break;
		case R.id.btn_5:
			b_5();
			break;
		case R.id.btn_6:
			b_6();
			break;
		case R.id.btn_7:
			b_7();
			break;
		case R.id.btn_8:
			b_8();
			break;
		case R.id.btn_9:
			b_9();
			break;
		case R.id.btn_0:
			b_0();
			break;
		case R.id.btn_calc_add:
			b_jia();
			break;
		case R.id.btn_sub:
			b_jian();
			break;
		case R.id.btn_multiply:
			b_cheng();
			break;
		case R.id.btn_divide:
			b_chu();
			break;
		case R.id.btn_point:
			b_point();
			break;
		case R.id.btn_equal:
			b_deng();
			break;

		}
	}

	public void clear() {
		s1 = new StringBuffer("0");
		flag=true;
		edt_calc.setText("");
		txt_calc.setText("");
	}

	public void back() {

		if (!(s1 == null || "".equals(s1.toString()))) {
			s1.deleteCharAt(s1.length() - 1);
			edt_calc.setText(s1);
		}
	}

	public void b_1() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_1.getText());
		edt_calc.setText(s1);
	}

	public void b_2() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_2.getText());
		edt_calc.setText(s1);
	}

	public void b_3() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_3.getText());
		edt_calc.setText(s1);
	}

	public void b_4() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_4.getText());
		edt_calc.setText(s1);
	}

	public void b_5() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_5.getText());
		edt_calc.setText(s1);
	}

	public void b_6() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_6.getText());
		edt_calc.setText(s1);
	}

	public void b_7() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_7.getText());
		edt_calc.setText(s1);
	}

	public void b_8() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_8.getText());
		edt_calc.setText(s1);
	}

	public void b_9() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_9.getText());
		edt_calc.setText(s1);
	}

	public void b_0() {
		if (flag) {
			s1.delete(0, s1.length());
			flag = false;
		}
		s1.append(b_0.getText());
		edt_calc.setText(s1);
	}

	public void b_jia() {
		if(s1.toString().endsWith("+")||s1.toString().endsWith("-")||s1.toString().endsWith("*")||s1.toString().endsWith("/")){
			return;
		}
		
		s1.append(b_jia.getText());
		flag = false;
		edt_calc.setText(s1);
	}

	public void b_jian() {
		if(s1.toString().endsWith("+")||s1.toString().endsWith("-")||s1.toString().endsWith("*")||s1.toString().endsWith("/")){
			return;
		}
		if(s1.toString().equals("0")){
			s1.delete(0, 1);
		}
		s1.append(b_jian.getText());
		flag = false;
		edt_calc.setText(s1);
	}

	public void b_cheng() {
		
		if(s1.toString().endsWith("+")||s1.toString().endsWith("-")||s1.toString().endsWith("*")||s1.toString().endsWith("/")){
			return;
		}
		
		s1.append(b_cheng.getText());
		flag = false;
		edt_calc.setText(s1);
	}

	public void b_chu() {
		if(s1.toString().endsWith("+")||s1.toString().endsWith("-")||s1.toString().endsWith("*")||s1.toString().endsWith("/")){
			return;
		}
		
		s1.append(b_chu.getText());
		flag = false;
		edt_calc.setText(s1);
	}

	public void b_point() {
		if (!flag) {
			s1.append("0");
		}
		if (!s1.toString().contains(".")) {

			s1.append(b_point.getText());
		}
		flag = false;
		edt_calc.setText(s1);
	}

	public void b_deng() {
		try {
			String[] sjia = s1.toString().split("\\+");
			for (int i = 0; i < sjia.length; i++) {
				if (sjia[i].contains("-") || sjia[i].contains("*")
						|| sjia[i].contains("/")) {
					String[] sjian = sjia[i].split("-");
					Log.i("msg", sjian.length+"sss");
					for (int j = 0; j < sjian.length; j++) {
						if(sjian[0].equals("")){
							sjian[0]="0";
						}
						if (sjian[j].contains("*") || sjia[i].contains("/")) {
							String[] scheng = sjian[j].split("\\*");
							for (int k = 0; k < scheng.length; k++) {
								if (scheng[k].contains("/")) {
									String[] schu = scheng[k].split("\\/");
									for (int h = 0; h < schu.length - 1; h++) {
										schu[h + 1] = new String(
												Double.parseDouble(schu[h])
														/ Double.parseDouble(schu[h + 1])
														+ "");
									}
									scheng[k] = schu[schu.length - 1];
								}
							}
							for (int k = 0; k < scheng.length - 1; k++) {
								scheng[k + 1] = new String(
										Double.parseDouble(scheng[k])
												* Double.parseDouble(scheng[k + 1])
												+ "");
							}
							sjian[j] = scheng[scheng.length - 1];
						}
					}
					for (int j = 0; j < sjian.length - 1; j++) {
						sjian[j + 1] = new String(Double.parseDouble(sjian[j])
								- Double.parseDouble(sjian[j + 1]) + "");
					}
					sjia[i] = sjian[sjian.length - 1];
				}
			}
			for (int i = 0; i < sjia.length - 1; i++) {
				sjia[i + 1] = new String(Double.parseDouble(sjia[i])
						+ Double.parseDouble(sjia[i + 1]) + "");
			}
			String result = new String(sjia[sjia.length - 1]);
			if (result.endsWith(".0")) {
				result = result.substring(0, result.length() - 2);
			}
			;
			txt_calc.setText(result);
			s1.delete(0, s1.length());
			s1.append(result);
			flag = true;
			if (s1 == null || "".equals(s1.toString())) {
				txt_calc.setText("0");
			}

		} catch (Exception e) {

			txt_calc.setText("ERROR!!!");
		}

	}

}
