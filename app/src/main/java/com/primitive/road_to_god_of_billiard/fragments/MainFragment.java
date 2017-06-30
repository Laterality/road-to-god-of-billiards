package com.primitive.road_to_god_of_billiard.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.RecordJson;
import com.primitive.road_to_god_of_billiard.objects.*;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;
import com.primitive.road_to_god_of_billiard.views.RatingView_Pie;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-08-11.
 */
public class MainFragment extends ChainFragment
{
	private final String TAG = "MainFragment";
	float mRate;
	View rootView;
	RatingView_Pie rvRating;
	RecordJson mRecord;
	TextView tvLastGame;
	TextView tvPlayCount;
	TextView tvWinCount;
	TextView tvLoseCount;
	ChainFragment currentMainFragment;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_main, container, false);

		rvRating = (RatingView_Pie) rootView.findViewById(R.id.rv_knowhow_rating);
		tvLastGame = (TextView) rootView.findViewById(R.id.tv_main_last_game);
		tvPlayCount = (TextView) rootView.findViewById(R.id.tv_count_play);
		tvWinCount = (TextView) rootView.findViewById(R.id.tv_count_win);
		tvLoseCount = (TextView) rootView.findViewById(R.id.tv_count_lose);


		Log.d(TAG, "user id : " + ((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());
		new GetUserRecord().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR
				, ((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());

		View v = getActivity().getCurrentFocus();
		if(v != null)
		{
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}

		return rootView;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(tvPlayCount.getWindowToken(), 0);
	}

	public boolean onBackPress()
	{
		if(currentMainFragment != null)
		{
			return currentMainFragment.onBackPress();
		}
		return false;
	}

	public interface GetUserRecordService
	{
		@GET(ServerInfo.API_RECORD_GET)
		Call<RecordJson> getRecord(@Path(ServerInfo.API_RECORD_PATH) int userId);
	}
	private class GetUserRecord extends AsyncTask<Integer, Void, RecordJson>
	{
		@Override
		protected RecordJson doInBackground(Integer... id)
		{
			try
			{
				Log.d(TAG, "Get user record...Entry Point");
				Log.d(TAG, "param id : " + id[0]);
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetUserRecordService service = retrofit.create(GetUserRecordService.class);

				Call<RecordJson> call = service.getRecord(id[0]);

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (RecordJson)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
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
		protected void onPostExecute(RecordJson record)
		{
			if(record != null)
			{
				if(getActivity().getApplication() == null)
				{

				}
				else
				{
					((CustomApplication) getActivity().getApplication()).setRecord(record);
					mRecord = ((CustomApplication) getActivity().getApplication()).getRecord();
					Log.d(TAG, mRecord.getTotal() + ", " + mRecord.getWin() + ", " + mRecord.getLose());
					mRate = mRecord.getRate() / 100;
					rvRating.setRate(mRecord.getRate() / 100);
					rvRating.invalidate();
					tvPlayCount.setText(String.valueOf(mRecord.getTotal()) + "전");
					tvWinCount.setText(String.valueOf(mRecord.getWin()) + "승");
					tvLoseCount.setText(String.valueOf(mRecord.getLose()) + "패");
				}

				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(tvPlayCount.getWindowToken(), 0);
			}
			else
			{
				Toast.makeText(getActivity()
				,"전적을 가져오는 도중 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
