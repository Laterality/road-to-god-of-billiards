package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.items.MyNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-07.
 */
public class MyNewsListAdapter extends BaseAdapter
{
	private Context mContext;
	private List<MyNews> listNews;

	private boolean removeModeState = false;


	public MyNewsListAdapter(Context context, List<MyNews> list)
	{
		mContext = context;
		listNews = list;
		if(listNews == null)
		{
			listNews = new ArrayList<>();
		}
	}

	@Override
	public int getCount()
	{
		return listNews.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listNews.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return listNews.get(position).getNewsId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup pareent)
	{
		ViewHolder holder;
		final MyNews news = listNews.get(position);
		news.setRemoveFlag(false);

		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_my_news_list, null);

			holder = new ViewHolder();
			holder.tvMessage = (TextView) convertView.findViewById(R.id.tv_my_news_item_message);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_my_news_item_date);
			holder.ibtnCheck = (ImageButton) convertView.findViewById(R.id.ibtn_my_news_item_check);

			holder.ibtnCheck.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(removeModeState)
					{
						// Do something for remove in list
						news.setRemoveFlag(true);
					}
				}
			});

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvMessage.setText(news.getNewsMessage());
		holder.tvDate.setText(news.getNewsDate());
		if(removeModeState){holder.ibtnCheck.setVisibility(View.VISIBLE);}
		else{holder.ibtnCheck.setVisibility(View.INVISIBLE);}


		return convertView;
	}

	public void toggleMode()
	{
		if(removeModeState){removeModeState = false;}
		else{removeModeState = true;}
		this.notifyDataSetChanged();
	}



	class ViewHolder
	{
		TextView tvMessage;
		TextView tvDate;
		ImageButton ibtnCheck;

	}
}
