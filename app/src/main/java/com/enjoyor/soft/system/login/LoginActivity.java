package com.enjoyor.soft.system.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.common.NetCheck;
import com.enjoyor.soft.product.TongFeng.Utils.PreferenceService;
import com.enjoyor.soft.product.car.model.LoginReturn;
import com.enjoyor.soft.system.index.Menu;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-7-1下午03:23:29
 * File Name: 登录界面
 * Last version: 1.0
 * Last Update Date: 2013-7-1
 * Change Log:
 */
public class LoginActivity extends Activity {

	Context context = this;
	private String webhead;
	private String localIP;
	private CheckBox showpass;
	ProgressDialog pd;
	private EditText username, password, ip1, ip2, ip3, ip4, duankou;
	private PreferenceService preferenceService;
	private Map<String, String> map;
	private static final String flag ="Login";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		inits();// 初始化界面
		//检测网络
		NetCheck.isConnect(this);

		// 显示密码和隐藏密码
		showpass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					Editable etable = password.getText();
					Selection.setSelection(etable, etable.length());
				} else {
					password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					Editable etable = password.getText();
					Selection.setSelection(etable, etable.length());
				}
			}
		});
		preferenceService = new PreferenceService(getApplicationContext());
		map = new HashMap<>();
		map = preferenceService.getPreferences();
		ip1.setText(map.get("loginip1"));
		ip2.setText(map.get("loginip2"));
		ip3.setText(map.get("loginip3"));
		ip4.setText(map.get("loginip4"));
		duankou.setText(map.get("duankouip"));
	}


	// 提交
	int item = -1;// 选择项
	public void login(View view) {

		localIP = ip1.getText().toString() + "." + ip2.getText().toString()
				+ "." + ip3.getText().toString() + "."
				+ ip4.getText().toString();
		webhead = "http://" + localIP + ":" + duankou.getText().toString()+ "/web/";
		try {
			preferenceService.save(ip1.getText().toString(),ip2.getText().toString(),ip3.getText().toString(),ip4.getText().toString(),flag);
			preferenceService.save(duankou.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constants.WEBURIHEAD = webhead;// 请求头
		Constants.putWebHead(context, webhead);
		Constants.LOGINURI = webhead + "loginAjax.htm?";// 登录链接头
//		System.out.println(localIP);
		Log.i("five", Constants.LOGINURI);
		String login_uri = Constants.LOGINURI + "userName="
				+ username.getText().toString().trim() + "&password="
				+ password.getText().toString().trim();
		Log.i("five", Constants.LOGINURI);
		Intent intent = new Intent(LoginActivity.this, Menu.class);
		intent.putExtra("username", username.getText().toString().trim());
		intent.putExtra("password", password.getText().toString().trim());
		intent.putExtra("webhead", webhead);
		Constants.WEBURIHEAD = webhead;
//		startActivity(intent);
		new LoginAsync().execute(login_uri);
		pd = new ProgressDialog(this);
		pd.setMessage("		正在登录...");
		pd.show();
		
//		startActivity(new Intent(LoginActivity.this, Menu.class));
	}
	public void cancle(View view){
		this.finish();
	}
	private void inits() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		ip1 = (EditText) findViewById(R.id.ip1);
		ip2 = (EditText) findViewById(R.id.ip2);
		ip3 = (EditText) findViewById(R.id.ip3);
		ip4 = (EditText) findViewById(R.id.ip4);
		duankou = (EditText) findViewById(R.id.duankou);
		showpass = (CheckBox) findViewById(R.id.showpass);
		
	}
	public void showToast(String msg){
		Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
	class LoginAsync extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = HttpUtils.getJsonFromHttp(params[0]);
			Log.i("123++++++++", result.toString());
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			pd = null;
			Gson gson = new Gson();
			System.out.println("login result:" + result);
			if (result == null || Constants.HTTP_ERROR_INFO.equals(result)) {
				showToast(Constants.HTTP_ERROR_INFO);
				return;
			}
			Log.i("five", "login_result:" + result);
			LoginReturn lr = gson.fromJson(result, LoginReturn.class);
			if (lr != null) {
				String mark = lr.getReturnMsgMap().getReturnAjaxState();
				if ("3".equals(mark)) {
					// 登陆成功
					Intent intent = new Intent(LoginActivity.this, Menu.class);
					intent.putExtra("username", username.getText().toString().trim());
					intent.putExtra("password", password.getText().toString().trim());
					String[] titles = new String[]{"温度管理", "湿度管理", "装卸货",  "消息中心", "巡更", "查仓", "查仓记录", "RFID写入", "手工通风"};
					String moudles = "";
					Constants.username = username.getText().toString().trim();
					//权限模块筛选
					String msg = lr.getReturnMsgMap().getMsg();
					msg=msg+"PDARFID写入"+"PDA手工通风";
					for(String str:titles){
						if(msg.contains("PDA"+str)){
							moudles+=str+",";
						}
					}
					if(moudles.endsWith(",")){
						moudles = moudles.substring(0, moudles.length()-1);
					}
					intent.putExtra("moudles", moudles);
					startActivity(intent);
					LoginActivity.this.finish();
				} else if ("2".equals(mark)) {
					// 登陆失败
					showToast(lr.getReturnMsgMap().getMsg());
				}
			}else{
				showToast("网络异常!");
			}
		}
	}

}
