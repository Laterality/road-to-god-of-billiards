package com.primitive.road_to_god_of_billiard.objects;

import android.graphics.drawable.Drawable;

/**
 * Created by 신진우- on 2015-08-12.
 */
public class UserProfile
{
	public static final int GAME_PLAYER_POSITION_SENDER = 0x01;
	public static final int GAME_PLYAER_POSITION_RECEIVER = 0x02;

	private int userId;
	private String userNmae;
	private String email;
	private String userTier;
	private PlayRecord Record;
	private Drawable userImage;
	private int teamId;

	public UserProfile(int id, String email, String username, int teamid)
	{
		this.userId = id;
		this.email = email;
		this.userNmae = username;
		this.teamId = teamid;
	}

	public int getUserId()
	{
		return userId;
	}

	public String getUserName()
	{
		return userNmae;
	}

	public String getEmail()
	{
		return email;
	}

	public String getUserTier()
	{
		return userTier;
	}

	public Drawable getUserDrawable()
	{
		return userImage;
	}
	public PlayRecord getRecord()
	{
		return Record;
	}

	public int getTeamId()
	{
		return teamId;
	}

	public class PlayRecord
	{
		private int playTotal;
		private int playWin;
		private int playLose;
		private int playSuspended;
		private float Rate;
		private int lastPlay = -1;

		public static final int ERR_RATING_ZERO = -1;

		public PlayRecord(int total, int win, int lose, int susp)
		{
			playTotal = total;
			playWin = win;
			playLose = lose;
			playSuspended = susp;
			Rate = getRate();
		}

		public PlayRecord()
		{
			playTotal = 0;
			playWin = 0;
			playLose = 0;
			playSuspended = 0;
			Rate = 0;
		}

		public float getRate()
		{
			if (playTotal <= 0)
			{
				return ERR_RATING_ZERO;
			}
			return playWin / playTotal;
		}

		public int getPlayTotal()
		{
			return playTotal;
		}

		public int getWin()
		{
			return playWin;
		}

		public int getLose()
		{
			return playLose;
		}

		public int getPlaySuspended()
		{
			return playSuspended;
		}


		public void addWin()
		{
			playTotal++;
			playWin++;
			Rate = getRate();
		}

		public void addLose()
		{
			playTotal++;
			playLose++;
			Rate = getRate();
		}

		public void addSuspended()
		{
			playTotal++;
			playSuspended++;
			Rate = getRate();
		}
	}
}
