package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-18.
 */
public class GameResultJson
{
	int ResultCode;
	int WinnerCode;
	int LoserCode;

	public GameResultJson(int gameCode, int winner, int loser)
	{
		ResultCode = gameCode;
		WinnerCode = winner;
		LoserCode = loser;
	}

	public int getGameCode()
	{
		return ResultCode;
	}

	public int getWinner()
	{
		return WinnerCode;
	}

	public int getLoser()
	{
		return LoserCode;
	}

}
