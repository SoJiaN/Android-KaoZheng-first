package com.example.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.example.fragment.R;

public class MainActivity extends FragmentActivity {

	LinearLayout linearLayout;

	Thread thread;
	ImageView img;
	int i;
	private FrameLayout fLayout;
	private LinearLayout llayout;
	private FragmentTabHost mTabHost;
	private LayoutInflater layoutInflater;
	WindowManager wm;
	@SuppressWarnings("rawtypes")
	private Class fragmentArray[] = { Fragment01.class, Fragment02.class,
			Fragment03.class };
	private int mBackground[] = { R.drawable.tubiao1, R.drawable.tubiao2,
			R.drawable.tubiao3 };

	// Tab选项卡的文字
	private String mTextviewArray[] = { "发现", "我的", "分类" };

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wm = this.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();

		setContentView(R.layout.main_activity);
		initView();

		LinearLayout.LayoutParams fLayout_p = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 690 * height / 800);

		fLayout = new FrameLayout(this);
		fLayout.setId(R.id.realtabcontent);
		llayout = (LinearLayout) findViewById(R.id.llayout1);
		llayout.addView(fLayout, fLayout_p);

		if(Fragment02.flag){
			mTabHost.setCurrentTab(1);
			mTabHost.setBackgroundResource(mBackground[1]);
//			mTabHost.getTabItemView(1);
//			View view_A = layoutInflater.inflate(R.layout.tab_item, null);
//			ImageView imageView_A = (ImageView) view_A.findViewById(R.id.imageview);
//			imageView_A.setImageResource(R.drawable.faxian);
		}else{
			mTabHost.setBackgroundResource(mBackground[0]);
		}
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String string) {
				if (mTabHost.getCurrentTabTag() == "发现") {
					mTabHost.setBackgroundResource(mBackground[0]);
				}
				if (mTabHost.getCurrentTabTag() == "我的") {
					mTabHost.setBackgroundResource(mBackground[1]);
				}
				if (mTabHost.getCurrentTabTag() == "分类") {
					mTabHost.setBackgroundResource(mBackground[2]);
				}

			}
		});

		// th.start();

	}

	// Thread th = new Thread(new Runnable() {
	//
	// public void run() {
	// // TODO Auto-generated method stub
	// if (mTabHost.getClass().equals(diyi.class)) {
	// linearLayout.setBackgroundResource(mBackground[0]);
	// }
	// if (mTabHost.getClass().equals(lianyi.class)) {
	// linearLayout.setBackgroundResource(mBackground[1]);
	// }
	// }
	// });

	private void initView() {

		// <FrameLayout
		// android:id="@+id/realtabcontent"
		// android:layout_width="fill_parent"
		// android:layout_height="0dp"
		// android:layout_weight="1" />

		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);

			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i)
			// .setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	int mImageViewArray[] = { R.drawable.faxian, R.drawable.wode,
			R.drawable.fenlei };

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		return view;
	}

	@SuppressWarnings("deprecation")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// moveTaskToBack(false);
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			isExit.setTitle("系统提示");
			isExit.setMessage("确定要退出吗");
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			isExit.show();
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:
				break;
			default:
				break;
			}
		}
	};

}
