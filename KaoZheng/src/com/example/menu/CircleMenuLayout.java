/**
 * 
 */
package com.example.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.R;

/**
 * @author Administrator
 * 
 */
public class CircleMenuLayout extends ViewGroup {

	private int mRadius;
	/**
	 * 该容器内child item的默认尺寸
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	/**
	 * 菜单的中心child的默认尺寸
	 */
	private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
	/**
	 * 该容器的内边距,无视padding属性，如需边距请用该变量
	 */
	private static final float RADIO_PADDING_LAYOUT = 1 / 12f;
	/**
	 * 如果移动角度达到该值，则屏蔽点击
	 */
	private static final int NOCLICK_VALUE = 3;
	/**
	 * 该容器的内边距,无视padding属性，如需边距请用该变量
	 */
	private float mPadding;
	/**
	 * 布局时的开始角度
	 */
	private double mStartAngle = 0;
	/** 资源数组 */
	private String[] mItemTexts;
	private int[] mItemImages;
	/**
	 * item数量
	 * */
	private int MenuItemCount1;
	/**
	 * 检测按下到抬起时旋转的角度
	 */
	private float mTmpAngle;
	/**
	 * 检测按下到抬起时使用的时间
	 */
	private long mDownTime;
	/**
	 * 判断是否正在自动滚动
	 */
	private boolean isFling;
	/**
	 * 当每秒移动角度达到该值时，认为是快速移动
	 */
	private static final int FLINGABLE_VALUE = 300;
	/**
	 * 当每秒移动角度达到该值时，认为是快速移动
	 */
	private int mFlingableValue = FLINGABLE_VALUE;
	/**
	 * @param context
	 * @param attrs
	 */
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPadding(0, 0, 0, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int resWidth = 0;
		int resHeight = 0;
		/**
		 * 根据传入的参数，分别获取的量模式和测量值
		 * */
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		/*
		 * 判断宽高的测量模式非精确值
		 */
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY) {
			// 如果未设置背景图片，则设置为屏幕宽高的默认值
			resWidth = getSuggestedMinimumWidth();
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;
			resHeight = getSuggestedMinimumHeight();
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
		} else {
			resWidth = resHeight = Math.min(width, height);
		}
		setMeasuredDimension(resWidth, resHeight);
		// 获得半径
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

		// menu item数量
		final int count = getChildCount();
		// menu item尺寸
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item测量模式
		int childMode = MeasureSpec.EXACTLY;

