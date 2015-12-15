package com.enjoyor.soft.common;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;

abstract public class HtlClientCallBack extends AsyncHttpResponseHandler {

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		contentResponse(true,statusCode, headers, responseBody);
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers,byte[] responseBody, Throwable error) {
		contentResponse(false,statusCode, headers, responseBody);
	}

	abstract public void contentResponse(boolean isSuccess,int statusCode,Header[] headers,byte[] responseBody);
	
	/*abstract public void onSuccess(int statusCode, Header[] headers, byte[] responseBody);

	abstract public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable error);*/
	
}
