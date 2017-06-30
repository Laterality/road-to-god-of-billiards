package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-25.
 */
public class SearchUserJson
{
	int UserCode;
	String Nickname;

	public SearchUserJson(int id, String username)
	{
		UserCode = id;
		Nickname = username;
	}

	public int getUserCode()
	{
		return UserCode;
	}

	public String getUsername()
	{
		return Nickname;
	}
}
