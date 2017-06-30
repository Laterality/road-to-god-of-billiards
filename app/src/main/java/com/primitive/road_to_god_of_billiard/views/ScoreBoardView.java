package com.primitive.road_to_god_of_billiard.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 신진우- on 2015-10-10.
 */
public class ScoreBoardView extends View
{
	private final String TAG = "ScoreBoardView";
	private Paint pFBorwn;
	private Paint pOutline;
	private Paint pWChip;
	private Paint pRChip;

	private float origin_width = 15.8f;
	private float origin_height = 2.3f;
	private float coef_chipWidth = 0.3f/origin_width;
	private float coef_chipHeight = 2.1f/origin_height;
	private float coef_marginL = 0.7f/origin_width;
	private float coef_marginR = 0.7f/origin_width;
	private float coef_scoreInterval = 4.8f/origin_width;
	private float coef_chipY = 0.07f/origin_height;
	private float coef_chipCorner = 0.04f/origin_width;
	private float coef_chipInterval = 1; // dp unit
	private float coef_corner = 0.04f/origin_width;
	private float coef_backboneHeight = 0.8f/origin_height;
	private float coef_backboneY = 0.75f/origin_height;

	private int vWidth;
	private int vHeight;

	private onScoreChangeListener mListener;
	private int score = 0;
	private int upperLimit = 0;

	public ScoreBoardView(Context context)
	{
		super(context);
		init();
	}

	public ScoreBoardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public ScoreBoardView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}


	/**
	 * brown - #996633
	 * red - #ff0000
	 * white - #ffffff
	 * gray_outline - #626262
	 */
	private void init()
	{


		pFBorwn = new Paint();
		pOutline = new Paint();
		pRChip = new Paint();
		pWChip = new Paint();

		pFBorwn.setAntiAlias(true);
		pFBorwn.setColor(Color.parseColor("#996633"));
		pFBorwn.setStrokeWidth(DpToPx(1));
		pFBorwn.setStyle(Paint.Style.FILL_AND_STROKE);

		pOutline.setAntiAlias(true);
		pOutline.setColor(Color.parseColor("#626262"));
		pOutline.setStyle(Paint.Style.STROKE);

		pRChip.setAntiAlias(true);
		pRChip.setColor(Color.parseColor("#ff0000"));
		pRChip.setStyle(Paint.Style.FILL_AND_STROKE);

		pWChip.setAntiAlias(true);
		pWChip.setColor(Color.parseColor("#ffffff"));
		pWChip.setStyle(Paint.Style.FILL_AND_STROKE);

		Log.d(TAG, "initialize complete...");
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		Log.d(TAG, "coef_marginL..." + coef_marginL);
		Log.d(TAG, "View_Width..." + getWidth());
		Log.d(TAG, "View_Height..." + getHeight());
		vWidth = getWidth();
		vHeight = getHeight();

		Log.d(TAG, "onDraw()...");
		super.onDraw(canvas);

		float marginL = vWidth * coef_marginL; Log.d(TAG, "marginL..." + marginL);
		float marginR = vWidth * coef_marginR;
		float marginCorner = vHeight * coef_corner;
		float backboneY = vHeight * coef_backboneY;
		float backboneHeight = vHeight * coef_backboneHeight;

		float chipWidth = vWidth * coef_chipWidth; Log.d(TAG, "chipWidth..." + chipWidth);
		float chipHeight = vHeight * coef_chipHeight; Log.d(TAG, "chipHeight..." + chipHeight);
		float chipY = vHeight * coef_chipY;
		float chipCorner = vWidth * coef_chipCorner;
		float interval = vWidth * coef_scoreInterval;

		float chipInterval = DpToPx(coef_chipInterval);

		RectF wood_Lside = new RectF();
		RectF wood_Rside = new RectF();
		RectF wood_back = new RectF();

		RectF Chip = new RectF();

		wood_Lside.set(1,1, marginL, vHeight);
		wood_Rside.set(vWidth-marginR, 1, vWidth, vHeight);
		wood_back.set(marginL, backboneY, vWidth - marginR, backboneY + backboneHeight);
		Log.d(TAG, "backboneY..." + backboneY);
		Log.d(TAG, "backboneHeight..."+backboneHeight);

		Log.d(TAG, "draw Woods...");
		canvas.drawRect(wood_Lside, pFBorwn);//canvas.drawRoundRect(0, 0, marginL, vHeight, marginCorner, marginCorner, pFBrown);
		canvas.drawRect(wood_Rside, pFBorwn);
		canvas.drawRect(wood_back, pFBorwn);

		interval = wood_back.width() - (chipWidth * 30) - DpToPx(4);

		float cursor = marginL + DpToPx(2);


		for(int i = 0 ; i< 30; i++)
		{
			if(i == score/10)
			{
				cursor += interval;
			}
			Chip.set(cursor, chipY, cursor + chipWidth, chipY + chipHeight);
			if((i/5)%2==0)
			{
				canvas.drawRect(Chip, pRChip);
				canvas.drawRect(Chip, pOutline);
			}
			else
			{
				canvas.drawRect(Chip, pWChip);
				canvas.drawRect(Chip, pOutline);
			}
			cursor += chipWidth;

		}
	}

	public void setScore(int score)
	{
		Log.d("Activity_GameActivity_", "set score...");
		Log.d(TAG, "setScore...Entry Point");
		if((score <=300 && score>=0)&&(score %10 == 0))
		{
			if((upperLimit == 0)||(score <= upperLimit))
			{
				Log.d(TAG, "setScore...Lim : " + upperLimit);
				Log.d(TAG, "setScore..." + score);
				this.score = score;
				this.invalidate();
				if(mListener != null){mListener.onChange(this, this.score);}
			}
		}
	}

	public int getScore()
	{
		return score;
	}


	public void setLimit(int limit)
	{
		Log.d(TAG, "setLimit..." + limit);
		upperLimit = limit;
		Log.d(TAG, "present Limit : " + upperLimit);
	}

	private float DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return px;
	}

	public void setOnScoreChangeListener(onScoreChangeListener listener)
	{
		mListener = listener;
	}

	public interface onScoreChangeListener
	{
		void onChange(View v, int presentScore);
	}
}
