package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGameTeamJson;

import java.util.List;

/**
 * Created by 신진우- on 2015-10-11.
 */
public class TeamSearchListAdapter extends BaseAdapter
{

	private Context mContext;
	private List<SearchGameTeamJson> listTeam;

	public TeamSearchListAdapter(List<SearchGameTeamJson> list, Context context)
	{
		mContext = context;
		listTeam = list;
	}

	@Override
	public int getCount()
	{
		return listTeam.size();
	}

	@Override
	public long getItemId(int position)
	{
		return listTeam.get(position).getTeamId();
	}

	@Override
	public Object getItem(int position)
	{
		return listTeam.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		SearchGameTeamJson iter = listTeam.get(position);

		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_game_team, null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.tv_search_team_name);
			holder.tvLeader = (TextView) convertView.findViewById(R.id.tv_search_team_leader);
			holder.tvCount = (TextView) convertView.findViewById(R.id.tv_search_team_count);
			holder.tvRate = (TextView) convertView.findViewById(R.id.tv_search_team_rate);
			holder.tvAvg = (TextView) convertView.findViewById(R.id.tv_search_team_avg);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tv_search_team_desc);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(iter.getTeamName());
		holder.tvLeader.setText(iter.getLeaderUsername());
		holder.tvCount.setText("인원 : " + iter.getTeamCount());
		holder.tvRate.setText("승률 : " + iter.getTeamRate() + "%");
		holder.tvAvg.setText("평균 점수 : " + iter.getTeamScore());
		holder.tvDesc.setText("한마디 : " + iter.getDesc());



		return convertView;
	}

	class ViewHolder
	{
		TextView tvName;
		TextView tvLeader;
		TextView tvCount;
		TextView tvRate;
		TextView tvAvg;
		TextView tvDesc;
	}
}
