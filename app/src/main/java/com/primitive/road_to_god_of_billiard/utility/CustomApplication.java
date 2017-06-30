package com.primitive.road_to_god_of_billiard.utility;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.RecordJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;
import com.primitive.road_to_god_of_billiard.objects.UserProfile;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-08-12.
 */
public class CustomApplication extends Application
{
	private static final String TAG = "CustomApplication";
	public static int LOGIN_ENABLED = 0;
	public static final String PREFERENCE_SHARED_FILENAME = "SharedPrefs";

	public static int BEACON_MAC = 0; //비콘을 찾음

	public static int BEACON_CONNECT = 0; //비콘 연결 여부

	public static int BLUETOOTH_ENABLED = 0; //블루투스 연결 여부

	public static int SEARCHGAME_SHOW = 0;

	private UserProfileJson GlobalUserProfile = new UserProfileJson();
	private RecordJson GlobaluserRecord;

	public void setUserProfile(UserProfileJson profile)
	{
		GlobalUserProfile = profile;
	}

	public void setRecord(RecordJson record)
	{
		GlobaluserRecord = record;
	}

	public UserProfileJson getProfile()
	{
		return GlobalUserProfile;
	}

	public RecordJson getRecord()
	{
		return GlobaluserRecord;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		//EstimoteSDK.initialize(this, "Android key 1", "AIzaSyAkYsu5cspe2uiDi-_yzIs3AKw4t-ebakI");

		//EstimoteSDK.enableDebugLogging(true);

	}

	public void loadProfile(int id)
	{
		new GETUserProfile().execute(id);
	}

	public interface GET_UserProfileService
	{
		@GET(ServerInfo.API_USER_GET)
		Call<UserProfileJson> getUserJson(@Path(ServerInfo.API_USER_PATH) int usderId);
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
				setUserProfile(json);
			}
			else
			{
				Toast.makeText(CustomApplication.this , "사용자 프로필을 가져오는 데 실패하였습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}


}
