package com.example.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Fragment01_one extends Activity {

	// ImageView视图
	ImageView imageView1;
	Bitmap bit;

	String path[] = { "image1.png", "image2.png", "image3.png", "image4.png" };
	// 路径名称
	String pathName[] = { "/assets/image1/", "/assets/image2/",
			"/assets/image3/", "/assets/image4/", "/assets/image5/",
			"/assets/image6/", "/assets/image7/" };

	String webPath[] = { "No11.html", "No12.html", "No13.html", "No14.html" };
	String pathWebName[] = { "file:///android_asset/image1/",
			"file:///android_asset/image2/", "file:///android_asset/image3/",
			"file:///android_asset/image4/", "file:///android_asset/image5/",
			"file:///android_asset/image6/", "file:///android_asset/image7/", };

	// imageView1P宽高设置
	LinearLayout.LayoutParams imageView1P;

	// 获取屏幕管理权限
	private WindowManager wm;
	// 初始化图片和屏幕的宽度和高度
	int width, height, ScreenW, ScreenH;

	// 图片输入流
	InputStream is;
	Bitmap bitmap;

	// 四个按钮
	Button btn1, btn2, btn3, btn4;

	// Index :1,2,3,4
	int index = 0;

	int key;

	// ViewPager的属性
	private ScheduledExecutorService scheduledExecutorService;
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	int[] imageResId;
	private int currentItem = 0;

	WebView webView1;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	// 三个按钮的LinearLayout
	LinearLayout llayout1;
	Button button1, button2, button3;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f1_one);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		// 获取Intent传递的KEY值
		Intent intent = this.getIntent();
		key = intent.getExtras().getInt("KEY");

		// Button
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button1.setOnClickListener(listener);
		button2.setOnClickListener(listener);
		button3.setOnClickListener(listener);

		// ViewPager开始
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < path.length; i++) {
			ImageView imageView = new ImageView(this);
			is = getClass().getResourceAsStream(pathName[key] + path[i]);
			bitmap = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 389 * ScreenH / 1280));
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// ViewPager结束

		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 92 * ScreenH / 1280);
		params1.weight = 1;

		btn1 = (Button) this.findViewById(R.id.btn1);
		btn2 = (Button) this.findViewById(R.id.btn2);
		btn3 = (Button) this.findViewById(R.id.btn3);
		btn4 = (Button) this.findViewById(R.id.btn4);

		btn1.setLayoutParams(params1);
		btn2.setLayoutParams(params1);
		btn3.setLayoutParams(params1);
		btn4.setLayoutParams(params1);

		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);

		webView1 = (WebView) this.findViewById(R.id.webView1);

		webView1.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView1.loadUrl(pathWebName[key] + webPath[0]);

	}

	// String path5[] = { "image5", "image6", "image7", "image8" };
	//
	// public void setBitmap() {
	// is = getClass().getResourceAsStream(
	// pathName[key] + path5[index] + ".png");
	// bit = BitmapFactory.decodeStream(is);
	// imageView1 = (ImageView) findViewById(R.id.imageView1);
	// imageView1P = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	// bit.getHeight() * ScreenH / 1280);
	// imageView1.setLayoutParams(imageView1P);
	// imageView1.setImageBitmap(bit);
	// }

	View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn1:
				webView1.loadUrl(pathWebName[key] + webPath[0]);
				break;
			case R.id.btn2:
				webView1.loadUrl(pathWebName[key] + webPath[1]);
				break;
			case R.id.btn3:
				webView1.loadUrl(pathWebName[key] + webPath[2]);
				break;
			case R.id.btn4:
				webView1.loadUrl(pathWebName[key] + webPath[3]);
				break;
			case R.id.button1:
				finish();
				break;
			case R.id.button2:
				Toast.makeText(getApplicationContext(), "收藏成功",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.button3:
				showShare();
				break;

			}
			// setBitmap();
		}
	};

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		getString(R.string.app_name);
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("标题");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博

		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("ShareSDK");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
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
			return path.length;
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
