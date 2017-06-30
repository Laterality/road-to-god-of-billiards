package com.primitive.road_to_god_of_billiard.adapters.jsonAdapters;

import android.provider.ContactsContract;
import android.util.Log;

import com.primitive.road_to_god_of_billiard.objects.UserProfile;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.io.Serializable;

/**
 * Created by 신진우- on 2015-10-13.
 */
public class UserProfileAdapter
{
	private final String TAG = "UserProfileAdapter";

	@FromJson
	UserProfile fromJson(UserProfileJson profileJson)
	{
		// TODO
		UserProfile profile = new UserProfile(profileJson.getUserCode(), profileJson.getEmail(), profileJson.getUsername(), profileJson.getTeamId());
		Log.d(TAG, "profile..." + profile);
		return profile;
	}



	@ToJson
	UserProfileJson toJson(UserProfile profile)
	{
		/*
		UserProfileJson json_ = new UserProfileJson();
		json_.E_mail = profile.getEmail();
		json_.Nickname = profile.getUserName();
		json_.Team = profile.getTeamId();
		return json_;*/
		return null;
	}



}

