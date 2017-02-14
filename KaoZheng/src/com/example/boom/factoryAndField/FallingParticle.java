package com.example.boom.factoryAndField;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class FallingParticle extends Particle {
	static Random random = new Random();
	float radius = FallingParticleFactory.PART_WH;
	float alpha = 1.0f;
	Rect mBound;

	/**
	 * @param color
	 *            ��ɫ
	 * @param x
	 * @param y
	 */
	public FallingParticle(int color, float x, float y, Rect bound) {
		super(color, x, y);
		mBound = bound;
	}

	protected void draw(Canvas canvas, Paint paint) {
		paint.setColor(color);
		paint.setAlpha((int) (Color.alpha(color) * alpha)); // ����͸����ɫ�Ͳ��Ǻ�ɫ��
		canvas.drawCircle(cx, cy, radius, paint);
	}

	protected void caculate(float factor) {
		cx = cx + factor * random.nextInt(mBound.width())
				* (random.nextFloat() - 0.5f);
		cy = cy + factor * random.nextInt(mBound.height() / 2);

		radius = radius - factor * random.nextInt(2);

		alpha = (1f - factor) * (1 + random.nextFloat());
	}

}
