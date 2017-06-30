package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import com.google.android.gms.games.internal.player.PlayerColumnNames;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class FriendJson
{
	int FriendCode;
	int Request;
	String Nickname;
	String Team;
	String Teamname;

	public FriendJson(int receiver, int request_code)
	{
		FriendCode = receiver;
		Request = request_code;
	}

	public int getFriendId()
	{
		return FriendCode;
	}

	public String getFriendUsername()
	{
		return Nickname;
	}

	public String getFriendTeamname()
	{
		return Teamname;
	}

	public int getTeamId()
	{
		return Team == null? 0 : Integer.valueOf(Team);
	}
}
