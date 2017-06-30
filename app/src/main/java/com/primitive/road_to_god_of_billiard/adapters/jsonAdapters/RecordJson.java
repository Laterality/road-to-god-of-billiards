package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

/**
 * Created by 신진우- on 2015-10-25.
 */
public class RecordJson
{
	int RecordHost;
	int Total;
	int Win;
	int Lose;
	int WinRate;
	int Class;

	public RecordJson(int user, int tot, int win, int lose, int rate, int _class)
	{
		RecordHost = user;
		Total = tot;
		Win = win;
		Lose = lose;
		WinRate = rate;
		Class = _class;
	}

	public int getPlayer()
	{
		return RecordHost;
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

	public int getRate()
	{
		return WinRate;
	}

	public int getTier()
	{
		return Class;
	}
}
