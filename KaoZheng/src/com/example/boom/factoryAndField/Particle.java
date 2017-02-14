package com.example.boom.factoryAndField;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Particle {
	float cx;
	float cy;
	int color;

	/**
	 * @param color
	 *            ÑÕÉ«
	 * @param x
	 * @param y
	 */
	public Particle(int color, float x, float y) {
		this.color = color;
		cx = x;
		cy = y;
	}

	protected abstract void draw(Canvas canvas, Paint paint);

	protected abstract void caculate(float factor);

	public void advance(Canvas canvas, Paint paint, float factor) {
		caculate(factor);
		draw(canvas, paint);
	}
}
