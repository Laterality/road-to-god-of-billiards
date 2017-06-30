package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import java.io.Serializable;

/**
 * Created by 신진우- on 2015-10-15.
 */
public class GameJson implements Serializable
{
	int GameCode = -1;
	int Score;
	int SendUser;
	int ReceiveUser;
	int Status = 1;

	public GameJson(int start, int sender, int receiver)
	{
		Score = start;
		SendUser = sender;
		ReceiveUser = receiver;
	}

	public GameJson(int code, int start,int sender, int receiver, int status)
	{
		GameCode = code;
		Score = start;
		SendUser = sender;
		ReceiveUser = receiver;
		Status = status;
	}

	public int getGameCode()
	{
		return GameCode;
	}

	public int getStart()
	{
		return Score;
	}

	public int getSender()
	{
		return Integer.valueOf(SendUser);
	}

	public int getReceiver()
	{
		return Integer.valueOf(ReceiveUser);
	}
}
