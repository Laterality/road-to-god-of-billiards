package com.primitive.road_to_god_of_billiard.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 신진우- on 2015-08-10.
 */
public class RatingView_Pie extends View
{
	Canvas mCanvas;
	Paint paint;
	Paint paint_stroke;
	float rate;

	public RatingView_Pie(Context context)
	{
		super(context);
		init();
	}

	public RatingView_Pie(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public RatingView_Pie(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		paint = new Paint();
		paint.setColor(Color.parseColor("#ffffff"));
		paint.setStrokeWidth(DpToPx(1));
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		this.rate = 0.5f;

		paint_stroke = new Paint();
		paint_stroke.setColor(Color.parseColor("#ffffff"));
		paint_stroke.setStrokeWidth(DpToPx(1));
		paint_stroke.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		mCanvas = canvas;
		paint.setAntiAlias(true);
		paint_stroke.setAntiAlias(true);
		final RectF oval = new RectF();

		float remRate = (360 * (1 - rate));

		oval.set(1, 1, getWidth() - 1, getHeight() - 1);

		paint.setColor(Color.parseColor("#ff5050"));
		mCanvas.drawArc(oval, 270, remRate, true, paint);
		paint.setColor(Color.parseColor("#008ffa"));
		mCanvas.drawArc(oval, (270 + remRate) % 360, 360 * rate, true, paint);

		mCanvas.drawArc(oval, 270, remRate, true, paint_stroke);
		mCanvas.drawArc(oval, (270 + remRate) % 360, 360 * rate, true, paint_stroke);

		paint.setColor(Color.parseColor("#404040"));
		oval.set(0.3f * getWidth(), 0.3f * getHeight(), 0.7f * getWidth(), 0.7f * getHeight());
		mCanvas.drawArc(oval, 0, 360, true, paint);
		mCanvas.drawArc(oval, 0, 360, true, paint_stroke);

		paint.setColor(Color.parseColor("#ffffff"));
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(DpToPx(20));
		paint.setStrokeWidth(DpToPx(1f));
		mCanvas.drawText("승률", 0.5f * getWidth(), 0.45f * getHeight(), paint);
		paint.setTextSize(DpToPx(18));
		mCanvas.drawText(rate*100+"%", 0.5f * getWidth(), 0.6f * getHeight(), paint);

	}

	public void setRate(float rate)
	{
		this.rate = rate;

	}


	private float DpToPx(float dp)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return px;
	}
}
