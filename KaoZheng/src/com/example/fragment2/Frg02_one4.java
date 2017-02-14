package com.example.fragment2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.example.fragment.R;

@SuppressLint("HandlerLeak")
public class Frg02_one4 extends Activity {

	Button button;
	LinearLayout lLayout1;
	private ImageView imageView1;
	private InputStream is;
	private Bitmap bitmap;
	private LinearLayout.LayoutParams imageViewP1;
	WindowManager wm;
	int ScreenW, ScreenH;

	private String str[] = { "概述", "考证可视化统计图", "书记考证购买", "线上培训平台", "线下培训机构",
			"考证APP二维码", "公众号、平台", "找同伴", "受众反馈", "调查问卷" };

	private String imageId[] = {

	"image11.png", "image21.png", "image31.png", "image41.png", "image51.png",
			"image61.png", "image71.png", "image81.png", "image91.png",
			"image101.png"

	};

	// ViewPager
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

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f2_two);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		lLayout1 = (LinearLayout) findViewById(R.id.lLayout1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);

		// ViewPager
		imageResId = new int[] { R.drawable.pingjia1, R.drawable.pingjia2 };

		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);

		}

		viewPager = (ViewPager) this.findViewById(R.id.vp);
		viewPager.setLayoutParams(new LinearLayout.LayoutParams(
				480 * ScreenW / 480, 230 * ScreenH / 800));
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// ViewPager

		for (int i = 0; i < str.length; i++) {
			button = new Button(this);
			button.setText(str[i]);
			button.setBackgroundColor(Color.WHITE);
			button.setId(i);
			button.setOnClickListener(listener);
			lLayout1.addView(button);
		}

		Intent intent = this.getIntent();
		int key = intent.getExtras().getInt("KEY");
		setBg(key);
	}

	View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			setBg(v.getId());
		}
	};

	public void setBg(int key) {
		is = getClass().getResourceAsStream(
				"/assets/my/pingjia/" + imageId[key]);
		bitmap = BitmapFactory.decodeStream(is);
		imageView1.setImageBitmap(bitmap);
		imageViewP1 = new LinearLayout.LayoutParams(ScreenW, bitmap.getHeight()
				* ScreenH / 1280);
		imageView1.setLayoutParams(imageViewP1);
	}

	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
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
