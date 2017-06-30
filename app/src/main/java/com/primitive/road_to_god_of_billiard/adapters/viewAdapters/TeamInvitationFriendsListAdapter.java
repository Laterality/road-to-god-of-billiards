package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.FriendJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class TeamInvitationFriendsListAdapter extends BaseAdapter
{
	private List<FriendJson> ntFriends;
	private List<Boolean> checkList;
	private Context mContext;


	public TeamInvitationFriendsListAdapter(Context context, List<FriendJson> list)
	{
		mContext = context;
		ntFriends = new ArrayList<>();

		for(FriendJson f : list)
		{
			if(f.getTeamId() == 0){ntFriends.add(f);}
		}

		checkList = new ArrayList<>();
		for(int i = 0 ; i < ntFriends.size(); i++){checkList.add(false);}
	}

	@Override
	public int getCount()
	{
		return ntFriends.size();
	}

	@Override
	public Object getItem(int position)
	{
		return ntFriends.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return ntFriends.get(position).getFriendId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_team_invitation_list, parent, false);
			holder = new ViewHolder();

			holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_team_invitation_list_username);
			holder.tvUserTier = (TextView) convertView.findViewById(R.id.tv_team_invitation_list_tier);
			holder.ivChecked = (ImageView) convertView.findViewById(R.id.iv_team_invitation_list_checked);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		if(checkList.get(position)){holder.ivChecked.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_icon_green));}
		else{holder.ivChecked.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_icon_gray));}
		holder.tvUsername.setText(ntFriends.get(position).getFriendUsername());
		holder.tvUserTier.setText("");


		return convertView;
	}

	public void setCheckToggle(int position)
	{
		checkList.set(position, !checkList.get(position));
		notifyDataSetChanged();
	}

	public List<FriendJson> getChecked()
	{
		List<FriendJson> list = new ArrayList<>();
		for(int i = 0 ; i< ntFriends.size(); i++)
		{
			if(checkList.get(i)){list.add(ntFriends.get(i));}
		}

		return list;
	}

	class ViewHolder
	{
		TextView tvUsername;
		TextView tvUserTier;
		ImageView ivChecked;
	}
}