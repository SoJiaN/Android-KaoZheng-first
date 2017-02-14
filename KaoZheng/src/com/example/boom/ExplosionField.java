package com.example.boom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.boom.factoryAndField.ParticleFactory;

public class ExplosionField extends View {
	private static final String TAG = "ExplosionField";
	private ArrayList<ExplosionAnimator> explosionAnimators;
	private HashMap<View, ExplosionAnimator> explosionAnimatorsMap;
	private View.OnClickListener onClickListener;
	private ParticleFactory mParticleFactory;

	public ExplosionField(Context context, ParticleFactory particleFactory) {
		super(context);
		init(particleFactory);
	}

	public ExplosionField(Context context, AttributeSet attrs,
			ParticleFactory particleFactory) {
		super(context, attrs);
		init(particleFactory);
	}

	private void init(ParticleFactory particleFactory) {
		explosionAnimators = new ArrayList<ExplosionAnimator>();
		explosionAnimatorsMap = new HashMap<View, ExplosionAnimator>();
		mParticleFactory = particleFactory;
		attach2Activity((Activity) getContext());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (ExplosionAnimator animator : explosionAnimators) {
			animator.draw(canvas);
		}
	}

	/**
	 * ����
	 * 
	 * @param view
	 *            ʹ�ø�view����
	 */
	public void explode(final View view) {
		// ��ֹ�ظ����
		if (explosionAnimatorsMap.get(view) != null
				&& explosionAnimatorsMap.get(view).isStarted()) {
			return;
		}
		if (view.getVisibility() != View.VISIBLE || view.getAlpha() == 0) {
			return;
		}

		final Rect rect = new Rect();
		view.getGlobalVisibleRect(rect); // �õ�view�����������Ļ������
		int contentTop = ((ViewGroup) getParent()).getTop();
		Rect frame = new Rect();
		((Activity) getContext()).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		rect.offset(0, -contentTop - statusBarHeight);// ȥ��״̬���߶Ⱥͱ������߶�
		if (rect.width() == 0 || rect.height() == 0) {
			return;
		}

		// �𶯶���
		ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			Random random = new Random();

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				view.setTranslationX((random.nextFloat() - 0.5f)
						* view.getWidth() * 0.05f);
				view.setTranslationY((random.nextFloat() - 0.5f)
						* view.getHeight() * 0.05f);
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				explode(view, rect);
			}
		});
		animator.start();
	}

	private void explode(final View view, Rect rect) {
		final ExplosionAnimator animator = new ExplosionAnimator(this,
				ExplosionUtils.createBitmapFromView(view), rect, mParticleFactory);
		explosionAnimators.add(animator);
		explosionAnimatorsMap.put(view, animator);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				// ��С,͸������
				view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f)
						.start();
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150)
						.start();

				// ��������ʱ�Ӷ��������Ƴ�
				explosionAnimators.remove(animation);
				explosionAnimatorsMap.remove(view);
				animation = null;
			}
		});
		animator.start();
	}

	/**
	 * ��Activity����ȫ�����ǵ�ExplosionField
	 */
	private void attach2Activity(Activity activity) {
		ViewGroup rootView = (ViewGroup) activity
				.findViewById(Window.ID_ANDROID_CONTENT);

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		rootView.addView(this, lp);
	}

	/**
	 * ϣ��˭������Ч�����͸�˭��Listener
	 * 
	 * @param view
	 *            ������ViewGroup
	 */
	public void addListener(View view) {
		if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) view;
			int count = viewGroup.getChildCount();
			for (int i = 0; i < count; i++) {
				addListener(viewGroup.getChildAt(i));
			}
		} else {
			view.setClickable(true);
			view.setOnClickListener(getOnClickListener());
		}
	}

	private OnClickListener getOnClickListener() {
		if (null == onClickListener) {
			onClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ExplosionField.this.explode(v);
				}
			};
		}
		return onClickListener;
	}
}