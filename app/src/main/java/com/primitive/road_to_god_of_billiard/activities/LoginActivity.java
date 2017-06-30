package com.primitive.road_to_god_of_billiard.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PushJson;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-08-02.
 */
public class LoginActivity extends Activity
{
	public static final String EXTRA_MESSAGE = "message";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private ProgressDialog pDialog;

	private EditText etEmailId;
	private EditText etPassword;
	private Button btLogin;
	private Button btSignin;
	private TextView tvFindId;

	private SharedPreferences sharedPrefs;
	private SharedPreferences.Editor prefsEditor;
	String SENDER_ID = "273379658926";

	GoogleCloudMessaging gcm;
	Context context;

	private final static String SITE = ServerInfo.BASE_URL + ServerInfo.API_LOGIN_POST;

	private String e_mail;
	private String password;

	String regid;
	int userid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etEmailId = (EditText) findViewById(R.id.emailId);
		etPassword = (EditText) findViewById(R.id.password);
		btLogin = (Button) findViewById(R.id.login);
		btSignin = (Button) findViewById(R.id.signin);

		tvFindId = (TextView) findViewById(R.id.findId);

		context = getApplicationContext();

		try {
			URI uri = new URI(SITE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				e_mail = etEmailId.getText().toString();
				password = etPassword.getText().toString();
				if (e_mail.isEmpty() || password.isEmpty()) {
					Toast.makeText(getApplicationContext(), "ID, PASSWORD를 확인해 주세요.", Toast.LENGTH_SHORT).show();
				} else {
					new LoginAsyncTask().execute();
				}
			}
		});
		btSignin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
				startActivity(intent);
			}
		});
	}

	class LoginAsyncTask extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("로그인 중...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			if (sharedPrefs == null) {
				sharedPrefs = getApplicationContext().getSharedPreferences(CustomApplication.PREFERENCE_SHARED_FILENAME,
						Context.MODE_PRIVATE);
			}

			if (prefsEditor == null) {
				prefsEditor = sharedPrefs.edit();
			}
			String result = null;
			StringBuilder strBuilder = null;
			BufferedReader bufReader = null;
			InputStream inputStream = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(SITE);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("E_mail", e_mail));
			params.add(new BasicNameValuePair("Password", password));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse response = httpclient.execute(httpPost);
				Log.e("response", response.toString());

				int responseCode = response.getStatusLine().getStatusCode();

				Log.e("responseCode", Integer.toString(responseCode));
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				if (responseCode >= 200 && responseCode < 300) {
					bufReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

					strBuilder = new StringBuilder();
					String line = "";
					while ((line = bufReader.readLine()) != null) {
						strBuilder.append(line);
					}
					result = getJSONUserLogin_DB(strBuilder);
				}
				Log.e("result", result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			etPassword = (EditText) findViewById(R.id.password);
			// dismiss the dialog once done
			pDialog.dismiss();
			if (result == null) {
				return;
			}
			if (result.equals("0")) {
				Toast.makeText(getApplicationContext(),
						"등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
			} else {
				prefsEditor.putString("user_id", result);
				prefsEditor.commit();
				CustomApplication.LOGIN_ENABLED = 1;
				userid = Integer.parseInt(result);
				if (checkPlayServices()) {
					gcm = GoogleCloudMessaging.getInstance(LoginActivity.this);
					regid = getRegistrationId(context);
					if (regid.equals("")) {
						registerInBackground();
					} else {
						Log.e("reg_id", regid);
						Log.e("user_id", Integer.toString(userid));
						new PostPush().execute(new PushJson(regid, userid));
					}
				}
			}
			etPassword.setText("");
		}
	}
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("TAG", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private void storeRegistrationId(Context context, String regId) {
		SharedPreferences prefs = getApplicationContext().getSharedPreferences(CustomApplication.PREFERENCE_SHARED_FILENAME,
				Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i("TAG", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("reg_id", regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private String getRegistrationId(Context context) {
		SharedPreferences prefs = getApplicationContext().getSharedPreferences(CustomApplication.PREFERENCE_SHARED_FILENAME,
				Context.MODE_PRIVATE);
		String registrationId = prefs.getString("reg_id", "");
		if (registrationId.equals("")) {
			Log.i("TAG", "Registration not found.");
			return "";
		}

		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("TAG", "App version changed.");
			return "";
		}
		return registrationId;
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
				Log.e("onPost_reg_id", regid);
				Log.e("onPost_user_id", Integer.toString(userid));
				new PostPush().execute(new PushJson(regid, userid));
			}
		}.execute(null, null, null);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

//    private void sendRegistrationIdToBackend() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, ListBeaconsActivity.class);
//                intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, MainActivity.class.getName());
//                startActivity(intent);
//                finish();
//            }
//        }, 2000);
//    }

	private class PostPush extends AsyncTask<PushJson, Void, Void> {
		@Override
		protected Void doInBackground(PushJson... push) {
			try {
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl("http://210.125.219.56")
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostPushService service = retrofit.create(PostPushService.class);

				Call<PushJson> call = service.postPush(push[0]);

				Response res = call.execute();

				if (res.isSuccess()) {
					Log.e("PostPush", "Execution success");

				} else {
					Log.e("PostPush", "Execution fail");
					Log.e("PostPush", res.code() + " : " + res.message());
				}
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private static String getJSONUserLogin_DB(StringBuilder buf) {
		String resultString = null;
		JSONObject wholeObject = null;

		try {
			wholeObject = new JSONObject(buf.toString());
			resultString = wholeObject.getString("UserCode");
		} catch (Exception e) {
			e.getStackTrace();
			resultString = "0";
		}
		return resultString;
	}

	interface PostPushService {
		@POST("/4Billion/api/Push/")
		Call<PushJson> postPush(@Body PushJson push);
	}
}
