package com.primitive.road_to_god_of_billiard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.MyNewsListAdapter;
import com.primitive.road_to_god_of_billiard.items.MyNews;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 신진우- on 2015-10-07.
 */
public class MyNewsFragment extends ChainFragment
{
	private Button btnRemoveCancel;
	private Button btnOK;
	private ListView lvNews;
	private MyNewsListAdapter adapter;

	private boolean removeModeState = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_my_news, container, false);

		btnRemoveCancel = (Button) rootView.findViewById(R.id.btn_my_news_remove_cancel);
		btnOK = (Button) rootView.findViewById(R.id.btn_my_news_ok);
		lvNews = (ListView) rootView.findViewById(R.id.lv_my_news_list);
		adapter = new MyNewsListAdapter(getActivity(), getTestingSource());
		lvNews.setAdapter(adapter);
		btnRemoveCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (removeModeState)
				{
					removeModeState = false;
					btnRemoveCancel.setText("삭제");
					btnOK.setVisibility(View.INVISIBLE);
				} else
				{
					removeModeState = true;
					btnRemoveCancel.setText("취소");
					btnOK.setVisibility(View.VISIBLE);
				}
				adapter.toggleMode();
			}
		});


		return rootView;
	}

	private List<MyNews> getTestingSource()
	{
		List<MyNews> list = new ArrayList<>();
		list.add(new MyNews(0, MyNews.MY_NEWS_REPLY, "댓글이 달렸어요", "2015. 10. 07"));
		list.add(new MyNews(1, MyNews.MY_NEWS_REPLY, "댓글이 달렸어요", "2015. 10. 07"));
		list.add(new MyNews(1, MyNews.MY_NEWS_REREPLY, "답댓글이 달렸어요", "2015. 10. 07"));

		return list;
	}

	@Override
	public boolean onBackPress()
	{
		getParentFragment_().RemoveChild();
		return true;
	}
}
