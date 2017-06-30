package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-29.
 */
public class ScoreRangeSpinnerAdapter extends BaseAdapter
{
	private static final String TAG = "ScoreRangeSpinnerAdapter";
	Context mContext = null;
	List<String> scoreList;
	boolean isMax;
	int val;

	public ScoreRangeSpinnerAdapter(Context context, boolean isMax, int arg)
	{
		super();
		Log.d(TAG, "isMax : " + isMax + ", arg : " + arg);
		mContext = context;
		this.isMax = isMax;
		List<String> items = Arrays.asList(context.getResources().getStringArray(R.array.score_range));
		val = arg;
		if(arg != 0)
		{
			scoreList = new ArrayList<>();
			if(isMax)
			{
				for (String str : items)
				{
					if (Integer.valueOf(str) >= arg){scoreList.add(str);}
				}
			}
			else
			{
				for(String str : items)
				{
					if(Integer.valueOf(str) <= arg){scoreList.add(str);}
				}
			}
		}
		else
		{
			scoreList = items;
		}
	}

	@Override
	public int getCount()
	{
		return scoreList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return scoreList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Integer.valueOf(scoreList.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		String str = scoreList.get(position);
		ViewHolder holder;

		if (convertView == null)
		{
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_search_game_score_range, null);

			holder.tvScore = (TextView) convertView.findViewById(R.id.tv_item_search_game_score);


			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}


		holder.tvScore.setText(str);

		return convertView;
	}

	class ViewHolder
	{
		TextView tvScore;
	}

}
