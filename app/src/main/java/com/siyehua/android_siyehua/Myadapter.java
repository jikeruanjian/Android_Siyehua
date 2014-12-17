package com.siyehua.android_siyehua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @类名:Myadapter
 * @介绍:ListView的适配器
 * @作者:XuanKe'Huang
 * @时间:2014-11-10 4:05:03
 * @Copyright 2014
 */
public class Myadapter extends BaseAdapter {
	private ArrayList<Blank> list;
	private Context context;
	private LayoutInflater inflater;

	public Myadapter(Context context, ArrayList<Blank> list) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Blank getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list, null);
			holder.contentTextView = (TextView) convertView
					.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.contentTextView.setText("" + list.get(position).getContent());
		if (list.get(position).getMode() == 1) {
			holder.contentTextView.setTextColor(context.getResources()
					.getColor(R.color.first_001));
			holder.contentTextView.setTextSize(24);
		} else if (list.get(position).getMode() == 2) {
			holder.contentTextView.setTextColor(context.getResources()
					.getColor(R.color.first_002));
			holder.contentTextView.setTextSize(20);
		} else {
			holder.contentTextView.setTextColor(context.getResources()
					.getColor(R.color.first_003));
			holder.contentTextView.setTextSize(16);
		}
		return convertView;
	}

	class ViewHolder {
		TextView contentTextView;
	}

}
