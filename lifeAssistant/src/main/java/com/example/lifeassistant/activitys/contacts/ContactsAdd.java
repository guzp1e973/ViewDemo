package com.example.lifeassistant.activitys.contacts;

import com.example.lifeassistant.R;
import com.example.lifeassistant.database.ContactsDBHelper;
import com.example.lifeassistant.util.Constants;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsAdd extends Activity implements OnClickListener {
	private Button btn_add,btn_back;
	private EditText edit_contacts_number,edit_contacts_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contents_add);
		init();
		
	}
	private void init() {
		// TODO Auto-generated method stub
		btn_add=(Button) findViewById(R.id.btn_add);
		btn_back=(Button) findViewById(R.id.btn_back);
		edit_contacts_number=(EditText) findViewById(R.id.edit_contents_num);
		edit_contacts_name=(EditText) findViewById(R.id.edit_contents);
		btn_add.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add:
			String contactsName = edit_contacts_name.getText().toString().trim();
			String contactsNumber = edit_contacts_number.getText().toString()
					.trim();
			if (contactsName.equals("") || edit_contacts_number.equals("")) {
				Toast.makeText(ContactsAdd.this, "带*的为必填内容",
						Toast.LENGTH_SHORT).show();
			} else {
				ContactsDBHelper add = new ContactsDBHelper(
						ContactsAdd.this);
				SQLiteDatabase db = add.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(Constants.C_CONTACTS_NAME, contactsName);
				values.put(Constants.C_CONTACTS_NUMBER, contactsNumber);
				long result = db.insert(Constants.TABLE_NAME, null, values);
				if (result > 0) {
					Toast.makeText(ContactsAdd.this, "添加成功！",
							Toast.LENGTH_SHORT).show();
					finish();

				} else {
					Toast.makeText(ContactsAdd.this, "添加失败！",
							Toast.LENGTH_SHORT).show();
				}
				db.close();
			}
			break;
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}
}
