package com.hkddy8.countdowndemo;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.fragment.R;
import com.hkddy8.countdowndemo.view.CountdownView;

/**
 * 倒计时演示Demo
 * 
 * @author zhouyou
 */
public class Time1 extends Activity implements
		CountdownView.OnCountdownEndListener {
	WindowManager wm;
	int ScreenW, ScreenH;
	RelativeLayout rLayout1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time);
		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		rLayout1 = (RelativeLayout) this.findViewById(R.id.rLayout1);
		rLayout1.setLayoutParams(new FrameLayout.LayoutParams(
				768 * ScreenW / 768, 826 * ScreenH / 1280));
		rLayout1.setBackgroundResource(R.drawable.img11);

		CountdownView mCvCountdownViewTest3 = (CountdownView) findViewById(R.id.cv_countdownViewTest3);
		long time3 = (long) 30 * 24 * 60 * 60 * 1000;
		mCvCountdownViewTest3.start(time3);

	}

	@Override
	public void onEnd(CountdownView cv) {
		// 倒计时结束时调用该方法
		Object tag = cv.getTag();
		if (null != tag) {
			Log.i("wg", "tag = " + tag.toString());
		}
	}
}
