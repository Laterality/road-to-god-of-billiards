package com.primitive.road_to_god_of_billiard.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReiterationCheckJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by lth on 2015-10-26.
 */
public class SigninActivity extends Activity {
    private static final int STATE_EMAIL = 0x01;
    private static final int STATE_EMAIL_INVALID_FORM = 0x10;
    private static final int STATE_EMAIL_REITERATION = 0x11;
    private static final int STATE_USERNAME_OK = 0x02;
    private static final int STATE_USERNAME_REITERATION = 0x12;
    private static final int STATE_USERNAME_TOO_SHORT = 0x13;
    private static final int STATE_PASSWORD_OK = 0x03;
    private static final int STATE_PASSWORD_TOO_SHORT = 0x14;
    private static final int STATE_PASSWORD_CONFIRM_OK = 0x04;
    private static final int STATE_PASSWORD_CONFIRM_NOT_MATCH = 0x15;


    private static final String TAG = "SignInActivity";

    private EditText etInputId;
    private EditText etInputPassword;
    private EditText etInputPasswordRetry;
    private EditText etInputNickname;
    private EditText etInputScore;
    private TextView tvCheckId;
    private TextView tvCheckNickname;
    private TextView tvCheckPassword;
    private TextView tvCheckPasswordConfirm;
    private Button btnSignIn;

    private boolean validityEamil;
    private boolean validityUseranem;
    private boolean validityPassword;
    private boolean validityPasswordConfirm;

    String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etInputId = (EditText) findViewById(R.id.input_id);
        etInputPassword = (EditText) findViewById(R.id.input_password);
        etInputPasswordRetry = (EditText) findViewById(R.id.input_password_retry);
        etInputNickname = (EditText) findViewById(R.id.input_nickname);
        etInputScore = (EditText) findViewById(R.id.input_score);
        tvCheckId = (TextView) findViewById(R.id.check_id);
        tvCheckNickname = (TextView) findViewById(R.id.check_nickname);
        tvCheckPassword = (TextView) findViewById(R.id.check_password);
        tvCheckPasswordConfirm = (TextView) findViewById(R.id.check_password_retry);
        btnSignIn = (Button) findViewById(R.id.signin_complete);

