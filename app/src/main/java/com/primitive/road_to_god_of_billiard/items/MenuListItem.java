package com.primitive.road_to_god_of_billiard.items;

import android.graphics.drawable.Drawable;

/**
 * Created by 신진우- on 2015-09-27.
 */
public class MenuListItem
{
	public Drawable mIcon;
	public String mTitle;
	public boolean isNew;

	public MenuListItem(Drawable icon, String title, boolean isNew)
	{
		mIcon = icon;
		mTitle = title;
		this.isNew = isNew;
	}
}