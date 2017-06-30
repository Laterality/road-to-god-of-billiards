package com.primitive.road_to_god_of_billiard.utility;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by 신진우- on 2015-10-27.
 */
public class MyGcmListenerService extends GcmListenerService
{
	public static final String TAG = "GCMListener";
	@Override
	public void onMessageReceived(String from, Bundle data)
	{
		Log.d(TAG, "Message Received...");
		Log.d(TAG, "from " + from);
	}
}
