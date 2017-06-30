package com.primitive.road_to_god_of_billiard.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.activities.LoginActivity;
import com.primitive.road_to_god_of_billiard.items.MenuListItem;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.MenuListAdapter;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 신진우- on 2015-07-31.
 */
public class LeftDrawerFragment extends ChainFragment
{
	private FragmentManager mFragmentManager;
	private ActionBarDrawerToggle dtToggle;
	private DrawerLayout dlDrawer;
	private int flMainContentId;
	private LinearLayout llBackground;
	private Context mContext;
	private ChainFragment currentFragment;

	private LinearLayout llSearchUser;
	private EditText etSearchUser;
	private ImageView ivSearchUser;

	private LinearLayout llSearchPost;
	private EditText etSearchPost;
	private ImageButton ibtnBoardFunction;
	private ImageView ivSearchPost;

	private Toolbar toolbar;
	private ListView lvMenu1;
	private ListView lvMenu2;

	private SharedPreferences sharedPrefs;
	private SharedPreferences.Editor prefsEditor;


	public LeftDrawerFragment()
	{
		// Constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{

		return inflater.inflate(R.layout.fragment_drawer_left, container, false);

	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout, Context context, FragmentManager fragmentManager, int contentId, Toolbar tb)
	{
		flMainContentId = contentId;
		mFragmentManager = fragmentManager;
		mContext = context;
		toolbar = tb;
		MenuListAdapter listAdapter1;
		MenuListAdapter listAdapter2;
		List<String> menuList1 = Arrays.asList(getResources().getStringArray(R.array.list_left_drawer_1));
		List<String> menuList2 = Arrays.asList(getResources().getStringArray(R.array.list_left_drawer_2));




		etSearchUser = (EditText) toolbar.findViewById(R.id.et_search_user);
		llSearchUser = (LinearLayout) tb.findViewById(R.id.ll_toolbar_search_user);
		llSearchPost = (LinearLayout) tb.findViewById(R.id.ll_toolbar_search_post);
		ImageView ivToMain = (ImageView) tb.findViewById(R.id.iv_toolbar_to_main);
		ivToMain.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				selectItem(-1);
			}
		});
		initToolbar();

		lvMenu1 = (ListView) getActivity().findViewById(R.id.lv_drawer_left_1);
		lvMenu1.setAdapter(listAdapter1 = new MenuListAdapter(mContext));
		lvMenu1.setOnItemClickListener(new ItemClickListener());

		lvMenu2 = (ListView) getActivity().findViewById(R.id.lv_drawer_left_2);
		lvMenu2.setAdapter(listAdapter2 = new MenuListAdapter(mContext));

		llBackground = (LinearLayout) getActivity().findViewById(R.id.ll_background_left);

		dlDrawer = drawerLayout;

		dlDrawer.setScrimColor(Color.parseColor("#00000000"));
		dtToggle = new ActionBarDrawerToggle(getActivity(), dlDrawer,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name)
		{
			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				//Toast.makeText(mContext, "onSlide(v) called", Toast.LENGTH_SHORT).show();

				super.onDrawerSlide(drawerView, slideOffset);
				llBackground.setTranslationX(slideOffset * drawerView.getWidth());
				//flMainContent.setTranslationX(slideOffset * drawerView.getWidth());
				dlDrawer.bringChildToFront(drawerView);
				dlDrawer.requestLayout();
			}
		};

		dlDrawer.setDrawerListener(dtToggle);




		//
		// EBGIN : Add the Items for Menu List
		//
		listAdapter1.addItem(new MenuListItem(getResources().getDrawable(R.drawable.knowhow_icon), menuList1.get(0), true));
		listAdapter1.addItem(new MenuListItem(getResources().getDrawable(R.drawable.pvp_icon), menuList1.get(1), false));
		listAdapter1.addItem(new MenuListItem(getResources().getDrawable(R.drawable.my_team), menuList1.get(2), false));
		listAdapter1.addItem(new MenuListItem(getResources().getDrawable(R.drawable.lanking), menuList1.get(3), false));
		listAdapter1.addItem(new MenuListItem(getResources().getDrawable(R.drawable.my_record), menuList1.get(4), false));

		listAdapter2.addItem(new MenuListItem(getResources().getDrawable(R.drawable.drawable_null), menuList2.get(0), true));
		listAdapter2.addItem(new MenuListItem(getResources().getDrawable(R.drawable.drawable_null), menuList2.get(1), true));
		listAdapter2.addItem(new MenuListItem(getResources().getDrawable(R.drawable.drawable_null), menuList2.get(2), true));
		listAdapter2.addItem(new MenuListItem(getResources().getDrawable(R.drawable.drawable_null), menuList2.get(3), true));

		setListViewHeightBasedOnChildren(lvMenu1);
		setListViewHeightBasedOnChildren(lvMenu2);
		//
		// END : Add the Items for Menu List
		//

		selectItem(-1);

	}

	public void selectItem(int position)
	{
		//Toast.makeText(mContext, (position + 1) + "click", Toast.LENGTH_SHORT).show();

		switch (position)
		{
			case -1: // 메인화면
				MainFragment mainFragment = new MainFragment();
				currentFragment = mainFragment;
				setToolbarState(1);
				mFragmentManager.beginTransaction().replace(flMainContentId, mainFragment).commit();
				break;
			case 0: // 노하우 게시판
				KnowHowBoardFragment knowHowBoardFragment = new KnowHowBoardFragment();
				knowHowBoardFragment.setToolbar(toolbar);
				currentFragment = knowHowBoardFragment;
				setToolbarState(2);
				mFragmentManager.beginTransaction().replace(flMainContentId, knowHowBoardFragment).commit();
				break;
			case 1: // 상대 검색
				SearchGameFragment searchGameFragment = new SearchGameFragment();
				currentFragment = searchGameFragment;
				setToolbarState(0);
				mFragmentManager.beginTransaction().replace(flMainContentId, searchGameFragment).commit();
				break;
			case 2: // 나의 팀
				MyTeamFragment myTeam = new MyTeamFragment();
				currentFragment = myTeam;
				setToolbarState(1);
				mFragmentManager.beginTransaction().replace(flMainContentId, myTeam).commit();
				break;
			case 3: // 당신의 지름길
				break;
			case 4: // 마이페이지
				MyPageFragment myPageFragment = new MyPageFragment();
				currentFragment = myPageFragment;
				setToolbarState(0);
				mFragmentManager.beginTransaction().replace(flMainContentId, myPageFragment).commit();
				break;
		}
	}

	public void selectItem2(int position) {
		Toast.makeText(mContext, (position + 1) + "click", Toast.LENGTH_SHORT).show();
		if (sharedPrefs == null) {
			sharedPrefs = getActivity().getApplicationContext().getSharedPreferences(CustomApplication.PREFERENCE_SHARED_FILENAME,
					Context.MODE_PRIVATE);
		}

		if (prefsEditor == null) {
			prefsEditor = sharedPrefs.edit();
		}
		switch (position) {
			case 3:
				if (CustomApplication.LOGIN_ENABLED == 0) {
					LoginActivity loginActivity = new LoginActivity();
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
				} else if(CustomApplication.LOGIN_ENABLED == 1){
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_drawer);
					builder.setTitle("로그아웃");
					builder.setMessage("로그아웃 하시겠습니까?");
					builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
							CustomApplication.LOGIN_ENABLED = 0;
							prefsEditor.remove("user_id");
							prefsEditor.remove("reg_id");
							prefsEditor.commit();
						}
					});
					builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {

						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				break;
		}
	}

	/**
	 *
	 * @param code : state of toolbar(0 : normal, 1 : searching user, 2 : searching post)
	 */
	private void setToolbarState(int code)
	{
		switch (code)
		{
			case 0:
				llSearchUser.setVisibility(View.GONE);
				llSearchPost.setVisibility(View.GONE);
				break;
			case 1:
				llSearchUser.setVisibility(View.VISIBLE);
				llSearchPost.setVisibility(View.GONE);
				break;
			case 2:
				llSearchUser.setVisibility(View.GONE);
				llSearchPost.setVisibility(View.VISIBLE);
				break;
		}
	}

	private void initToolbar()
	{
		etSearchUser = (EditText) toolbar.findViewById(R.id.et_toolbar_search);
		ivSearchUser = (ImageView) toolbar.findViewById(R.id.iv_toolbar_search);
	}

	private void showFunctionPopup(View anchorView)
	{
		View popupView = getLayoutInflater(null).inflate(R.layout.popup_knowhow_board_function, null);

		PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		// set view content
		ListView lvFunctions = (ListView) popupView.findViewById(R.id.lv_knowhow_post_reply_functions);
		lvFunctions.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				switch (position)
				{
					case 0:

						break;
				}
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

	class ItemClickListener implements AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
		{
			selectItem(position);
		}
	}

	@Override
	public boolean onBackPress()
	{
		return currentFragment.onBackPress();
	}


}
