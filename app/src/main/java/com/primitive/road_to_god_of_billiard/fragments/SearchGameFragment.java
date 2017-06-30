package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.SearchGameViewPagerAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class SearchGameFragment extends ChainFragment
{
	private ViewPager vpPager;
	private Button btnIndi;
	private Button btnTeam;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState0)
	{
		super.onCreateView(inflater, container, savedInstanceState0);

		View rootView = inflater.inflate(R.layout.fragment_search_game, container, false);

		vpPager = (ViewPager) rootView.findViewById(R.id.vp_search_game_pager);
		btnIndi = (Button)rootView.findViewById(R.id.btn_searh_game_individual);
		btnTeam = (Button) rootView.findViewById(R.id.btn_searh_game_team);

		vpPager.setAdapter(new SearchGameViewPagerAdapter(getChildFragmentManager()));

		btnIndi.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(0);
			}
		});
		btnTeam.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(1);
			}
		});


		return rootView;
	}
}
