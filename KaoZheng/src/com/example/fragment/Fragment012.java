package com.example.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Pattern;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import com.example.contestlist.ConstactAdapter;
import com.example.contestlist.ConstastBean;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment012 extends Activity {

	private Button btn1;

	// 标题，查找
	LinearLayout llayout0, llayout4;

	// 垂直滚动条里面的LinearLayout
	LinearLayout llayout2;

	// 水平滚动条里面的LinearLayout
	LinearLayout llayout11, llayout12;
	// 水平滚动里面的ImageView
	ImageView iv;

	Bitmap bitmap;

	String str[] = { "黑龙江", "吉林", "辽宁", "河北", "河南", "山东", "江苏", "山西", "陕西",
			"甘肃", "四川", "青海", "湖南", "湖北", "江西", "安徽", "浙江", "福建", "广东", "广西",
			"贵州", "云南", "海南", "内蒙古", "新疆", "宁夏", "西藏", "北京", "天津", "上海", "重庆",
			"香港", "澳门", "天津" };
	Button button;

	WindowManager wm;
	int ScreenW, ScreenH;

	// 圈和线
	ImageView imageQuan, imageXian;
	Animation animation, animationX, animationX2;
	boolean flag = false;
	// 弹出视图
	LinearLayout llayout1;
	ImageView imageView1;
	RelativeLayout.LayoutParams llayoutP1;

	View lastView = null;

	private ListView listView; // 联系人列表
	private LinearLayout layoutIndex; // 索引边框布局
	private TextView showTv;// 索引字母提示

	private List<ConstastBean> datas = new ArrayList<ConstastBean>();// 保存转化后的数据（排序）
	private HashMap<String, Integer> selector;// 存放含有索引字母的位置
	private int height = 0;
	private boolean flag1 = false;

	private ConstactAdapter constactAdapterNew;

	private String[] indexStr = { "↑", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "#" };

	private List<ConstastBean> sourceData = new ArrayList<ConstastBean>(); // 原始数据（从服务器获取）

	ArrayList<String> array = new ArrayList<String>();

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment012);

		wm = this.getWindowManager();
		ScreenW = wm.getDefaultDisplay().getWidth();
		ScreenH = wm.getDefaultDisplay().getHeight();

		// 联系人列表
		listView = (ListView) findViewById(R.id.listView);
		layoutIndex = (LinearLayout) findViewById(R.id.layout);
		showTv = (TextView) findViewById(R.id.tv_show);
		initSourceData();

		constactAdapterNew = new ConstactAdapter(this, datas);
		listView.setAdapter(constactAdapterNew);

		setData(sourceData);

		System.out.println(selector.toString());
		constactAdapterNew.updataData(datas);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int index, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(
						Fragment012.this,
						view.getId()
								+ adapterView.getItemAtPosition(index)
										.toString(), Toast.LENGTH_SHORT).show();
				for (int i = 0; i < array.size(); i++) {
					if (view.getId() == (adapterView.getItemIdAtPosition(i))) {
						Toast.makeText(Fragment012.this, i, Toast.LENGTH_SHORT)
								.show();
					}
				}

			}
		});

		// -----------------------------

		llayout0 = (LinearLayout) findViewById(R.id.llayout0);
		llayout11 = (LinearLayout) findViewById(R.id.llayout11);
		llayout12 = (LinearLayout) findViewById(R.id.llayout12);
		llayout2 = (LinearLayout) findViewById(R.id.llayout2);
		llayout4 = (LinearLayout) findViewById(R.id.llayout4);
		// 弹出视图
		llayout1 = (LinearLayout) findViewById(R.id.llayout1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		llayoutP1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		llayoutP1.topMargin = 103 * ScreenH / 1280;
		llayout1.setLayoutParams(llayoutP1);
		imageView1.setLayoutParams(new LinearLayout.LayoutParams(
				226 * ScreenW / 768, 172 * ScreenH / 1280));
		// 弹出视图的动画
		final Animation an1 = new AlphaAnimation(0, 1);
		an1.setDuration(1000);
		an1.setFillAfter(true);
		final Animation an2 = new AlphaAnimation(1, 0);
		an2.setDuration(1000);
		an2.setFillAfter(true);

		imageQuan = (ImageView) findViewById(R.id.imageQuan);
		imageXian = (ImageView) findViewById(R.id.imageXian);
		imageQuan.setLayoutParams(new LinearLayout.LayoutParams(
				100 * ScreenW / 768, 100 * ScreenW / 768));
		imageXian.setLayoutParams(new LinearLayout.LayoutParams(
				100 * ScreenW / 768, 100 * ScreenW / 768));

		animationX = new RotateAnimation(0, 90, imageQuan.getX() + 50 * ScreenW
				/ 768, imageQuan.getY() + 50 * ScreenW / 768);
		animationX.setDuration(1000);
		animationX.setFillAfter(true);
		animationX2 = new RotateAnimation(90, 0, imageQuan.getX() + 50
				* ScreenW / 768, imageQuan.getY() + 50 * ScreenW / 768);
		animationX2.setDuration(1000);
		animationX2.setFillAfter(true);

		animation = new RotateAnimation(0, 360, imageQuan.getX() + 50 * ScreenW
				/ 768, imageQuan.getY() + 50 * ScreenW / 768);
		animation.setDuration(1000);

		// 弹出视图

		imageQuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imageQuan.startAnimation(animation);

			}
		});
		imageXian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!flag) {
					flag = true;
					imageXian.startAnimation(animationX);
					llayout1.setVisibility(View.VISIBLE);
					llayout1.startAnimation(an1);
				} else {
					flag = false;
					imageXian.startAnimation(animationX2);

					llayout1.startAnimation(an2);
					an2.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							// TODO Auto-generated method stub
							llayout1.setVisibility(View.GONE);
						}
					});
				}

			}
		});

		// 自动填充Start
		String[] books = new String[] { "安全评价师", "安全工程师", "报关员", "报检员", "BEC" };
		// 创建一个ArrayAdapter封装数组
		ArrayAdapter<String> av = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, books);
		AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		auto.setLayoutParams(new LinearLayout.LayoutParams(ScreenW,
				80 * ScreenH / 1280));
		auto.setAdapter(av);
		// 自动填充Stop

		addBit(R.drawable.image01, 768, 103, llayout0);
		addBit(R.drawable.image04, 768, 71, llayout4);
		// addBit(R.drawable.image031, 768, 2095, llayout2);
		// addBit(R.drawable.image032, 768, 2180, llayout2);

		for (int i = 0; i < str.length; i++) {
			button = new Button(this);
			button.setLayoutParams(new LinearLayout.LayoutParams(
					150 * ScreenW / 768, 90 * ScreenH / 1280));
			button.setId(i);
			if (i != 0) {
				button.setBackgroundResource(R.drawable.kachi);
			} else {
				button.setBackgroundResource(R.drawable.kachi2);
				lastView = button;
			}
			button.setText(str[i]);
			button.setTextSize(12);
			button.setOnClickListener(listener);
			llayout12.addView(button);

		}

		// btn1 = (Button) findViewById(R.id.btn1);
		// RelativeLayout.LayoutParams btnP1 = new RelativeLayout.LayoutParams(
		// ScreenW, 92 * ScreenH / 1280);
		// btnP1.topMargin = 72 * ScreenH / 1280;
		// btn1.setLayoutParams(btnP1);
		//
		// btn1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new Intent(Fragment012.this,
		// Fragment02_one.class);
		// startActivity(intent);
		// }
		// });

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (lastView != null) {
				lastView.setBackgroundResource(R.drawable.kachi);
			}
			v.setBackgroundResource(R.drawable.kachi2);
			lastView = v;
		}
	};

	public void addBit(int id, int w, int h, LinearLayout l) {
		iv = new ImageView(this);
		bitmap = BitmapFactory.decodeResource(getResources(), id);
		iv.setLayoutParams(new LinearLayout.LayoutParams(w * ScreenW / 768, h
				* ScreenH / 1280));
		iv.setImageBitmap(bitmap);
		l.addView(iv);
	}

	// 联系人列表
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		drawIndex();
	}

	/**
	 * 模拟数据
	 */

	public void initSourceData() {

		array.add("安全评价师");
		array.add("安全工程师");
		array.add("报关员");
		array.add("报检员");
		array.add("BEC");
		array.add("单证员");
		array.add("房地产经纪人");
		array.add("房地估价师");
		array.add("国际商务师");
		array.add("公用英语");
		array.add("环境影响评价师");
		array.add("监理工程师");
		array.add("建造师");
		array.add("结构工程师");
		array.add("经济师");

		List<ConstastBean> list = new ArrayList<ConstastBean>();
		for (int i = 0; i < array.size(); i++) {
			ConstastBean constastBean = new ConstastBean();
			constastBean.setNickName(array.get(i));
			sourceData.add(constastBean);
			list.add(constastBean);
		}
		initPinYinData(sourceData);

	}

	public void setData(List<ConstastBean> constastBeans) {
		String[] allNames = sortIndex(constastBeans);// 按昵称首字母排序
		datas = getAllLists(allNames);// 根据排号的顺序对数据进行排序， 并返回
		selector = new HashMap<String, Integer>();
		// 遍历排好序的数据，获取每个字母的位置
		for (int i = 0; i < indexStr.length; i++) {
			for (int j = 0; j < datas.size(); j++) {
				if (datas
						.get(j)
						.getPinyin()
						.toLowerCase(Locale.getDefault())
						.startsWith(
								indexStr[i].toLowerCase(Locale.getDefault()))) {
					selector.put(indexStr[i], j);
					break;
				}
				String pinyin = datas.get(j).getPinyin();
				if (indexStr[i].equals("#")
						&& isNumeric(pinyin.substring(0, 1))) {
					selector.put(indexStr[i], j);
					return;
				}
			}
		}

	}

	/**
	 * 把数据排序，并把A-Z顺序加进去
	 * 
	 * @param carTypes
	 * @return
	 */
	public String[] sortIndex(List<ConstastBean> constastBeans) {
		TreeSet<String> set = new TreeSet<String>();
		for (ConstastBean constastBean : constastBeans) {
			char ch = constastBean.getPinyin().charAt(0);
			set.add(String.valueOf(ch).toUpperCase(Locale.getDefault()));// 获取首字母
		}
		String[] names = new String[constastBeans.size() + set.size()];// 新数组，用于保存首字母和车辆类型
		int i = 0;
		for (String string : set) { // 把set中的字母添加到新数组中（前面）
			names[i] = string;
			i++;
		}

		String[] pyheader = new String[constastBeans.size()];
		for (int j = 0; j < constastBeans.size(); j++) {
			pyheader[j] = constastBeans.get(j).getPinyin();
		}

		System.arraycopy(pyheader, 0, names, set.size(), pyheader.length);// 将转换为拼音的数组加到新数组后面
		// 自动按照首字母排序
		Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);// 严格按照字母顺序排序，忽略字母大小写，结果为按拼音排序的数组返回
		return names;

	}

	/**
	 * 根据名字排序对数据进行排序 因为默认是数字在首位，为了把数字排到末尾，需要进行转换
	 * 
	 * @param arry
	 * @return
	 */
	public ArrayList<ConstastBean> getAllLists(String[] arry) {
		ArrayList<ConstastBean> lists = new ArrayList<ConstastBean>();// 保存排好序的数据
		ArrayList<ConstastBean> lists2 = new ArrayList<ConstastBean>();// 保存数字开头的数据
		ArrayList<ConstastBean> lists3 = new ArrayList<ConstastBean>();// 保存字母数据
		// 对数据进行排序
		for (int i = 0; i < arry.length; i++) {
			for (int j = 0; j < sourceData.size(); j++) {
				if (arry[i].equals(sourceData.get(j).getPinyin())) {
					lists.add(sourceData.get(j));
					break;
				}
				// else //不显示字母索引item
				// {
				// ConstastBean contactBean = new ConstastBean();
				// contactBean.setPinyin(arry[i]);
				// contactBean.setNickName(arry[i]);
				// lists.add(contactBean);
				// break;
				// }
			}
		}
		// 分离出数字数据和字母数据
		int index = getLetter(lists);// 获取字母开头的位置
		for (int i = 0; i < lists.size(); i++) {
			if (i < index) {
				lists2.add(lists.get(i));
			} else {
				lists3.add(lists.get(i));
			}
		}
		lists.clear();
		lists.addAll(lists3);
		lists.addAll(lists2);

		return lists;
	}

	/**
	 * 根据昵称设置拼音
	 * 
	 * @param constastBeans
	 */
	public void initPinYinData(List<ConstastBean> constastBeans) {
		for (ConstastBean contactRespMsg : constastBeans) {
			contactRespMsg.setPinyin(getPinYin(contactRespMsg.getNickName()));
		}
	}

	/**
	 * 将汉字转换为全拼
	 * 
	 * @param src
	 * @return String
	 */
	public String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式

		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4;
	}

	/**
	 * 获取已字母开头的联系人的位置
	 * 
	 * @param arry
	 * @return
	 */
	public int getLetter(ArrayList<ConstastBean> arry) {

		int length = arry.size();
		for (int i = 0; i < length; i++) {
			String pinyin = arry.get(i).getPinyin();
			if (isLetter(pinyin.substring(0, 1))) {
				return i;
			}
		}
		return length;
	}

	/**
	 * 是否是字母
	 * 
	 * @param str
	 * @return
	 */
	public boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 绘制索引条
	 */
	public void drawIndexView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, height);
		for (int i = 0; i < indexStr.length; i++) {
			TextView tv = new TextView(this);
			tv.setLayoutParams(params);
			tv.setText(indexStr[i]);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(Color.rgb(25, 25, 25));
			tv.setTextSize(13);

			layoutIndex.addView(tv);

			layoutIndex.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					float y = event.getY();
					int index = (int) y / height;// 得到点击字母位置的索引
					String key = "";
					if (index < indexStr.length && index > -1) {
						key = indexStr[index];
						if (selector.containsKey(key)) {
							int position = selector.get(key);
							if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏。
								listView.setSelectionFromTop(position
										+ listView.getHeaderViewsCount(), 0);
							} else {
								listView.setSelectionFromTop(position, 0);// 滑动到第一项
							}
						}
						if (key.equals("↑")) {
							listView.setSelectionFromTop(0, 0);// 滑动到第一项
						}
					}
					if (!key.equals("")) {
						showTv.setText(key);
						showTv.setVisibility(View.VISIBLE);
					}

					switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
					case MotionEvent.ACTION_OUTSIDE:
						showTv.setVisibility(View.GONE);
						break;
					case MotionEvent.ACTION_DOWN:
						// layoutIndex.setBackground();
						break;
					}

					return true;
				}
			});
		}
	}

	public void onDrawIndexView(int h) {
		if (!flag1) {
			height = h / indexStr.length;
			drawIndexView();
			flag1 = true;
			layoutIndex.setVisibility(View.VISIBLE);
		}
	}

	public void drawIndex() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (layoutIndex.getMeasuredHeight() != 0) {
					onDrawIndexView(layoutIndex.getMeasuredHeight());
				} else {
					drawIndex();
				}
			}
		}, 1000);
	}

}
