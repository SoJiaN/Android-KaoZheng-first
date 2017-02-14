package com.example.fragment;

import java.util.HashMap;

import android.util.*;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView.Validator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.gui.RegisterPage;

import com.example.fragment2.Denglu;
import com.mob.commons.SMSSDK;
import com.mob.*;
import com.mob.tools.*;

import cn.smssdk.*;

public class RegistersActivity extends Activity {

	EditText verify;
	EditText password;
	EditText username;
	RelativeLayout relative;
	SQLiteDatabase db;
	Fragment02 main;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_ac);
		
		main = new Fragment02();

//		Intent intent=this.getIntent();
//		final String phone=intent.getStringExtra("phone");
		
//		verify = new EditText(this);
//		relative = (RelativeLayout) findViewById(R.id.Rela);
//		relative.addView(verify);

		username = (EditText) findViewById(R.id.Register_author_username);
		password = (EditText) findViewById(R.id.register_author_password);

//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(320,
//				120);
//		lp.addRule(RelativeLayout.ALIGN_LEFT, R.id.author_password);
//		lp.addRule(RelativeLayout.ALIGN_TOP, R.id.author_password);
//		lp.leftMargin = (int) 0;
//		lp.topMargin = (int) 120;

//		verify.setLayoutParams(lp);

//		username.setText();
		
//		username.setText(phone);
		Log.i("onCreat_>>>>>>>>>>>>>>>>>>.", "******************");
		username.setText(Fragment02.phone);
				
		
		Button button_submit = (Button) findViewById(R.id.Register_button11);

		button_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String name = username.getText().toString();
				String pass = password.getText().toString();
			
				
				//初始注册界面的注册方法
				if ((!(name.equals(""))) && (!(pass.equals("")))) {
					
					String name_U=username.getText().toString();
					
					Log.i("zai wai mian", "shi fou cun zai ");
					
					
					if(isAlreadyExit(name_U)){
						
						if (addUser(name, pass)) {
							DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// 跳转到登录界面
									Log.i("Register if", "aaaaaaa");
									// Intent in = new Intent();
									// in.setClass(RegistersActivity.this,
									// Fragment02.class);
									// startActivity(in);
									finish();
									// 销毁当前activity
									RegistersActivity.this.onDestroy();
								}
							};
							new AlertDialog.Builder(RegistersActivity.this)
									.setTitle("注册成功").setMessage("注册成功")
									.setPositiveButton("确定", ss).show();
						} else {
							new AlertDialog.Builder(RegistersActivity.this)
									.setTitle("注册失败").setMessage("注册失败")
									.setPositiveButton("确定", null).show();
						}
					}else{
						Log.i("已经存在了", "不知道写啥了");
						
						
						DialogInterface.OnClickListener sos = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 跳转到登录界面
								Log.i("点击已经存在", "zzzzzzzzzzz");
								// Intent in = new Intent();
								// in.setClass(RegistersActivity.this,
								// Fragment02.class);
								// startActivity(in);
								finish();
								// 销毁当前activity
								RegistersActivity.this.onDestroy();
							}
						};
						
						
						new AlertDialog.Builder(RegistersActivity.this).setTitle("错误")
						.setMessage("Already_帐号已存在，请直接登录")
						.setPositiveButton("确定", sos).show();
					}
				} else {
					Log.i("帐号密码皆不能为空", "帐号密码皆不能为空");

					new AlertDialog.Builder(RegistersActivity.this)
							.setTitle("帐号密码皆不能为空").setMessage("帐号密码皆不能为空")
							.setPositiveButton("确定", null).show();
					Log.i("AlertDialog.show()", "是否显示AlertDialog");
				}
			}
		});
	}

	// 添加用户
	public Boolean addUser(String name, String password) {
		
		String str = "insert into tb_user values(?,?) ";
		
		Log.i("$$$$$$$$$$$", "songsongsongosngsongsong");

		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/test.dbs", null);

		Log.i("Registers.db", this.db.toString());

		main.Fragmentdb = db;

		Log.i("fragment01.main.db", main.Fragmentdb.toString());
		
//		db.execSQL(str, new String[] { name, password });
//		main.createDb();
		
		try {
			Log.i("songjian1", "in the try");
//			main.Fragmentdb.execSQL(str, new String[]{ name, password});
			db.execSQL(str, new String[] { name, password });
			Log.i("songjian2", "in the try aaaaaaa");
			return true;
		} catch (Exception e) {
			Log.i("aaaaaa", "bbbbbbbbbb");
//第一改			 main.createDb();
			Log.i("cccccc", "zzzzzzzzzz");
		}
		
		return false;
	}

	public Boolean isAlreadyExit(String name){
		Log.i("isAlredyExit", "((((((((((((((((((((");
		
		//第一改
		try{
			main.createDb();
		}catch(Exception e ){
			
		}
		
		Log.i("已经成功createDb", "已经成功createDb");
		
		String str="select * from tb_user where name=?";
		Cursor cursor = main.Fragmentdb.rawQuery(str, new String[] { name});
		if(cursor.getCount() <= 0){
			return true;
		}else{
			
			return false;
		}
		
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
