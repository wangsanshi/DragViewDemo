package com.wangsanshi.dragviewdemo.main;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DragViewGroup extends FrameLayout {
	private ViewDragHelper mViewDragHelper;

	private View mMainView;
	private View mMenuView;

	public DragViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public DragViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public DragViewGroup(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		mViewDragHelper = ViewDragHelper.create(DragViewGroup.this, mCallback);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mMenuView = getChildAt(0);
		mMainView = getChildAt(1);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mMenuView.getMeasuredWidth();
	}

	private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return mMainView == child;
		}

		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}

		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}

		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
		}

		// 垂直方向上的滑动
		public int clampViewPositionVertical(View child, int top, int dy) {
			return 0;
		}

		// 水平方向上的滑动
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			return left;
		}

		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if (mMainView.getLeft() < 300) {
				mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
				ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
			} else {
				mViewDragHelper.smoothSlideViewTo(mMainView, 200, 0);
				ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
			}
		}

	};

	public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
		return mViewDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将触摸事件传递给ViewDragHelper
		mViewDragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public void computeScroll() {
		if (mViewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
		}
	}

}
