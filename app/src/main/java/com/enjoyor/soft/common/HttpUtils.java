package com.enjoyor.soft.common;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:30:54
 * File Name: 网络数据获取类
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class HttpUtils {

	/**请求json数据
	 * @param uri	访问地址链接
	 * @return	返回json字符串
	 */
	public static String getJsonFromHttp(String uri){
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);//设置连接超时
		HttpConnectionParams.setSoTimeout(httpParams, 10000); //设置请求超时
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(uri);
		
		HttpResponse httpResponse;
			try {
				httpResponse = client.execute(request);
				if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					return EntityUtils.toString(httpResponse.getEntity());
				}
			}  catch (Exception e) {
				return Constants.HTTP_ERROR_INFO;
			}
		return null;
	}
	
}
