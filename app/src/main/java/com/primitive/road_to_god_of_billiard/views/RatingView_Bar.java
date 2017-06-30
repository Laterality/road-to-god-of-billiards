package com.primitive.road_to_god_of_billiard.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 신진우- on 2015-09-29.
 */
public class RatingView_Bar extends TextView
{
	private Canvas mCanvas;
	private Paint paint_win;
	private Paint paint_lose;
	private Paint paint_stroke;

	private float rate = 0.5f;
	private int Color_win = Color.parseColor("#2196F3");
	private int Color_lose = Color.parseColor("#FF5252");

	public RatingView_Bar(Context context)
	{
		super(context);
		init();
	}

	public RatingView_Bar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public RatingView_Bar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		paint_stroke = new Paint();
		paint_win = new Paint();
		paint_lose = new Paint();

		paint_stroke.setColor(Color.parseColor("#FFFFFF"));
		paint_stroke.setAntiAlias(true);
		paint_stroke.setStrokeWidth(DpToPx(3));
		paint_stroke.setStyle(Paint.Style.STROKE);

		paint_win.setColor(Color_win);
		paint_win.setAntiAlias(true);
		paint_win.setStrokeWidth(DpToPx(1));
		paint_win.setStyle(Paint.Style.FILL_AND_STROKE);

		paint_lose.setColor(Color_lose);
		paint_lose.setAntiAlias(true);
		paint_lose.setStrokeWidth(DpToPx(1));
		paint_lose.setStyle(Paint.Style.FILL_AND_STROKE);

		//this.setPadding((int)DpToPx(8),(int)DpToPx(8),0,(int)DpToPx(8));
		//this.setGravity(Gravity.LEFT);
		this.setTextColor(Color.parseColor("#FFFFFF"));
		this.setText((rate*100) + "%");
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		mCanvas = canvas;

		final RectF oval = new RectF();

		float loseRate = 1 - rate;

		oval.set(1, 1, (getWidth() - 1) * rate, getHeight() - 1);
		mCanvas.drawRect(oval, paint_win);
		oval.set((getWidth() - 1) * rate, 1, getWidth() - 1, getHeight() - 1);
		mCanvas.drawRect(oval, paint_lose);
		oval.set(1, 1, getWidth(), getHeight());
		mCanvas.drawRect(oval, paint_stroke);
		oval.set(1, 1, (getWidth() - 1) * rate, getHeight() - 1);
		mCanvas.drawRect(oval, paint_stroke);
		super.onDraw(canvas);
	}

	public void setRate(float rate)
	{
		this.rate = rate;
		this.setText((rate*100) + "%");
		this.invalidate();
	}

	private float DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return px;
	}
}
