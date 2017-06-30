package com.primitive.road_to_god_of_billiard.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.activities.MainActivity;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.KnowhowBoardViewPagerAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

/**
 * Created by 신진우- on 2015-09-23.
 */
public class KnowHowBoardFragment extends ChainFragment
{
	private final String TAG = "KnowhowBoardFragment";

	private ViewPager vpPager;
	private Button btnPop;
	private Button btnLatest;
	private ImageButton ibtnFunction;
	private LinearLayout llParent;
	private EditText etSearch;
	private int flPostId;
	private FrameLayout flPost;
	private LinearLayout llContent;

	private EditText etSearchPost;
	private ImageView ivSearchPost;
	private ImageButton ibtnBoardFunction;

	private IBoardRequest boardRequest;
	private IReplyRequest replyRequest;


	private boolean isFragmentShowing = false;
	private Toolbar toolbar;


	//Mandatory Constructor
	public KnowHowBoardFragment()
	{
		super();
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		replyRequest = new IReplyRequest()
		{
			@Override
			public void RequestShowReplyFunction(View anchorView, int replyId)
			{
				showReplyFunctionPopup(anchorView, replyId);
			}
		};

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_board_knowhow, container, false);

		flPostId = R.id.fl_knowhow_board_post;
		flPost = (FrameLayout) rootView.findViewById(flPostId);
		llContent = (LinearLayout) rootView.findViewById(R.id.ll_knowhow_board_content);
		//
		// BEGIN : setting up to showing post
		//
		boardRequest = new IBoardRequest()
		{
			@Override
			public void RequestShowPost(PostJson post)
			{
				showPost(post);
			}
		};
		//
		// END : setting up to showing post
		//

		vpPager = (ViewPager) rootView.findViewById(R.id.vp_knowhow_board_pager);
		vpPager.setAdapter(new KnowhowBoardViewPagerAdapter(getChildFragmentManager(), boardRequest));
		vpPager.setCurrentItem(0);


		btnPop = (Button) rootView.findViewById(R.id.btn_knowhow_board_tab_0);
		btnLatest = (Button) rootView.findViewById(R.id.btn_knowhow_board_tab_1);
		//ibtnFunction = (ImageButton) rootView.findViewById(R.id.ibtn_icon_search);
		//etSearch = (EditText) rootView.findViewById(R.id.et_fragment_board_knowhow_search);

