package com.example.fragment2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.fragment.R;

public class Denglu extends Activity {

	WindowManager wm;
	int ScreenW, ScreenH;
	LinearLayout llayout1;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denglu);
		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();
		llayout1 = (LinearLayout) findViewById(R.id.llayout1);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.denglu);
		ImageView imageView = new ImageView(this);
		imageView
				.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, bitmap.getHeight() * ScreenH
								/ 1280));
		imageView.setImageBitmap(bitmap);
		llayout1.addView(imageView);

	}
}
