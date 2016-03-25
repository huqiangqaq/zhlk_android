package com.enjoyor.soft.product.car.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cilico.tools.ICardTool;
import cilico.tools.Nfcreceive;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.product.car.model.AssayInfo;
import com.enjoyor.soft.product.car.model.CarInfoClient;
import com.enjoyor.soft.product.car.model.JsonReturn;
import com.enjoyor.soft.product.car.service.CarService;
import com.google.gson.Gson;

/**
 * 显示每个车辆详细信息和点击操作
 * 
 * @author Administrator 请求卸货
 * 
 */
public class RightContent extends Activity {
	private TextView carNumber, liangcang, category, how_to;
	private TextView cur_status;// 当前状态文本
	private Button managerBtn; // 点击操作按钮
	private String status_record;// 记录当前状态
	private TextView liangcang_doornumber;// 仓库操作门的位置
	private CarInfoClient cic;
	public static final int REQUEST = 100;
	public static final int COMPLETE = 101;
	CarService dbService;
	String carCode, rfidCode;
	String id;
	int comp = 10;
	ProgressDialog pd;
	Context context = this;
	String doorNumber = "";
	//private TextView cur_rongzhong,cur_water,cur_chonghai,cur_zhazhi,cur_notcomplete,cur_color;      //新增化验信息
	// *************************/
	// 定义接收的hander(dongqiwu)：
	String rfidGuid = null;// 寻卡成功标志
	private Handler mnfcHandler = new MainNfcHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Nfcreceive.m_handler = mnfcHandler;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.right_content);
		findviews();
		// "东北门","西北门","西南门","东南门"
		final Map<String, String> doormap = new HashMap<String, String>();
		doormap.put("0", "东北门");
		doormap.put("1", "西北门");
		doormap.put("2", "西南门");
		doormap.put("3", "东南门");
		dbService = new CarService(this);
		carCode = getIntent().getStringExtra("car_num");
		rfidCode = getIntent().getStringExtra("rfidCode");
		id = getIntent().getStringExtra("id");
		System.out.println("传过来的rfid：" + rfidCode);
		// 在本地数据库查询单个车辆记录
		cic = dbService.query(carCode);
		carNumber.setText(cic.getCarCode());
		liangcang.setText(cic.getStorehouseName());
		category.setText(cic.getItemName());
		how_to.setText("[" + ("0".equals(cic.getType()) ? "采购" : "销售") + "]");
		if (!TextUtils.isEmpty(cic.getDoorNumber()))
			liangcang_doornumber.setText(doormap.get(cic.getDoorNumber()));
		status_record = cic.getStatus();
