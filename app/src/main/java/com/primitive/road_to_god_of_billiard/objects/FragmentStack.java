package com.primitive.road_to_god_of_billiard.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-07.
 */
public class FragmentStack
{

	List<ChainFragment> stack;

	public FragmentStack()
	{
		stack = new ArrayList<>();
	}

	public int getLength()
	{
		return stack.size();
	}

	public void push(ChainFragment f)
	{
		stack.add(f);
	}

	public ChainFragment pop()
	{
		if(stack.size()==0)
		{
			return null;
		}
		else
		{
			ChainFragment ret = stack.get(getLength() - 1);
			stack.remove(getLength() - 1);
			return ret;
		}
	}

	public void clearStack()
	{
		stack.clear();
	}

	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
}
