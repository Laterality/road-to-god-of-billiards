package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.primitive.road_to_god_of_billiard.R;

/**
 * Created by 신진우- on 2015-09-30.
 */
public class MyInfoFragment extends Fragment
{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState)
	{
		super.onCreateView(inflater, container, savedinstanceState);

		View rootView = inflater.inflate(R.layout.fragment_my_info, container, false);


		return rootView;
	}
}
