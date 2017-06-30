package com.primitive.road_to_god_of_billiard.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PushJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.RecordJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchUserJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchUserPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamReiterationPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamSignInJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.TeamMemberListAdapter;
import com.primitive.road_to_god_of_billiard.fragments.LeftDrawerFragment;
import com.primitive.road_to_god_of_billiard.fragments.RightDrawerFragment;
import com.primitive.road_to_god_of_billiard.objects.GetTameNameReIterationPath;
import com.primitive.road_to_god_of_billiard.objects.UserProfile;
import com.primitive.road_to_god_of_billiard.utility.ActivityParams;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.IAsyncCallback;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


public class MainActivity extends AppCompatActivity
{

	public static Context rootContext;

	public static final String TAG = "Activity_MainActivity_";
	public final int BACK_QUIT_WAIT_TIME = 2000;
	private Toast toast;
	private boolean QUIT_FLAG = false;
	// for toolbar
	private Toolbar toolBar;
	private ImageView ivMenu;
	private ImageView ivSearch;
	private TextView tvTitle;
	private EditText etSearch;

	// drawer fragment
	private LeftDrawerFragment fld;
	private RightDrawerFragment frd;
	private DrawerLayout dlDrawerLeft;
	private DrawerLayout dlDrawerRight;


	private boolean searchState = false;

	// for game
	private UserProfileJson userJson;
	private UserProfileJson tempUserJson;
	private GameJson receivedGame;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		((CustomApplication)getApplication()).setUserProfile(new UserProfileJson(1013, null, null, null, 0, 0));

		//
		// BEGIN : Load user profile and check gameMessage
		//
		//

		((CustomApplication)getApplication()).loadProfile(1015);

		//
		// END : Load user profile and check gameMessage
		//

		rootContext = this;

		//
		// BEGIN : set Toolbar as ActionBar and set click event listener
		//
		toolBar = (Toolbar) findViewById(R.id.tb_toolbar);
		ivMenu = (ImageView) findViewById(R.id.iv_toolbar_menu);
		ivSearch = (ImageView) findViewById((R.id.iv_toolbar_search));
		tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
		etSearch = (EditText) findViewById(R.id.et_toolbar_search);

