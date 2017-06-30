package com.primitive.road_to_god_of_billiard;

/**
 * Created by 신진우- on 2015-08-12.
 */

import com.primitive.road_to_god_of_billiard.objects.UserProfile;

/**
 * this class is Managing Connection between App Process and DB
 */
public class ProfileManager
{
	private static int initFlag = 0;
	private static int UserId = -1;
	private UserProfile profile;

	public static final int ERR_NOT_INITIALIZED = 0x20;
	public static final int SUCCESS_INITIALIZED= 0x10;
	public static final int FAILED_INITIALIZE = 0x21;

	public int init(int userId)
	{
		if(initFlag == 0)
		{
			initFlag++;
			UserId = userId;

			// get profile from json

			return SUCCESS_INITIALIZED;
		}
		else
		{
			return FAILED_INITIALIZE;
		}
	}



}
