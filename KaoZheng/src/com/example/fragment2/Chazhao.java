package com.example.fragment2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fragment.R;

public class Chazhao extends Activity {

	ImageView imageView;
	WindowManager wm;
	int ScreenW, ScreenH;
	Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chazhao);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		imageView = (ImageView) findViewById(R.id.imageView1);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.chazhao);
		imageView
				.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, bitmap.getHeight() * ScreenH
								/ 1280));
		imageView.setImageBitmap(bitmap);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}
