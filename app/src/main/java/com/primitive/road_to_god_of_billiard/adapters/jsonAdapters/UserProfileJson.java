package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import java.io.Serializable;

/**
 * Created by 신진우- on 2015-10-13.
 */
public class UserProfileJson implements Serializable
{
	int UserCode;
	String E_mail;
	String Password;
	String Nickname;
	String Team = null;
	int Score;
	// originally, team id is int but managed with String internally for the parsing problem

	public UserProfileJson()
	{

	}

	public UserProfileJson(int UserCode, String e_mail, String password, String username, int team, int score)
	{
		E_mail = e_mail;
		Password = password;
		Nickname = username;
		Team = String.valueOf(team);
		Score = score;
	}

	public void setUserCode(int id)
	{
		UserCode = id;
	}

	public int getUserCode()
	{
		return UserCode;
	}

	public String getEmail()
	{
		return E_mail;
	}

	public String getUsername()
	{
		return Nickname;
	}

	public int getTeamId()
	{
		return Integer.valueOf(Team.isEmpty()? "0": Team);
	}

	public int getScore()
	{
		return Score;
	}
}
