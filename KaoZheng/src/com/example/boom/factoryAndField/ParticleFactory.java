package com.example.boom.factoryAndField;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class ParticleFactory {
	public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
