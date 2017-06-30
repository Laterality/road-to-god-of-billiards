package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.primitive.road_to_god_of_billiard.fragments.MyPostsPostsFragment;
import com.primitive.road_to_god_of_billiard.fragments.MyPostsRepliesFragment;

import java.util.List;

/**
 * Created by 신진우- on 2015-10-06.
 */
public class MyPostsViewPagerAdapter extends FragmentPagerAdapter
{
	private List<Fragment> pages;

	public MyPostsViewPagerAdapter(FragmentManager fm, List<Fragment> list)
	{
		super(fm);
		pages = list;
	}

	@Override
	public int getCount()
	{
		return pages.size();
	}

	@Override
	public Fragment getItem(int position)
	{
		return pages.get(position);
	}

}
