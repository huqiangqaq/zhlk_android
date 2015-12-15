package com.enjoyor.soft.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:31:52
 * File Name: 静态常量类
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class Constants {
//192.168.1.177
	//TongFeng接口
	public static String URL_TONGFENG_1 = "http://183.247.179.238:7000/twenty.html";
	public static String URL_TONGFENG_2 = "http://183.247.179.238:7000/twentyOne.html";
	//巡更提交
	//userName=test&password=admin&likeStr=111
	public static String URL_XG_POST = "cargoAjax!patrol.htm?";
	//网站请求头
	public static String WEBURIHEAD = "http://192.168.23.56:80/";
	//固定写入ic卡的块号
	public static int ADD = 50;
	//读卡密码：
	public static String PASSWORD ="FFFFFFFFFFFF";
	//网络异常
	public static final String HTTP_ERROR_INFO= "网络异常，稍后再试...";
	//登录验证链接
	public static String LOGINURI = "http://192.168.0.107:8099/web/loginAjax.htm?";
	public static String username = "";
	public static String password = "";
	//扫描rfid卡号获取数据信息
	public static String URL_CHECK_RFID = "cargoAjax!checkRFID.htm?rfidCode=";
	public static String URL_MSG = "personalinfoAjax!receive.htm";
	public static String URL_ChaCang_record = "temperatureAjax.htm";
	
	public static String getWebHead(Context context){
		String head = context.getSharedPreferences("head", 0).getString("head", "");
		return head;
	}
	public static void putWebHead(Context context,String head){
		SharedPreferences sp = context.getSharedPreferences("head", 0);
		sp.edit().putString("head", head).commit();
	}
}
