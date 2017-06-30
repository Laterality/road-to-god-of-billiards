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
import com.primitive.road_to_god_of_billiard.items.KnowHowPost;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-09-23.
 */
public class BoardOrderbyPop extends Fragment
{
	private final String TAG = "BoardPop";
	private PullToRefreshListView lvList;
	private List<KnowHowPost> postList;
	private KnowHowBoardFragment.IBoardRequest boardRequest;
	private BoardAdapter bAdapter;

	public BoardOrderbyPop()
	{
		super();

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
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.board_knowhow_orderby_pop, container, false);
		lvList  = (PullToRefreshListView) rootView.findViewById(R.id.lv_board_orderby_pop);
		lvList.setAdapter(bAdapter = new BoardAdapter(getActivity(), new ArrayList<PostJson>(), true));
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
				new GetPostsOderbyPop().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{

			}
		});

		new GetPostsOderbyPop().execute();


		return rootView;
	}

	public void setRequestCallback(KnowHowBoardFragment.IBoardRequest callback)
	{
		boardRequest = callback;
	}

	public interface GetPostsOderbyPopService
	{
		@GET(ServerInfo.API_POST_ORDERBY_POP_GET)
		Call<List<PostJson>> getPosts();
	}

	private class GetPostsOderbyPop extends AsyncTask<Void,Void,List<PostJson>>
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

				GetPostsOderbyPopService service = retrofit.create(GetPostsOderbyPopService.class);
				Call<List<PostJson>> call = service.getPosts(); // TODO
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
			super.onPostExecute(posts);
			if (posts != null)
			{
				lvList.setAdapter(bAdapter = new BoardAdapter(getActivity(), posts, true));
				lvList.onRefreshComplete();
			}
		}
	}

}
