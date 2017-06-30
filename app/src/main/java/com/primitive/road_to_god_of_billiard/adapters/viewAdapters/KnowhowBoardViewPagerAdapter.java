package com.primitive.road_to_god_of_billiard.adapters.viewAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.primitive.road_to_god_of_billiard.fragments.BoardOrderbyLatest;
import com.primitive.road_to_god_of_billiard.fragments.BoardOrderbyPop;
import com.primitive.road_to_god_of_billiard.fragments.KnowHowBoardFragment;

/**
 * Created by 신진우- on 2015-09-27.
 */
public class KnowhowBoardViewPagerAdapter extends FragmentPagerAdapter
{
	private static final int FRAGMENT_CNT = 2; // the number of view pages

	KnowHowBoardFragment.IBoardRequest requestCallbcak;

	public KnowhowBoardViewPagerAdapter(FragmentManager fm, KnowHowBoardFragment.IBoardRequest callback)
	{
		super(fm);
		requestCallbcak = callback;
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
				BoardOrderbyPop boardPop = new BoardOrderbyPop();
				boardPop.setRequestCallback(requestCallbcak);
				return boardPop;
			case 1:
				BoardOrderbyLatest boardLatest = new BoardOrderbyLatest();
				boardLatest.setRequestCallback(requestCallbcak);
				return boardLatest;
		}

		return null;
	}



}
