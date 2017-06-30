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
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.BoardAdapter;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.MyRepliesListAdapter;
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
public class MyPostsRepliesFragment extends Fragment
{
	private final String TAG = "MyPostsRepliesFragment";
	private ListView lvReplies;
	private IRequestShowPost mRequest;
	private MyRepliesListAdapter bAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_my_posts_replies, container, false);
		lvReplies = (ListView) rootView.findViewById(R.id.lv_my_posts_replies_list);
		new GetRepliesByUser().execute(((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());


		return rootView;
	}

	public void setShowPostRequest(IRequestShowPost request)
	{
		mRequest = request;
	}

	public interface GetRepliesByUserService
	{
		@GET(ServerInfo.API_MY_REPLIES_GET)
		Call<List<ReplyJson>> getRepliesByUser(@Path(ServerInfo.API_MY_REPLIES_PATH) int userId);
	}
	private class GetRepliesByUser extends AsyncTask<Integer, Void, List<ReplyJson>>
	{
		@Override
		protected List<ReplyJson> doInBackground(Integer... userid)
		{
			try
			{
				Log.d(TAG, "GetRepliesByUser...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetRepliesByUserService service = retrofit.create(GetRepliesByUserService.class);

				Call<List<ReplyJson>> call = service.getRepliesByUser(userid[0]);

				Response res = call.execute();


				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (List<ReplyJson>)res.body();
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch(Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<ReplyJson> replies)
		{
			if(replies != null)
			{
				lvReplies.setAdapter(bAdapter = new MyRepliesListAdapter(getActivity(), replies));
				lvReplies.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						RequestPost req = new RequestPost();
						req.execute(
								((ReplyJson) bAdapter.getItem(position)).getParentPostId()
						);
					}
				});
			}
		} // onPostExecute
	}

	public interface GetPostService
	{
		@GET(ServerInfo.API_POST_GET)
		Call<PostJson> getPost(@Path(ServerInfo.API_POST_PATH) int postId);
	}

	private class RequestPost extends AsyncTask<Integer, Void, PostJson>
	{
		@Override
		protected PostJson doInBackground(Integer... postId)
		{
			try
			{
				Log.d(TAG, "GetPost...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetPostService service = retrofit.create(GetPostService.class);
				Call<PostJson> call = service.getPost(postId[0]);
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return (PostJson)res.body();
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(PostJson post)
		{
			if(mRequest != null)
			{
				KnowhowPostFragment frag = new KnowhowPostFragment();
				frag.setPost(post);
				mRequest.requestShow(frag);
			}
		}
	}
}
