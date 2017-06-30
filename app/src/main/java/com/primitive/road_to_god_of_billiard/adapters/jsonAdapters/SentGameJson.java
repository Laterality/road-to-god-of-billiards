package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-17.
 */
public class SentGameJson
{
	int GameCode;
	String E_mail;
	String Password;
	String Nickname;
	int Team;

	public SentGameJson(int code, String email, String passwd, String username, int team)
	{
		GameCode = code;
		E_mail = email;
		Password = passwd;
		Nickname = username;
		Team = team;
	}

	public int getGameCode()
	{
		return GameCode;
	}

	public String getEmail()
	{
		return E_mail;
	}

	public String Password()
	{
		return Password;
	}

	public String getUsername()
	{
		return Nickname;
	}

	public int getTeam()
	{
		return Team;
	}

}
