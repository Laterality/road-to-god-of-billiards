package com.primitive.road_to_god_of_billiard.utility;

/**
 * Created by 신진우- on 2015-10-18.
 */
public class ActivityParams
{
	public static final int PARAM_GAME_PVP = 0x01;
	public static final int PARAM_GAME_TVT = 0x02;

	// For MainActivity to GameActivity
	public static final String PARAM_GAMECODE = "GameCode";
	public static final String PARAM_SENDER = "Sender";
	public static final String PARAM_RECEIVER = "Receiver";
	public static final String PARAM_START = "Start";
	public static final String PARAM_MY_POSITION = "Position";
	public static final String PARAM_SENDER_USERNAME = "SenderUsername";
	public static final String PARAM_RECEIVER_USERNAME = "ReceiverUsername";

	// For GameActivity to ResultActivity
	public static final String PARAM_GAME_TYPE = "GameType";
	public static final String PARAM_WINNER = "Winner";
}
