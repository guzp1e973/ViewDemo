package com.example.cnblog.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.cnblog.BlogViewAct;
import com.example.cnblog.R;
import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.BlogOff;
import com.example.cnblog.instance.Bloger;
import com.example.cnblog.util.ConvertDate;
import com.example.cnblog.util.DownAvatar;
import com.example.cnblog.util.DownAvatarsSync;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private Context context;
	private List<Bloger> blogers;
	private Map<String, Bitmap> maps = new HashMap<String, Bitmap>();

	public SearchAdapter(Context context) {
		this.context = context;
		this.blogers = AppStatic.blogers;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.blogers.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.search_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.blogger_name);
			holder.time = (TextView) convertView.findViewById(R.id.time_day);
			holder.count = (TextView) convertView.findViewById(R.id.blog_count);
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.blogger_img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(blogers.get(position).getTitle());
		try {
			holder.time.setText(ConvertDate.publishedToDate(blogers.get(
					position).getUpdated()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.count.setText(blogers.get(position).getPostcount() + "篇博文");
		String u = blogers.get(position).getAvatar();

		holder.avatar.setTag(u);
		Bitmap bit = getBitmap(u, holder.avatar);
		if (bit == null) {
			holder.avatar.setImageResource(R.drawable.sample_face);
		} else {
			holder.avatar.setImageBitmap(bit);
		}
		return convertView;
	}

	private Bitmap getBitmap(String u, ImageView imgv) {
		// TODO Auto-generated method stub
		if (maps.get(u) != null) {
			return maps.get(u);
		}
		if (u == null || "".equals(u)) {
			return null;
		}
		
		new DownAvatarsSync(maps, imgv).execute(u);
		return null;
	}

	private static class ViewHolder {
		TextView time;
		TextView name;
		TextView count;
		ImageView avatar;
	}

}
