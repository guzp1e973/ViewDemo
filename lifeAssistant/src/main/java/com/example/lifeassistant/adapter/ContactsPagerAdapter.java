package com.example.lifeassistant.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ContactsPagerAdapter extends PagerAdapter {
	
	private List<View> views;
	public ContactsPagerAdapter(List<View> views){
	
		this.views=views;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView( views.get(position));
		return views.get(position);
	}

}
