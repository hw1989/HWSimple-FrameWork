package org.wind.calendarview;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class TableView extends View {
	// 控件的宽度
	private int width = 0;
	// 横向的线数
	private int Hlines = 25;
	// 纵向的线数
	private int Vlines = 8;
	// 单元格的高度
	private int cellhei = 200;
	private int cellwid = 0;
	// 文字的间距
	private int paddtxt = 8;
	//
	private int txtwid = 0;
	// 距离上边界和右边界的距离
	private int paddlr = 8;
	private int paddtb = 0;
	// 文本画笔
	private Paint paint_txt = null;
	// 线的画笔
	private Paint paint = null;
    private DecimalFormat format=null;
	public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TableView(Context context) {
		super(context);
		init();
	}

	private void init() {
		format=new DecimalFormat("00");
		paint_txt = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_txt.setAntiAlias(true);
		paint_txt.setTextSize(25);
		Rect rect = new Rect();
		paint_txt.getTextBounds("00:00", 0, "00:00".length(), rect);
		txtwid = rect.width();
		//
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(2f);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int height = cellhei * (Hlines - 1);
		super.onMeasure(widthMeasureSpec,
				MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
		width = MeasureSpec.getSize(widthMeasureSpec);
		// (总宽度-文字和文字左右边距-控件的右边距)/一周的时间
		cellwid = (width - (paddtxt * 2 + txtwid) - paddlr) / (Vlines - 1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawHorizontal(canvas);
		drawVertical(canvas);
	}

	/**
	 * 画横向的线
	 */
	private void drawHorizontal(Canvas canvas) {
		for (int i = 0; i < Hlines; i++) {
			canvas.drawLine(paddtxt * 2 + txtwid, paddtb + i * cellhei, width
					- paddlr, paddtb + i * cellhei, paint);
			//画文字
			canvas.drawText(format.format(i)+":00", paddtxt, paddtb + i * cellhei, paint_txt);
		}
	}

	/**
	 * 画垂直的线
	 */
	private void drawVertical(Canvas canvas) {
		for (int i = 0; i < Vlines; i++) {
			canvas.drawLine(paddtxt * 2 + txtwid + (i * cellwid), paddtb,
					paddtxt * 2 + txtwid + (i * cellwid), paddtb + Hlines
							* cellhei, paint);
		}
	}
}
