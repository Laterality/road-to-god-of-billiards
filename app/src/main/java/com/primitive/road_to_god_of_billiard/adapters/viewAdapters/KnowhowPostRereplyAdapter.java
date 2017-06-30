package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;

import java.util.List;
import java.util.Objects;

/**
 * Created by 신진우- on 2015-10-23.
 */
public class KnowhowPostRereplyAdapter extends BaseAdapter
{
	private final String TAG = "KnowhowPostRereplyAdapter";
	private Context mContext;
	private List<ReplyJson> Rereplies;

	public KnowhowPostRereplyAdapter(Context context, List<ReplyJson> rereplies)
	{
		super();

		Log.d(TAG, "RereplyAdapter...Entry Point");
		mContext = context;
		Rereplies = rereplies;
	}

	@Override
	public int getCount()
	{
		Log.d(TAG, "getCount()...return " + Rereplies.size());
		return Rereplies.size();
	}

	@Override
	public Object getItem(int position)
	{
		return Rereplies.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Rereplies.get(position).getReplyId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		Log.d(TAG, "getView..." + position);
		ReplyJson rereply = Rereplies.get(position);
		if(convertView == null)
		{
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_knowhow_post_reply, null);

			holder.ivRereplyMark = (ImageView) convertView.findViewById(R.id.iv_knowhow_post_reply_rereply_mark);
			holder.ivProfile = (ImageView) convertView.findViewById(R.id.iv_knowhow_post_reply_profile);
			holder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_author);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_date);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_content);


			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		if(rereply.isRereply()){holder.ivRereplyMark.setVisibility(View.VISIBLE);}
		//holder.ivProfile.setImageDrawable(reply.getProfile());
		holder.tvAuthor.setText(rereply.getWriterUsername());
		holder.tvDate.setText(rereply.getDate().substring(0,10));
		holder.tvContent.setText(rereply.getContent());

		return convertView;
	}


	class ViewHolder
	{
		ImageView ivRereplyMark;
		ImageView ivProfile;
		TextView tvAuthor;
		TextView tvDate;
		TextView tvContent;
	}
}
