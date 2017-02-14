package com.example.fragment;

import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.example.fragment2.Frg02_one2;
import com.example.fragment2.Frg02_one3;
import com.example.fragment2.Frg02_one4;
import com.hkddy8.countdowndemo.Time1;

public class Fragment02_one extends Activity {

	// imageView1
	ImageView imageView1;
	LinearLayout.LayoutParams imageViewP1;

	// 线性布局
	private LinearLayout lLayout1;
	// 线性布局里的三个图片
	private String imgId[] = { "image2.png", "image3.png", "image4.png" };
	// 线性布局里面的ImageView
	private ImageView imageView;
	// imageViewP
	LinearLayout.LayoutParams imageViewP;

	// 十个方框的相对布局
	private RelativeLayout rLayout1;

	// 十个方框的土拍图片
	private String imageId[] = { "img1.png", "img2.png", "img3.png",
			"img4.png", "img5.png", "img6.png", "img7.png", "img8.png",
			"img9.png", "img10.png"

	};
	// 是个图片按钮
	ImageButton imageBtn;

	// 图片输入流
	InputStream is;
	// Bitmap图片
	Bitmap bitmap;
	// 是个头图片的布局
	RelativeLayout.LayoutParams imageBtnP;
	// 获取屏幕宽和高
	WindowManager wm;
	int ScreenW, ScreenH;

	// 返回按钮
	Button button1;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f2_one);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();
		imageView1 = (ImageView) this.findViewById(R.id.imageView1);
		lLayout1 = (LinearLayout) this.findViewById(R.id.lLayout1);
		rLayout1 = (RelativeLayout) this.findViewById(R.id.rLayout1);

		is = getClass().getResourceAsStream("/assets/my/pingjia/image1.png");
		bitmap = BitmapFactory.decodeStream(is);

		// 返回按钮
		button1 = (Button) this.findViewById(R.id.button1);
		button1.setLayoutParams(new RelativeLayout.LayoutParams(bitmap
				.getHeight() * ScreenH / 1280, bitmap.getHeight() * ScreenH
				/ 1280));
		button1.setAlpha(0);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		imageView1.setImageBitmap(bitmap);
		imageViewP1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				bitmap.getHeight() * ScreenH / 1280);
		imageView1.setLayoutParams(imageViewP1);

		for (int i = 0; i < imgId.length; i++) {
			imageView = new ImageView(this);
			is = getClass().getResourceAsStream(
					"/assets/my/pingjia/" + imgId[i]);
			bitmap = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bitmap);
			imageViewP = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, bitmap.getHeight() * ScreenH
							/ 1280);
			imageView.setId(11 + i);
			imageView.setOnClickListener(listener);
			lLayout1.addView(imageView, imageViewP);
		}

		for (int i = 0; i < imageId.length; i++) {
			imageBtn = new ImageButton(this);
			is = getClass().getResourceAsStream(
					"/assets/my/pingjia/" + imageId[i]);
			bitmap = BitmapFactory.decodeStream(is);
			imageBtn.setImageBitmap(bitmap);
			imageBtnP = new RelativeLayout.LayoutParams(310 * ScreenW / 768,
					286 * ScreenH / 1280);
			// imageBtnP.topMargin = 10 + (10 + 286) * ScreenW / 768 * (i / 2);
			imageBtnP.setMargins(50 * ScreenW / 768 + (42 + 310) * ScreenW
					/ 768 * (i % 2),
					10 + (10 + 286) * ScreenH / 1280 * (i / 2), 0, 0);

			// imageBtnP.leftMargin = 12 * ScreenW / 768 + (6 + 310) * ScreenW
			// / 768 * (i % 2);
			// imageBtnP.rightMargin = 6 * ScreenW / 768 * (i % 2);

			imageBtn.setLayoutParams(imageBtnP);
			imageBtn.setId(i);
			imageBtn.setOnClickListener(listener);
			rLayout1.addView(imageBtn);
		}

	}

	View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case 11:
				intent.setClass(getApplicationContext(), Time1.class);
				break;
			case 12:
				intent.setClass(getApplicationContext(), Frg02_one2.class);
				break;
			case 13:
				intent.setClass(getApplicationContext(), Frg02_one3.class);
				break;

			default:
				intent = new Intent(getApplicationContext(), Frg02_one4.class);
				intent.putExtra("KEY", v.getId());
				break;
			}
			startActivity(intent);

		}

	};

}
