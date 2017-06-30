package com.primitive.road_to_god_of_billiard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.BoardRequestBodyJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.BoardAdapter;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-09-23.
 */
public class BoardOrderbyLatest extends Fragment
{
	private final int TEMP_HEAD_CONST = 2000000000;
	private final String TAG = "BoardLat";
	private boolean FLAG_FIRST_REFRESHING = true;
	private PullToRefreshListView lvList;
	private KnowHowBoardFragment.IBoardRequest boardRequest;
	private BoardAdapter bAdapter;

	public BoardOrderbyLatest()
	{
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.board_knowhow_orderby_latest, container, false);
		lvList  = (PullToRefreshListView) rootView.findViewById(R.id.lv_board_orderby_latest);
		lvList.setAdapter(bAdapter = new BoardAdapter(getActivity(), new ArrayList<PostJson>(), false));
		lvList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				boardRequest.RequestShowPost((PostJson) bAdapter.getItem(position - 1));
			}
		});
		lvList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				boardRequest.RequestShowPost((PostJson) bAdapter.getItem(position - 1));
			}
		});
		lvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
		{
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				new GetPostsOderbyLat().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{

			}
		});
		lvList.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener()
		{
			@Override
			public void onLastItemVisible()
			{
				new GetPastPostOrderbyLatest().execute();
			}
		});
		new GetPostsOderbyLat().execute();

		return rootView;
	}

	public void setRequestCallback(KnowHowBoardFragment.IBoardRequest callback)
	{
		boardRequest = callback;
	}


	public interface GetPostsOderbyLatService
	{
		@POST(ServerInfo.API_POST_ORDERBY_LAT_POST)
		Call<List<PostJson>> getPosts(@Body BoardRequestBodyJson json);
	}

	/**
	 * Refreshing
	 */
	private class GetPostsOderbyLat extends AsyncTask<Void,Void,List<PostJson>>
	{
		@Override
		protected List<PostJson> doInBackground(Void... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetPostsOderbyLatService service = retrofit.create(GetPostsOderbyLatService.class);
				Call<List<PostJson>> call = service.getPosts(
						FLAG_FIRST_REFRESHING? new BoardRequestBodyJson(TEMP_HEAD_CONST) : new BoardRequestBodyJson(bAdapter.getHeadId() + 10));
				if(FLAG_FIRST_REFRESHING){FLAG_FIRST_REFRESHING = false;}
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (List<PostJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}// doInBackground

		@Override
		protected void onPostExecute(List<PostJson> posts)
		{
			if (posts != null)
			{
				bAdapter.insertListToHead(posts);
				lvList.onRefreshComplete();
			}
		}
	}

	private class GetPastPostOrderbyLatest extends AsyncTask<Void,Void, List<PostJson>>
	{
		@Override
		protected List<PostJson> doInBackground(Void... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetPostsOderbyLatService service = retrofit.create(GetPostsOderbyLatService.class);

				Call<List<PostJson>> call = service.getPosts(new BoardRequestBodyJson(bAdapter.getTailId()));

				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (List<PostJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		} // doInBackground

		@Override
		protected void onPostExecute(List<PostJson> posts)
		{
			bAdapter.appendList(posts);
		}
	}
}
