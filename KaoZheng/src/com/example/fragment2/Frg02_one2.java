package com.example.fragment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.fragment.R;

public class Frg02_one2 extends Activity {

	FrameLayout.LayoutParams p1;
	LinearLayout.LayoutParams p;
	WindowManager wm;
	int ScreenH, ScreenW;
	private LinearLayout llayout1, llayout2, llayout3;
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frg02_one2);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		llayout1 = (LinearLayout) findViewById(R.id.llayout1);
		llayout2 = (LinearLayout) findViewById(R.id.llayout2);
		llayout3 = (LinearLayout) findViewById(R.id.llayout3);
		imageButton = (ImageButton) findViewById(R.id.imageButton);

		p1 = new FrameLayout.LayoutParams(768 * ScreenW / 768,
				459 * ScreenH / 1280);
		llayout1.setLayoutParams(p1);
		p = new LinearLayout.LayoutParams(748 * ScreenW / 768,
				74 * ScreenH / 1280);
		// p.leftMargin = 10 * ScreenW / 768;
		// p.rightMargin = 10 * ScreenW / 768;
		p.topMargin = 190 * ScreenH / 1280;
		llayout2.setLayoutParams(p);
		p = new LinearLayout.LayoutParams(748 * ScreenW / 768,
				74 * ScreenH / 1280);
		p.topMargin = 16;
		llayout3.setLayoutParams(p);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Frg02_one2.this, Denglu.class);
				startActivity(intent);
				finish();
			}
		});

	}

}
