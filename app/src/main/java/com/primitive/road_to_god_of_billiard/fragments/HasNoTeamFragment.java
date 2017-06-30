package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class HasNoTeamFragment extends ChainFragment
{
	private static final String TAG = "HasNoTeam";

	private Button btnEstablishTeam;
	private int flEstablishingId;
	private FrameLayout flEstablishing;
	private LinearLayout llContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_my_team_has_no_team, container, false);
		flEstablishingId = R.id.fl_has_no_team_establishing_fragments;
		flEstablishing = (FrameLayout) rootView.findViewById(flEstablishingId);
		llContent = (LinearLayout) rootView.findViewById(R.id.ll_has_no_team_content);
		btnEstablishTeam = (Button) rootView.findViewById(R.id.btn_has_no_team_make_team);

		btnEstablishTeam.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				llContent.setVisibility(View.GONE);
				flEstablishing.setVisibility(View.VISIBLE);
				TeamEstablishingFragment namingFragment = new TeamEstablishingFragment();
				setChildFragment(namingFragment);
				getChildFragmentManager().beginTransaction().replace(flEstablishingId, namingFragment).commit();
			}
		});


		return rootView;
	}

	@Override
	public boolean onBackPress()
	{
		if(hasChild()){return getChildFragment().onBackPress();}
		return false;
	}

	@Override
	public void onRemoveChild()
	{
		llContent.setVisibility(View.VISIBLE);
		flEstablishing.setVisibility(View.GONE);
	}




}
