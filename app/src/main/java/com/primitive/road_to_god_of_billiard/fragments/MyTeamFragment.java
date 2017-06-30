package com.primitive.road_to_god_of_billiard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamReiterationPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamSignInJson;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-09-29.
 */
public class MyTeamFragment extends ChainFragment
{
	private final String TAG = "MyTeamFragment";
	private int teamId = 0;
	private boolean hasTeam = false;

	private int flContentId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_my_team, container, false);
		flContentId = R.id.fl_my_team_content;

		teamId = ((CustomApplication)getActivity().getApplication()).getProfile().getTeamId();
		if(teamId != 0){hasTeam = true;}

		if(hasTeam)
		{
			HasTeamFragment fragHasTeam = new HasTeamFragment();
			Bundle arg = new Bundle();
			arg.putInt("TeamCode", teamId);
			fragHasTeam.setArguments(arg);
			setChildFragment(fragHasTeam);
			getChildFragmentManager().beginTransaction().replace(flContentId, fragHasTeam).commit();
		}
		else
		{
			HasNoTeamFragment fragHasNoTeam = new HasNoTeamFragment();
			setChildFragment(fragHasNoTeam);
			getChildFragmentManager().beginTransaction().replace(flContentId, fragHasNoTeam).commit();
		}



		return rootView;
	}

	@Override
	public boolean onBackPress()
	{
		return getChildFragment().onBackPress();
	}

}