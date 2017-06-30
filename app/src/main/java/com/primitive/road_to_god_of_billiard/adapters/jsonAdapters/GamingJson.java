package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-14.
 * For Refreshing game in process
 * get Scores from server
 */
public class GamingJson
{
	int GammingCode;
	int GameScore;
	int SendUserScore;
	int ReceiveUserScore;

	public GamingJson(int gamingCode,int start, int senderScore, int receiverScore)
	{
		GammingCode = gamingCode;
		GameScore = start;
		SendUserScore = senderScore;
		ReceiveUserScore = receiverScore;
	}

	public int getGammingCode()
	{
		return GammingCode;
	}

	public int getStart()
	{
		return GameScore;
	}

	public int getSenderScore()
	{
		return SendUserScore;
	}

	public int getReceiverScore()
	{
		return ReceiveUserScore;
	}

}
