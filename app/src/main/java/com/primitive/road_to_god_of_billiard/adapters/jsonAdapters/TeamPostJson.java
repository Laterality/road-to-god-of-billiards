package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-26.
 */
public class TeamPostJson
{
	String TeamName;
	int Leader;
	String Introduction;
	int TeamScore;

	public TeamPostJson(String teamName, int leaderId, String description, int score)
	{
		TeamName = teamName;
		Leader = leaderId;
		Introduction = description;
		TeamScore = score;
	}
}