//		new AssayInfoStatus().execute(Constants.WEBURIHEAD+Constants.URL_ASSAYINFO+rfidCode);
		// cur_status.setText(statusNum2Str(status_record));
		// 显示车辆状态
		cur_status.setText(statusNum2Str(cic.getType(), cic.getStatus()));
		/**
		 * 请求卸货操作： 2015-07-29
		 * */
		managerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 首先选择门的编号
				// 点击操作按钮前首先判断是否扫描rfid卡，并且是同一张卡
				//old: if(ICardTool.xunka(RightContent.this)){
				if (null!= rfidGuid) {
					// 此处也需要改：
					// @Admindqw
					// 替换为：
					String newrfidcode = Nfcreceive.readSigOneBlock(
							Constants.PASSWORD, Constants.ADD);
					//old: if(rfidCode.equals(ICardTool.duIc(RightContent.this).trim())){
					if (rfidCode.equals(newrfidcode)) {
						if (cic.getDoorNumber() == null) {
							AlertDialog.Builder abdoor = new AlertDialog.Builder(
									context);
							abdoor.setTitle("请选择门的位置");
							final String[] items = new String[] { "东北门", "西北门",
									"西南门", "东南门" };
							abdoor.setSingleChoiceItems(items, -1,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											doorNumber = which + "";
											Log.i("five", "您点击的是:" + doorNumber);
											liangcang_doornumber
													.setText(doormap
															.get(doorNumber));
											dialog.dismiss();
											alertManual(doorNumber,
													items[which]);
										}
									});
							abdoor.create().show();
						} else {
							alertManual(cic.getDoorNumber(),
									doormap.get(cic.getDoorNumber()));
						}
					} else {
						showToast("RFID卡不正确");
					}
				} else {
					showToast("寻卡失败" + "\n" + "请放置好IC卡的位置:后盖左上方");
				}

			}
		});
	}

	public void alertManual(final String number, String door) {
		doorNumber = number;
		AlertDialog.Builder ab = new AlertDialog.Builder(RightContent.this);
		ab.setTitle("操作提示").setMessage(
				"确定要在" + cic.getStorehouseName() + " " + door + " 【"
						+ managerBtn.getText() + "】?");
		ab.setPositiveButton("确定 ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// http://192.168.0.99/web/cargoAjax!update.htm?bean.id=140&bean.status=2
				String puri = Constants.getWebHead(context)
						+ "cargoAjax!update.htm?bean.workflowId=" + id
						+ "&bean.status=" + cic.getStatus();
				puri = puri + "&bean.type=" + cic.getType() + "&bean.carCode="
						+ cic.getCarCode();
				puri = puri + "&bean.doorNumber=" + number
						+ "&bean.receivePerson="
						+ getIntent().getStringExtra("userName");
				// intent.putExtra("userName", userName);
				Log.i("five", "puri:" + puri);

				// 发送链接改变和获取状态
				// new ChangeStatus().execute(new
				// String[]{puri.toString(),status_record+""});
				new ChangeStatus().execute(puri.toString());
				System.out.println("获取状态--" + puri.toString());
				pd = new ProgressDialog(RightContent.this);
				pd.setMessage("操作中请稍候...");
				pd.show();
			}
		}).setNegativeButton("取消", null);
		ab.create().show();
	}

	public void showToast(String str) {
		Toast.makeText(RightContent.this, str, Toast.LENGTH_SHORT).show();
	}

	public String statusNum2Str(String type, String status) {
		int i = Integer.valueOf(status);
		switch (i) {
		case 2:
			// 未开始装卸货
			if ("0".equalsIgnoreCase(type)) {
				managerBtn.setText("请求卸货");
				return "采购 未卸货";
			} else if ("1".equalsIgnoreCase(type)) {
				managerBtn.setText("请求装货");
				return "销售 未装货";
			}
			break;
		case 3:
			// 正在装卸货
			if ("0".equalsIgnoreCase(type)) {
				managerBtn.setText("完成卸货");
				return "采购 正在卸货";
			} else if ("1".equalsIgnoreCase(type)) {
				managerBtn.setText("完成装货");
				return "采购 正在装货";
			}
			break;
		case 4:
			// 装卸货已完成
			managerBtn.setClickable(false);
			managerBtn.setText("已完成");
			if ("0".equalsIgnoreCase(type)) {
				return "采购 卸货完成";
			} else if ("1".equalsIgnoreCase(type)) {
				return "销售 装货完成";
			}
			break;
		default:
			throw new IllegalArgumentException("unKnown status code:" + i);
		}
		return null;
	}

	// 异步任务改变状态
	class ChangeStatus extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			String result = HttpUtils.getJsonFromHttp(params[0]);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			pd = null;
			if (result == null) {
				showToast("网络异常,稍后再试!");
				startActivity(new Intent(RightContent.this, StoreTask.class));
			} else {
				// 判断网络异常
				if (Constants.HTTP_ERROR_INFO.equals(result)) {
					showToast(result);
					return;
				}
				JsonReturn jr = new Gson().fromJson(result, JsonReturn.class);
				String return_code = null;// 返回状态码
				// 0 无登陆1无权限2异常3正常
				return_code = jr.getReturnMsgMap().getReturnAjaxState();

				System.out.println("--return_code-->>>" + return_code);
				if ("3".equals(return_code)) {// 返回正常的状态码
					cic.setDoorNumber(doorNumber);
					String after_sta = dbService.query(carCode).getStatus();// 查询当前状态码
					System.out.println(after_sta + "===after_sta");

					if ("2".equals(after_sta)) {
						cic.setStatus(3 + "");
					} else if ("3".equals(after_sta)) {
						cic.setStatus(4 + "");
						managerBtn.setClickable(false);
					}
					dbService.update(cic);// 本地数据库同步
					// 修改按钮文本和当前状态文本
					cur_status.setText(statusNum2Str(cic.getType(),
							(Integer.parseInt(after_sta) + 1) + ""));
				} else if ("2".equals(return_code)) {
					// 错误信息
					Toast.makeText(RightContent.this,
							jr.getReturnMsgMap().getMsg(), Toast.LENGTH_LONG)
							.show();
				}
			}
			// super.onPostExecute(result);
		}
	}

	//异步获取化验信息
	class AssayInfoStatus extends AsyncTask<String,Integer,String>{

		@Override
		protected String doInBackground(String... params) {
			String result = HttpUtils.getJsonFromHttp(params[0]);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				showToast("网络异常,稍后再试!");
				startActivity(new Intent(RightContent.this, StoreTask.class));
			} else {
				// 判断网络异常
				if (Constants.HTTP_ERROR_INFO.equals(result)) {
					showToast(result);
					return;
				}
				AssayInfo assayInfo = new Gson().fromJson(result, AssayInfo.class);
//				cur_rongzhong.setText(assayInfo.getMcur_rongzhong());
//				cur_water.setText(assayInfo.getMcur_color());
//				cur_zhazhi.setText(assayInfo.getMcur_zhazhi());
//				cur_chonghai.setText(assayInfo.getMcur_chonghai());
//				cur_notcomplete.setText(assayInfo.getMcur_notcomplete());
//				cur_color.setText(assayInfo.getMcur_color());
			}
		}
	}

	// 初始化组件
	private void findviews() {
		carNumber = (TextView) findViewById(R.id.car_number);
		liangcang = (TextView) findViewById(R.id.liangcang);
		category = (TextView) findViewById(R.id.category);
		how_to = (TextView) findViewById(R.id.how_to);
		cur_status = (TextView) findViewById(R.id.cur_status);
		managerBtn = (Button) findViewById(R.id.managerbtn);
		liangcang_doornumber = (TextView) findViewById(R.id.liangcang_doornumber);
//		cur_rongzhong = (TextView) findViewById(R.id.cur_rongzhong);
//		cur_water = (TextView) findViewById(R.id.cur_water);
//		cur_chonghai = (TextView) findViewById(R.id.cur_chonghai);
//		cur_zhazhi = (TextView) findViewById(R.id.cur_zhazhi);
//		cur_notcomplete = (TextView) findViewById(R.id.cur_notcomplete);
//		cur_color = (TextView) findViewById(R.id.cur_color);



	}

	/*****************************************************************************************/
	/**
	 * @author Administrator 接收NFC消息： dongqiwu
	 * */
	private class MainNfcHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 2) {
				rfidGuid = (String) msg.obj;
				// txt_code.setText((String)msg.obj);
				// m_nCount++;
				// txt_scancount.setText("Scanning:"+String.valueOf(m_nCount));
				// 将结果保存到数据库：
				// 清空状态：
				new Handler().postDelayed(new Runnable() {
					public void run() {
						// txt_code.setText("");
					}
				}, 1000);
			}

			super.handleMessage(msg);
		}
	};
}
