package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.MyPageViewPagerAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class MyPageFragment extends ChainFragment
{
	private ViewPager vpPager;
	private Button btnTabIndi;
	private Button btnTabTeam;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

		vpPager = (ViewPager) rootView.findViewById(R.id.vp_my_page_content);
		btnTabIndi = (Button) rootView.findViewById(R.id.btn_my_page_individual);
		btnTabTeam = (Button) rootView.findViewById(R.id.btn_my_page_team);

		vpPager.setAdapter(new MyPageViewPagerAdapter(getChildFragmentManager()));
		btnTabIndi.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(0);
			}
		});
		btnTabTeam.setOnClickListener(new View.OnClickListener()
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
