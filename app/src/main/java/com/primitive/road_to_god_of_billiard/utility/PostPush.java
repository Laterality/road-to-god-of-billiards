package com.primitive.road_to_god_of_billiard.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PushJson;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-10-25.
 */
public class PostPush extends AsyncTask<PushJson, Void, Void>
{
	@Override
	protected Void doInBackground(PushJson... push)
	{
		try
		{
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(ServerInfo.BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();

			PostPushService service = retrofit.create(PostPushService.class);

			Call<PushJson> call = service.postPush(push[0]);

			Response res = call.execute();

			if(res.isSuccess())
			{
				Log.d("PostPush", "Execution success");

			}
			else
			{
				Log.d("PostPush", "Execution fail");
				Log.d("PostPush", res.code() + " : " + res.message());
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}
}
interface PostPushService
{
	@POST("/4Billion/api/Push/")
	Call<PushJson> postPush(@Body PushJson push);
}