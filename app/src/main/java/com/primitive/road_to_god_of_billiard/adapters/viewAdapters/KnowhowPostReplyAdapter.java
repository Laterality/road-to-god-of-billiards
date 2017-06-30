package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.fragments.KnowHowBoardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-04.
 */
public class KnowhowPostReplyAdapter extends BaseAdapter
{
	private final String TAG = "KnowhowPostRepliesAdapter";
	private List<ReplyJson> listReply;
	private Context mContext;
	private KnowHowBoardFragment.IReplyRequest replyRequest;
	private List<ReplyJson> Replies = new ArrayList<>(); // only for flag == 0
	private List<ReplyJson> Rereplies = new ArrayList<>(); // only for flag == 1

	public KnowhowPostReplyAdapter(Context context, List<ReplyJson> replies, KnowHowBoardFragment.IReplyRequest callback)
	{
		super();
		listReply = replies;
		mContext = context;
		replyRequest = callback;

		for (ReplyJson r : replies)
		{
			if (r.isRereply())
			{
				Rereplies.add(r);
			} else
			{
				Replies.add(r);
			}
		}
	}

	@Override
	public int getCount()
	{
		return Replies.size();
	}

	@Override
	public Object getItem(int position)
	{
		return Replies.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Replies.get(position).getReplyId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = new ViewHolder();
		final ReplyJson reply = Replies.get(position);

		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_knowhow_post_reply, null);


			holder.ivRereplyMark = (ImageView) convertView.findViewById(R.id.iv_knowhow_post_reply_rereply_mark);
			holder.ivProfile = (ImageView) convertView.findViewById(R.id.iv_knowhow_post_reply_profile);
			holder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_author);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_date);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_knowhow_post_reply_content);
			holder.lvRereply = (ListView) convertView.findViewById(R.id.lv_knowhow_post_rereply_list);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		if(reply.isRereply()){holder.ivRereplyMark.setVisibility(View.VISIBLE);}
		else
		{
			holder.ivRereplyMark.setVisibility(View.GONE);
			holder.tvAuthor.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					replyRequest.RequestShowReplyFunction(v, reply.getReplyId());
				}
			});
		}

		//holder.ivProfile.setImageDrawable(reply.getProfile());
		holder.tvAuthor.setText(reply.getWriterUsername());
		holder.tvDate.setText(reply.getDate().substring(0, 10));
		holder.tvContent.setText(reply.getContent());
		holder.lvRereply.setAdapter(new KnowhowPostRereplyAdapter(mContext, getRepliesByComment(reply.getReplyId())));
		setListViewHeightBasedOnChildren(holder.lvRereply);
		//Log.d(TAG, "set Reply..." + reply.getContent());



		return convertView;
	}

	private List<ReplyJson> getRepliesByComment(int replyId)
	{
		List<ReplyJson> list = new ArrayList<>();

		Log.d(TAG, "getRepliesByComment...Entry Point");
		for(ReplyJson r : Rereplies)
		{
			Log.d(TAG, "iter : " + r.getByReply() + ", comp : " + replyId);
			if(r.getByReply() == replyId)
			{
				Log.d(TAG, "add Rereply..." + r.getReplyId());
				list.add(r);
			}
		}

		return list;
	}

	private int DpToPx(float dp)
	{
		Resources r = mContext.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return (int)px;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	class ViewHolder
	{
		ImageView ivRereplyMark;
		ImageView ivProfile;
		TextView tvAuthor;
		TextView tvDate;
		TextView tvContent;
		ListView lvRereply;
	}


}
