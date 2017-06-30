package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-11.
 */
public class SearchGamePlayerJson
{

	long UserCode;
	String Nickname;
	int Score;
	int WinRate;
	String TopScore;
	int Total;
	int Win;
	int Lose;


	public SearchGamePlayerJson(long id, String name, String tier, int rank, int rate, int avg, int hi)
	{
		UserCode = id;
		Nickname = name;
		WinRate = rate;
		TopScore = String.valueOf(hi);
	}

	public long getUserId()
	{
		return UserCode;
	}

	public String getUsername()
	{
		return Nickname;
	}

	public int getScore()
	{
		return Score;
	}

	public int getRate()
	{
		return WinRate;
	}

	public int getHiScore()
	{
		return TopScore==null||TopScore.equals("")? 0:Integer.valueOf(TopScore);
	}

	public int getTotal()
	{
		return Total;
	}

	public int getWin()
	{
		return Win;
	}

	public int getLose()
	{
		return Lose;
	}
}
