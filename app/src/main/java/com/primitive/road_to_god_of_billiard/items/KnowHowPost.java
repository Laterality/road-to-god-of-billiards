package com.primitive.road_to_god_of_billiard.items;

import android.graphics.drawable.Drawable;

/**
 * Created by 신진우- on 2015-09-23.
 */
public class KnowHowPost
{
	private int Id;
	private Drawable image;
	private String Title;
	private String Content;
	private String Author;
	private int cntReply;
	private int cntLike;

	public KnowHowPost(int id, Drawable img, String title, String content, String author, int reply, int like)
	{
		Id = id;
		image = img;
		Title = title;
		Content = content;
		Author = author;
		cntReply = reply;
		cntLike = like;
	}

	public int getId()
	{
		return Id;
	}

	public Drawable getImage()
	{
		return image;
	}

	public String getTitle()
	{
		return Title;
	}

	public String getContent()
	{
		return Content;
	}

	public String getAuthor()
	{
		return Author;
	}

	public int getCntReply()
	{
		return cntReply;
	}

	public int getCntLike()
	{
		return cntLike;
	}
}

