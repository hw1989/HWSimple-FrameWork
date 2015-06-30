package org.wind.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class CanlendarView extends FrameLayout {
	private Scroller scroller = null;
	private VelocityTracker tracker = null;
	private ViewConfiguration configuration = null;
	private TableView table1 = null;
	private LayoutParams params = null;
	private int tablewid;
	private int tablehei;
	// 触摸开始处理距离
	private int touchlen = 15;
	private GestureDetector detector = null;

	private enum Direction {
		None, Horizontal, Vertical
	};

	// 最大滑动速度
	private int maxspeed;
	// 最小滑动速度
	private int minspeed;
	private int moveMaxY;
	private Direction dir = Direction.None;
	private GestureDetector.SimpleOnGestureListener simple = new GestureDetector.SimpleOnGestureListener() {
		private int xlen = 0;
		private int ylen = 0;

		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// 判断是否确定这次滑动的方向
			// 不确定，就等待确定的条件
			// 确定，就执行滑动的距离
			if (dir == Direction.None) {
				xlen += Math.abs(distanceX);
				ylen += Math.abs(distanceY);
				if (xlen - ylen >= touchlen) {
					dir = Direction.Horizontal;
				}
				if (ylen - xlen >= touchlen) {
					dir = Direction.Vertical;
				}
			}
			if (dir == Direction.Horizontal) {
				scrollBy((int) distanceX, 0);
			} else if (dir == Direction.Vertical) {
				scrollBy(0, (int) distanceY);
			}
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float abs_velocityX = Math.abs(velocityX);
			float abs_velocityY = Math.abs(velocityY);
			if (dir == Direction.Horizontal) {
				if (abs_velocityX > minspeed && abs_velocityX < maxspeed) {
					scroller.fling((int) e1.getX(), 0, (int) -velocityX, 0,
							-tablewid, tablewid, 0, 0);
					awakenScrollBars(scroller.getDuration());
					invalidate();
				}
			} else if (dir == Direction.Vertical) {
				if (abs_velocityY > minspeed && abs_velocityY < maxspeed) {
					Log.e("info", "speed   " + abs_velocityY + "   ");
					scroller.fling(0, (int) e1.getY(), 0, (int) -abs_velocityY,
							0, 0, 0, moveMaxY);
					awakenScrollBars(scroller.getDuration());
					invalidate();
				}
			}
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			dir = Direction.None;
			// 初始化，用于下次的判断
			xlen = 0;
			ylen = 0;
			return true;
		}

	};

	public CanlendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CanlendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CanlendarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		configuration = ViewConfiguration.get(getContext());
		scroller = new Scroller(getContext());
		detector = new GestureDetector(getContext(), simple);
		table1 = new TableView(getContext());
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.addView(table1);
		maxspeed = configuration.getScaledMaximumFlingVelocity();
		minspeed = configuration.getScaledMinimumFlingVelocity();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		table1.layout(0, 0, table1.getMeasuredWidth(),
				table1.getMeasuredHeight());
		tablehei = table1.getMeasuredHeight();
		tablewid = table1.getMeasuredWidth();
		moveMaxY = Math.abs(getHeight() - tablehei);
	}

	private Point point = null;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// boolean flag= detector.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			dir = Direction.None;
			point = new Point((int) event.getX(), (int) event.getY());
			initTracker(event);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int disx = (int) event.getX() - point.x;
			int disy = (int) event.getY() - point.y;
			if (dir == Direction.None) {
				if (Math.abs(disx) > Math.abs(disy) && Math.abs(disx) > 20) {
					dir = Direction.Horizontal;
				}
				if (Math.abs(disy) > Math.abs(disx) && Math.abs(disy) > 20) {
					dir = Direction.Vertical;
				}
			}
			if (dir == Direction.Horizontal) {
				// 两个临界值(1.最顶部继续向上滑动)(2.最底部继续下滑)
				if (getScaleY() >= moveMaxY && disy > 0) {
					// scrollTo(0, moveMaxY);
				} else if (getScaleY() == 0 && disy < 0) {
					// scrollTo(0, 0);
				} else {
					scrollBy(-disx, 0);
				}
				point.set((int) event.getX(), (int) event.getY());
			} else if (dir == Direction.Vertical) {
				scrollBy(0, -disy);
				point.set((int) event.getX(), (int) event.getY());
			}
			point.set((int) event.getX(), (int) event.getY());
			tracker.addMovement(event);
		} else if (event.getAction() == MotionEvent.ACTION_CANCEL
				|| event.getAction() == MotionEvent.ACTION_UP) {
			// 垂直方向的fling
			if (dir == Direction.Vertical) {
				tracker.computeCurrentVelocity(1000, maxspeed);
				int initialVelocity = (int) tracker.getYVelocity();
				if (Math.abs(initialVelocity) > minspeed) {
					// scroller.fling(0, (int) event.getY(), 0,
					// -initialVelocity,
					// 0, getWidth(), 0 , moveMaxY );
					scroller.fling(getScrollX(), getScrollY(), 0,
							-initialVelocity, 0, getWidth(), -160,
							moveMaxY + 160);
					awakenScrollBars(scroller.getDuration());
					invalidate();
				}
			}
			destoryTracker();
		}
		return true;
	}

	/**
	 * 初始化 VelocityTracker
	 */
	private void initTracker(MotionEvent event) {
		if (tracker == null) {
			tracker = VelocityTracker.obtain();
		} else {
			tracker.clear();
		}
		tracker.addMovement(event);
	}

	/**
	 * 释放 VelocityTracker
	 */
	private void destoryTracker() {
		if (tracker != null) {
			tracker.recycle();
			tracker = null;
		}
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) {
			// if (dir == Direction.Vertical) {
			// if (getScrollY()< -250) {
			// scrollTo(0, 0);
			// } else if (getScrollY() > moveMaxY + 250) {
			// scrollTo(0, moveMaxY);
			// }
			// return;
			// } else if (dir == Direction.Horizontal) {
			//
			// }
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		} else {
			if (dir == Direction.Vertical) {
				if (getScrollY() < 0) {
					scrollTo(0, 0);
				} else if (getScrollY() > moveMaxY) {
					scrollTo(0, moveMaxY);
				}
			} else if (dir == Direction.Horizontal) {

			}
		}
	}
}
