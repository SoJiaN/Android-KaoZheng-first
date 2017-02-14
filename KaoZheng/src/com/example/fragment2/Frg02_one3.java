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

public class Frg02_one3 extends Activity {

	WindowManager wm;
	int ScreenW, ScreenH;
	private LinearLayout llayout1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frg02_one3);
		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();
		llayout1 = (LinearLayout) this.findViewById(R.id.llayout1);

		ImageView image = new ImageView(this);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.xinwen);
		image.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, bitmap.getHeight() * ScreenH / 1280));
		image.setImageBitmap(bitmap);
		llayout1.addView(image);

	}

}
