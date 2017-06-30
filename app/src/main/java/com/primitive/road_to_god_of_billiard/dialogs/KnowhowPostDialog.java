package com.primitive.road_to_god_of_billiard.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.primitive.road_to_god_of_billiard.R;

/**
 * Created by 신진우- on 2015-10-05.
 */
public class KnowhowPostDialog extends Dialog
{
	private long postId;
	private Context mContext;

	public KnowhowPostDialog(Context context, long postId)
	{
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_knowhow_board_post);

		Window win = getWindow();
		win.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		mContext = context;
		this.postId = postId;
	}


}
