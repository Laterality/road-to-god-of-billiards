package com.primitive.road_to_god_of_billiard.fragments;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.PostJson;
import com.primitive.road_to_god_of_billiard.adapters.jsonAdapters.ReplyJson;
import com.primitive.road_to_god_of_billiard.R;
import com.primitive.road_to_god_of_billiard.adapters.viewAdapters.KnowhowPostReplyAdapter;
import com.primitive.road_to_god_of_billiard.objects.ChainFragment;
import com.primitive.road_to_god_of_billiard.utility.CustomApplication;
import com.primitive.road_to_god_of_billiard.utility.ServerInfo;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by 신진우- on 2015-10-05.
 */
public class KnowhowPostFragment extends ChainFragment// implements MainActivity.BackPressHnadler, KnowHowBoardFragment.IRequestRemove
{
	private final String TAG = "KnowhowPostFragment";
	private KnowHowBoardFragment.IRequestRemove mCallback;



	private FrameLayout flRereplyWrite;
	private ScrollView svContent;


	private Button btnWriteReply;
	private TextView tvTitle;
	private TextView tvAuthor;
	private TextView tvDate;
	private TextView tvCntReply;
	private TextView tvCntLike;
	private TextView tvContent;
	private TextView tvCntReply2;
	private TextView tvCntLike2;
	private EditText etReply;
	private ListView lvReply;
	private ImageView ivLike;

	private PostJson mPost;

	private int flRereplyWriteId;

	private boolean isRereplyWriting = false;
	private Fragment currentFrag;

