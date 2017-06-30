package com.primitive.road_to_god_of_billiard.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.utility.*;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.DELETE;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-25.
 */
public class MyPostsPostsLongClickFunctionDialog extends Dialog
{
	private final String TAG = "MyPostsLongClickDialog";
	private PostJson mPost;
	private ListView lvFunctions;
	private IMyPostsPostsLongClcikFunctionDialogRequest mListener;
	private Context mContext;

	public MyPostsPostsLongClickFunctionDialog(Context context, PostJson post)
	{
		super(context);
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_my_posts_posts_long_click);
		mPost = post;
		Log.d(TAG, "post id : " + mPost.getPostId());
		lvFunctions = (ListView)findViewById(R.id.lv_my_posts_posts_long_click_function_list);
		lvFunctions.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectItem(position);
				dismiss();
			}
		});
	}


	private void selectItem(int position)
	{
		switch (position)
		{
			case 0:
				new DeletePost().execute();
				mListener.RequestRefreshList();
				break;
			case 1:
				mListener.RequestShowPostModifying(mPost);
				break;
		}
	}

	public void setDialogListener(IMyPostsPostsLongClcikFunctionDialogRequest listener)
	{
		mListener = listener;
	}


	private interface DeletePostService
	{
		@DELETE(ServerInfo.API_POST_DELETE)
		Call<PostJson> deletePost(@Path(ServerInfo.API_POST_PATH) int postId);
	}

	private class DeletePost extends AsyncTask<Void, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(Void... param)
		{
			try
			{
				Log.d(TAG, "Delete Post...Entry Point");

				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				DeletePostService service = retrofit.create(DeletePostService.class);
				Call<PostJson> call = service.deletePost(mPost.getPostId());
				Response res = call.execute();
				if (res.isSuccess())
				{
					Log.d(TAG, "execution success");
					return true;
				}
				else
				{
					Log.d(TAG, "execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
					return false;
				}

			} catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean res)
		{
			Toast.makeText(mContext, res?
			"삭제되었습니다"
			: "삭제에 실패하였습니다"
			,Toast.LENGTH_SHORT).show();
		}
	}


}

