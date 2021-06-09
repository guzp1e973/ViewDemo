package com.example.lifeassistant.adapter;

import java.util.List;

import com.example.lifeassistant.R;
import com.example.lifeassistant.adapter.NoteAdapter.ViewHolder;
import com.example.lifeassistant.bean.DayWeather;
import com.example.lifeassistant.bean.NotePad;
import com.example.lifeassistant.util.Constants;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherAdapter extends BaseAdapter{
	private Context context;
	private List<DayWeather> list;

	public WeatherAdapter(Context context, List<DayWeather> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,R.layout.weather_forcast_list, null);
			holder.t_weather_day = (TextView) convertView.findViewById(R.id.t_weather_day);
			holder.t_weather_mounth = (TextView) convertView.findViewById(R.id.t_weather_mounth);
			holder.t_weather = (TextView) convertView.findViewById(R.id.t_weather);
			holder.t_weather_details = (TextView) convertView.findViewById(R.id.t_weather_details);
			holder.img_weather_logo=(ImageView) convertView.findViewById(R.id.img_weather_logo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.t_weather_day.setText(list.get(position).getWeek());
		holder.t_weather_mounth.setText(list.get(position).getDate_y());
		holder.t_weather.setText(list.get(position).getTemp());
		holder.t_weather_details.setText(list.get(position).getWeather());
		Constants.setWeatherImg(holder.img_weather_logo, list.get(position).getWeather());
		return convertView;
	}
	static class ViewHolder{
		TextView t_weather_day,t_weather_mounth,t_weather,t_weather_details;
		ImageView img_weather_logo;
	}
}
