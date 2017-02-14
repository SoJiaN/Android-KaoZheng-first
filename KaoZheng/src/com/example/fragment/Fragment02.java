package com.example.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment2.Denglu;
import com.example.fragment2.Frg02_one2;
import com.example.fragment2.Frg02_one3;
import com.example.fragment2.Frg02_one4;
import com.hkddy8.countdowndemo.Time1;
import com.mob.*;

import cn.smssdk.*;
import cn.smssdk.gui.RegisterPage;

public class Fragment02 extends Fragment {

	View view, view_my;
	View.OnClickListener listener_my;
	ImageView wode1;
	EditText username, password;
	public static boolean flag;
	public static String phone, phone_my;
	public static SQLiteDatabase Fragmentdb;
	public Fragment02 main_A;
	int myimageid;
	private String imageId_my[] = { "img1.png", "img2.png", "img3.png",
			"img4.png", "img5.png", "img6.png", "img7.png", "img8.png" };
	FileOutputStream photo_outStream;
	File file_photo;
	String fileName_photo;
	Bundle extras;
	Bitmap photo;
	final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	
	ContentResolver resolver;

	// View.OnClickListener listener_my;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		main_A = new Fragment02();

		if (Fragment02.flag) {

			view_my = inflater.inflate(R.layout.activity_main, null);

			// int[] imageId0 = new int[] { R.drawable.ic_launcher,
			// R.drawable.img22, R.drawable.fanhui };

			final String[] Title0 = new String[] { "����", "�����ѡ��", "�鿴��ͼ" };

			List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < Title0.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				// map.put("image1", imageId0[i]);
				map.put("title1", Title0[i]);

				listitems.add(map);

			}

			final SimpleAdapter adapter = new SimpleAdapter(this.getActivity(),
					listitems, R.layout.items, new String[] { "title1" },
					new int[] { R.id.textView1 });

			// �ڶ���
			TextView textView = (TextView) view_my
					.findViewById(R.id.textView1_my);
			if (Fragment02.phone == null) {
				textView.setText(Fragment02.phone_my);
			} else {
				textView.setText(Fragment02.phone);
			}

			ImageButton imageButton = (ImageButton) view_my
					.findViewById(R.id.imageButton1);

			imageButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Builder alert = new AlertDialog.Builder(getActivity());
					alert.setIcon(R.drawable.ic_launcher);
					alert.setTitle("ϵͳ��ʾ");
					// alert.setMessage("ruheshezhi");

