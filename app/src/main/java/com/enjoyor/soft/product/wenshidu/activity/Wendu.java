package com.enjoyor.soft.product.wenshidu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cilico.tools.I2CTools;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.product.wenshidu.model.ScanBean;
import com.enjoyor.soft.product.wenshidu.util.MyThread;
import com.google.gson.Gson;

public class Wendu extends Activity {

	EditText rfid_code, input_wendu;//rfid显示框，温度输入框
	EditText cangku_tv, bianhao_tv;//仓库、编号显示框
	Button wendu_scan, queding_btn;//rfid扫描按钮，提交按钮
	String storeCode;
	GetBean gb;//异步获取数据
	GetBean2 gb2;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.wendu);
		inits();
		gb = new GetBean();
		gb2 = new GetBean2();
		//温度扫描
		wendu_scan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击下方扫描按钮
				boolean x = xunka();// 每次寻卡
				// 扫描ic卡
				String rfidCode = duIc();
				if (rfidCode != null) {
					// 查询链接
					String uri = Constants.WEBURIHEAD
							+ "tempAjax!tempfpda.htm?code=123456&userName="+Constants.username+"&password="+Constants.password;
					if (x) {
//						gb.execute(new String[] { "0", uri });
						new MyThread(handler, uri, 0).start();
						pd = new ProgressDialog(Wendu.this);
						pd.show();
					}
				} else {
//					 showToast("IC卡位置不正确!");
				}
			}
		});
		//提交
		queding_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tempCode = rfid_code.getText().toString();
				String temperature = input_wendu.getText().toString();
				System.out.println("temperature-->" + temperature);
				// 提交温度
				String uri2 = Constants.WEBURIHEAD
						+ "tempCodeAjax!insert.htm?bean.tempCode=" + tempCode
						+ "&bean.storeCode=" + storeCode + "&bean.temperature="
						+ temperature + "&userName="+Constants.username+"&password="+Constants.password;
//				gb2.execute(new String[] { "1", uri2 });
				new MyThread(handler, uri2, 1).start();
				pd = new ProgressDialog(Wendu.this);
				pd.show();
			}
		});
	}
	//这是一段测试代码
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
						pd.dismiss();
						pd = null;
						storeCode = scanBean.getBean().getStoreCode();
						rfid_code.setText(scanBean.getBean().getCode());
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
						rfid_code.setText("");
						cangku_tv.setText("");
						bianhao_tv.setText("");
						input_wendu.setText("");
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


	class GetBean extends AsyncTask<String, Integer, String> {
		String flag = "";

		@Override
		protected String doInBackground(String... params) {
			flag = params[0];
			return HttpUtils.getJsonFromHttp(params[1]);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ScanBean scanBean = new Gson().fromJson(result, ScanBean.class);
			if ("0".equals(flag)) {
				System.out.println("scanBean-->" + scanBean);
				if (scanBean != null
						&& scanBean.getBean() != null
						&& "3".equals(scanBean.getReturnMsgMap()
								.getReturnAjaxState())) {
					storeCode = scanBean.getBean().getStoreCode();
					rfid_code.setText(scanBean.getBean().getCode());
					cangku_tv.setText(scanBean.getBean().getStoreName());
					bianhao_tv.setText(scanBean.getBean().getCode());
				} else if ("2".equals(scanBean.getReturnMsgMap()
						.getReturnAjaxState())) {
					showToast(scanBean.getReturnMsgMap().getMsg());
				} else {
					showToast("查询出错");
				}
			} else if ("1".equals(flag)) {
				if (scanBean != null
						&& "3".equals(scanBean.getReturnMsgMap()
								.getReturnAjaxState())) {
					showToast("提交成功");
				} else {
					showToast(scanBean.getReturnMsgMap().getMsg());
				}
			}
		}
	}

	class GetBean2 extends AsyncTask<String, Integer, String> {
		String flag = "";

		@Override
		protected String doInBackground(String... params) {
			flag = params[0];
			return HttpUtils.getJsonFromHttp(params[1]);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if ("0".equals(flag)) {
				ScanBean scanBean = new Gson().fromJson(result, ScanBean.class);
				storeCode = scanBean.getBean().getStoreCode();
				rfid_code.setText(scanBean.getBean().getCode());
				cangku_tv.setText(scanBean.getBean().getStoreName());
				bianhao_tv.setText(scanBean.getBean().getCode());
			} else if ("1".equals(flag)) {
				ScanBean scanBean = new Gson().fromJson(result, ScanBean.class);
				if ("3".equals(scanBean.getReturnMsgMap().getReturnAjaxState())) {
					showToast("提交成功");
				} else {
					showToast(scanBean.getReturnMsgMap().getMsg());
				}
			}
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
//			showToast("失败");
			return null;
		}
	}

	public void showToast(String text) {
		Toast.makeText(Wendu.this, text, Toast.LENGTH_LONG).show();
	}

	private void inits() {
		rfid_code = (EditText) findViewById(R.id.rfid_code);
		input_wendu = (EditText) findViewById(R.id.input_wendu);
		cangku_tv = (EditText) findViewById(R.id.cangku_tv);
		bianhao_tv = (EditText) findViewById(R.id.bianhao_tv);
		wendu_scan = (Button) findViewById(R.id.wendu_scan);
		queding_btn = (Button) findViewById(R.id.queding_btn);
		
	};
}
