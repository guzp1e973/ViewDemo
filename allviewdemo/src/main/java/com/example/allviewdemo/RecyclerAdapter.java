package com.example.allviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2015/1/22.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static final int STYLE1 = 0;
	private static final int STYLE2 = 1;
	private static final int FOOTER = 2;
	public static  int position = -1;
	private static List<Integer> data;

	public RecyclerAdapter(List<Integer> data) {
		this.data=data;
	}

	public static class ViewHolder1 extends  RecyclerView.ViewHolder{
		public TextView tx;
		public ViewHolder1(final View itemView) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//ComponentName cn = new ComponentName("com.example.demo","com.example.demo.MainActivity");
					Intent it = new Intent();
					it.putExtra("data", data.get(getPosition()));

					it.setClassName("com.example.demo",
							"com.example.demo.MainActivity");
					// it.setComponent(cn);
					if (itemView.getContext().getPackageManager().resolveActivity(it, 0) == null) {
						// 说明系统中不存在这个activity
						Toast.makeText(itemView.getContext(),"未安装插件，请上www.xxx.com",Toast.LENGTH_SHORT).show();
						return;
					} else {
						position=getPosition();
						itemView.getContext().startActivity(it);
					}

				}
			});
			tx= (TextView) itemView.findViewById(R.id.tx_1);
		}
	}
	public static class ViewHolder2 extends  RecyclerView.ViewHolder{
		public TextView tx;
		public ViewHolder2(View itemView) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(),"Text2",Toast.LENGTH_SHORT).show();
				}
			});
			tx= (TextView) itemView.findViewById(R.id.tx_2);
		}
	}
	public static class FooterHolder extends RecyclerView.ViewHolder{
		public ContentLoadingProgressBar progress;
		public FooterHolder(View itemView) {
			super(itemView);
			progress= (ContentLoadingProgressBar) itemView.findViewById(R.id.progress_bar);
		}
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		RecyclerView.ViewHolder holder;
		RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin =2;
		lp.rightMargin = 2;
		lp.topMargin = 2;
		lp.bottomMargin = 2;
		if (viewType==STYLE1){
			 view=View.inflate(parent.getContext(),R.layout.item_1,null);
			view.setFocusable(true);
			view.setLayoutParams(lp);
			holder= new ViewHolder1(view);
		}else if (viewType==STYLE2){
			view=View.inflate(parent.getContext(),R.layout.item_2,null);
			//view.setPadding(20, 0,0, 0);
			view.setFocusable(true);
			view.setLayoutParams(lp);
			holder= new ViewHolder2(view);
		}else {
			view=View.inflate(parent.getContext(),R.layout.footeract,null);
			view.setFocusable(true);
			view.setLayoutParams(lp);
			holder= new FooterHolder(view);
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		if (getItemViewType(position)==STYLE1){
			ViewHolder1 holder1= (ViewHolder1) holder;
			holder1.tx.setText(data.get(position)+"");
		}else if (getItemViewType(position)==STYLE2){
			ViewHolder2 holder2= (ViewHolder2) holder;
			holder2.tx.setText(data.get(position)+"");
		}else{
			FooterHolder holder3= (FooterHolder) holder;
			holder3.progress.show();
		}

	}

	@Override
	public int getItemCount() {
		return data.size()+1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position==data.size()){
			return FOOTER;
		}
		if (data.get(position) % 3 == 1) {
			return STYLE1;
		} else {
			return STYLE2;
		}

	}
}
