package com.primitive.road_to_god_of_billiard.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamInfoJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.UserProfileJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.TeamMemberListAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.objects.UserProfile;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;
import com.primitive.road_to_god_of_billiard.views.RatingView_Bar;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by 신진우- on 2015-09-29.
 */
public class HasTeamFragment extends ChainFragment
{
	private final String TAG = "HasTeamFragment";
	// The x, y position of the Button on screen
	private Point p;
	private RatingView_Bar rvTeamRating;
	private Button btnTeamFunctions;
	private ListView lvFunctions;
	private ListView lvMembers;
	private TextView tvTeamName;
	private TextView tvTeamDesc;


	private boolean isLeader = false;
	private List<UserProfileJson> memberList;
	private TeamMemberListAdapter listAdapter;
	private int teamId;


	// Get the x and y position after the button is draw on screen
	// (It's important to note that we can't get the position in the onCreate(),
	// because at that stage most probably the view isn't drawn yet, so it will return (0, 0)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		teamId = getArguments().getInt("TeamCode");

		View rootView = inflater.inflate(R.layout.fragment_my_team_has_team, container, false);

		rvTeamRating = (RatingView_Bar) rootView.findViewById(R.id.rv_my_team_rating);
		tvTeamName = (TextView) rootView.findViewById(R.id.tv_my_team_team_name);
		tvTeamDesc = (TextView) rootView.findViewById(R.id.tv_my_team_desc);

		lvMembers = (ListView) rootView.findViewById(R.id.lv_my_team_member);
		//btnTeamFunctions = (Button) rootView.findViewById(R.id.btn_my_team_function);
		/*btnTeamFunctions.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showPopup(v);// TODO : Open popup window
			}
		});*/
		new GetTeamInfo().execute();
		new GetTeamMemberList().execute();

		return rootView;
	}

	private void showPopup(View anchorView)
	{
		View popupView = getLayoutInflater(null).inflate(R.layout.popup_my_team_function, null);

		PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		isLeader = true; // TODO : this line is for just testing, remove this line when release
		// set view content
		lvFunctions = (ListView) popupView.findViewById(R.id.lv_my_team_function_list);
		if(isLeader)
		{
			lvFunctions.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_my_team_function, R.id.tv_my_team_function_name, getResources().getStringArray(R.array.team_function_leader)));
		}
		else
		{
			lvFunctions.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_my_team_function, R.id.tv_my_team_function_name, getResources().getStringArray(R.array.team_function_member)));
		}

		// If  the PopupWindow should be focusable
		popupWindow.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		popupWindow.setBackgroundDrawable(new ColorDrawable());

		int[] location = new int[2];
		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.setWidth(DpToPx(120));
		popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());
	}

	private int DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return (int)px;
	}


	public interface GetTeamMemberListService
	{
		@GET(ServerInfo.API_TEAM_MEMBERS_GET)
		Call<List<UserProfileJson>> getTeamMemberList(@Path(ServerInfo.API_TEAM_MEMBERS_PATH) int teamId);
	}

	private class GetTeamMemberList extends AsyncTask<Void, Void, List<UserProfileJson>>
	{
		@Override
		protected List<UserProfileJson> doInBackground(Void... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetTeamMemberListService service = retrofit.create(GetTeamMemberListService.class);

				Call<List<UserProfileJson>> call = service.getTeamMemberList(teamId);

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (List<UserProfileJson>)res.body();
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
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<UserProfileJson> list)
		{
			if(list != null)
			{
				lvMembers.setAdapter(new TeamMemberListAdapter(getActivity(), list));
			}
			else
			{
				Toast.makeText(getActivity()
				, "팀원 목록을 가져오는 도중 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public interface GetTeamInfoService
	{
		@GET(ServerInfo.API_TEAM_GET)
		Call<TeamInfoJson> getTeamInfo(@Path(ServerInfo.API_TEAM_PATH) int teamId);
	}

	private class GetTeamInfo extends AsyncTask<Void, Void, TeamInfoJson>
	{
		@Override
		protected TeamInfoJson doInBackground(Void... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetTeamInfoService service = retrofit.create(GetTeamInfoService.class);

				Call<TeamInfoJson> call = service.getTeamInfo(teamId);

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (TeamInfoJson)res.body();
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
		protected void onPostExecute(TeamInfoJson team)
		{
			if(team != null)
			{
				rvTeamRating.setRate(team.getWinRate()/100);
				tvTeamName.setText(team.getTeamName());
				tvTeamDesc.setText(team.getDesc());
			}
			else
			{
				Toast.makeText(getActivity(), "팀 정보를 가져오는 도중 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
