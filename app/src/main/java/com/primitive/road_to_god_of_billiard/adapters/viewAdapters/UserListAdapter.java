package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;

import java.util.ArrayList;

/**
 * Created by 신진우- on 2015-08-09.
 */
public class UserListAdapter extends BaseAdapter
{
	Context mContext;
	ArrayList<ListUser> userList = new ArrayList<>();

	public UserListAdapter(Context context)
	{
		super();
		mContext = context;
	}

	@Override
	public int getCount()
	{
		return userList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return userList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		UserViewHolder viewHolder = new UserViewHolder();

		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_user_list, null);

			viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.iv_list_user_profile);
			viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tv_list_user_username);

			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (UserViewHolder) convertView.getTag();
		}

		viewHolder.tvUsername.setText(userList.get(position).usernmae);
		viewHolder.ivProfile.setImageDrawable(userList.get(position).profileImage);

		return convertView;
	}

	private class UserViewHolder
	{
		public ImageView ivProfile;
		public TextView tvUsername;

	}
}

class ListUser
{
	public Drawable profileImage;
	public String usernmae;
}

