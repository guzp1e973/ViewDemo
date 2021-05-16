package com.example.slidingpaneldemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends Activity {
	private ListView leftlist;
	private List<Integer> data;
	private ArrayAdapter<Integer> leftadapter;
	private Button but;
	private SlidingPaneLayout slid;
	private RecyclerView recycler;
	private RecyclerAdapter recyadapter;
	private ContentLoadingProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<Integer>();
		changeData();
		initView();
		LinearLayoutManager manager=new LinearLayoutManager(this);
		//manager.setOrientation(LinearLayoutManager.HORIZONTAL);
		recycler.setLayoutManager(manager);
		recyadapter=new RecyclerAdapter(data);
		recycler.setAdapter(recyadapter);
		slid.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
			@Override
			public void onPanelSlide(View view, float v) {

			}

			@Override
			public void onPanelOpened(View view) {
				but.setText("CLOSE");
				progressBar.hide();
			}

			@Override
			public void onPanelClosed(View view) {
				but.setText("SHOW");
				progressBar.show();
			}
		});
		but.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (but.getText().equals("SHOW")) {
					slid.openPane();
				} else {
					slid.closePane();
				}
			}
		});

		leftadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, data);
		leftlist.setAdapter(leftadapter);



	}

	private void initView() {
		leftlist = (ListView) findViewById(R.id.leftlist);
		slid = (SlidingPaneLayout) findViewById(R.id.slidingpanel);
		but = (Button) findViewById(R.id.showLeft);
		recycler= (RecyclerView) findViewById(R.id.recycler);
		progressBar= (ContentLoadingProgressBar) findViewById(R.id.progress);
		progressBar.show();
	}

	private void changeData() {
		for (int i = 0; i < 100; i++) {
			data.add(i + 1);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}




