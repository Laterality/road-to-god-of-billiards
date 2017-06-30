package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.items.KnowHowPost;
import com.primitive.road_to_god_of_billiard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-09-23.
 */
public class BoardAdapter extends BaseAdapter
{
	private final String TAG = "BoardAdapter";
	private Context mContext;
	private List<PostJson> Items = new ArrayList<>();
	private boolean IsPop;
	private int headPostId = 0;
	private int tailPostId = 0;

	public BoardAdapter(Context context, List<PostJson> posts, boolean isPop)
	{
		super();

		IsPop = isPop;
		mContext = context;
		Items = posts;
		if((Items != null)&&(Items.size() != 0))
		{
			headPostId = Items.get(0).getPostId();
			tailPostId = Items.get(Items.size() - 1).getPostId();
		}
		Log.d(TAG, "headId : " + headPostId + ", tailId : " + tailPostId);
	}

	@Override
	public int getCount()
	{
		//Log.d(TAG, "getCount...return " + Items.size());
		return Items.size();
	}

	@Override
	public Object getItem(int position)
	{
		//Log.d(TAG, "getItem()...position " + position);
		return Items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		//Log.d(TAG, "getItemId...position " + position);
		return Items.get(position).getPostId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//Log.d(TAG, "getView()...position " + position);
		ViewHolder viewHolder = new ViewHolder();

		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if(IsPop){convertView = inflater.inflate(R.layout.item_knowhow_pop_post, null);}
			else{convertView = inflater.inflate(R.layout.item_knowhow_latest_post,null);}

			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_board_knowhow_item_image);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_board_knowhow_item_title);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_board_knowhow_item_content);
			viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_board_knowhow_author_username);
			viewHolder.tvCntReply = (TextView) convertView.findViewById(R.id.tv_board_knowhow_item_count_reply);
			viewHolder.tvCntLike = (TextView) convertView.findViewById(R.id.tv_board_knowhow_item_count_like);

			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		// TODO : set view of items
		//viewHolder.ivImage.setImageDrawable(Items.get(position).getImage());
		viewHolder.tvTitle.setText(Items.get(position).getTitle());
		viewHolder.tvContent.setText(Items.get(position).getContent());
		viewHolder.tvAuthor.setText("By " + Items.get(position).getAuthorUsername());
		viewHolder.tvCntReply.setText(Items.get(position).getReplyCount() + "");
		viewHolder.tvCntLike.setText(Items.get(position).getLike() + "");
		viewHolder.filter();

		return convertView;
	}

	public void insertListToHead(List<PostJson> list)
	{
		for(int i = list.size()-1;i>=0;i--)
		{
			if(list.get(i).getPostId() <= getHeadId()){break;}
			Items.add(0, list.get(i));
			headPostId = list.get(i).getPostId();
		}
		tailPostId = Items.get(Items.size()-1).getPostId();
		notifyDataSetChanged();
	}

	public void appendList(List<PostJson> list)
	{
		Log.d(TAG,"appendList...Entry Point");
		if(list.size() >0){	tailPostId = list.get(list.size()-1).getPostId(); }
		for(PostJson p : list)
		{
			Log.d(TAG, "p.getPostId() returns " + p.getPostId());
			if(p.getPostId() == getTailId()){break;}
			Items.add(p);
			tailPostId = p.getPostId();
			Log.d(TAG, "tailPostId : " + tailPostId);
		}
		notifyDataSetChanged();
	}

	public int getTailId()
	{
		return tailPostId;
	}

	public int getHeadId()
	{
		return headPostId;
	}


	class ViewHolder
	{
		ImageView ivImage;
		TextView tvTitle;
		TextView tvContent;
		TextView tvAuthor;
		TextView tvCntReply;
		TextView tvCntLike;

		public void filter()
		{
			String temp;
			if(tvTitle.getText().toString().length()>=12)
			{
				temp = tvTitle.getText().toString().substring(0, 10);
				tvTitle.setText(temp + "...");
			}

			if(tvAuthor.getText().toString().length()>=16)
			{
				temp = tvAuthor.getText().toString().substring(0, 10);
				tvAuthor.setText(tvAuthor.getText().subSequence(0, 10) + "...");
			}

			if(tvContent.getText().toString().length()>=75)
			{
				temp = tvContent.getText().toString().substring(0, 10);
				tvContent.setText(tvContent.getText().subSequence(0, 74) + "...");
			}

			if(Integer.valueOf(String.valueOf(tvCntReply.getText()))>=100)
			{
				tvCntReply.setText("99+");
			}

			if(Integer.valueOf(String.valueOf(tvCntLike.getText()))>=100)
			{
				tvCntLike.setText("99+");
			}
		}
	}
}

