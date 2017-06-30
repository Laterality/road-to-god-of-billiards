package com.primitive.road_to_god_of_billiard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GamingJson;
import com.primitive.road_to_god_of_billiard.objects.UserProfile;
import com.primitive.road_to_god_of_billiard.utility.ActivityParams;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;
import com.primitive.road_to_god_of_billiard.views.ScoreBoardView;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-09.
 */
public class GameActivity extends AppCompatActivity
{
	private final String TAG = "Activity_GameActivity_";


	private Chronometer chTimer;
	private ImageButton ibtnScoreUp;
	private ImageButton ibtnScoreDown;
	private TextView tvMyScore;
	private TextView tvOpScore;
	private ScoreBoardView sbMyScore;
	private ScoreBoardView sbOpScore;

	private int gameCode;
	private int sender;
	private int receiver;
	private int start;
	private int gameType;

	private int myId;
	private int opId;
	private int myPos;
	private String myUsername;
	private String opUsername;

	private static final long REFRESH_INTERVAL = 3000; // ms unit



	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "GameActivity...Entry Point");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//
		// BEGIN : get Args from MainActivity
		//
		Log.d(TAG, "GameActivity...get Arguments from MainActivity");
		Intent intent = getIntent();
		gameCode = intent.getIntExtra(ActivityParams.PARAM_GAMECODE, 0); Log.d(TAG, "GameCode : " + gameCode);
		sender = intent.getIntExtra(ActivityParams.PARAM_SENDER, 0); Log.d(TAG, "sender : " + sender);
		receiver = intent.getIntExtra(ActivityParams.PARAM_RECEIVER, 0); Log.d(TAG, "receiver : " + receiver);
		start = intent.getIntExtra(ActivityParams.PARAM_START, 0); Log.d(TAG, "start : " + start);
		myPos = intent.getIntExtra(ActivityParams.PARAM_MY_POSITION, 0); Log.d(TAG, "my Pos : " + (myPos == UserProfile.GAME_PLAYER_POSITION_SENDER? "Sender" : "Receiver"));
		gameType = intent.getIntExtra(ActivityParams.PARAM_GAME_TYPE, 0);

		if(myPos == UserProfile.GAME_PLAYER_POSITION_SENDER )
		{
			myId = sender;
			opId = receiver;
			myUsername = intent.getStringExtra(ActivityParams.PARAM_SENDER_USERNAME);
			opUsername = intent.getStringExtra(ActivityParams.PARAM_RECEIVER_USERNAME);
		}
		else if(myPos == UserProfile.GAME_PLYAER_POSITION_RECEIVER)
		{
			myId = receiver;
			opId = sender;
			myUsername = intent.getStringExtra(ActivityParams.PARAM_RECEIVER_USERNAME);
			opUsername = intent.getStringExtra(ActivityParams.PARAM_SENDER_USERNAME);
		}

		//
		// END : get Args from MainActivity
		//

		//
		// BEGIN : Notifying Game start (put status 1 to 0)
		//
		PutNotifyGameStart put = new PutNotifyGameStart();
		put.execute(new GameJson(gameCode, start, sender, receiver, 0));
		//
		// END : Notifying Game start (put status 1 to 0)
		//

		//
		// BEGIN : setting view
		//
		chTimer = (Chronometer) findViewById(R.id.ch_game_timer);
		chTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
		{
			@Override
			public void onChronometerTick(Chronometer chronometer)
			{
				long time = SystemClock.elapsedRealtime() - chronometer.getBase();
				int h = (int) (time / 3600000);
				int m = (int) (time - h * 3600000) / 60000;
				int s = (int) (time - h * 3600000 - m * 60000) / 1000;
				String hh = h < 10 ? "0" + h : h + "";
				String mm = m < 10 ? "0" + m : m + "";
				String ss = s < 10 ? "0" + s : s + "";
				chronometer.setText(hh + ":" + mm + ":" + ss);
			}
		});
		chTimer.start();

		ibtnScoreUp = (ImageButton) findViewById(R.id.ibtn_game_score_up);
		ibtnScoreDown = (ImageButton) findViewById(R.id.ibtn_game_score_down);
		tvMyScore = (TextView) findViewById(R.id.tv_game_my_score);
		tvOpScore = (TextView) findViewById(R.id.tv_game_op_score);
		sbMyScore = (ScoreBoardView) findViewById(R.id.sb_game_my_score);
		sbOpScore = (ScoreBoardView) findViewById(R.id.sb_game_op_score);

		sbMyScore.setLimit(start);
		sbMyScore.setScore(start);
		ibtnScoreUp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (sbMyScore.getScore() <= 290)
				{
					sbMyScore.setScore(sbMyScore.getScore() + 10);
					tvMyScore.setText((sbMyScore.getScore()) + "");
					ScoreUpdate update = new ScoreUpdate();
					update.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			}
		});
		ibtnScoreDown.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (sbMyScore.getScore() >= 10)
				{
					sbMyScore.setScore(sbMyScore.getScore() - 10);
					tvMyScore.setText((sbMyScore.getScore()) + "");
					ScoreUpdate update = new ScoreUpdate();
					update.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			}
		});
		sbMyScore.setLimit(start);
		sbMyScore.setScore(start);
		sbOpScore.setLimit(start);
		sbOpScore.setScore(start);
		tvMyScore.setText(String.valueOf(start));
		tvOpScore.setText(String.valueOf(start));
		sbMyScore.setOnScoreChangeListener(new ScoreBoardView.onScoreChangeListener()
		{
			@Override
			public void onChange(View v, int presentScore)
			{
				if(presentScore == 0)
				{
					// TODO : set Result
					finishGame(new GameJson(gameCode, start, sender, receiver, 0), myId);
				}
			}
		});

		sbOpScore.setOnScoreChangeListener(new ScoreBoardView.onScoreChangeListener()
		{
			@Override
			public void onChange(View v, int presentScore)
			{
				if(presentScore == 0)
				{
					// TODO : set Result
					finishGame(new GameJson(gameCode, start, sender, receiver, 0), opId);
				}
			}
		});
		//
		// END : setting view
		//


		new RefreshScore().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, gameType);

	}

	private void changeOpScore(int op)
	{
		Log.d(TAG, "change Op Score ..." + op);
		sbOpScore.setScore(op);sbOpScore.invalidate();
		tvOpScore.setText(String.valueOf(op));

	}

	private void finishGame(GameJson game, int winnerId)
	{
		Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
		intent.putExtra(ActivityParams.PARAM_GAME_TYPE, game);
		intent.putExtra(ActivityParams.PARAM_WINNER, winnerId);
		startActivity(intent);
		finish();
	}

	private int getSenderScore()
	{
		if(myPos == UserProfile.GAME_PLYAER_POSITION_RECEIVER)
		{
			return sbOpScore.getScore();
		}
		else
		{
			return sbMyScore.getScore();
		}
	}

	private int getReceiverScore()
	{
		if(myPos == UserProfile.GAME_PLAYER_POSITION_SENDER)
		{
			return sbOpScore.getScore();
		}
		else
		{
			return sbMyScore.getScore();
		}
	}


	public interface ScoreRefreshService
	{
		@GET(ServerInfo.API_GAMING_GET)
		Call<GamingJson> pvpScoreRefresh(@Path(ServerInfo.API_GAMING_PATH) int gamingCode);

	}

	private class RefreshScore extends AsyncTask<Integer, GamingJson, Void>
	{
		@Override
		protected Void doInBackground(Integer... type)
		{
			while(true)
			{
				try
				{
					Log.d(TAG, "RefreshScore...Entry Point");
					Retrofit retrofit = new Retrofit.Builder()
							.baseUrl(ServerInfo.BASE_URL)
							.addConverterFactory(GsonConverterFactory.create())
							.build();

					ScoreRefreshService service = retrofit.create(ScoreRefreshService.class);

					Call<GamingJson> call = service.pvpScoreRefresh(gameCode);
					retrofit.Response res = call.execute();
					if (res.isSuccess())
					{
						Log.d(TAG, "Refresh Request Success...");
						final GamingJson score = (GamingJson) res.body();
						Log.d(TAG, "GammingCode : " + score.getGammingCode());
						Log.d(TAG, "Sender : " + score.getSenderScore());
						Log.d(TAG, "Receiver : " + score.getReceiverScore());
						publishProgress(score);
						Log.d(TAG, "Progress published");

					} else
					{
						Log.d(TAG, "Refresh Fail...");
						Log.d(TAG, res.code() + " : " + res.message());

					}

				} catch (Exception e)
				{
					Log.d(TAG, e.getMessage());
					break;
				}

				try{Thread.sleep(REFRESH_INTERVAL);}catch (Exception e){Log.d(TAG, "wait Exception : " + e.getMessage());}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(GamingJson... score)
		{
			try
			{
				Log.d(TAG, "onProgressUpdate...Entry Point");

				if (myPos == UserProfile.GAME_PLAYER_POSITION_SENDER)
				{
					changeOpScore(score[0].getReceiverScore());
				} else if (myPos == UserProfile.GAME_PLYAER_POSITION_RECEIVER)
				{
					changeOpScore(score[0].getSenderScore());
				}
				Log.d(TAG, "Score Refreshed...");

				/*
				if (myPos == UserProfile.GAME_PLAYER_POSITION_SENDER)
				{
					Log.d(TAG, "my Position == Sender ...");
					sbOpScore.setScore(score[0].getReceiverScore());
				} else if (myPos == UserProfile.GAME_PLYAER_POSITION_RECEIVER)
				{
					Log.d(TAG, "my Position == Receiver ...");
					sbOpScore.setScore(score[0].getSenderScore());
				}
				else
				{
					Log.d(TAG, "Unknown player position");
					Log.d(TAG, "myPos : " + myPos);
				}*/
				//RefreshScoreSend ref = new RefreshScoreSend();
				//ref.executeOnExecutor(THREAD_POOL_EXECUTOR, score[0].getSenderScore(), score[0].getReceiverScore());
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred updating view");
				Log.d(TAG, e.getMessage());
			}
			super.onProgressUpdate(score);
		}
	}

	public interface SenderScoreUpdateService
	{
		@PUT(ServerInfo.API_GAMING_SENDER_PUT)
		Call<GamingJson> updateScore(@Path(ServerInfo.API_SENDER_SCORE_UPDATE_PATH) int gamingCode, @Body GamingJson arg);
	}

	public interface ReceiverScoreUpdateService
	{
		@PUT(ServerInfo.API_GAMING_RECEIVER_PUT)
		Call<GamingJson> updateScore(@Path(ServerInfo.API_RECEIVER_SCORE_UPDATE_PATH) int gamingCode, @Body GamingJson arg);
	}

	private class ScoreUpdate extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... param)
		{
			Log.d(TAG, "ScoreUpdate...Entry Point");
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				if(myPos == UserProfile.GAME_PLAYER_POSITION_SENDER)
				{
					Log.d(TAG, "update sender score...");
					SenderScoreUpdateService service = retrofit.create(SenderScoreUpdateService.class);
					Call<GamingJson> call = service.updateScore(gameCode, new GamingJson(gameCode, start, getSenderScore(), getReceiverScore()));
					Response res = call.execute();
					if(res.isSuccess())
					{
						Log.d(TAG, "score update success");
					}
					else
					{
						Log.d(TAG, "score update fail");
					}
					Log.d(TAG, res.code() + " : " + res.message());
				}
				else
				{
					Log.d(TAG, "update receiver score...");
					ReceiverScoreUpdateService service = retrofit.create(ReceiverScoreUpdateService.class);
					Call<GamingJson> call = service.updateScore(gameCode, new GamingJson(gameCode, start, getSenderScore(), getReceiverScore()));
					Response res = call.execute();
					if(res.isSuccess())
					{
						Log.d(TAG, "score update success");
					}
					else
					{
						Log.d(TAG, "score update fail");
					}
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

	public interface GameNotifyingService
	{
		@PUT(ServerInfo.API_GAME_PUT)
		Call<GameJson> putGameJson(@Path(ServerInfo.API_GAME_PATH) int gameCode ,@Body GameJson json);
	}

	private class PutNotifyGameStart extends AsyncTask<GameJson, Void, Void>
	{
		@Override
		protected Void doInBackground(GameJson... game)
		{
			try
			{
				Log.d(TAG, "NotifyingGameStart...");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GameNotifyingService service = retrofit.create(GameNotifyingService.class);

				Log.d(TAG, "PUT...");
				Log.d(TAG, "GameCode : " + game[0].getGameCode());
				Log.d(TAG, "Sender : " + game[0].getSender());
				Log.d(TAG, "Receiver : " + game[0].getReceiver());

				Call<GameJson> call = service.putGameJson(game[0].getGameCode(), game[0]);
				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Put is Success...");
					Log.d(TAG, res.code() + " : " + res.message());
				}
				else
				{
					Log.d(TAG, "Put is fail...");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred...");
				Log.d(TAG, e.getMessage());
			}
			return null;
		}
	}


	private class RefreshScoreSend extends AsyncTask<Integer, Void, Void>
	{
		@Override
		protected Void doInBackground(final Integer... param)
		{
			Log.d(TAG, "RefreshScoreSend...Entry Point");
			Runnable r = new Runnable()
			{
				@Override
				public void run()
				{
					if (myPos == UserProfile.GAME_PLAYER_POSITION_SENDER)
					{
						sbOpScore.setScore(param[1]);
					} else if (myPos == UserProfile.GAME_PLYAER_POSITION_RECEIVER)
					{
						sbOpScore.setScore(param[0]);
					}
				}
			};
			GameActivity.this.runOnUiThread(r);

			return null;
		}
	}

}
