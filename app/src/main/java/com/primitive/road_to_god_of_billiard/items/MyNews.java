package com.primitive.road_to_god_of_billiard.items;

/**
 * Created by 신진우- on 2015-10-07.
 */
public class MyNews
{

	private long newsId;
	private int newsCode;
	private String newsMessage;
	private String newsDate;
	private boolean removeFlag = false;

	public static final int MY_NEWS_REPLY = 0x11;
	public static final int MY_NEWS_REREPLY = 0x12;

	public MyNews(long id, int code, String message, String date)
	{
		newsId = id;
		newsCode = code;
		newsMessage = message;
		newsDate = date;
	}

	public long getNewsId()
	{
		return newsId;
	}

	public int getNewsCode()
	{
		return newsCode;
	}

	public String getNewsMessage()
	{
		return newsMessage;
	}

	public String getNewsDate()
	{
		return newsDate;
	}

	public boolean getRemoveFlag()
	{
		return removeFlag;
	}

	public void setRemoveFlag(boolean flag)
	{
		removeFlag = flag;
	}
}
