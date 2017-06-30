package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.primitive.road_to_god_of_billiard.fragments.SearchGameIndividualFragment;
import com.primitive.road_to_god_of_billiard.fragments.SearchGameTeamFragment;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class SearchGameViewPagerAdapter extends FragmentPagerAdapter
{
	private static final int FRAGMENT_CNT = 2;


	public SearchGameViewPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public int getCount()
	{
		return FRAGMENT_CNT;
	}

	@Override
	public Fragment getItem(int position)
	{
		switch (position)
		{
			case 0:
				return new SearchGameTeamFragment();
			case 1:
				return new SearchGameIndividualFragment();

		}

		return null;
	}


}
