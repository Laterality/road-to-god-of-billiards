package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

import java.util.List;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class MakeTeamProgressViewPagerAdapter extends FragmentPagerAdapter
{
	List<ChainFragment> list;

	public MakeTeamProgressViewPagerAdapter(FragmentManager fm, List<ChainFragment> list)
	{
		super(fm);
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Fragment getItem(int position)
	{
		return list.get(position);
	}
}
