package org.wind.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CanlendarView extends ViewGroup {

	public CanlendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CanlendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CanlendarView(Context context) {
		super(context);
	}
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
	}
}
