package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import java.io.Serializable;

/**
 * Created by 신진우- on 2015-10-19.
 */
public class PostJson implements Serializable
{
	int BoardNum=0;
	int Writer;
	String Nickname;
	String Head;
	String Body;
	String Date;
	int Like_it = 0;
	int Comment = 0;

	public PostJson(int writer, String head, String body, String date)
	{
		Writer = writer;
		Head = head;
		Body = body;
		Date = date;
	}

	public PostJson(int postId, int writer, String head, String body, String date)
	{
		BoardNum = postId;
		Writer = writer;
		Head = head;
		Body = body;
		Date = date;
	}

	public int getPostId()
	{
		return BoardNum;
	}

	public int getAuthorId()
	{
		return Writer;
	}

	public String getAuthorUsername()
	{
		return Nickname;
	}

	public String getTitle()
	{
		return Head;
	}

	public String getContent()
	{
		return Body;
	}

	public String getDate()
	{
		return Date;
	}

	public int getLike()
	{
		return Like_it;
	}

	public int getReplyCount()
	{
		return Comment;
	}
}
