package com.example.lifeassistant.activitys.notepad;



import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.NoteAdapter;
import com.example.lifeassistant.bean.NotePad;
import com.example.lifeassistant.database.NotePadUpdate;
import com.example.lifeassistant.util.Constants;
import com.example.lifeassistant.util.SlideCutListView;
import com.example.lifeassistant.util.SlideCutListView.RemoveDirection;
import com.example.lifeassistant.util.SlideCutListView.RemoveListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class NotePadActivity extends Activity {
	private TextView none;
	private SlideCutListView lv;
	private Button add;
	private TextView count;
	private NoteAdapter adapter;
	List<NotePad> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notepad);
		none=(TextView) findViewById(R.id.tv_notepad_none);
		lv=(SlideCutListView) findViewById(R.id.ls_notepad);
		add=(Button) findViewById(R.id.btn_notepad_add);
		count=(TextView) findViewById(R.id.btn_notepad_count);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(NotePadActivity.this,NotepadAdd.class);
				startActivityForResult(it, Constants.NOTEPAD_ADD_RESULT);
			}
		});
		NotePadUpdate update=new NotePadUpdate(this);
		list=update.adapterData();
		if(list.size()!=0){
			none.setVisibility(View.GONE);
		}
		count.setText("全部\n"+list.size());
		adapter=new NoteAdapter(this, list);
		lv.setDivider(null);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(NotePadActivity.this, EditNotePadActivity.class);
				NotePadUpdate mnotepadDao  = new NotePadUpdate(NotePadActivity.this);
				List<NotePad> list = mnotepadDao.adapterData();
				 NotePad notepad = list.get(position);
				 intent.putExtra("title",notepad.getmTitle());
				 intent.putExtra("body",notepad.getmBody());
				 intent.putExtra("mid",notepad.getmID());
				 startActivityForResult(intent, Constants.NOTEPAD_ADD_RESULT);
			}
		});
		lv.setRemoveListener(new RemoveListener() {

			@Override
			public void removeItem(RemoveDirection direction, int position) {

				NotePadUpdate mNotepadDao = new NotePadUpdate(
						NotePadActivity.this);
				NotePad notepad = (NotePad) adapter.getItem(position);
				long id = notepad.getmID();
				mNotepadDao.deleteData(id, NotePadActivity.this);
				// 删除完之后 重新查询数据
				list = mNotepadDao.adapterData();
				adapter.addItem(list);
				adapter.notifyDataSetChanged();
				count.setText("");
				count.setText("全部\n" + Long.toString(list.size()));
				if(list.size()==0){
					none.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		NotePadUpdate mnotepadupdate = new NotePadUpdate(NotePadActivity.this);
		if (list.size() > 0) {
			list.clear();
			
		}
		list = mnotepadupdate.adapterData();
		if(list.size()==0){
			none.setVisibility(View.VISIBLE);
		}else{
			none.setVisibility(View.GONE);
		}
		adapter.addItem(list);
		adapter.notifyDataSetChanged();
		count.setText("全部\n" + Long.toString(list.size()));

	}
}