		btnPop.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(0);
			}
		});

		btnLatest.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				vpPager.setCurrentItem(1);
			}
		});

		/*
		ibtnFunction.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showFunctionPopup(v);
			}
		});*/


		return rootView;
	}

	private void showPost(PostJson post)
	{
		isFragmentShowing = true;
		llContent.setVisibility(View.GONE);
		flPost.setVisibility(View.VISIBLE);
		Bundle args = new Bundle();
		args.putLong("postId", post.getPostId());
		KnowhowPostFragment postFragment = new KnowhowPostFragment();
		postFragment.setPost(post);

		postFragment.setArguments(args);

		setChildFragment(postFragment);
		postFragment.setParentFragment(this);
		getChildFragmentManager().beginTransaction().replace(flPostId, postFragment).commit();
	}

	private void showWritePost()
	{
		llContent.setVisibility(View.GONE);
		flPost.setVisibility(View.VISIBLE);
		isFragmentShowing = true;
		KnowhowPostWriteFragment writeFragment = new KnowhowPostWriteFragment();

		setChildFragment(writeFragment);
		getChildFragmentManager().beginTransaction().replace(flPostId, writeFragment).commit();
	}

	private void showWritePost(PostJson post)
	{
		llContent.setVisibility(View.GONE);
		flPost.setVisibility(View.VISIBLE);
		isFragmentShowing = true;
		KnowhowPostWriteFragment writeFragment = new KnowhowPostWriteFragment();
		Bundle arg = new Bundle();
		arg.putSerializable("Target",post);
		writeFragment.setArguments(arg);

		setChildFragment(writeFragment);
		getChildFragmentManager().beginTransaction().replace(flPostId, writeFragment).commit();
	}

	private void showMyPosts()
	{
		isFragmentShowing = true;
		llContent.setVisibility(View.GONE);
		flPost.setVisibility(View.VISIBLE);

		MyPostsFragment myPostsFragment = new MyPostsFragment();
		myPostsFragment.setRequestModify(new IRequestShowModify()
		{
			@Override
			public void requestModify(PostJson post)
			{
				Log.d(TAG, "post id  : " + post.getPostId());
				showWritePost(post);
			}
		});
		setChildFragment(myPostsFragment);
		getChildFragmentManager().beginTransaction().replace(flPostId, myPostsFragment).commit();
	}


	private void showMyNews()
	{
		isFragmentShowing = true;
		llContent.setVisibility(View.GONE);
		flPost.setVisibility(View.VISIBLE);

		MyNewsFragment newsFragment = new MyNewsFragment();
		setChildFragment(newsFragment);
		getChildFragmentManager().beginTransaction().replace(flPostId, newsFragment).commit();
	}

	private void showFunctionPopup(View anchorView)
	{
		View popupView = getLayoutInflater(null).inflate(R.layout.popup_knowhow_board_function, null);

		final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		// set view content
		ListView lvFunctions = (ListView) popupView.findViewById(R.id.lv_knowhow_board_function_list);
		lvFunctions.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				switch (position)
				{
					case 0:
						showWritePost();
						break;
					case 1:
						showMyPosts();
						break;
					case 2:
						// show my Box Fragment
						break;
					case 3:
						showMyNews();
						break;
				}
				popupWindow.dismiss();
			}
		});

		// If  the PopupWindow should be focusable
		popupWindow.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		popupWindow.setBackgroundDrawable(new ColorDrawable());

		int[] location = new int[2];
		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.setWidth(DpToPx(120));
		popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1]);
	}

	private void showReplyFunctionPopup(View anchorView, long replyId)
	{
		LayoutInflater inflater = (LayoutInflater)MainActivity.rootContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = getLayoutInflater(null).inflate(R.layout.popup_knowhow_post_reply_function, null);

		PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		// set view content
		ListView lvFunctions = (ListView) popupView.findViewById(R.id.lv_my_team_function_list);

		// If  the PopupWindow should be focusable
		popupWindow.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		popupWindow.setBackgroundDrawable(new ColorDrawable());

		int[] location = new int[2];
		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.setWidth(DpToPx(120));
		popupWindow.showAtLocation(anchorView, Gravity.LEFT, location[0], location[1]);
	}


	@Override
	public boolean onBackPress()
	{
		if(hasChild())
		{
			getChildFragment().onBackPress();
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void onRemoveChild()
	{
		flPost.setVisibility(View.GONE);
		llContent.setVisibility(View.VISIBLE);
	}

	private int DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return (int)px;
	}

	/**** Method for Setting the Height of the ListView dynamically.
	 **** Hack to fix the issue of not showing all the items of the ListView
	 **** when placed inside a ScrollView  ****/
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void initToolbar()
	{
		etSearchPost = (EditText) toolbar.findViewById(R.id.et_fragment_board_knowhow_search);
		ibtnBoardFunction = (ImageButton) toolbar.findViewById(R.id.ibtn_icon_search);
		ivSearchPost = (ImageView) toolbar.findViewById(R.id.iv_icon_search);
		ibtnBoardFunction.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showFunctionPopup(v);
			}
		});
	}

	public void setToolbar(Toolbar tb)
	{
		toolbar = tb;
		initToolbar();
	}

	/*
	@Override
	public void OnBackPressed()
	{
		Log.d(TAG, "KnowhowBoard.OnBackPressed()...");
		if(isFragmentShowing)
		{

			if(currentFragment instanceof KnowhowPostFragment)
			{
				Log.d(TAG, "invoke KnowhowPostFragment.OnBackPressed...");
				((KnowhowPostFragment)currentFragment).OnBackPressed();
			}
			else
			{
				Log.d(TAG, "remove currentFragment...");
				removeFragment(currentFragment);
			}

			Log.d(TAG, "remove currentFragment...");
			removeFragment(currentFragment);
		}
		else
		{
			if(getActivity() != null)
			{
				((MainActivity) getActivity()).invokeSuperBack();
			}
		}
	}*/

	public interface IBoardRequest
	{
		void RequestShowPost(PostJson post);
	}

	public interface IReplyRequest
	{
		void RequestShowReplyFunction(View anchorView, int replyId);
	}

	public interface IRequestRemove
	{
		//void RequestShowReplyFunction(View anchorView, long replyId);
		void RequestRemove(Fragment f);
	}
}
