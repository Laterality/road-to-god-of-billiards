package com.primitive.road_to_god_of_billiard.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.UserListAdapter;

/**
 * Created by 신진우- on 2015-07-31.
 */
public class RightDrawerFragment extends Fragment
{
	private View mFragmentContainerView;
	private ActionBarDrawerToggle dtToggle;
	private DrawerLayout dlDrawer;
	private FrameLayout flMainContent;
	private LinearLayout llBackground;
	private Context mContext;
	private ListView lvSettings;
	private EditText etSearch;

	public RightDrawerFragment()
	{
		// Contsructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_drawer_right, container, false);
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout, Context context)
	{
		mContext = context;

		lvSettings = (ListView) getActivity().findViewById(R.id.lv_right_drawer);
		lvSettings.setAdapter(new UserListAdapter(mContext));
		lvSettings.setOnItemClickListener(new ItemClickListener());

		etSearch = (EditText) getActivity().findViewById(R.id.et_search_user);
		etSearch.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// send query searching user
				Toast.makeText(mContext, "TextChanged", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});


		llBackground = (LinearLayout) getActivity().findViewById(R.id.ll_background_right);

		dlDrawer = drawerLayout;
		dlDrawer.setScrimColor(Color.parseColor("#00000000"));
		dtToggle = new ActionBarDrawerToggle(getActivity(), dlDrawer,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name)
		{
			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
				View view = getActivity().getCurrentFocus();
				if(view != null)
				{
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				etSearch.setSelection(0, etSearch.getText().toString().length());
				etSearch.requestFocus();
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				super.onDrawerSlide(drawerView, slideOffset);
				llBackground.setTranslationX((slideOffset * -1) * drawerView.getWidth());
				dlDrawer.bringChildToFront(drawerView);
				dlDrawer.requestLayout();
			}
		};
		dlDrawer.setDrawerListener(dtToggle);
	}


	public void selectItem(int position)
	{
		Toast.makeText(mContext, (position + 1) + "click", Toast.LENGTH_SHORT).show();
	}

	class ItemClickListener implements AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
		{
			selectItem(position);
		}
	}
}
