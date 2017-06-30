package com.primitive.road_to_god_of_billiard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGamePlayerJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.IndividualSearchListAdapter;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.ScoreRangeSpinnerAdapter;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-10-01.
 */
public class SearchGameIndividualFragment extends Fragment
{
	private final String TAG = "SearchGameIndividual";
	private ListView lvSearchPlayer;
	private Spinner spMin;
	private Spinner spMax;
	private ScoreRangeSpinnerAdapter spMinAdapter;
	private ScoreRangeSpinnerAdapter spMaxAdapter;
	private int min, max;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_search_game_individual, container, false);

		lvSearchPlayer = (ListView) rootView.findViewById(R.id.lv_search_game_individual_search_result);

		spMin = (Spinner) rootView.findViewById(R.id.sp_search_individual_score_min);
		spMax = (Spinner) rootView.findViewById(R.id.sp_search_individual_score_max);

		spMin.setAdapter(spMinAdapter = new ScoreRangeSpinnerAdapter(getActivity(), false, 0));
		spMax.setAdapter(spMaxAdapter = new ScoreRangeSpinnerAdapter(getActivity(), true, 0));


		spMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				spMax.setAdapter(spMaxAdapter = new ScoreRangeSpinnerAdapter(getActivity(), true, (int) id));
				min = (int) id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		spMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				new GetSearchPlayer().execute(min, (int) id);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		spMax.post(new Runnable()
		{
			@Override
			public void run()
			{
				spMax.setSelection(5);
			}
		});

		return rootView;
	}

	public interface GetSearchPlayerServeice
	{
		@POST(ServerInfo.API_SEARCH_GAME_PVP)
		Call<List<SearchGamePlayerJson>> getSearchPlayer(@Body SearchGameJson range);
	}

	private class GetSearchPlayer extends AsyncTask<Integer, Void, List<SearchGamePlayerJson>>
	{
		@Override
		protected List<SearchGamePlayerJson> doInBackground(Integer... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetSearchPlayerServeice service = retrofit.create(GetSearchPlayerServeice.class);

				Call<List<SearchGamePlayerJson>> call = service.getSearchPlayer(new SearchGameJson(param[0], param[1]));

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success from GetSearchTeam");
					return (List<SearchGamePlayerJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail from GetSearchTeam");
					Log.d(TAG, res.code() + " : " + res.message());
					return null;
				}
			}
			catch(Exception e)
			{
				Log.d(TAG, "Exception occurred from GetSearchTeam");
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<SearchGamePlayerJson> list)
		{
			if(list == null)
			{

			}
			else
			{
				lvSearchPlayer.setAdapter(new IndividualSearchListAdapter(getActivity(), list));
			}
		}
	}


	public interface PostGameMessageService
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

				PostGameMessageService service = retrofit.create(PostGameMessageService.class);

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

}
