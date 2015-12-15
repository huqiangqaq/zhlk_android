package com.enjoyor.soft.product.wenshidu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import cilico.tools.I2CTools;
import cilico.tools.Nfcreceive;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.product.wenshidu.model.ScanBean;
import com.enjoyor.soft.product.wenshidu.util.MyThread;
import com.google.gson.Gson;

/**
 * 湿度页面
 * 
 * @author hutuanle
 * 
 */
public class Shidu extends Activity {

	EditText cangku_tv, bianhao_tv;
	EditText shidubianhao, shidu_input;
	ProgressDialog pd;
	String storeCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.shidu);
		inits();
		
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			ScanBean scanBean = (ScanBean) msg.obj;
			System.out.println("scanBean-->"+scanBean);
			if(scanBean!=null){
				switch (msg.what) {
				case 0:
					// 查询rfidcode
					if ("3".equals(scanBean.getReturnMsgMap().getReturnAjaxState())) {
						pd.dismiss();pd = null;
						storeCode = scanBean.getBean().getStoreCode();
						System.out.println("storeCode-->"+storeCode);
						shidubianhao.setText(scanBean.getBean().getCode());
						cangku_tv.setText(scanBean.getBean().getStoreName());
						bianhao_tv.setText(scanBean.getBean().getCode());
					}else{
						pd.dismiss();pd = null;
						showToast(scanBean.getReturnMsgMap().getMsg());
					}
					break;
				case 1:
					// 提交温度
					if ("3".equals(scanBean.getReturnMsgMap().getReturnAjaxState())) {
						pd.dismiss();pd = null;
						showToast("提交成功");
						//清空
						shidubianhao.setText("");
						cangku_tv.setText("");
						bianhao_tv.setText("");
						shidu_input.setText("");
					} else {
						pd.dismiss();pd = null;
						showToast(scanBean.getReturnMsgMap().getMsg());
					}
					break;
				default:
					break;
				}
			}else{
				pd.dismiss();pd = null;
				showToast("连接出错");
			}
			
		}

	};
	
	public void shiduScan(View view) {
		boolean x = xunka();// 每次寻卡
		// 扫描ic卡
		String rfidCode = duIc();
		if (rfidCode != null) {
			// 查询链接
			//humidityAjax!humidityfpda.htm?userName=1&password=123456&code=008
			String uri = Constants.WEBURIHEAD
					+ "humidityAjax!humidityfpda.htm?code=123456&userName="+Constants.username+"&password="+Constants.password;
			System.out.println(uri);
			if (x) {
//				new ShiduAsync().execute(new String[] {uri });
				new MyThread(handler, uri, 0).start();
				pd = new ProgressDialog(Shidu.this);
				pd.show();
			}
		} else {
			
		}
	}

	public void shiduTijiao(View view) {
		String humidity = shidu_input.getText().toString().trim();
		String humidityCode = shidubianhao.getText().toString().trim();
		//提交温度humidityCodeAjax!insert.htm
		if(humidity!=null&&humidityCode!=null){
			String uri2 = Constants.WEBURIHEAD+"humidityCodeAjax!insert.htm?bean.humidity="
			+humidity+"&bean.humidityCode="+humidityCode+"&bean.storeCode="+storeCode+"&userName="+Constants.username+"&password="+Constants.password;
			System.out.println(uri2);
			System.out.println("humidity--"+humidity);
			System.out.println("humidityCode--"+humidityCode);
			System.out.println("storeCode-->"+storeCode);
//			new ShiduAsync2().execute(new String[]{uri2});
			new MyThread(handler, uri2, 1).start();
			pd = new ProgressDialog(Shidu.this);
			pd.show();
		}else{
			showToast("数据为空");
		}
	}
	
	class ShiduAsync extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params){
			// TODO Auto-generated method stub
			return HttpUtils.getJsonFromHttp(params[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			ScanBean sb = new Gson().fromJson(result, ScanBean.class);
			shidubianhao.setText(sb.getBean().getCode());
			bianhao_tv.setText(sb.getBean().getStoreCode());
			cangku_tv.setText(sb.getBean().getStoreName());
			super.onPostExecute(result);
		}
	}
	class ShiduAsync2 extends AsyncTask<String, Integer, String>{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return HttpUtils.getJsonFromHttp(params[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			//humidity--湿度值   humidityCode--湿度计CODE storeCode--仓库CODE
			ScanBean sb = new Gson().fromJson(result, ScanBean.class);
			if("3".equals(sb.getReturnMsgMap().getReturnAjaxState())){
				showToast("提交湿度成功");
			}else{
				showToast(sb.getReturnMsgMap().getMsg());
			}
			super.onPostExecute(result);
		}
	}
	
	// 是否寻卡过
	public boolean xunka() {
		boolean isX = false;
		// 寻卡
		String strUI = "";
		String t = I2CTools.ReadUID();
		// length不等0 则寻卡成功
		if (t.length() != 0) {
			strUI += ("<UID> : " + t + "\n");
			showToast(strUI + "寻卡成功!");
			isX = true;
		} else {
			strUI += ("错误:");
			showToast(strUI + "寻卡失败" + "\n" + "请放置好IC卡的位置:后盖左上方");
			isX = false;
		}
		return isX;
	}

	// 读ic卡号
	public String duIc() {
		byte[] passw = I2CTools.stringToBytes("FFFFFFFFFFFF");
		byte[] buffer = new byte[16];
		int add = Constants.ADD;// 50
		int t;
		t = I2CTools.ReadBlock(buffer, passw, (byte) 0x60, (byte) add);
		if (t == 0) {
//			showToast("成功:" + new String(buffer));
			return new String(buffer);
		} else {
			showToast("失败");
			return null;
		}
	}

	public void showToast(String text) {
		Toast.makeText(Shidu.this, text, Toast.LENGTH_LONG).show();
	}

	private void inits() {
		// TODO Auto-generated method stub
		cangku_tv = (EditText) findViewById(R.id.cangku_tv);
		bianhao_tv = (EditText) findViewById(R.id.bianhao_tv);
		shidubianhao = (EditText) findViewById(R.id.shidubianhao);
		shidu_input = (EditText) findViewById(R.id.shidu_input);
		
	}
	
}
