package org.wind.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class TableView extends View {
	//横向的线数
    private int Hlines=25;
    //纵向的线数
    private int Vlines=8;
    //单元格的高度
    private int cellhei=80;
    //文字的间距
    private int padd=8;
	public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
    
	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
	public TableView(Context context) {
		super(context);
		init();
	}
    private void init(){
    	
    }
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
	}
}
