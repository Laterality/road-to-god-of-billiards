package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;
import com.primitive.road_to_god_of_billiard.items.KnowHowPostReply;
import com.primitive.road_to_god_of_billiard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-06.
 */
public class MyRepliesListAdapter extends BaseAdapter
{
	private Context mContext;
	private List<ReplyJson> listReplies;

	public MyRepliesListAdapter(Context context, List<ReplyJson> list)
	{
		mContext = context;
		listReplies = list;
		if(listReplies == null) {listReplies = new ArrayList<>();}
	}

	@Override
	public int getCount()
	{
		return listReplies.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listReplies.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return listReplies.get(position).getReplyId();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		ReplyJson reply = listReplies.get(position);
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_my_posts_replies_list, null);

			holder = new ViewHolder();

			holder.tvContent = (TextView)convertView.findViewById(R.id.tv_my_posts_replies_content);
			holder.tvDate = (TextView)convertView.findViewById(R.id.tv_my_posts_replies_date);

			convertView.setTag(holder);

		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.tvContent.setText(reply.getContent());
		holder.tvDate.setText(reply.getDate().substring(0,10));

		holder.set();


		return convertView;
	}


	class ViewHolder
	{
		TextView tvContent;
		TextView tvDate;

		public void set()
		{
			if(tvContent.getText().length()>16)
			{
				tvContent.setText(tvContent.getText().subSequence(0,15) + "...");
			}
		}
	}

}
