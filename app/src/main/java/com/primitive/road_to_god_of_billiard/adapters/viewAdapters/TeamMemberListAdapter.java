package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;

import org.w3c.dom.Text;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Objects;

/**
 * Created by 신진우- on 2015-10-26.
 */
public class TeamMemberListAdapter extends BaseAdapter
{
	private Context mContext;
	private List<UserProfileJson> Members;

	public TeamMemberListAdapter(Context context, List<UserProfileJson> list)
	{
		mContext = context;
		Members = list;
	}

	@Override
	public int getCount()
	{
		return Members.size();
	}

	@Override
	public Object getItem(int position)
	{
		return Members.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Members.get(position).getUserCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if(convertView == null)
		{
			holder = new ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_team_member_list, null);

			holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_my_team_member_list_username);
			holder.tvTier = (TextView) convertView.findViewById(R.id.tv_my_team_member_list_tier);
			holder.tvLastLogin = (TextView) convertView.findViewById(R.id.tv_my_team_member_list_last_login);
			holder.ivLeadermark = (ImageView) convertView.findViewById(R.id.iv_team_member_leader_mark);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvUsername.setText(Members.get(position).getUsername());
		if(position == 0){holder.ivLeadermark.setVisibility(View.VISIBLE);}
		else{holder.ivLeadermark.setVisibility(View.GONE);}



		return convertView;
	}

	class ViewHolder
	{
		TextView tvUsername;
		TextView tvTier;
		TextView tvLastLogin;
		ImageView ivLeadermark;
	}
}


