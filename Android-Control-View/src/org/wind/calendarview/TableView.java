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
	// �ؼ��Ŀ��
	private int width = 0;
	// ���������
	private int Hlines = 25;
	// ���������
	private int Vlines = 8;
	// ��Ԫ��ĸ߶�
	private int cellhei = 200;
	private int cellwid = 0;
	// ���ֵļ��
	private int paddtxt = 8;
	//
	private int txtwid = 0;
	// �����ϱ߽���ұ߽�ľ���
	private int paddlr = 8;
	private int paddtb = 0;
	// �ı�����
	private Paint paint_txt = null;
	// �ߵĻ���
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
		// (�ܿ��-���ֺ��������ұ߾�-�ؼ����ұ߾�)/һ�ܵ�ʱ��
		cellwid = (width - (paddtxt * 2 + txtwid) - paddlr) / (Vlines - 1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawHorizontal(canvas);
		drawVertical(canvas);
	}

	/**
	 * ���������
	 */
	private void drawHorizontal(Canvas canvas) {
		for (int i = 0; i < Hlines; i++) {
			canvas.drawLine(paddtxt * 2 + txtwid, paddtb + i * cellhei, width
					- paddlr, paddtb + i * cellhei, paint);
			//������
			canvas.drawText(format.format(i)+":00", paddtxt, paddtb + i * cellhei, paint_txt);
		}
	}

	/**
	 * ����ֱ����
	 */
	private void drawVertical(Canvas canvas) {
		for (int i = 0; i < Vlines; i++) {
			canvas.drawLine(paddtxt * 2 + txtwid + (i * cellwid), paddtb,
					paddtxt * 2 + txtwid + (i * cellwid), paddtb + Hlines
							* cellhei, paint);
		}
	}
}
