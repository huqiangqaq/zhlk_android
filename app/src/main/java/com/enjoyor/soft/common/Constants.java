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
	public static String JUANPI_AD="http://api.juanpi.com/product/juanpi/lists?n_utm=101228&n_api_version=2.1&n_app_version=3.2.2.1&n_newuser=1&n_catname=all&n_platform=Android&n_dataversion=1431920911284&n_app_name=zhe&n_pagenum=20&n_page=";
	//TongFeng接口
	public static String URL_TONGFENG_1 = "http://192.168.1.121:7000/getAllBarnNameList.html";
	public static String URL_TONGFENG_2 = "http://192.168.1.121:7000/getBarnDevList/CK001";
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
	public static String URL_ASSAYINFO = "";


	//抽样称重界面接口信息
	public static String ITEM_URL = "http://192.168.8.116:7000/PDA/getDetailByRfidCode/";
	public static String getWebHead(Context context){
		String head = context.getSharedPreferences("head", 0).getString("head", "");
		return head;
	}
	public static void putWebHead(Context context,String head){
		SharedPreferences sp = context.getSharedPreferences("head", 0);
		sp.edit().putString("head", head).commit();
	}
}
