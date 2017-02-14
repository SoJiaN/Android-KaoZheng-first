package com.example.boom;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.boom.factoryAndField.Particle;
import com.example.boom.factoryAndField.ParticleFactory;

public class ExplosionAnimator extends ValueAnimator {

	public static final int DEFAULT_DURATION = 0x400;
	private Particle[][] mParticles;
	private Paint mPaint;
	private View mContainer;
	private ParticleFactory mParticleFactory;

	public ExplosionAnimator(View view, Bitmap bitmap, Rect bound,
			ParticleFactory particleFactory) {
		mParticleFactory = particleFactory;
		mPaint = new Paint();
		mContainer = view;
		setFloatValues(0.0f, 1.0f);
		setDuration(DEFAULT_DURATION);
		mParticles = mParticleFactory.generateParticles(bitmap, bound);
	}

	public void draw(Canvas canvas) {
		if (!isStarted()) { // ��������ʱֹͣ
			return;
		}
		// ���������˶�
		for (Particle[] particle : mParticles) {
			for (Particle p : particle) {
				p.advance(canvas, mPaint, (Float) getAnimatedValue());
			}
		}
		mContainer.invalidate();
	}

	@Override
	public void start() {
		super.start();
		mContainer.invalidate();
	}
}