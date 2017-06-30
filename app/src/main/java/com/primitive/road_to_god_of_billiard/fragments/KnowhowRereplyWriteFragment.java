package com.primitive.road_to_god_of_billiard.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 신진우- on 2015-10-07.
 */
public class KnowhowRereplyWriteFragment extends ChainFragment
{
	private final String TAG = "KnowhowRereplyWriteFragment";
	private Button btnCancel;
	private Button btnOK;
	private EditText etContent;
	private RequestListener mListener;
	private int mReplyId;
	private int mPostid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_write_rereply, container, false);
		etContent = (EditText) rootView.findViewById(R.id.et_knowhow_post_rereply_write_content);
		btnCancel = (Button) rootView.findViewById(R.id.btn_knowhow_post_rereply_cancel);
		btnOK = (Button) rootView.findViewById(R.id.btn_knowhow_post_rereply_ok);
		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mListener.RequestRemove(KnowhowRereplyWriteFragment.this);
			}
		});
		btnOK.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PostRereply post = new PostRereply();
				post.execute(etContent.getText().toString());
			}
		});



		return rootView;
	}

	private interface PostRereplyService
	{
		@POST(ServerInfo.API_REPLY_POST)
		Call<ReplyJson> postRereply(@Body ReplyJson rereply);
	}

	private class PostRereply extends AsyncTask<String, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(String... content)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostRereplyService service = retrofit.create(PostRereplyService.class);

				Call<ReplyJson> call = service.postRereply(new ReplyJson(mPostid,
						((CustomApplication)getActivity().getApplication()).getProfile().getUserCode(),
						content[0],
						mReplyId));

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					return true;
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
					return true;
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean res)
		{
			if(res)
			{
				Toast.makeText(getActivity(), "답댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getActivity(), "답댓글 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
			}
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
			mListener.RequestRefresh();
			mListener.RequestRemove(KnowhowRereplyWriteFragment.this);
		}
	}

	public void setParams(int postId, int replyId)
	{
		mReplyId = replyId;
		mPostid = postId;
	}

	public void setRequestListener(RequestListener listener)
	{
		mListener = listener;
	}

	public interface RequestListener
	{
		void RequestRefresh();
		void RequestRemove(Fragment f);
	}
}
