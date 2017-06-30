package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.MyPostsViewPagerAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.IRequestShowPost;
import com.squareup.okhttp.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-06.
 */
public class MyPostsFragment extends ChainFragment
{
	private final String TAG = "MyPostFragment";
	private ViewPager vpPager;
	private Button btnTab0;
	private Button btnTab1;
	private FrameLayout flPost;
	private LinearLayout llContent;
	private int flPostId;
	private boolean isShowing = false;
	private Fragment currentFragment;
	private IRequestShowModify mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_my_posts, container, false);
		vpPager = (ViewPager) rootView.findViewById(R.id.vp_my_posts_tab);
		btnTab0 = (Button) rootView.findViewById(R.id.btn_my_posts_tab_0);
		btnTab1 = (Button) rootView.findViewById(R.id.btn_my_posts_tab_1);
		flPostId = R.id.fl_my_posts;
		flPost = (FrameLayout) rootView.findViewById(flPostId);
		llContent = (LinearLayout) rootView.findViewById(R.id.ll_my_posts_content);

		List<Fragment> pages = new ArrayList<>();
		MyPostsPostsFragment frag1 = new MyPostsPostsFragment();
		frag1.setShowPostListener(new IRequestShowPost()
		{
			@Override
			public void requestShow(KnowhowPostFragment post)
			{
				isShowing = true;
				set();
				currentFragment = post;
				post.setCallback(new KnowHowBoardFragment.IRequestRemove()
				{
					@Override
					public void RequestRemove(Fragment f)
					{
						getChildFragmentManager().beginTransaction().remove(f);
						isShowing = false;
						set();
					}
				});
				getChildFragmentManager().beginTransaction().replace(flPostId, post).addToBackStack("").commit();
			}

			@Override
			public void requestDismiss(Fragment f)
			{
				getChildFragmentManager().beginTransaction().remove(f);
				isShowing = false;
				set();
			}
		});

		frag1.setRequestModifyListener(new IRequestModifyPost()
		{
			@Override
			public void requestModify(PostJson post)
			{
				Log.d(TAG, "post id" + post.getPostId());
				mListener.requestModify(post);
			}
		});

		MyPostsRepliesFragment frag2 = new MyPostsRepliesFragment();
		frag2.setShowPostRequest(new IRequestShowPost()
		{
			@Override
			public void requestShow(KnowhowPostFragment post)
			{
				isShowing = true;
				set();
				currentFragment = post;
				post.setCallback(new KnowHowBoardFragment.IRequestRemove()
				{
					@Override
					public void RequestRemove(Fragment f)
					{
						getChildFragmentManager().beginTransaction().remove(f);
						isShowing = false;
						set();
					}
				});
				getChildFragmentManager().beginTransaction().replace(flPostId, post).addToBackStack("").commit();
			}

			@Override
			public void requestDismiss(Fragment f)
			{
				getChildFragmentManager().beginTransaction().remove(f);
				isShowing = false;
				set();
			}
		});

		pages.add(frag1);
		pages.add(frag2);
		vpPager.setAdapter(new MyPostsViewPagerAdapter(getChildFragmentManager(), pages));
		btnTab0.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(0);
			}
		});
		btnTab1.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(1);
			}
		});

		return rootView;
	}

	private void set()
	{
		if(isShowing)
		{
			flPost.setVisibility(View.VISIBLE);
			llContent.setVisibility(View.GONE);
		}
		else
		{
			flPost.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onBackPress()
	{
		getParentFragment_().RemoveChild();
		return true;
	}


	public void setRequestModify(IRequestShowModify listener)
	{
		mListener = listener;
	}
}

interface IRequestShowModify
{
	void requestModify(PostJson post);
}