		// 迭代测量
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE) {
				continue;
			}

			// 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circleMenu1) {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;

	}

	/**
	 * 获得默认该layout的尺寸
	 */
	private int getDefaultWidth() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}

	/**
	 * 设置menu item 的位置
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutRadius = mRadius;
		final int childCount = getChildCount();
		int left, top;
		// menu item的尺寸
		int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// 通过menuitemCount计算每个的角度
		float angleDelay = 0;
		if(childCount>1){
			angleDelay = 360 / (getChildCount() - 1);
		}
		// 遍历去设置menuitem的位置
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child.getId() == R.id.id_circleMenu1)
				continue;
			if (child.getVisibility() == GONE)
				continue;
			mStartAngle %= 360;
			// 中心点到menuItem的距离
			float tem = layoutRadius / 2f - cWidth / 2 - mPadding;
			// temp的cos就是menuitem中心点的横坐标
			left = (int) (layoutRadius / 2 + Math.round(tem
					* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f * cWidth));
			// temp的sin就是menuitem中心点的横坐标
			top = layoutRadius
					/ 2
					+ (int) Math.round(tem
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);

			child.layout(left, top, left + cWidth, top + cWidth);
			// 叠加尺寸
			mStartAngle += angleDelay;
		}

	}

	/**
	 * @param mItenText1
	 */
	public void setMenuItemText(int[] resIds, String[] texts) {
		mItemImages = resIds;
		mItemTexts = texts;
		if (texts == null && resIds == null) {
			throw new IllegalArgumentException("项目Item名字不能为空");
		}
		MenuItemCount1 = texts.length;
		addMenuItems();
	}

	/**
	 * 添加菜单项
	 */
	private void addMenuItems() {
		LayoutInflater inflate = LayoutInflater.from(getContext());
		/**
		 * 根据之前设置的ItemCount，初始化View
		 */
		for (int i = 0; i < MenuItemCount1; i++) {
			final int j = i;
			View view = inflate.inflate(R.layout.circle_menu, this, false);
			ImageView image = (ImageView) view
					.findViewById(R.id.circle_menu_image1);
			TextView text = (TextView) view
					.findViewById(R.id.circle_menu_text1);
			if (image != null) {
				image.setVisibility(View.VISIBLE);
				image.setImageResource(mItemImages[0]);
				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						if (mOnMenuItemClickListener != null) {
							mOnMenuItemClickListener.itemClick(view, j);
						}
					}
				});
			}
			if (text != null) {
				text.setVisibility(View.VISIBLE);
				text.setText(mItemTexts[i]);
			}
			/*
			 * 添加到view容器中
			 */
			addView(view);
		}
	}

	/**
	 * 重写方法
	 */
	public interface OnMenuItemClickListener {
		void itemClick(View view, int pos);
	}

	private OnMenuItemClickListener mOnMenuItemClickListener;

	public void setOnMenuItemClickLitener(
			OnMenuItemClickListener mOnMenuItemClickListener) {
		// TODO Auto-generated method stub
		this.mOnMenuItemClickListener = mOnMenuItemClickListener;
	}

	public void setPadding(float mPadding) {
		// TODO Auto-generated method stub
		this.mPadding = mPadding;
	}

	/**
	 * 点击事件的代码部分
	 */

	/*
	 * 记录上一次点几的x,y坐标
	 */
	private float mLastX;
	private float mLastY;
	/**
	 * 自动滚动的Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		float x=ev.getX();
		float  y=ev.getY();
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;
			mDownTime = System.currentTimeMillis();
			mTmpAngle = 0;

			// 如果当前已经在快速滚动
			if (isFling) {
				// 移除快速滚动的回调
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		case MotionEvent.ACTION_MOVE:

			/**
			 * 获得开始的角度
			 */
			float start = getAngle(mLastX, mLastY);
			/**
			 * 获得当前的角度
			 */
			float end = getAngle(x, y);

			// Log.e("TAG", "start = " + start + " , end =" + end);
			// 如果是一、四象限，则直接end-start，角度值都是正值
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
				mStartAngle += end - start;
				mTmpAngle += end - start;
			} else
			// 二、三象限，色角度值是付值
			{
				mStartAngle += start - end;
				mTmpAngle += start - end;
			}
			// 重新布局
			requestLayout();

			mLastX = x;
			mLastY = y;

			break;
		case MotionEvent.ACTION_UP:

			// 计算，每秒移动的角度
			float anglePerSecond = mTmpAngle * 1000
					/ (System.currentTimeMillis() - mDownTime);

			// Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
			// mTmpAngle);

			// 如果达到该值认为是快速移动
			if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
				// post一个任务，去自动滚动
				post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

				return true;
			}

			// 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
			if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
				return true;
			}

			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 根据触摸的位置，计算角度
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch) {
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 根据当前位置计算象限
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y) {
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}

	}

	/**
	 * 自动滚动的任务
	 * 
	 * 
	 * 
	 */
	private class AutoFlingRunnable implements Runnable {

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity) {
			this.angelPerSecond = velocity;
		}

		public void run() {
			// 如果小于20,则停止
			if ((int) Math.abs(angelPerSecond) < 20) {
				isFling = false;
				return;
			}
			isFling = true;
			// 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
			mStartAngle += (angelPerSecond / 30);
			// 逐渐减小这个值
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 重新布局
			requestLayout();
		}
	}
}
