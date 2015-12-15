package com.enjoyor.soft.product.chacang;

import org.apache.http.Header;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HtlClientCallBack;
import com.enjoyor.soft.common.HtlClientUtils;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *========================================						
 * 说明：查仓														
 * 作者：胡团乐															
 * 项目：zhlk_android
 * 时间：2014年10月14日 下午1:35:08
 *========================================
 */
public class ChacangActivity extends Activity implements OnClickListener{

	String username;
	String password;
	String webhead;
	String store_no;
	String store_name;
	EditText edi_content;
	Button storeNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chacang_activity);
		username = getIntent().getStringExtra("userName");
		password = getIntent().getStringExtra("password");
		webhead = getIntent().getStringExtra("webhead");
		storeNo = (Button) findViewById(R.id.btn_choose);
		storeNo.setOnClickListener(this);
		findViewById(R.id.btn_post).setOnClickListener(this);
		edi_content = (EditText) findViewById(R.id.edi_content);
		
	}
	ProgressDialog pd;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_choose://选择仓号
			startActivityForResult(new Intent(ChacangActivity.this,ChooseNOactivity.class), 701);
			break;
		case R.id.btn_post://提交
			pd = new ProgressDialog(this);pd.setMessage("正在提交");pd.setCanceledOnTouchOutside(false);pd.show();
			if(TextUtils.isEmpty(store_no)){
				Toast.makeText(ChacangActivity.this, "请先选择仓号", 1).show();
				return;
			}
			String content = TextUtils.isEmpty(edi_content.getText().toString())?"正常":edi_content.getText().toString();
			//http://localhost/web/storeHouseAjax.htm? loginName=enjoyor&content=11&storeHouseCode=CK002 
			String url = Constants.WEBURIHEAD+"storeHouseAjax.htm?";
			url+="loginName="+username+"&content="+content+"&storeHouseCode="+store_no;
			HtlClientUtils.getContent(url, null, true, new HtlClientCallBack() {
				
				@Override
				public void contentResponse(boolean isSuccess, int statusCode, Header[] headers, byte[] responseBody) {
					// TODO Auto-generated method stub
					if(pd!=null) pd.dismiss();;
					if(isSuccess){
						try {
							//{"returnMsgMap":{"returnAjaxState":"3"},"storeHouseMap":{}}
							String json = new String(responseBody);
							ChooseReturn cr = new Gson().fromJson(json, ChooseReturn.class);
							if("3".equalsIgnoreCase(cr.getReturnMsgMap().getReturnAjaxState())){
								Toast.makeText(ChacangActivity.this, "提交成功", 1).show();
							}
						} catch (Exception e) {
							Toast.makeText(ChacangActivity.this, "解析异常", 1).show();
						}
						
					}else{
						Toast.makeText(ChacangActivity.this, "网络异常", 1).show();
					}
				}
			});
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode==701 && data!=null){
			store_no = data.getStringExtra("storeno");
			store_name = data.getStringExtra("storename");
			storeNo.setText(store_name);
		}
	}
}
