package com.enjoyor.soft.common;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
/**
 * class for:		AsyncHttpClient 工具类	
 * Create Author: 	hutuanle
 * Create Date: 	2013-12-22 下午12:45:39
 * Change Log:		
 */
public class HtlClientUtils {

	public static AsyncHttpClient client;
	public static AsyncHttpClient getClient(){
		if(client==null){
			return new AsyncHttpClient();
		}
		return client;
	}
	public static byte[] bytes = null;
	/**
	 * @param url
	 * @param params
	 * @param isGet 		true使用get方式 提交	false使用post方式提交
	 * @param callback
	 */
	public static void getContent(String url, RequestParams params, boolean isGet, HtlClientCallBack callback){
		client = getClient();
		if (isGet) {
			client.get(url, params, callback);
		} else {
			client.post(url, params, callback);
		}
	}
}


