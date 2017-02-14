package com.example.fragment2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.fragment.MainActivity;
import com.example.fragment.R;

public class Huanyingye extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// ����ͼƬ��Դ
	private static final int[] pics = { R.drawable.huan1, R.drawable.huan2,
			R.drawable.huan3, R.drawable.huan4 };

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
	private int currentIndex;
	Button btn1;
	int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huanyingye);

		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				320 * width / 480, 464 * height / 800);

		params.leftMargin = 58 * width / 480;
		params.topMargin = 188 * height / 800;

		List<Integer> str = new ArrayList<Integer>();

		// setContentView(bk);

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// ��ʼ������ͼƬ�б�
		for (int i = 0; i < pics.length; i++) {

			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setBackgroundResource(pics[i]);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// ��ʼ��Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);

		// ��ʼ���ײ�С��
		initDots();

	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.llayout);

		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
		if (currentIndex == 1) {

			btn1 = (Button) findViewById(R.id.btn1);
			btn1.setVisibility(View.VISIBLE);

		}
		ll.setVisibility(View.GONE);

	}

	/**
	 * ���õ�ǰ������ҳ
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * ��ֻ��ǰ����С���ѡ��
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		if (positon == 3) {
			btn1 = (Button) findViewById(R.id.btn1);
			btn1.setBackgroundResource(R.drawable.huan5);
			btn1.setVisibility(View.VISIBLE);
			btn1.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(Huanyingye.this, MainActivity.class);
					startActivity(in);
					finish();
				}
			});
		} else {
			btn1 = (Button) findViewById(R.id.btn1);
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			p.bottomMargin = 30;
			btn1.setVisibility(View.GONE);
		}

		currentIndex = positon;
	}

	// ������״̬�ı�ʱ����
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// ����ǰҳ�汻����ʱ����
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// ���µ�ҳ�汻ѡ��ʱ����
	public void onPageSelected(int arg0) {
		// ���õײ�С��ѡ��״̬
		setCurDot(arg0);
	}

	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);

	}

}
