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
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.GameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGameJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.SearchGameTeamJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.ScoreRangeSpinnerAdapter;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.TeamSearchListAdapter;
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
public class SearchGameTeamFragment extends Fragment
{
	private final String TAG = "SearchGameTeamFragment";
	private ListView lvTeamList;
	private Spinner spMin;
	private Spinner spMax;
	private ScoreRangeSpinnerAdapter spMinAdapter;
	private ScoreRangeSpinnerAdapter spMaxAdapter;
	private int min, max;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_search_game_team, container, false);

		lvTeamList = (ListView) rootView.findViewById(R.id.lv_search_game_team_list);
		spMin = (Spinner) rootView.findViewById(R.id.sp_search_team_score_range_min);
		spMax = (Spinner) rootView.findViewById(R.id.sp_search_team_score_range_max);

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
				new GetSearchTeam().execute(min, (int)id);
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


		return  rootView;
	}





	public interface PostTeamGameService
	{
		@POST(ServerInfo.API_TEAM_GAME_POST)
		Call<String> postTeamGame(@Body GameJson game);
	}

	private class PostTeamGame extends AsyncTask<GameJson, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(GameJson... game)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostTeamGameService service = retrofit.create(PostTeamGameService.class);

				Call<String> call = service.postTeamGame(game[0]);

				Response res = call.execute();

				if (res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return true;
				}
				else
				{
					Log.d(TAG, "Execution fail");
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
			Toast.makeText(getActivity()
					, res?  "게임을 신청했습니다" : "게임 신청에 실패했습니다", Toast.LENGTH_SHORT).show();
		}
	}

	public interface GetSearchTeamService
	{
		@POST(ServerInfo.API_SEARCH_GAME_TVT)
		Call<List<SearchGameTeamJson>> getSearchTeam(@Body SearchGameJson range);
	}

	private class GetSearchTeam extends AsyncTask<Integer, Void, List<SearchGameTeamJson>>
	{
		@Override
		protected List<SearchGameTeamJson> doInBackground(Integer... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetSearchTeamService service = retrofit.create(GetSearchTeamService.class);

				Call<List<SearchGameTeamJson>> call = service.getSearchTeam(new SearchGameJson(param[0], param[1]));

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success from GetSearchTeam");
					return (List<SearchGameTeamJson>)res.body();
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
		protected void onPostExecute(List<SearchGameTeamJson> list)
		{
			if(list == null)
			{

			}
			else
			{
				lvTeamList.setAdapter(new TeamSearchListAdapter(list, getActivity()));
			}
		}
	}


}
