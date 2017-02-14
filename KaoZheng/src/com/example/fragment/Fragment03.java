package com.example.fragment;

import com.example.boom.ExplosionField;
import com.example.boom.factoryAndField.FallingParticleFactory;
import com.example.menu.CircleMenuLayout;
import com.example.menu.CircleMenuLayout.OnMenuItemClickListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Fragment03 extends Fragment {
	private CircleMenuLayout mCircleMenuLayout1;
	// private CircleMenuLayout mCircleMenuLayout2;
	private int[] mItemImage = new int[] { R.drawable.circle_menu_back };
	private String[] mItemText1 = new String[] { "东亚", "南亚", "西亚", "北欧", "东欧",
			"西欧", "南欧", "北美" };
	// private String[] mItemText2 = new String[] { "教育部门", "培训认证", "保管员",
	// "网络教学",
	// "保险类", "认证类", "旅游局", "劳动部门", "人事部门", "建设部门" };
	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment03, null);
		mCircleMenuLayout1 = (CircleMenuLayout) view
				.findViewById(R.id.id_menulayout1);
		mCircleMenuLayout1.setMenuItemText(mItemImage, mItemText1);
		mCircleMenuLayout1
				.setOnMenuItemClickLitener(new OnMenuItemClickListener() {

					@Override
					public void itemClick(View view, int pos) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), mItemText1[pos],
								Toast.LENGTH_SHORT).show();
					}
				});
		// mCircleMenuLayout2 = (CircleMenuLayout) view
		// .findViewById(R.id.id_menulayout2);
		// mCircleMenuLayout2.setMenuItemText(mItemImage, mItemText2);
		// mCircleMenuLayout2
		// .setOnMenuItemClickLitener(new OnMenuItemClickListener() {
		//
		// @Override
		// public void itemClick(View view, int pos) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getActivity(), mItemText2[pos],
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		ExplosionField explosionField = new ExplosionField(getActivity(),
				new FallingParticleFactory());
		explosionField.addListener(view.findViewById(R.id.boom_layout));
		return view;
	}

}