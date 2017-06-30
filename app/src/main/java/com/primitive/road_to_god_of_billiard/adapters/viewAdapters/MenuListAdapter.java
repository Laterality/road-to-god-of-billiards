package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.items.MenuListItem;
import com.primitive.road_to_god_of_billiard.R;

import java.util.ArrayList;

/**
 * Created by 신진우- on 2015-08-09.
 */
public class MenuListAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<MenuListItem> Items = new ArrayList<>();

	public MenuListAdapter(Context context)
	{
		super();
		mContext = context;
	}

	@Override
	public int getCount()
	{
		return Items.size();
	}

	@Override
	public Object getItem(int position)
	{
		return Items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = new ViewHolder();
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_menu_list, null);

			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_list_item_icon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_list_item_name);
			holder.ivNew = (ImageView) convertView.findViewById(R.id.iv_list_item_symbol_new);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		MenuListItem item = Items.get(position);

		if (item.mIcon != null)
		{
			holder.ivIcon.setVisibility(View.VISIBLE);
			holder.ivIcon.setImageDrawable(item.mIcon);
		}
		else
		{
			holder.ivIcon.setVisibility(View.GONE);
		}

		holder.tvTitle.setText(item.mTitle);
		if(item.isNew)
		{
			holder.ivNew.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.ivNew.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	public void addItem(MenuListItem item)
	{
		Items.add(item);
	}

	private class ViewHolder
	{
		public ImageView ivIcon;
		public TextView tvTitle;
		public ImageView ivNew;
	}



}

