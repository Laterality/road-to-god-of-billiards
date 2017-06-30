package com.primitive.road_to_god_of_billiard.items;

import android.graphics.drawable.Drawable;

/**
 * Created by 신진우- on 2015-10-04.
 */
public class KnowHowPostReply
{
	private boolean isRereply = false;
	private Drawable replyProfile;
	private long replyId;
	private long postId;
	private String replyAuthor;
	private String replyDate;
	private String replyContent;

	public KnowHowPostReply(boolean isRere, Drawable profile, long id, long post, String author, String date, String content)
	{
		isRereply = isRere;
		replyProfile = profile;
		replyId = id;
		postId = post;
		replyAuthor = author;
		replyDate = date;
		replyContent = content;
	}

	public boolean isRereply()
	{
		return isRereply;
	}

	public Drawable getProfile()
	{
		return replyProfile;
	}

	public long getId()
	{
		return replyId;
	}

	public long getPostId()
	{
		return postId;
	}

	public String getAuthor()
	{
		return replyAuthor;
	}

	public String getDate()
	{
		return replyDate;
	}

	public String getContent()
	{
		return replyContent;
	}

}
