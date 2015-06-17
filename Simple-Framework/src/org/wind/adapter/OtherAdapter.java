package org.wind.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class OtherAdapter<T> extends BaseAdapter {

	protected ArrayList<T> list = null;
	protected Context context = null;
	protected LayoutInflater inflater = null;

	public OtherAdapter(Context context, ArrayList<T> list) {
		this.context = context;
		if (list == null) {
			this.list = new ArrayList<T>();
		} else {
			this.list = list;
		}
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
