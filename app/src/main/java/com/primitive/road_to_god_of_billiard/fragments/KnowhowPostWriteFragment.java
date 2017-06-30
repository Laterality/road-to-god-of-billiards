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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-06.
 */
public class KnowhowPostWriteFragment extends ChainFragment
{
	private final String TAG = "postWritingFragment";
	private Button btnCancel;
	private Button btnWrite;
	private EditText etTitle;
	private EditText etContent;
	private ImageButton ibtnAttachExpand;
	private ImageButton ibtnAttachPicture;
	private ImageButton ibtnAttachVideo;
	private ImageButton ibtnAttachLink;
	private LinearLayout llExapnd;

	private PostJson willModifiedPost;
	private boolean flag_modifying = false;

	private boolean isExpanded = false;

	public KnowhowPostWriteFragment()
	{
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d(TAG, "Knowhow Posting...Entry Point");
		try{willModifiedPost = (PostJson)getArguments().getSerializable("Target");}
		catch (Exception e){}
		if(willModifiedPost != null){flag_modifying = true;}
		Log.d(TAG, "Modifying? " + flag_modifying);
		if(flag_modifying){Log.d(TAG, "modifying post Id : " + willModifiedPost.getPostId());}

		View rootView = inflater.inflate(R.layout.fragment_knowhow_write_post, container, false);

		btnCancel = (Button) rootView.findViewById(R.id.btn_knowhow_write_cancel);
		btnWrite = (Button) rootView.findViewById(R.id.btn_knowhow_write_add);
		etTitle = (EditText) rootView.findViewById(R.id.et_knowhow_write_title);
		etContent = (EditText) rootView.findViewById(R.id.et_knowhow_write_content);
		ibtnAttachExpand = (ImageButton) rootView.findViewById(R.id.ibtn_knowhow_write_attach_expand);
		ibtnAttachPicture = (ImageButton) rootView.findViewById(R.id.ibtn_knowhow_write_attach_picture);
		ibtnAttachVideo = (ImageButton) rootView.findViewById(R.id.ibtn_knowhow_write_attach_video);
		ibtnAttachLink = (ImageButton) rootView.findViewById(R.id.ibtn_knowhow_write_attach_link);
		llExapnd = (LinearLayout) rootView.findViewById(R.id.ll_knowhow_write_attach_function_expanded);

		ibtnAttachExpand.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(isExpanded)
				{
					isExpanded = false;
					llExapnd.setVisibility(View.GONE);
				}
				else
				{
					isExpanded = true;
					llExapnd.setVisibility(View.VISIBLE);
				}
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
				suicide();
			}
		});
		btnWrite.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(etContent.getText().length() >= 15)
				{
					if(!flag_modifying)
					{
						Date date = new Date();
						String fDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
						PostPost post = new PostPost();
						Log.d(TAG, "write post...author id : " + ((CustomApplication) getActivity().getApplication()).getProfile().getUserCode());
						post.execute(new PostJson(
								((CustomApplication) getActivity().getApplication()).getProfile().getUserCode(),/**/
								etTitle.getText().toString(),
								etContent.getText().toString(),
								fDate
						));

					}
					else
					{
						new PutPost().execute(new PostJson(willModifiedPost.getPostId()
								, willModifiedPost.getAuthorId()
								,etTitle.getText().toString()
						,etContent.getText().toString()
						,willModifiedPost.getDate()));
					}
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
					Log.d(TAG, "btnWrite onClick()...");
					suicide();

				}
				else
				{
					Toast.makeText(getActivity(), "게시글은 최소 15자 이상이어야 합니다",Toast.LENGTH_SHORT).show();
				}
			}
		});

		if(flag_modifying)
		{
			etTitle.setText(willModifiedPost.getTitle());
			etContent.setText(willModifiedPost.getContent());
		}


		return rootView;
	}

	public void suicide()
	{
		getParentFragment_().RemoveChild();
	}

	@Override
	public boolean onBackPress()
	{
		getParentFragment_().RemoveChild();
		return true;
	}


	public interface RemoveRequest
	{
		void RemoveRequest(Fragment f);
	}


	public interface PostPostService
	{
		@POST(ServerInfo.API_POST_POST)
		Call<PostJson> postPost(@Body PostJson post);
	}
	private class PostPost extends AsyncTask<PostJson, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(PostJson... post)
		{
			try
			{
				Log.d(TAG, "Posting post...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostPostService service = retrofit.create(PostPostService.class);
				Call<PostJson> call = service.postPost(post[0]);
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Posting post...success");
					return true;
				}
				else
				{
					Log.d(TAG, "Posting post...fail");

				}
				Log.d(TAG, res.code() + " : " + res.message());
				return false;
			}
			catch(Exception e)
			{
				Log.d(TAG, "Posting post occurred exception...");
				Log.d(TAG , e.getMessage());
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean res)
		{
			Toast.makeText(getActivity(),
					res? "게시글이 등록되었습니다" : "게시글 등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
		}
	}

	public interface PutPostService
	{
		@PUT(ServerInfo.API_POST_PUT)
		Call<PostJson> putPost(@Path(ServerInfo.API_POST_PATH) int postId, @Body PostJson post);
	}

	private class PutPost extends AsyncTask<PostJson, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(PostJson... post)
		{
			try
			{
				Log.d(TAG, "Putting post...Entry Point");
				Log.d(TAG, "Putting post id : " + post[0].getPostId());
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PutPostService service = retrofit.create(PutPostService.class);

				Call<PostJson> call = service.putPost(post[0].getPostId(), post[0]);

				Response res = call.execute();

				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
					Log.d(TAG, res.code() + " : " + res.message());
					return true;
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
					return false;
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
			Toast.makeText(getActivity(), res?
					"수정되었습니다"
					: "게시글 수정에 실패하였습니다"
					, Toast.LENGTH_SHORT).show();
		}
	}
}
