package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.primitive.road_to_god_of_billiard.fragments.MyInfoFragment;
import com.primitive.road_to_god_of_billiard.fragments.MyTeamFragment;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class MyPageViewPagerAdapter extends FragmentPagerAdapter
{
	private static final int FRAGMENT_CNT = 2; // the number of view pages

	public MyPageViewPagerAdapter(FragmentManager fm)
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
				return new MyInfoFragment();
			case 1:
				return new MyTeamFragment();
		}

		return null;
	}

}
