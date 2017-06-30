package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGamePlayerJson;

import java.util.List;

/**
 * Created by 신진우- on 2015-10-11.
 */
public class IndividualSearchListAdapter extends BaseAdapter
{

	Context mContext;
	List<SearchGamePlayerJson> listPlayer;

	public IndividualSearchListAdapter(Context context, List<SearchGamePlayerJson> list)
	{
		mContext = context;
		listPlayer = list;
	}

	@Override
	public int getCount()
	{
		return listPlayer.size();
	}

	@Override
	public long getItemId(int position)
	{
		return listPlayer.get(position).getUserId();
	}

	@Override
	public Object getItem(int position)
	{
		return listPlayer.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		ViewHolder holder;
		SearchGamePlayerJson player = listPlayer.get(position);

		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_game_indi, null);
			holder = new ViewHolder();

			holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_search_game_indi_username);
			holder.tvTier = (TextView) convertView.findViewById(R.id.tv_search_game_indi_tier);
			holder.tvRank = (TextView) convertView.findViewById(R.id.tv_search_game_indi_rank);
			holder.tvRate = (TextView) convertView.findViewById(R.id.tv_search_game_indi_rate);
			holder.tvAvg = (TextView) convertView.findViewById(R.id.tv_search_game_indi_avg);
			holder.tvHi = (TextView) convertView.findViewById(R.id.tv_search_game_indi_hi);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvUsername.setText(player.getUsername());
		//holder.tvTier.setText(player.userTier); //TODO : How to handle this?
		holder.tvRank.setText(player.getTotal() + "전 " + player.getWin() + "승 " + player.getLose() + "패");
		holder.tvRate.setText("승률 : " + player.getRate() + "%");
		holder.tvAvg.setText("평균 점수 : " + player.getScore());
		holder.tvHi.setText("최고 점수 : " + player.getHiScore());


		return convertView;
	}

	class ViewHolder
	{
		TextView tvUsername;
		TextView tvTier;
		TextView tvRank;
		TextView tvRate;
		TextView tvAvg;
		TextView tvHi;
	}
}