	public KnowhowPostFragment()
	{
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//postId = getArguments().getLong("postId");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_knowhow_board_post, container, false);

		btnWriteReply = (Button) rootView.findViewById(R.id.btn_write_reply);
		lvReply = (ListView) rootView.findViewById(R.id.lv_popup_post_reply);
		flRereplyWrite = (FrameLayout) rootView.findViewById(R.id.fl_knowhow_post_rereply);
		ivLike = (ImageView) rootView.findViewById(R.id.iv_knowhow_post_like);
		flRereplyWriteId = R.id.fl_knowhow_post_rereply;
		svContent = (ScrollView) rootView.findViewById(R.id.sv_knowhow_post_content);
		GetReplies get = new GetReplies();
		get.execute();
		setListViewHeightBasedOnChildren(lvReply);
		ivLike.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Like like = new Like();
				like.execute(mPost.getPostId());
			}
		});
		btnWriteReply.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(etReply.getText().length()>=2)
				{
					PostReply post = new PostReply();
					post.execute(new ReplyJson(mPost.getPostId()
							, ((CustomApplication) getActivity().getApplication()).getProfile().getUserCode()
							, etReply.getText().toString()));
					etReply.setText("");
					new PostRefresh().execute();
				}
				else
				{
					Toast.makeText(getActivity(), "댓글 내용은 최소 2글자 이상이어야 합니다", Toast.LENGTH_SHORT).show();
				}
			}
		});


		tvTitle = (TextView) rootView.findViewById(R.id.tv_popup_post_title);
		tvAuthor = (TextView) rootView.findViewById(R.id.tv_popup_post_author);
		tvDate = (TextView) rootView.findViewById(R.id.tv_popup_post_date);
		tvCntReply = (TextView) rootView.findViewById(R.id.tv_popup_post_count_reply);
		tvCntLike = (TextView) rootView.findViewById(R.id.tv_popup_post_count_like);
		tvContent = (TextView) rootView.findViewById(R.id.tv_popup_post_content);
		tvCntReply2 = (TextView) rootView.findViewById(R.id.tv_popup_post_count_reply2);
		tvCntLike2 = (TextView) rootView.findViewById(R.id.tv_popup_post_count_like2);
		etReply = (EditText) rootView.findViewById(R.id.et_knowhow_post_reply_input);


		tvTitle.setText(mPost.getTitle());
		tvDate.setText(mPost.getDate().substring(0,10));
		tvCntReply.setText(String.valueOf(mPost.getReplyCount()));
		tvCntReply2.setText(String.valueOf(mPost.getReplyCount()));
		tvCntLike.setText(String.valueOf(mPost.getLike()));
		tvCntLike2.setText(String.valueOf(mPost.getLike()));
		tvContent.setText(mPost.getContent());
		tvAuthor.setText(mPost.getAuthorUsername());


		return rootView;
	}

	private void showWriteRereply(int replyId)
	{
		flRereplyWrite.setVisibility(View.VISIBLE);
		svContent.setVisibility(View.GONE);
		KnowhowRereplyWriteFragment rereplyFragment = new KnowhowRereplyWriteFragment();
		currentFrag = rereplyFragment;
		isRereplyWriting = true;
		rereplyFragment.setParams(mPost.getPostId(), replyId);
		rereplyFragment.setRequestListener(new KnowhowRereplyWriteFragment.RequestListener()
		{
			@Override
			public void RequestRefresh()
			{
				GetReplies get = new GetReplies();
				get.execute();
			}

			@Override
			public void RequestRemove(Fragment f)
			{
				KnowhowPostFragment.this.RequestRemove(f);
			}
		});
		setChildFragment(rereplyFragment);
		getChildFragmentManager().beginTransaction().replace(flRereplyWriteId, rereplyFragment).commit();
	}

	public void RequestRemove(Fragment f)
	{
		if(isRereplyWriting)
		{
			removeFragment(currentFrag);
			isRereplyWriting = false;

			flRereplyWrite.setVisibility(View.GONE);
			svContent.setVisibility(View.VISIBLE);
		}
		else
		{
			removeFragment(f);
		}
	}

	public void setPost(PostJson post)
	{
		mPost = post;
	}

	private void removeFragment(Fragment f)
	{
		getChildFragmentManager().beginTransaction().remove(f);
	}

	private void showReplyFunctionPopup(View anchorView, final int replyId)
	{
		View popupView = getLayoutInflater(null).inflate(R.layout.popup_knowhow_post_reply_function, null);

		final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


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
						showWriteRereply(replyId);
						break;

				}
				popupWindow.dismiss();
			}
		});

		// If  the PopupWindow should be focusable
		popupWindow.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		popupWindow.setBackgroundDrawable(new ColorDrawable());

		int[] location = new int[2];
		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		Point offset = new Point();

		popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		anchorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		offset.y = popupView.getMeasuredHeight();
		Log.d(TAG, "popupWindow.getHeight()..." + popupWindow.getHeight());
		Log.d(TAG, "popupView.getMeasuredHeight()..." + popupView.getMeasuredHeight());
		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.setWidth(DpToPx(120));
		popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] - offset.y);
	}

	private int DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return (int)px;
	}

	@Override
	public boolean onBackPress()
	{
		getParentFragment_().RemoveChild();
		return true;
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

	public void setCallback(KnowHowBoardFragment.IRequestRemove callback)
	{
		mCallback = callback;
	}

	public interface LikerService
	{
		@GET(ServerInfo.API_LIKE_GET)
		Call<PostJson> like(@Path(ServerInfo.API_LIKE_PATH) int postId);
	}

	private class Like extends AsyncTask<Integer, Void, Void>
	{
		@Override
		protected Void doInBackground(Integer... postId)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				LikerService service = retrofit.create(LikerService.class);

				Call<PostJson> call = service.like(postId[0]);

				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Execution success");
				}
				else
				{
					Log.d(TAG, "Execution fail");
					Log.d(TAG, res.code() + " : " + res.message());
				}
			}
			catch (Exception e)
			{
				Log.d(TAG, "Exception Occurred...");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void param)
		{
			PostRefresh ref = new PostRefresh();
			ref.execute();
		}
	}

	public interface PostRefreshService
	{
		@GET(ServerInfo.API_POST_GET)
		Call<PostJson> postRefresh(@Path(ServerInfo.API_POST_PATH) int postId);
	}

	private class PostRefresh extends AsyncTask<Void, Void, PostJson>
	{
		@Override
		protected PostJson doInBackground(Void... param)
		{
			try
			{
				Log.d(TAG, "Refreshing Post...Entry Point");
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostRefreshService service = retrofit.create(PostRefreshService.class);

				Call<PostJson> call = service.postRefresh(mPost.getPostId());
				Response res = call.execute();
				if(res.isSuccess())
				{
					Log.d(TAG, "Refreshing Post success...");
					return (PostJson)res.body();
				}
				else
				{
					Log.d(TAG, "Refreshing Post fail...");
					return null;
				}
			}
			catch(Exception e)
			{
				Log.d(TAG, "Exception occurred");
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

		protected void onPostExecute(PostJson post)
		{
			if(post != null)
			{
				tvTitle.setText(post.getTitle());
				tvDate.setText(post.getDate().substring(0,10));
				tvCntReply.setText(String.valueOf(post.getReplyCount()));
				tvCntReply2.setText(String.valueOf(post.getReplyCount()));
				tvCntLike.setText(String.valueOf(post.getLike()));
				tvCntLike2.setText(String.valueOf(post.getLike()));
				tvContent.setText(post.getContent());
				tvAuthor.setText(post.getAuthorUsername());
			}
			else
			{
				Toast.makeText(getActivity(), "글을 새로 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public interface GetRepliesService
	{
		@GET(ServerInfo.API_REPLIES_BY_POST_GET)
		Call<List<ReplyJson>> getReplies(@Path(ServerInfo.API_REPLIES_BY_POST_PATH) int postId);
	}

	private class GetReplies extends AsyncTask<Void, Void, List<ReplyJson>>
	{
		@Override
		protected List<ReplyJson> doInBackground(Void... param)
		{
			try
			{
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				GetRepliesService service = retrofit.create(GetRepliesService.class);

				Call<List<ReplyJson>> call = service.getReplies(mPost.getPostId());

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
				Log.d(TAG, "Exception occurred...");
				Log.d(TAG, e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<ReplyJson> replies)
		{
			lvReply.setAdapter(new KnowhowPostReplyAdapter(getActivity(), replies, new KnowHowBoardFragment.IReplyRequest()
			{
				@Override
				public void RequestShowReplyFunction(View anchorView, int replyId)
				{
					showReplyFunctionPopup(anchorView, replyId);
				}
			}));
			setListViewHeightBasedOnChildren(lvReply);
		}
	}

	public interface PostReplyService
	{
		@POST(ServerInfo.API_REPLY_POST)
		Call<ReplyJson> postReply(@Body ReplyJson reply);
	}

	private class PostReply extends AsyncTask<ReplyJson, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(ReplyJson... reply)
		{
			try
			{
				Log.d(TAG, "Post Reply...Entry Point");

				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(ServerInfo.BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.build();

				PostReplyService service = retrofit.create(PostReplyService.class);

				Call<ReplyJson> call = service.postReply(reply[0]);

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
		protected void onPostExecute(Boolean result)
		{
			if(result)
			{
				Toast.makeText(getActivity(), "댓글이 등록되었습니다", Toast.LENGTH_SHORT).show();
				GetReplies get = new GetReplies();
				get.execute();
			}
			else
			{
				Toast.makeText(getActivity(), "댓글 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
