package com.example.cnblog.adapter;

import java.io.InputStream;

import com.example.cnblog.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SheZhiAdapter extends BaseAdapter {
	private Context context;
	public View item;
	private int[] data = { R.raw.left_menu_bg3, R.raw.left_menu_bg4,
			R.raw.left_menu_bg5, };

	public SheZhiAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=View.inflate(context, R.layout.shezhiitem, null);
		TextView t=(TextView) v.findViewById(R.id.menu_back_text);
		ImageView imgs=(ImageView) v.findViewById(R.id.menu_back);
		ImageView img=(ImageView) v.findViewById(R.id.yes);
		SharedPreferences share=context.getSharedPreferences("bg", Activity.MODE_PRIVATE);
		int pos=share.getInt("bg", 0);
		
		if(pos==position){
			img.setVisibility(View.VISIBLE);
			item=img;
		}
		t.setText("壁纸"+(position+1));
		if(position==0){
			t.setBackgroundColor(0xff1F2020);
		}else{
			InputStream is = context.getResources().openRawResource(data[position-1]);
			   BitmapFactory.Options options=new BitmapFactory.Options();
			   options.inJustDecodeBounds = false;
			   options.inSampleSize = 5;   //width，hight设为原来的十分一
			   Bitmap btp =BitmapFactory.decodeStream(is,null,options);
			imgs.setImageBitmap(btp);//Resource(data[position-1]);
			
		}
		
		return v;
	}

}
