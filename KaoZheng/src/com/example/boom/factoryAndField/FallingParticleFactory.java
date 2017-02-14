package com.example.boom.factoryAndField;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class FallingParticleFactory extends ParticleFactory {

	public static final int PART_WH = 8; // Ĭ��С����

	public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
		int w = bound.width();
		int h = bound.height();

		int partW_Count = w / PART_WH; // �������
		int partH_Count = h / PART_WH; // �������

		int bitmap_part_w = bitmap.getWidth() / partW_Count;
		int bitmap_part_h = bitmap.getHeight() / partH_Count;

		Particle[][] particles = new Particle[partH_Count][partW_Count];
		Point point = null;
		for (int row = 0; row < partH_Count; row++) { // ��
			for (int column = 0; column < partW_Count; column++) { // ��
				// ȡ�õ�ǰ��������λ�õ���ɫ
				int color = bitmap.getPixel(column * bitmap_part_w, row
						* bitmap_part_h);

				float x = bound.left + FallingParticleFactory.PART_WH * column;
				float y = bound.top + FallingParticleFactory.PART_WH * row;
				particles[row][column] = new FallingParticle(color, x, y, bound);
			}
		}

		return particles;
	}

}
