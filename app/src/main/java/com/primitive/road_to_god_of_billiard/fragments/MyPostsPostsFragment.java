package com.primitive.road_to_god_of_billiard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.BoardAdapter;
import com.primitive.road_to_god_of_billiard.utility.*;
import com.primitive.road_to_god_of_billiard.dialogs.MyPostsPostsLongClickFunctionDialog;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.IRequestShowPost;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-06.
 */
public class MyPostsPostsFragment extends Fragment
{
	private final String TAG = "MyPostsFragment";
	private ListView lvPosts;
	private BoardAdapter boardAdapter;
	private LinearLayout llContent;
	private IRequestShowPost mRequest;
	private IRequestModifyPost mModifyRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_my_posts_posts, container, false);

		lvPosts = (ListView) rootView.findViewById(R.id.lv_my_posts_posts_list);
		llContent = (LinearLayout) rootView.findViewById(R.id.ll_my_posts_content);
		//lvPosts.setAdapter(new BoardAdapter(getActivity(), getTestingSource(), true));
		lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if(mRequest != null)
				{
					KnowhowPostFragment frag = new KnowhowPostFragment();
					frag.setPost((PostJson)boardAdapter.getItem(position));
					mRequest.requestShow(frag);
				}
			}
		});
		lvPosts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				MyPostsPostsLongClickFunctionDialog dialog = new MyPostsPostsLongClickFunctionDialog(getActivity(), (PostJson)boardAdapter.getItem(position));
				dialog.setDialogListener(new IMyPostsPostsLongClcikFunctionDialogRequest()
				{
					@Override
					public void RequestShowPostModifying(PostJson post)
					{
						Log.d(TAG, "post id : " + post.getPostId());
						mModifyRequest.requestModify(post);
					}

					@Override
					public void RequestRefreshList()
					{
						new GetMyPosts().execute(((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());
					}
				});
				dialog.show();

				return true;
			}
		});

		new GetMyPosts().execute(((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());




		return rootView;
	}


	public void setShowPostListener(IRequestShowPost listener)
	{
		mRequest = listener;
	}

	public void setRequestModifyListener(IRequestModifyPost listener)
	{
		mModifyRequest = listener;
	}

	public interface GetMyPostsService
	{
		@GET(ServerInfo.API_MY_POSTS_GET)
		Call<List<PostJson>> getMyPosts(@Path(ServerInfo.API_MY_POSTS_PATH) int userId);
	}
	private class GetMyPosts extends AsyncTask<Integer, Void, List<PostJson>>
	{
		@Override
		protected List<PostJson> doInBackground (Integer... userId)
		{
			try
			{
				Log.d(TAG, "GetMyPosts...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetMyPostsService service = retrofit.create(GetMyPostsService.class);

				Call<List<PostJson>> call = service.getMyPosts(userId[0]);

				Response res = call.execute();
				if (res.isSuccess())
				{
					Log.d(TAG, "GetMyPosts...Execution success");
					List<PostJson> posts = (List<PostJson>)res.body();
					return posts;
				}
				else
				{
					Log.d(TAG, "GetMyPosts...Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}

			}
			catch (Exception e)
			{
				Log.d(TAG,"GetMyPosts...Exception occurred");
				Log.d(TAG, e.getMessage());
			}


			return null;
		}

		@Override
		protected void onPostExecute(List<PostJson> posts)
		{
			lvPosts.setAdapter(boardAdapter = new BoardAdapter(getActivity(), posts, true));

		}
	}

}

interface IRequestModifyPost
{
	void requestModify(PostJson post);
}
