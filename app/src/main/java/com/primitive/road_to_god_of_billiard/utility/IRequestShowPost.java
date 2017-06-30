package com.primitive.road_to_god_of_billiard.utility;

import android.support.v4.app.Fragment;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.fragments.KnowhowPostFragment;

/**
 * Created by 신진우- on 2015-10-21.
 */
public interface IRequestShowPost
{
	void requestShow(KnowhowPostFragment post);
	void requestDismiss(Fragment f);
}
