package com.primitive.road_to_god_of_billiard.objects;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by 신진우- on 2015-10-28.
 */
public class ChainFragment extends Fragment
{
	private ChainFragment childFragment;
	private ChainFragment parentFragment;

	public void setParentFragment (ChainFragment f)
	{
		parentFragment = f;
	}

	public void setChildFragment (ChainFragment f)
	{
		f.setParentFragment(this);
		childFragment = f;
	}

	public ChainFragment getParentFragment_()
	{
		return parentFragment;
	}

	public ChainFragment getChildFragment()
	{
		return childFragment;
	}

	public void RemoveChild()
	{
		onRemoveChild();
		getChildFragmentManager().beginTransaction().remove(getChildFragment()).commit();
		childFragment = null;
	}

	public boolean hasChild()
	{
		if(childFragment != null){return true;}
		else{return false;}
	}

	public boolean hasParent()
	{
		if(parentFragment != null){return true;}
		else{return false;}
	}

	/**
	 * should be Override
	 * @return whether the BackPress Event handled
	 */
	public boolean onBackPress()
	{
		return false;
	}


	public void onRemoveChild()
	{

	}
}
