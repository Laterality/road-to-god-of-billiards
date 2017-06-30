package com.primitive.road_to_god_of_billiard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameResultJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GamingJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;
import com.primitive.road_to_god_of_billiard.utility.ActivityParams;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import org.w3c.dom.Text;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-18.
 */
public class ResultActivity extends AppCompatActivity
{
	private static final String TAG = "Activity_ResultActivity";
	private int senderId;
	private int receiverId;
	private int winner;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent intent = getIntent();
		Log.d(TAG, "Get Args...");
		GameJson gameJson = (GameJson)intent.getSerializableExtra(ActivityParams.PARAM_GAME_TYPE);
		winner = intent.getIntExtra(ActivityParams.PARAM_WINNER, 0);
		senderId = gameJson.getSender();
		receiverId = gameJson.getReceiver();
		Log.d(TAG, "Game Info...");
		Log.d(TAG, "GameCode : " + gameJson.getGameCode());
		Log.d(TAG, "Sender : " + gameJson.getSender());
		Log.d(TAG, "Receiver : " + gameJson.getReceiver());
		Log.d(TAG, "Winner : " + winner);
		GameResultJson resultJson = new GameResultJson(gameJson.getGameCode(), winner, winner == senderId ? receiverId : senderId);
		PostResult postResult = new PostResult();
		postResult.execute(resultJson);
	}



	public interface PostResultService
	{
		@POST(ServerInfo.API_RESULT_POST)
		Call<GameResultJson> postResult(@Body GameResultJson result);
	}

	private class PostResult extends AsyncTask<GameResultJson, Void, Void>
	{
		@Override
		protected Void doInBackground(GameResultJson... result)
		{
			try
			{
				Log.d(TAG, "Posting Result...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostResultService service = retrofit.create(PostResultService.class);
				Call<GameResultJson> call = service.postResult(result[0]);
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "POST Result Success");
					Log.d(TAG, res.code() + " : " + res.message());
				}
				else
				{
					Log.d(TAG, "POST Result Fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}
	}

	public interface GetUserProfileService
	{
		@GET(ServerInfo.API_USER_GET)
		Call<UserProfileJson> getProfile(@Path(ServerInfo.API_USER_PATH) int id);

	}
	private class SetUserNames extends AsyncTask<Integer, UserProfileJson, Void>
	{
		@Override
		protected Void doInBackground(Integer... id)
		{
			Log.d(TAG, "Getting User Profile...Entry Point");
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetUserProfileService service = retrofit.create(GetUserProfileService.class);
				Call<UserProfileJson> call_winner = service.getProfile(id[0]);
				Call<UserProfileJson> call_loser = service.getProfile(id[1]);

				Response res_winner = call_winner.execute();
				Response res_loser = call_loser.execute();

				if(res_winner.isSuccess()&&res_loser.isSuccess())
				{
					Log.d(TAG, "Getting Profile success");

				}

			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception Occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected  void onProgressUpdate(UserProfileJson... profiles)
		{
			Log.d(TAG, "Winner : " + profiles[0].getUsername());
			Log.d(TAG, "Loser : " + profiles[1].getUsername());
		}
	}

}
