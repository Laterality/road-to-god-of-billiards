package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-11.
 */
public class SearchGameTeamJson
{

	long TeamCode;
	String TeamName;
	String NickName;
	String Introduction;
	int TeamScore;
	int TeamCount;
	int WinRate;
	//public int teamAvg;

	public SearchGameTeamJson(long id, String name, String leader, String desc, int score, int count, int rate)
	{
		TeamName = name;
		NickName = leader;
		Introduction = desc;
		TeamCount = count;
		WinRate = rate;
		TeamScore = score;
	}

	public long getTeamId()
	{
		return TeamCode;
	}

	public String getTeamName()
	{
		return TeamName;
	}

	public String getLeaderUsername()
	{
		return NickName;
	}

	public String getDesc()
	{
		return Introduction;
	}

	public int getTeamScore()
	{
		return TeamScore;
	}

	public int getTeamCount()
	{
		return TeamCount;
	}

	public int getTeamRate()
	{
		return WinRate;
	}

}
