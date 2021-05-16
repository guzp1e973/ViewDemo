package com.yeepay.myrefresh;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/1/20.
 */
public class DoubleAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> data;
    private static final int STYLE1 = 0;
    private static final int STYLE2 = 1;

    public DoubleAdapter(Context context, List<Integer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == STYLE1) {
            ViewHolder1 holder1 = null;
            if (convertView == null) {
                holder1 = new ViewHolder1();
                convertView = View.inflate(context, R.layout.item_1, null);
                holder1.text = (TextView) convertView.findViewById(R.id.tx_1);
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolder1) convertView.getTag();
            }
            holder1.text.setText(data.get(position) + "");
        } else {
            ViewHolder2 holder = null;
            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = View.inflate(context, R.layout.item_2, null);
                holder.txt = (TextView) convertView.findViewById(R.id.tx_2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder2) convertView.getTag();
            }
            holder.txt.setText(data.get(position) + "");
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) % 3 == 1) {
            return STYLE1;
        } else {
            return STYLE2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public static class ViewHolder1 {
        TextView text;
    }

    public static class ViewHolder2 {
        TextView txt;
    }
}
