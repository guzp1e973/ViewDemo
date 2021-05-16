package com.example.slidingpaneldemo;

import android.support.v7.widget.RecyclerView;
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
	private  List<Integer> data;

	public RecyclerAdapter(List<Integer> data) {
		this.data=data;
	}

	public static class ViewHolder1 extends  RecyclerView.ViewHolder{
		public TextView tx;
		public ViewHolder1(View itemView) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(),"Text1",Toast.LENGTH_SHORT).show();
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
		}else {
			view=View.inflate(parent.getContext(),R.layout.item_2,null);
			//view.setPadding(20, 0,0, 0);
			view.setFocusable(true);
			view.setLayoutParams(lp);
			holder= new ViewHolder2(view);
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position)==STYLE1){
			ViewHolder1 holder1= (ViewHolder1) holder;
			holder1.tx.setText(data.get(position));
		}else{
			ViewHolder2 holder2= (ViewHolder2) holder;
			holder2.tx.setText(data.get(position));
		}

	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (data.get(position) % 3 == 1) {
			return STYLE1;
		} else {
			return STYLE2;
		}
	}
}