					alert.setAdapter(adapter,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									if (arg1 == 0) {
										
										Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//										intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//												.fromFile(new File(Environment
//														.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
										
										Toast.makeText(getActivity(),String.valueOf(intent_camera),Toast.LENGTH_SHORT).show();
										
//										intent_camera.putExtra("crop", "true");
//										intent_camera.putExtra("aspectX", 1);
//										intent_camera.putExtra("aspectY", 1);
//										intent_camera.putExtra("outputX", 200);
//										intent_camera.putExtra("outputY", 200);
//										intent_camera.putExtra("return-data", true);

										
										startActivityForResult(intent_camera,arg1);

									} else {
										if (arg1 == 1) {
											
											Intent intent_gallery = new Intent();
											Log.i("nali1", "nali1");
											// �����ļ�����
											intent_gallery.setAction(Intent.ACTION_GET_CONTENT);
											
//											intent_gallery.addCategory(Intent.CATEGORY_OPENABLE);
											
											intent_gallery
													.setDataAndType(
															MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
															"image/*");

											intent_gallery.putExtra("crop", "true");
											
//											intent_gallery.putExtra("aspectX", 1);
//											intent_gallery.putExtra("aspectY", 1);
//											intent_gallery.putExtra("outputX", 160);
//											intent_gallery.putExtra("outputY", 160);
											intent_gallery.putExtra("return-data", true);
											startActivityForResult(
													intent_gallery, arg1);
										} else {
											Log.i("arg1==2", String.valueOf(arg1));
										}
									}
									Log.i("!@##$%^&*()_+", String.valueOf(arg1));
									Toast.makeText(getActivity(),
											"��ѡ����[" + Title0[arg1] + "]",
											Toast.LENGTH_SHORT).show();
								}
							});
					alert.create().show();
				}
			});

			Log.i("###############", "wode");

			// resolver=this.getActivity().getContentResolver();
			//
			//
			// file_photo = new File("/sdcard/myImage/");
			// file_photo.mkdirs();// �����ļ���
			// fileName_photo = "/sdcard/myImage/111.jpg";
			// try{
			// photo_outStream=new FileOutputStream(fileName_photo);
			// photo.compress(CompressFormat.JPEG, 100, photo_outStream);
			// }catch(FileNotFoundException e){
			// e.printStackTrace();
			// }finally {
			// try {
			// photo_outStream.flush();
			// photo_outStream.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
			photo = BitmapFactory.decodeFile("/sdcard/myImage/111.jpg");

			if (photo != null) {
				imageButton.setImageBitmap(photo);
			} else {
				Log.i("file_photo quanJu null NULL", String.valueOf(file_photo));
				Log.i("bundle quanJu null NULL", String.valueOf(extras));
				Log.i("bitmap quanJu null NULL", String.valueOf(photo));
			}

			listener_my = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("onClick.setonClicklistener",
							"onClick.setonClicklistener");
					Log.i("getid", String.valueOf(v.getId()));
					Log.i("Imageid", String.valueOf(myimageid));
					switch (v.getId()) {
					case 0:
						Log.i("switch", "songhiuan");
						Toast.makeText(getActivity(),
								"��ѡ����[" + imageId_my[myimageid] + "]zero",
								Toast.LENGTH_SHORT).show();
						break;
					case 3:
						Toast.makeText(getActivity(),
								"��ѡ����[" + imageId_my[myimageid] + "]three",
								Toast.LENGTH_SHORT).show();
						break;
					case 7:
						Toast.makeText(getActivity(),
								"��ѡ����[" + imageId_my[myimageid] + "]seven",
								Toast.LENGTH_SHORT).show();
						break;

					default:
						Toast.makeText(getActivity(),
								"��ѡ����[" + imageId_my[myimageid] + "]",
								Toast.LENGTH_SHORT).show();
						break;
					}

				}

			};

			view_my.setOnClickListener(listener_my);
			Log.i("listener_my", String.valueOf(listener_my));

			RelativeLayout relativeLayout = (RelativeLayout) view_my
					.findViewById(R.id.Rela_my);

			for (int i = 0; i < imageId_my.length; i++) {
				myimageid = i;
				WindowManager wm;
				int ScreenW, ScreenH;
				wm = this.getActivity().getWindowManager();
				ScreenW = wm.getDefaultDisplay().getWidth();
				ScreenH = wm.getDefaultDisplay().getHeight();

				ImageButton imageBtn_my = new ImageButton(this.getActivity());
				InputStream is_my = getClass().getResourceAsStream(
						"/assets/my/pingjia/" + imageId_my[i]);
				Bitmap bitmap = BitmapFactory.decodeStream(is_my);
				imageBtn_my.setImageBitmap(bitmap);
				RelativeLayout.LayoutParams imageBtnP = new RelativeLayout.LayoutParams(
						113 * ScreenW / 768, 113 * ScreenH / 1280);
				// imageBtnP.topMargin = 10 + (10 + 286) * ScreenW / 768 * (i /
				// 2);
				imageBtnP.setMargins(66 * ScreenW / 768 + (40 + 135) * ScreenW
						/ 768 * (i % 4), 475 * ScreenH / 1280 + (60 + 135)
						* ScreenH / 1280 * (i / 4), 0, 0);

				// imageBtnP.leftMargin = 12 * ScreenW / 768 + (6 + 310) *
				// ScreenW
				// / 768 * (i % 2);
				// imageBtnP.rightMargin = 6 * ScreenW / 768 * (i % 2);

				imageBtn_my.setLayoutParams(imageBtnP);
				imageBtn_my.setId(i);
				imageBtn_my.setOnClickListener(listener_my);
				relativeLayout.addView(imageBtn_my);

				Log.i("switch22", "@@@@@@@@@@@@@@@@@@@@");

			}

			return view_my;

		} else {

			view = inflater.inflate(R.layout.fragment02, null);

			wode1 = (ImageView) view.findViewById(R.id.wode1);

			username = (EditText) view.findViewById(R.id.author_username);
			password = (EditText) view.findViewById(R.id.author_password);

			Fragmentdb = SQLiteDatabase.openOrCreateDatabase(Fragment02.this
					.getActivity().getFilesDir().toString()
					+ "/test.dbs", null);

			Button button11 = (Button) view.findViewById(R.id.button11);
			Button button12 = (Button) view.findViewById(R.id.button12);

			wode1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					wode1.setBackgroundResource(R.drawable.wode1);
				}
			});

			button12.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// ��ת��RegistersActivity
					// Intent intent = new Intent();
					// intent.setClass(getActivity(), RegistersActivity.class);
					// startActivity(intent);

					cn.smssdk.SMSSDK.initSDK(getActivity(), "16d84a0234063",
							"f9e4868a63379d6f785eb8e682655773");

					// ��ע��ҳ��
					RegisterPage registerPage = new RegisterPage();
					registerPage.setRegisterCallback(new EventHandler() {
						public void afterEvent(int event, int result,
								Object data) {
							// ����ע����
							if (result == cn.smssdk.SMSSDK.RESULT_COMPLETE) {
								@SuppressWarnings("unchecked")
								HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
								String country = (String) phoneMap
										.get("country");
								phone = (String) phoneMap.get("phone");

								if (result == cn.smssdk.SMSSDK.RESULT_COMPLETE) {
									// �ص����
									if (event == cn.smssdk.SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
										Intent intent = new Intent(
												getActivity(),
												RegistersActivity.class);
										// intent.putExtra("phone", phone);
										startActivity(intent);

										// �ύ��֤��ɹ�
									} else if (event == cn.smssdk.SMSSDK.EVENT_GET_VERIFICATION_CODE) {
										Toast.makeText(getActivity(), "��ȡ",
												Toast.LENGTH_SHORT).show();
										// ��ȡ��֤��ɹ�
									} else if (event == cn.smssdk.SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
										Toast.makeText(getActivity(), "֧��",
												Toast.LENGTH_SHORT).show();
										// ����֧�ַ�����֤��Ĺ����б�
									}
								} else {
									((Throwable) data).printStackTrace();
								}
								// �ύ�û���Ϣ���˷������Բ����ã�

							}
						}
					});

					registerPage.show(getActivity());

				}
			});

			username.setText(phone);

			button11.setOnClickListener(new LoginListener());
			// main_A.getView().invalidate();
			return view;
		}

	}

	@SuppressLint("SdCardPath")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i("OnActivityResult", "onActivityResult");
		Log.i("songjian", String.valueOf(data));

		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			return;
		} else {
			switch (requestCode) {

			case 0:
				Log.e("zheli0", "zheli0");

				
//				Intent intentFromCapture = new Intent(
//						MediaStore.ACTION_IMAGE_CAPTURE);
//				intentFromCapture
//						.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//								.fromFile(new File(Environment
//										.getExternalStorageDirectory(),
//										IMAGE_FILE_NAME)));

				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
//				Log.i("zheli4", "zheli4");
//				cropRawPhoto(Uri.fromFile(tempFile));
				Log.i("data", String.valueOf(data));
				try {
					extras = data.getExtras();
					Log.i("extras0", String.valueOf(extras));
					photo = extras.getParcelable("data");
					Log.i("bitmap photo juBu", String.valueOf(photo));

					file_photo = new File("/sdcard/myImage/");
					file_photo.mkdirs();// �����ļ���
					fileName_photo = "/sdcard/myImage/111.jpg";
					try {
						photo_outStream = new FileOutputStream(fileName_photo);
						photo.compress(CompressFormat.JPEG, 100,
								photo_outStream);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						try {
							photo_outStream.flush();
							photo_outStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					Log.i("file_photo", String.valueOf(file_photo));

					((ImageView) view_my.findViewById(R.id.imageButton1))
							.setImageBitmap(photo);// ��ͼƬ��ʾ��ImageView��
				} catch (Exception e) {
					Log.i("Excepetion case 0", String.valueOf(e));
				}

				break;
			// ==========================================================================
			// Bundle bundle = data.getExtras();
			// Bitmap bitmap = (Bitmap) bundle.get("data");//
			// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
			// FileOutputStream b = null;
			// File file = new File("/sdcard/myImage/");
			// file.mkdirs();// �����ļ���
			// String fileName = "/sdcard/myImage/111.jpg";
			// try {
			// b = new FileOutputStream(fileName);
			// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// } finally {
			// try {
			// b.flush();
			// b.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
			// ((ImageView) view_my.findViewById(R.id.imageButton1))
			// .setImageBitmap(bitmap);
			// ��ͼƬ��ʾ��ImageView��
			// ===================================================================================================

			case 1:

				Log.i("nali0", "nali0");
//				Intent intentFromGallery = new Intent();
				Log.i("nali1", "nali1");
				// �����ļ�����
//				intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//				intentFromGallery
//						.setDataAndType(
//								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//								"image/*");
//				startActivity(intentFromGallery);
				// File tempFile_gallery = new
				// File(Environment.getExternalStorageDirectory(),
				// "tempfile_222.jpg");
				if (data != null) {
//					cropRawPhoto(data.getData());
					try {
						extras = data.getExtras();
						photo = extras.getParcelable("data");
						Log.i("extras1", String.valueOf(extras));
						Log.i("photo1", String.valueOf(photo));
						((ImageView) view_my.findViewById(R.id.imageButton1))
								.setImageBitmap(photo);// ��ͼƬ��ʾ��ImageView��}
					} catch (Exception e) {
						Log.i("Excepetion case 1", String.valueOf(e));
					}
				} else {
					Toast.makeText(getActivity(), "������data.getData()==null",
							Toast.LENGTH_SHORT).show();
				}
				
				file_photo = new File("/sdcard/myImage/");
				file_photo.mkdirs();// �����ļ���
				fileName_photo = "/sdcard/myImage/111.jpg";
				try {
					photo_outStream = new FileOutputStream(fileName_photo);
					photo.compress(CompressFormat.JPEG, 100,
							photo_outStream);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						photo_outStream.flush();
						photo_outStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				
				// Log.i("nali5", String.valueOf(data.getData()));

				// ((ImageView)
				// view_my.findViewById(R.id.imageButton1)).setImageBitmap(photo1);

				break;

			}

			// if (resultCode == 0) {}
			// if(resultCode == 1){
			// Intent intentFromGallery = new Intent();
			// // �����ļ�����
			// intentFromGallery.setType("image/*");
			// intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
			// Log.i("intentFromGallery", String.valueOf(intentFromGallery));
			// // cropRawPhoto(data.getData());
			// Bundle extras1 = data.getExtras();
			// Bitmap photo1 = extras1.getParcelable("data");
			// Log.i("extras1", String.valueOf(extras1));
			// Log.i("photo1", String.valueOf(photo1));
			// ((ImageView)
			// view_my.findViewById(R.id.imageButton1)).setImageBitmap(photo1);//
			// ��ͼƬ��ʾ��ImageView��}
			// }

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// ���òü�
		intent.putExtra("crop", "true");

		// aspectX , aspectY :��ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : �ü�ͼƬ���
		intent.putExtra("outputX", 20);
		intent.putExtra("outputY", 20);
		intent.putExtra("return-data", true);

	}

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String name = username.getText().toString();
			String pass = password.getText().toString();
			phone_my = name;

			// ���Եõ�name��password��ֵ
			// String str = "select * from tb_user where name=? and password=?";
			// Cursor cursor = Fragmentdb.rawQuery(str, new String[] { name,
			// pass });
			// while(cursor.moveToNext()){
			// Log.i("���ݿ�name11111111,password", this.toString());
			// }

			Log.i("���ǵ�¼�İ�������", "dengdengdengdengdengdeng");

			if (name.equals("") || pass.equals("")) {
				Log.i("����Ϊ��", "kongkongkongkongkong");
				// ������Ϣ��
				new AlertDialog.Builder(Fragment02.this.getActivity())
						.setTitle("����").setMessage("�ʺŻ����벻�ܿ�")
						.setPositiveButton("ȷ��", null).show();
			} else {
				isUserinfo(name, pass);
			}
		}

		// �ж�������û��Ƿ���ȷ
		public Boolean isUserinfo(String name, String pwd) {

			Log.i("�ж�������û��Ƿ���ȷ", "quequequequeque");

			try {
				Log.i("���ж��û���try��", "Lilililililililili");
				String str = "select * from tb_user where name=? and password=?";
				Cursor cursor = Fragmentdb.rawQuery(str, new String[] { name,
						pwd });

				if (cursor.getCount() <= 0) {
					Log.i("�ʺŻ��������", "�ʺŻ��������");
					new AlertDialog.Builder(Fragment02.this.getActivity())
							.setTitle("����").setMessage("��ȷ������ע�ᣬ�ʺŻ��������")
							.setPositiveButton("ȷ��", null).show();
					return false;
				} else {
					Log.i("�ɹ���¼", "�ɹ���¼");

					DialogInterface.OnClickListener deng = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							// ��ת����¼����
							Log.i("Register if", "aaaaaaa");

							Fragment02.flag = true;

							Intent refresh = new Intent();
							refresh.setClass(getActivity(), MainActivity.class);

							startActivity(refresh);
							getActivity().finish();

							Log.i("�ɹ���¼flag", String.valueOf(flag));

						}
					};
					new AlertDialog.Builder(Fragment02.this.getActivity())
							.setTitle("��ȷ").setMessage("�ɹ���¼")
							.setPositiveButton("ȷ��", deng).show();

					// ��½�ɹ��������ת
					// if (flag == true) {
					// Intent intent = new Intent();
					// intent.setClass(MainActivity.this, null);
					// startActivity(intent);
					// } else {
					// Toast.makeText(MainActivity.this, "���¼",
					// Toast.LENGTH_SHORT).show();
					// }
					// return true;
				}

			} catch (SQLiteException e) {
				Log.i("���ж��û���catch��", "�������������");

				new AlertDialog.Builder(Fragment02.this.getActivity())
						.setTitle("����")
						.setMessage("��ע�᡾ no such table: tb_user�쳣��")
						.setPositiveButton("ȷ��", null).show();

				Log.i("ԭcatch��createDb",
						"registersActivity_isAlreadyExit��createDb");

			}

			return false;
		}
	}

	// �������ݿ���û���
	public void createDb() {
		Fragmentdb
				.execSQL("create table tb_user( name varchar(30) primary key,password varchar(30))");
	}

}
