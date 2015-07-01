package org.wind.ximageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class XImageView extends ImageView {
	private BitmapDrawable drawable;
	private int borderwid = 0;
	// ±ß¿ò»­±Ê
	private Paint bpaint = null;
	// Í¼Æ¬»­±Ê
	private Paint paint = null;
	private int width;
	private int height;
    private int min;
	public XImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public XImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public XImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setStyle(Style.STROKE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		min = Math.min(width, height);
		setMeasuredDimension(min, min);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// µ±¿í¸ßÐ¡ÓÚ±ß¿òµÄÖ±¾¶Ê±
		if (getWidth() <= borderwid * 2 || getHeight() <= 0) {
			return;
		}
		if (getDrawable() == null) {
			return;
		}
		drawable = (BitmapDrawable) getDrawable();

	}

	private Bitmap getShape(Canvas canvas) {
		canvas.drawCircle(width/2,height/2, min/2, paint);
		return null;
	}
}
