package com.primitive.road_to_god_of_billiard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.FriendJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.TeamInvitationFriendsListAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class TeamEstablishingInvitationFragment extends ChainFragment
{
	private static final String TAG = "TeamInvitation";
	private Button btnCancel;
	private Button btnOk;
	private ListView lvFriends;
	private TeamInvitationFriendsListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_team_invitation, container, false);

		btnCancel = (Button) rootView.findViewById(R.id.btn_team_invitation_cancel);
		btnOk = (Button) rootView.findViewById(R.id.btn_team_invitation_ok);
		lvFriends = (ListView) rootView.findViewById(R.id.lv_team_invitation_friends);

		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPress();
			}
		});

		btnOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				List<FriendJson> checked = mAdapter.getChecked();
				if(checked.size() < 2)
				{
					Toast.makeText(getActivity(), "팀원이 부족합니다", Toast.LENGTH_SHORT).show();
				}
				else
				{
					((TeamEstablishingFragment) getParentFragment_()).setInvitation(mAdapter.getChecked());
					getParentFragment_().RemoveChild();
				}
			}
		});

		lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				mAdapter.setCheckToggle(position);
			}
		});

		new GetFriendList().execute(((CustomApplication)getActivity().getApplication()).getProfile().getUserCode());

		return rootView;
	}

	@Override
	public boolean onBackPress()
	{
		if(hasChild())
		{
			return getChildFragment().onBackPress();
		}
		else
		{
			getParentFragment_().RemoveChild();
			return true;
		}
	}

	public interface GetFriendListService
	{
		@GET(ServerInfo.API_FRIEND_LIST_GET)
		Call<List<FriendJson>> getFriendList(@Path(ServerInfo.API_FRIEND_LIST_PATH) int userId);
	}

	private class GetFriendList extends AsyncTask<Integer, Void, List<FriendJson>>
	{
		@Override
		protected List<FriendJson> doInBackground(Integer... userId)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetFriendListService service = retrofit.create(GetFriendListService.class);

				Call<List<FriendJson>> call = service.getFriendList(userId[0]);

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success from " + this.getClass().getSimpleName());
					return (List<FriendJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail from " + this.getClass().getSimpleName());
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred from " + this.getClass().getSimpleName());
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<FriendJson> list)
		{
			if(list == null)
			{
				Toast.makeText(getActivity(), "친구목록을 가져오는 데 실패했습니다", Toast.LENGTH_SHORT).show();
			}
			else
			{
				lvFriends.setAdapter(mAdapter = new TeamInvitationFriendsListAdapter(getActivity(), list));
			}
		}
	}
}
