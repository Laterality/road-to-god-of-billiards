package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-27.
 */
public class TeamInfoJson
{
	String TeamName;
	String Introduction;
	int Leader;
	int WinRate;
	int LoseRate;

	public String getTeamName()
	{
		return TeamName;
	}

	public String getDesc()
	{
		return Introduction;
	}

	public int getLeader()
	{
		return Leader;
	}

	public int getWinRate()
	{
		return WinRate;
	}
}
