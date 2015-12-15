package com.enjoyor.soft.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:33:28
 * File Name: 手机网络判断
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class NetCheck{
	
	public static void isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		boolean flag = false;
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						//网络正常
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		//网络异常
		//判断
		if(!flag){
			ShowDia(context);
		}
	}
	
	public static void ShowDia(Context context){
		new AlertDialog.Builder(context)
		.setTitle("网络错误")
		.setMessage("网络连接失败，请确认网络连接")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						/*android.os.Process
								.killProcess(android.os.Process
										.myPid());*/
//						System.exit(0);
					}
				}).show();
	}

}
