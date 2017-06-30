package com.primitive.road_to_god_of_billiard.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.FriendJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamReiterationPostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.TeamSignInJson;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class TeamEstablishingFragment extends ChainFragment
{
	private static final String TAG = "TeamEstablishing-Naming";

	private boolean teamNameValidity = false;
	private boolean invitationValidity = false;


	private Button btnCancel;
	private Button btnOk;
	private Button btnCheck;
	private Button btnInvite;
	private EditText etTeamName;
	private TextView tvReiterationWarning;
	private TextView tvTeamMinMemberWarning;
	private TextView tvTeamMemberTitle;
	private TextView tvTeamInvited;
	private LinearLayout llContent;
	private FrameLayout flInvitation;
	private int flInvitationId;

	private List<FriendJson> invitation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		Log.d(TAG, "onCreateView()...");
		View rootView = inflater.inflate(R.layout.fragment_team_setup, container, false);
		flInvitationId = R.id.fl_establishing_team_invitation_frame;
		flInvitation = (FrameLayout) rootView.findViewById(flInvitationId);
		llContent = (LinearLayout) rootView.findViewById(R.id.ll_establishing_team_content);
		btnCancel = (Button) rootView.findViewById(R.id.btn_establish_team_cancel);
		btnOk = (Button) rootView.findViewById(R.id.btn_establish_team_ok);
		btnCheck = (Button) rootView.findViewById(R.id.btn_establish_team_check);
		btnInvite = (Button) rootView.findViewById(R.id.btn_establish_team_invite);
		etTeamName = (EditText) rootView.findViewById(R.id.et_establish_team_name);
		tvReiterationWarning = (TextView) rootView.findViewById(R.id.tv_establish_team_reiteration_warning);
		tvTeamMinMemberWarning = (TextView) rootView.findViewById(R.id.tv_establish_team_min_member_warning);
		tvTeamMemberTitle = (TextView) rootView.findViewById(R.id.tv_establish_team_invited_title);
		tvTeamInvited = (TextView) rootView.findViewById(R.id.tv_establish_team_invited_members);

		btnOk.setEnabled(false);
		btnInvite.setEnabled(false);
		btnCheck.setEnabled(false);
		btnCheck.setBackground(new ColorDrawable(getActivity().getResources().getColor(R.color.background_search_game_indi)));
		etTeamName.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{


			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				teamNameValidity = false;
				if (s.toString().length() >= 3)
				{
					btnCheck.setEnabled(true);
					btnCheck.setBackground(new ColorDrawable(Color.GREEN));
				} else
				{
					btnCheck.setEnabled(false);
					btnCheck.setBackground(new ColorDrawable(getActivity().getResources().getColor(R.color.background_search_game_indi)));
					btnInvite.setEnabled(false);
					btnInvite.setBackground(new ColorDrawable(getActivity().getResources().getColor(R.color.background_search_game_indi)));
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		btnCheck.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new GetTeamNameReIteration().execute(etTeamName.getText().toString());
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPress();
			}
		});

		btnInvite.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				llContent.setVisibility(View.GONE);
				flInvitation.setVisibility(View.VISIBLE);
				TeamEstablishingInvitationFragment invitationFragment = new TeamEstablishingInvitationFragment();
				setChildFragment(invitationFragment);
				getChildFragmentManager().beginTransaction().replace(flInvitationId, invitationFragment).commit();
			}
		});

		btnOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(invitationValidity && teamNameValidity)
				new PostTeamAdd().execute(etTeamName.getText().toString());
			}
		});

		return rootView;
	}

	public void setInvitation(List<FriendJson> list)
	{
		invitation = list;

		if(list.size()<2)
		{
			btnOk.setEnabled(false);
			invitationValidity = false;
			tvTeamMinMemberWarning.setVisibility(View.VISIBLE);
		}
		else
		{
			if(teamNameValidity)
			{
				etTeamName.setFocusable(false);
				etTeamName.setClickable(false);
				btnCheck.setVisibility(View.GONE);
				btnOk.setEnabled(true);
				invitationValidity = true;
				tvTeamMinMemberWarning.setVisibility(View.INVISIBLE);
				tvTeamMemberTitle.setVisibility(View.VISIBLE);
				tvTeamInvited.setVisibility(View.VISIBLE);
				String m = "";
				for(FriendJson f : list)
				{
					m += "\n" + f.getFriendUsername();
				}
				tvTeamInvited.setText(m.substring(1, m.length()));
			}
		}
	}

	@Override
	public boolean onBackPress()
	{
		if(!hasChild())
		{
			getParentFragment_().RemoveChild();
			return true;
		}
		else
		{
			return getChildFragment().onBackPress();
		}
	}

	@Override
	public void onRemoveChild()
	{
		flInvitation.setVisibility(View.GONE);
		llContent.setVisibility(View.VISIBLE);
	}

	public interface GetTeamNameReIterationService
	{
		@POST(ServerInfo.API_TEAM_NAME_REITERATION_POST)
		Call<Boolean> getIsReIter(@Body TeamReiterationPostJson name);
	}

	private class GetTeamNameReIteration extends AsyncTask<String, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(String... teamName)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetTeamNameReIterationService service = retrofit.create(GetTeamNameReIterationService.class);

				Call<Boolean> call = service.getIsReIter(new TeamReiterationPostJson(teamName[0]));

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
			return false;
		}

		@Override
		protected void onPostExecute(Boolean isReiter)
		{
			Log.d(TAG, "isReiter " + isReiter);
			if(!isReiter)
			{
				tvReiterationWarning.setTextColor(Color.RED);
				tvReiterationWarning.setText("중복되는 팀 이름입니다!");
				btnInvite.setEnabled(false);
				btnInvite.setBackground(new ColorDrawable(getActivity().getResources().getColor(R.color.background_search_game_indi)));
				teamNameValidity = false;
			}
			else
			{
				tvReiterationWarning.setTextColor(Color.GREEN);
				tvReiterationWarning.setText("사용 가능합니다");
				btnInvite.setEnabled(true);
				btnInvite.setBackground(new ColorDrawable(getActivity().getResources().getColor(R.color.background_search_game_team)));
				teamNameValidity = true;
			}
		}
	}

	public interface PostTeamAddService
	{
		@POST(ServerInfo.API_TEAM_ADD_POST)
		Call<String> putAddTeam(@Body TeamPostJson team);
	}

	private class PostTeamAdd extends AsyncTask<String, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(String... teamName)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostTeamAddService service = retrofit.create(PostTeamAddService.class);

				Call<String> call = service.putAddTeam(new TeamPostJson(teamName[0]
						, ((CustomApplication)getActivity().getApplication()).getProfile().getUserCode()
						, ""
						, 100));

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return true;
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
			return false;
		}

		@Override
		protected void onPostExecute(Boolean res)
		{
			Toast.makeText(getActivity()
					, res ? "팀이 등록되었습니다" : "팀 등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
			((CustomApplication)getActivity().getApplication()).loadProfile(
					((CustomApplication) getActivity().getApplication()).getProfile().getUserCode()
			);
			for(FriendJson f : invitation)
			{
				new PutTeamSignIn().execute(f.getFriendId()
				, ((CustomApplication) getActivity().getApplication()).getProfile().getTeamId());
			}

		}
	}

	public interface PutTeamSignInService
	{
		@PUT(ServerInfo.API_TEAM_SIGNIN_PUT)
		Call<String> signInTeam(@Path(ServerInfo.API_TEAM_SIGNIN_PATH) int userId ,@Body TeamSignInJson team);
	}

	private class PutTeamSignIn extends AsyncTask<Integer, Void, Boolean>
	{

		/**
		 *
		 * @param params 0_id of user which will be signed in, 1_id of team which user will sign in
		 * @return
		 */
		@Override
		protected Boolean doInBackground(Integer... params)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PutTeamSignInService service = retrofit.create(PutTeamSignInService.class);

				Call<String> call = service.signInTeam(params[0], new TeamSignInJson(params[1]));

				Response res = call.execute();

				if (res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return true;
				}
				else
				{
					Log.d(TAG, "Execution fail");
				}
				return false;
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
			if(res){Log.d(TAG, "User is signed in...");}
			else{Log.d(TAG, "User signing in fail...");}
		}
	}
}
