package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 신진우- on 2015-10-21.
 */
public class ReplyJson
{
	int Comment_Code;
	int Board_Code;
	int Comment_UserCode;
	String Nickname = null;
	String Body;
	String Time;
	int ByComment;
	int Flag;

	public ReplyJson(int postId, int authorId, String content)
	{
		Board_Code = postId;
		Comment_UserCode = authorId;
		Body = content;
		Flag = 0;
		Date d = new Date();
		Time = new SimpleDateFormat("yyyy-MM-dd").format(d);
	}

	public ReplyJson(int postId, int authorId, String content,int repliedCommentId)
	{
		Board_Code = postId;
		Comment_UserCode = authorId;
		Body = content;
		ByComment = repliedCommentId;
		Flag = 1;
	}

	public int getReplyId()
	{
		return Comment_Code;
	}

	public int getParentPostId()
	{
		return Board_Code;
	}

	public int getWriterId()
	{
		return Comment_UserCode;
	}

	public String getWriterUsername()
	{
		return Nickname;
	}

	public String getContent()
	{
		return Body;
	}

	public String getDate()
	{
		return Time;
	}

	public int getByReply()
	{
		return ByComment;
	}

	public boolean isRereply()
	{
		return Flag == 1;
	}


}
