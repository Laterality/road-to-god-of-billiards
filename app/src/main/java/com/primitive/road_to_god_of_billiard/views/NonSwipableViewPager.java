package com.primitive.road_to_god_of_billiard.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 신진우- on 2015-10-31.
 */
public class NonSwipableViewPager extends ViewPager
{
	public NonSwipableViewPager(Context context)
	{
		super(context);
	}

	public NonSwipableViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
