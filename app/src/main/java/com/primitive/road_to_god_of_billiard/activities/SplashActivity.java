package com.primitive.road_to_god_of_billiard.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.activities.MainActivity;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PushJson;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.io.IOException;
import java.net.URL;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

public class SplashActivity extends Activity {

    private static int SFLASH_DELAY = 2000; // ms

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, MainActivity.class.getName());
                startActivity(intent);
                finish();
            }
        }, SFLASH_DELAY);
    }
}