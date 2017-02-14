package com.example.contestlist;

import java.util.ArrayList;
import java.util.List;
import com.example.fragment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConstactAdapter extends BaseAdapter {

	protected Context context;
	protected LayoutInflater mlayoutInflate;
	protected List<ConstastBean> mDatas = new ArrayList<ConstastBean>();

	public ConstactAdapter(Context context, List<ConstastBean> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
		mlayoutInflate = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ConstastBean constastBean = mDatas.get(position);
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			convertView = mlayoutInflate.inflate(
					R.layout.adapter_list_contacts, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_alias);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvName.setText(constastBean.getNickName());
		
		return convertView;
	}
	
	public void updataData(List<ConstastBean> datas)
	{
		this.mDatas = datas;
		notifyDataSetChanged();
	}
	
	public class ViewHolder
	{
		public TextView tvName;
	}
}