		if (toolBar != null)
		{
			setSupportActionBar(toolBar);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

		ivMenu.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (dlDrawerLeft.isDrawerOpen(GravityCompat.START))
				{
					dlDrawerLeft.closeDrawer(GravityCompat.START);
				} else
				{
					dlDrawerLeft.openDrawer(GravityCompat.START);
				}
			}
		});


		//
		// BEGIN : setup drawer layout
		//
		dlDrawerLeft = (DrawerLayout) findViewById(R.id.dl_drawer_layout_left);
		dlDrawerRight = (DrawerLayout) findViewById(R.id.dl_drawer_layout_right);
		fld = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_left_drawer);
		frd = (RightDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_right_drawer);

		fld.setUp(R.id.fragment_left_drawer, dlDrawerLeft, this, getSupportFragmentManager(), R.id.fl_content, toolBar);
		frd.setUp(R.id.fragment_right_drawer, dlDrawerRight, this);
		//
		// END : setup drawer layout
		//


	}

	@Override
	public void onBackPressed()
	{
		if(!fld.onBackPress())
		{
			new QuitApp().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			if(QUIT_FLAG){finish();}
		}
	}





	//
	// BEGIN : Network Tasks
	//

	public interface GET_UserProfileService
	{
		@GET(ServerInfo.API_USER_GET)
		Call<UserProfileJson> getUserJson(@Path(ServerInfo.API_USER_PATH) int usderId);
	}


	public interface GET_ReceivedGameService
	{
		@GET(ServerInfo.API_RECEIVED_GAME_GET)
		Call<List<GameJson>> getReceiveGame(@Path(ServerInfo.API_RECEIVED_GAME_PATH) int userId);
	}

	public interface GET_SentGameService
	{
		@GET(ServerInfo.API_SENT_GAME_GET)
		Call<GameJson> getSentGame(@Path(ServerInfo.API_SENT_GAME_PATH) int userId);
	}


	private class GETUserProfile extends AsyncTask<Integer, Void, UserProfileJson>
	{
		@Override
		protected UserProfileJson doInBackground(Integer... id)
		{
			try
			{
				Log.d(TAG, "GetUserProfile...EntryFlag");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GET_UserProfileService service = retrofit.create(GET_UserProfileService.class);

				Call<UserProfileJson> call = service.getUserJson(id[0]);
				Log.d(TAG, "GetUserProfile...Execute");
				Response res = call.execute();

				if (res.isSuccess())
				{
					UserProfileJson json = (UserProfileJson) res.body();
					Log.d(TAG, "Request Success...");
					Log.d(TAG, json.getEmail());

					return json;
				} else
				{
					Log.d(TAG, "Request Fail...");
					Log.d(TAG, res.code() + " : " + res.message());

				}
			} catch (Exception e)
			{
				Log.d(TAG, "GetUserProfile...Exception Occurred");
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(UserProfileJson json)
		{
			if(json != null)
			{
				((CustomApplication) getApplication()).setUserProfile(json);
			}
			else
			{
				Toast.makeText(MainActivity.this, "사용자 프로필을 가져오는 데 실패하였습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}




	private class ReceiveGameSequence extends AsyncTask<Integer, Void, GameJson>
	{
		UserProfileJson senderProfile;
		@Override
		protected GameJson doInBackground(Integer... id)
		{
			try
			{
				Log.d(TAG, "GETReceivedGame...");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();



				GET_ReceivedGameService service = retrofit.create(GET_ReceivedGameService.class);
				Call<List<GameJson>> call = service.getReceiveGame(id[0]);
				Response res = call.execute();
				if (res.isSuccess())
				{
					Log.d(TAG, "Request Success...");
					GameJson json = ((List<GameJson>) res.body()).get(0);

					if (json != null)
					{
						Log.d(TAG, "Player has received message");
						//showGameMessage(json.getSender(), json);

						GET_UserProfileService gupService = retrofit.create(GET_UserProfileService.class);
						Call<UserProfileJson> gupCall = gupService.getUserJson(json.getSender());
						Response gupRes = gupCall.execute();
						if(gupRes.isSuccess())
						{
							Log.d(TAG, "Getting Sender Profile Success");
							senderProfile = (UserProfileJson)gupRes.body();
						}
						else
						{
							Log.d(TAG, "Getting Sender Profile Fail");
							Log.d(TAG, gupRes.code() + " : " + gupRes.message());
						}

					}

					return json;
				}
				else
				{
					Log.d(TAG, "Request fail...");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			} catch (Exception e)
			{
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(GameJson json)
		{
			Log.d(TAG, "showGameMessage...Entry Point");
			boolean dialogResult = true;
			try
			{

				if (senderProfile == null)
				{
					Log.d(TAG, "senderProfile is null...");
				}
				else
				{
					Log.d(TAG, "sender username : " + senderProfile.getUsername());
					// TODO : show dialog starting game or not
					if (dialogResult)
					{
						Intent intent = new Intent(getApplicationContext(), GameActivity.class);
						intent.putExtra(ActivityParams.PARAM_GAMECODE, json.getGameCode());
						intent.putExtra(ActivityParams.PARAM_SENDER, json.getSender());
						intent.putExtra(ActivityParams.PARAM_RECEIVER, json.getReceiver());
						intent.putExtra(ActivityParams.PARAM_START, json.getStart()); // TODO : put int value for starting point of game
						intent.putExtra(ActivityParams.PARAM_MY_POSITION, UserProfile.GAME_PLYAER_POSITION_RECEIVER);
						intent.putExtra(ActivityParams.PARAM_SENDER_USERNAME, senderProfile.getUsername());
						intent.putExtra(ActivityParams.PARAM_RECEIVER_USERNAME, "my_username"); // TODO : send my Username
						intent.putExtra(ActivityParams.PARAM_GAME_TYPE, ActivityParams.PARAM_GAME_PVP);
						startActivity(intent);
					}
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred...");
				Log.d(TAG, e.getMessage());
			}
		}
	}

	private class SentGameSequence extends AsyncTask<Integer, Void, Void>
	{
		@Override
		protected Void doInBackground(Integer... id)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GET_SentGameService service = retrofit.create(GET_SentGameService.class);

				Call<GameJson> call = service.getSentGame(id[0]);
				Response res = call.execute();
				if (res.isSuccess())
				{
					Log.d(TAG, "GET Success");
					GameJson json = (GameJson)res.body();

					if(json == null)
					{
						Log.d(TAG, "Has no accepted message");
						return null;
					}
					else
					{
						Log.d(TAG, "Has accepted message");
						Intent intent = new Intent(getApplicationContext(), GameActivity.class);
						intent.putExtra(ActivityParams.PARAM_GAMECODE, json.getGameCode());
						intent.putExtra(ActivityParams.PARAM_SENDER, json.getSender());
						intent.putExtra(ActivityParams.PARAM_RECEIVER, json.getReceiver());
						intent.putExtra(ActivityParams.PARAM_START, 100);
						intent.putExtra(ActivityParams.PARAM_MY_POSITION, UserProfile.GAME_PLAYER_POSITION_SENDER);
						intent.putExtra(ActivityParams.PARAM_SENDER_USERNAME, "my_username"); // TODO : send my username
						intent.putExtra(ActivityParams.PARAM_RECEIVER_USERNAME, "op_username"); // TODO : send op's Username
						startActivity(intent);
					}
				}
				else
				{
					Log.d(TAG, "GET fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception Occurred...");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}
	}


	public interface POST_GameMessage
	{
		@POST(ServerInfo.API_GAME_POST)
		Call<GameJson> postGame(@Body GameJson json);
	}

	private class PostGameMessage extends AsyncTask<Integer, Void, Void>
	{
		@Override
		protected Void doInBackground(Integer... param)
		{
			try
			{
				Log.d(TAG, "PostGameMessage...");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				POST_GameMessage service = retrofit.create(POST_GameMessage.class);

				Call<GameJson> call = service.postGame(new GameJson(param[0], param[1], param[2]));
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "PostGameMessage...Post is success");
					Log.d(TAG, res.code() + " : "  + res.message());
					Log.d(TAG, "SendUser : " + param[0] + ", ReceiveUser : " + param[1]);
				}
				else
				{
					Log.d(TAG, "PostGameMessage...Post is fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "PostGameMessage...Exception occurred");
				Log.d(TAG, e.getMessage());
			}
			finally
			{
				return null;
			}
		}
	}


	public interface PostUserSearchService
	{
		@POST(ServerInfo.API_SEARCH_USER_POST)
		Call<List<SearchUserJson>> postSearchUser(@Body SearchUserPostJson Keyword);
	}

	private class PostUserSearch extends AsyncTask<String, Void, List<SearchUserJson>>
	{
		@Override
		protected List<SearchUserJson> doInBackground(String... keyword)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostUserSearchService service = retrofit.create(PostUserSearchService.class);

				Call<List<SearchUserJson>> call = service.postSearchUser(new SearchUserPostJson(keyword[0]));

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (List<SearchUserJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail");
					return null;
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<SearchUserJson> list)
		{
			if(list == null)
			{
				Log.d(TAG, "list is null");
			}
			else
			{
				for (SearchUserJson s : list)
				{
					Log.d(TAG, s.getUsername());
				}
			}
		}
	}

	public interface PostPushService
	{
		@POST("/4Billion/api/Push/")
		Call<PushJson> postPush(@Body PushJson push);
	}

	private class PostPush extends AsyncTask<PushJson, Void, Void>
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

	private class QuitApp extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute()
		{
			Log.d(TAG, "Quitting app seq execute...");
			QUIT_FLAG = true; Log.d(TAG, "QUIT_FLAG : " + QUIT_FLAG);
			toast = Toast.makeText(MainActivity.this, "종료하시려면 한번 더 눌러주세요", Toast.LENGTH_LONG);
			toast.show();
		}

		@Override
		protected Void doInBackground(Void... parma)
		{
			try
			{
				Thread.sleep(BACK_QUIT_WAIT_TIME);
			}
			catch (Exception e)
			{

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void param)
		{
			QUIT_FLAG = false;
			toast.cancel();
			Log.d(TAG, "QUIT_FLAG : " + QUIT_FLAG);
			/*
			Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					toast.cancel();
				}
			}, BACK_QUIT_WAIT_TIME);*/
		}
	}

	//
	// END : Network Tasks
	//


}