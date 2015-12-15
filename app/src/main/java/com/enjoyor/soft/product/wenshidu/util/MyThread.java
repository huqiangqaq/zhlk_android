package com.enjoyor.soft.product.wenshidu.util;

import android.os.Handler;
import android.os.Message;

import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.product.wenshidu.model.ScanBean;
import com.google.gson.Gson;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:40:09
 * File Name: 利用handler后台开启thread获取网络数据回传
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class MyThread extends Thread{

	Handler handler;
	String uri;
	int what;
	boolean flag = false;
	public MyThread(Handler handler,String uri,int what) {
		super();
		this.handler = handler;
		this.uri = uri;
		this.what = what;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String json = HttpUtils.getJsonFromHttp(uri);
		if(Constants.HTTP_ERROR_INFO.equals(json)){
			
			return;
		}
		Message msg = handler.obtainMessage();
		ScanBean sb = new Gson().fromJson(json, ScanBean.class);
		msg.what = what;msg.obj = sb;
		handler.sendMessage(msg);
	}
}
