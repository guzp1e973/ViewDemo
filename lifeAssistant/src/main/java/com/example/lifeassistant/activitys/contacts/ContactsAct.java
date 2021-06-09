package com.example.lifeassistant.activitys.contacts;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.ContactsAdapter;
import com.example.lifeassistant.database.ContactsDBHelper;
import com.example.lifeassistant.util.Constants;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ContactsAct extends Activity {
	private ListView contacts_list;
	private Button btn_contacts_back;
	HashMap<String, String> conHS;
	ArrayList<HashMap<String, String>> conListData;
	ContactsAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactsactivity_layout);
		contacts_list=(ListView) findViewById(R.id.contents_list);
		btn_contacts_back=(Button) findViewById(R.id.btn_contacts_back);
		conListData = new ArrayList<HashMap<String, String>>();
		showMessage();
		adapter = new ContactsAdapter(ContactsAct.this, conListData);
		contacts_list.setAdapter(adapter);
		contacts_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String number = conListData.get(position).get(
						Constants.C_CONTACTS_NAME);
				Uri uri = Uri.parse("tel:" + number);
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				startActivity(intent);
			}
		});
		contacts_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Builder dialog = new Builder(ContactsAct.this);
				dialog.setTitle("删除信息");
				dialog.setMessage("删除："
						+ conListData.get(position).get(
								Constants.C_CONTACTS_NAME) + "吗？");
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ContactsDBHelper helper = new ContactsDBHelper(
								ContactsAct.this);
						SQLiteDatabase db = helper.getWritableDatabase();
						// 复习 SQL 的删除语句
						int result = db.delete(
								Constants.TABLE_NAME,
								Constants.C_ID + "=?",
								new String[] { conListData.get(position).get(
										Constants.C_ID) });
						if (result > 0) {
							Toast.makeText(ContactsAct.this, "删除成功！",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(ContactsAct.this, "删除失败！",
									Toast.LENGTH_LONG).show();
						}
						db.close();
					}
				});
				dialog.setNegativeButton("取消", null);
				dialog.show();
				return false;
			}
		});
		btn_contacts_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContactsAct.this.finish();
			}
		});
	}
	@Override
	protected void onRestart() {
		showMessage();
		adapter = new ContactsAdapter(ContactsAct.this, conListData);
		contacts_list.setAdapter(adapter);
		
		super.onRestart();
	}
	private void showMessage() {
		// TODO Auto-generated method stub
		ContactsDBHelper helper = new ContactsDBHelper(ContactsAct.this);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query(Constants.TABLE_NAME, null, null, null, null, null,
				null);
		if (c != null) {
			conListData.clear();
			while (c.moveToNext()) {
				conHS = new HashMap<String, String>();
				conHS.put(Constants.C_ID,
						"" + c.getInt(c.getColumnIndex(Constants.C_ID)));
				conHS.put(
						Constants.C_CONTACTS_NAME,
						""
								+ c.getString(c
										.getColumnIndex(Constants.C_CONTACTS_NAME)));
				
				conHS.put(
						Constants.C_CONTACTS_NUMBER,
						""
								+ c.getInt(c
										.getColumnIndex(Constants.C_CONTACTS_NUMBER)));
				conListData.add(conHS);
			}
			c.close();
		}
		db.close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(Menu.NONE, 1, 1, "添加").setIcon(android.R.drawable.ic_menu_add);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			Intent it=new Intent(ContactsAct.this,ContactsAdd.class);
			startActivity(it);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