        btnSignIn.setEnabled(false);
        /*
        etInputPassword.setEnabled(false);
        etInputPasswordRetry.setEnabled(false);
        etInputNickname.setEnabled(false);
        etInputScore.setEnabled(false);*/

        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new SignIn().execute(new UserProfileJson(0
                , etInputId.getText().toString()
                , etInputPassword.getText().toString()
                , etInputNickname.getText().toString()
                , 0
                , Integer.valueOf(etInputScore.getText().toString())));
            }
        });

        etInputId.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // do Check Reiteration
                if (checkEmail(s.toString()))
                {
                    new GetIsEmailReiterate().execute(s.toString());
                } else
                {
                    setState(STATE_EMAIL_INVALID_FORM);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        etInputPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length()<5)
                {
                    setPasswordState(STATE_PASSWORD_TOO_SHORT);
                }
                else
                {
                    setPasswordState(STATE_PASSWORD_OK);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        etInputPasswordRetry.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().equals(etInputPassword.getText().toString()))
                {
                    setPasswordConfirmState(STATE_PASSWORD_CONFIRM_OK);
                }
                else
                {
                    setPasswordConfirmState(STATE_PASSWORD_CONFIRM_NOT_MATCH);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        etInputNickname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length()<3)
                {
                    setUsernameState(STATE_USERNAME_TOO_SHORT);
                }
                else
                {
                    setUsernameState(STATE_USERNAME_OK);
                    new GetIsUsernameReiterate().execute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvCheckId.getWindowToken(), 0);
    }

    public void setState(int code)
    {
        switch (code)
        {
            case STATE_EMAIL:
                tvCheckId.setTextColor(Color.GREEN);
                tvCheckId.setText("사용 가능");
                break;
            case STATE_EMAIL_INVALID_FORM:
                tvCheckId.setTextColor(Color.RED);
                tvCheckId.setText("잘못된 이메일 형식입니다");
                break;
            case STATE_EMAIL_REITERATION:
                tvCheckId.setTextColor(Color.RED);
                tvCheckId.setText("중복된 이메일입니다");
                break;
        }
        /*
        etInputPassword.setEnabled(validityEamil);
        etInputPasswordRetry.setEnabled(validityEamil);
        etInputNickname.setEnabled(validityEamil);
        etInputScore.setEnabled(validityEamil);
        */
    }

    private void setUsernameState(int code)
    {
        switch (code)
        {
            case STATE_USERNAME_OK:
                tvCheckNickname.setTextColor(Color.GREEN);
                tvCheckNickname.setText("사용가능합니다");
                break;
            case STATE_USERNAME_REITERATION:
                tvCheckNickname.setTextColor(Color.RED);
                tvCheckNickname.setText("이미 사용중입니다");
                break;
            case STATE_USERNAME_TOO_SHORT:
                tvCheckNickname.setTextColor(Color.RED);
                tvCheckNickname.setText("닉네임은 3글자 이성이어야 합니다");
        }
    }

    private void setPasswordState(int code)
    {
        switch (code)
        {
            case STATE_PASSWORD_OK:
                validityPassword = true;
                tvCheckPassword.setText("");
                break;
            case STATE_PASSWORD_TOO_SHORT:
                validityPassword = false;
                tvCheckPassword.setTextColor(Color.RED);
                tvCheckPassword.setText("5글자 이상이어야 합니다");
                break;
        }
        if(validityEamil && validityPassword &&validityPasswordConfirm && validityUseranem){btnSignIn.setEnabled(true);}
        else{btnSignIn.setEnabled(false);}
    }

    private void setPasswordConfirmState(int code)
    {
        switch (code)
        {
            case STATE_PASSWORD_CONFIRM_OK:
                validityPasswordConfirm = true;
                tvCheckPasswordConfirm.setTextColor(Color.GREEN);
                tvCheckPasswordConfirm.setText("일치합니다");
                break;
            case STATE_PASSWORD_CONFIRM_NOT_MATCH:
                validityPasswordConfirm = false;
                tvCheckPasswordConfirm.setTextColor(Color.RED);
                tvCheckPasswordConfirm.setText("비밀번호가 일치하지 않습니다");
                break;
        }
        if(validityEamil && validityPassword &&validityPasswordConfirm && validityUseranem){btnSignIn.setEnabled(true);}
        else{btnSignIn.setEnabled(false);}
    }
    private boolean checkEmail(String email) {
        String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";//"^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(mail);
        Matcher m = p.matcher(email);
        return m.matches();
    }



    public interface GetIsEmailReiterateService
    {
        @POST(ServerInfo.API_REITERATION_CHECK_EMAIL_POST)
        Call<Boolean> getEmailReiteration(@Body ReiterationCheckJson check);
    }

    private class GetIsEmailReiterate extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... param)
        {
            try
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInfo.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetIsEmailReiterateService service = retrofit.create(GetIsEmailReiterateService.class);

                Call<Boolean> call = service.getEmailReiteration(new ReiterationCheckJson(param[0]));

                Response res = call.execute();

                if(res.isSuccess())
                {
                    Log.d(TAG, "Execution success");
                    return (Boolean)res.body();
                }
                else
                {
                    Log.d(TAG, "Execution fail");
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

        @Override
        protected void onPostExecute(Boolean res)
        {
            if(res){setState(STATE_EMAIL); validityEamil= true; }
            else{setState(STATE_EMAIL_REITERATION); validityEamil = false;}

            if(validityEamil && validityPassword &&validityPasswordConfirm && validityUseranem){btnSignIn.setEnabled(true);}
            else{btnSignIn.setEnabled(false);}
        }
    }

    public interface GetIsUsernameReiterateService
    {
        @POST(ServerInfo.API_REITERATION_CHECK_USERNAME_POST)
        Call<Boolean> getIsUsernameReiterate(@Body ReiterationCheckJson check);
    }

    private class GetIsUsernameReiterate extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... param)
        {
            try
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInfo.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetIsUsernameReiterateService service = retrofit.create(GetIsUsernameReiterateService.class);

                Call<Boolean> call = service.getIsUsernameReiterate(new ReiterationCheckJson(param[0]));

                Response res = call.execute();

                if(res.isSuccess())
                {
                    Log.d(TAG, "Execution success from GetIsUsernameReiterate");
                    return (Boolean)res.body();
                }
                else
                {
                    Log.d(TAG, "Execution fail from GetIsUsernameReiterate");
                    Log.d(TAG, res.code() + " : " + res.message());
                    return false;
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, "Exception occurred");
                Log.d(TAG, e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean res)
        {
            validityUseranem = res;
            if(validityEamil && validityPassword &&validityPasswordConfirm && validityUseranem){btnSignIn.setEnabled(true);}
            else{btnSignIn.setEnabled(false);}
        }
    }

    public interface SignInService
    {
        @POST(ServerInfo.API_USER_POST)
        Call<UserProfileJson> setUserJson(@Body UserProfileJson json);
    }

    private class SignIn extends AsyncTask<UserProfileJson, Void, Void>
    {
        @Override
        protected Void doInBackground(UserProfileJson... arg)
        {
            try
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInfo.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SignInService service = retrofit.create(SignInService.class);

                Call<UserProfileJson> call = service.setUserJson(arg[0]);
                retrofit.Response res = call.execute();
                if (res.isSuccess())
                {
                    Log.d(TAG, "Execution sign in success");
                }
                else
                {
                    Log.d(TAG, "Execution sign in fail");
                    Log.d(TAG, res.code() + " : " + res.message());
                }

            }
            catch (Exception e)
            {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }
}
