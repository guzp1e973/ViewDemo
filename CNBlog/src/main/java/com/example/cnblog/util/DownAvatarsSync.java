package com.example.cnblog.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.cnblog.R;
import com.example.cnblog.instance.AppStatic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownAvatarsSync extends AsyncTask<String, String, Bitmap> {
	private String u;
	private ImageView avatar;
	private Map<String, Bitmap> maps;

	public DownAvatarsSync(Map<String, Bitmap> maps, ImageView avatar) {
		// TODO Auto-generated constructor stub
		this.maps = maps;
		this.avatar = avatar;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		if(AppStatic.down==false){
			return null;
		}
		Bitmap bit = null;
		u = params[0];
		try {
			URL ur = new URL(params[0]);
			// Log.i("msg", blogers.get(position).getAvatar());
			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			bit = BitmapFactory.decodeStream(in);
			in.close();
			maps.put(u, bit);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bit;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		if (result != null && u.equals(avatar.getTag())) {

			avatar.setImageBitmap(result);

		}
		// if(result==null){
		// avatar.setImageResource(R.drawable.sample_face);
		// }
		super.onPostExecute(result);
	}
}
