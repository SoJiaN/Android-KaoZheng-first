package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.fragment2.Chazhao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Fragment01 extends Fragment {

	private LinearLayout llayout0;
	private LinearLayout llayout1;
	private ImageView imageView;
	private int imageId[] = { R.drawable.image1, R.drawable.image2,
			R.drawable.image3, R.drawable.image4, R.drawable.image5,
			R.drawable.image6, R.drawable.image7 };
	private LinearLayout.LayoutParams imageViewP;
	private View view;

	// ViewPager的属性
	private ScheduledExecutorService scheduledExecutorService;
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	int[] imageResId;
	private int currentItem = 0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	// 最上面标题ImageView
	private ImageView imageView1;
	// // 标题的Bitmap图片
	// private Bitmap imageBit1;

	Bitmap bitmap;
	// 获取屏幕管理权限
	private WindowManager wm;
	// 初始化图片和屏幕的宽度和高度
	int width, height, ScreenW, ScreenH;

	// 车厢按钮
	Button button;
	// 车厢上的文字
	String str[] = { "教育部门", "培训认证", "报关员", "网络教学", "保险类", "认证类", "旅游局",
			"劳动部门", "人事部门", "建设部门", "财政部门" };

	// 查找按钮
	Button button1;

	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment01, null);
		wm = this.getActivity().getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		init();

		// FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
		// 1141 * ScreenW / 720, 96 * ScreenH / 1280);
		// llayout0.setLayoutParams(p);

		LinearLayout.LayoutParams buttonP = new LinearLayout.LayoutParams(
				192 * ScreenW / 768, 96 * ScreenH / 1280);

		for (int i = 0; i < str.length; i++) {
			button = new Button(getActivity());
			button.setLayoutParams(buttonP);
			button.setText(str[i]);
			button.setBackgroundResource(R.drawable.chexiang);
			button.setOnClickListener(l);
			button.setId(20 + i);
			llayout0.addView(button);
		}

		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.image0);
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		// 查找Button
		button1 = (Button) view.findViewById(R.id.button1);
		button1.setLayoutParams(new LinearLayout.LayoutParams(height * ScreenH
				/ 1280, height * ScreenH / 1280));
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), Chazhao.class);
				startActivity(intent);
			}
		});

		imageViewP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				height * ScreenH / 1280);
		imageView1.setLayoutParams(imageViewP);
		imageView1.setImageBitmap(bitmap);

		// ViewPager开始
		imageResId = new int[] { R.drawable.image001, R.drawable.image002,
				R.drawable.image003, R.drawable.image004 };

		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(view.getContext());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);

		}

		viewPager = (ViewPager) view.findViewById(R.id.vp);
		viewPager.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 230 * ScreenH / 800));
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// ViewPager结束

		for (int i = 0; i < imageId.length; i++) {
			imageView = new ImageView(this.getActivity());
			bitmap = BitmapFactory.decodeResource(view.getResources(),
					imageId[i]);
			width = bitmap.getWidth();
			height = bitmap.getHeight();
			imageViewP = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, height * ScreenH / 800);
			imageView.setImageBitmap(bitmap);
			imageView.setId(i);
			imageView.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(getActivity(), Fragment01_one.class);
					intent.putExtra("KEY", v.getId());
					startActivity(intent);
				}
			});

			llayout1.addView(imageView, imageViewP);
		}

		return view;

	}

	View.OnClickListener l = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case 23:
				Intent intent = new Intent(getActivity(), Fragment012.class);
				startActivity(intent);
				break;
			default:
				Toast.makeText(getActivity(), "暂无相关内容", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	public void init() {
		imageView1 = (ImageView) view.findViewById(R.id.imageView1);
		llayout0 = (LinearLayout) view.findViewById(R.id.llayout0);
		llayout1 = (LinearLayout) view.findViewById(R.id.llayout1);

	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		public void onPageSelected(int position) {
			currentItem = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	private class MyAdapter extends PagerAdapter {

		public int getCount() {
			return imageResId.length;
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

		public void finishUpdate(View arg0) {

		}
	}

}
