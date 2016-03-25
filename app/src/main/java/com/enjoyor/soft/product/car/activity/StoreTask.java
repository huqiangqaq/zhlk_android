package com.enjoyor.soft.product.car.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cilico.tools.I2CTools;
import cilico.tools.ICardTool;
import cilico.tools.Nfcreceive;
import cilico.tools.control_nfc;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HttpUtils;
import com.enjoyor.soft.common.StringUtils;
import com.enjoyor.soft.product.car.model.Bean;
import com.enjoyor.soft.product.car.model.CarInfoClient;
import com.enjoyor.soft.product.car.model.JsonReturn;
import com.enjoyor.soft.product.car.service.CarService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**仓库管理列表
 * @author Administrator
 *
 */
public class StoreTask extends Activity {
	ListView mListView;
	List<CarInfoClient> msgs;
	MlistAdapter adapter;
	CarService dbService;
	Button scanBtn;
	AlertDialog.Builder ab;
	ProgressDialog pd;
	String userName,password;
	int lightLine = -1;//扫描后当前行醒目 
	Context context = this;
	
	//定义接收的hander(dongqiwu)：
	String rfidGuid=null;//寻卡成功标志
	String rfidCode = null;//改为全局定义：接收读到的卡号
    private Handler mnfcHandler = new MainNfcHandler();
	private static final String TAG = StoreTask.class.getSimpleName();
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1001, 100, 1, "清空数据");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			dbService.clearList();
			msgs.clear();
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//"东北门","西北门","西南门","东南门"
			Map<String, String> doormap = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.showtask);
		Nfcreceive.m_handler=mnfcHandler;
		doormap.put("0", "东北门");
		doormap.put("1", "西北门");
		doormap.put("2", "西南门");
		doormap.put("3", "东南门");
		
		Intent getintent = getIntent();
		userName = getintent.getStringExtra("userName");
		password = getintent.getStringExtra("password");
		
		//listview定位
		lightLine = getintent.getIntExtra("lightLine", -1);
		if(lightLine!=-1){
			mListView.setSelection(lightLine);
		}
		scanBtn = (Button) findViewById(R.id.scanBtn);
//		msgs = Constants.list;
		mListView = (ListView) findViewById(R.id.mListView);
		dbService = new CarService(this);
		/*msgs = dbService.queryList();// 获取列表
		adapter = new MlistAdapter(this, msgs);
		mListView.setAdapter(adapter);*/

		
		
		/*mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				Intent intent = new Intent(StoreTask.this, RightContent.class);
				CarInfoClient cic = msgs.get(position);
				intent.putExtra("car_num", cic.getCarCode());
				intent.putExtra("rfidCode", cic.getRfidCode());
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
			}
		});*/
		/**
		 * @author Administrator
		 * 扫描RFID卡
		 * 先扫描RFID
		 * 读取固定块号的内容：
		 * 2015--07-24
		 * ***/
		scanBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击下方扫描按钮
				if(null==rfidGuid)
				{
					Toast.makeText(StoreTask.this, "请放入IC卡", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					try {
						//rfidCode = ICardTool.duIc(StoreTask.this).trim();
						int add = Constants.ADD;
						rfidCode=Nfcreceive.readSigOneBlock(Constants.PASSWORD, add);
						String aaa=rfidCode;
//						rfidCode=Long.valueOf("0x3131313131313131",16).toString();
//						int aa=Integer.valueOf("0x3131313131313131",16);
						
						
					} catch (Exception e) {
						Toast.makeText(StoreTask.this, "读取卡号失败",
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (rfidCode != null){
						StringBuilder sb = new StringBuilder();
						// 查询链接
						String uri = Constants.getWebHead(context)
								+ Constants.URL_CHECK_RFID + rfidCode
								+ "&userName=" + userName + "&password="
								+ password;
						System.out.println("uri-->" + uri);
						
						sb.append(uri);
						new BacAsync().execute(sb.toString());
						pd = new ProgressDialog(StoreTask.this);
						pd.setMessage("正在查询数据...");
						pd.show();
					}
					
				}
//				
//				if (rfidCode != null) {
//					StringBuilder sb = new StringBuilder();
//					//查询链接
//					String uri = Constants.getWebHead(context)+Constants.URL_CHECK_RFID+rfidCode+"&userName="+userName+"&password="+password;
//					System.out.println("uri-->"+uri);
//					sb.append(uri);
//					//if (x)
//					
//						new BacAsync().execute(sb.toString());
//						pd= new ProgressDialog(StoreTask.this);
//						pd.setMessage("正在查询数据...");
//						pd.show();
//					}
//				    else{
//						Toast.makeText(StoreTask.this, "请放入IC卡", Toast.LENGTH_SHORT).show();
//					}
				/*else{
					showToast("ic卡没初始化写入!");
				}*/
			}
		});
	}
	@Override
	protected void onResume() {
		//再次显示时候重新查询本地数据库
		super.onResume();
		dbService.del();//删除已经完成作业的车辆
		Log.i(TAG, "OnResume");
		msgs = dbService.queryList();
//		adapter.notifyDataSetChanged();
		adapter = new MlistAdapter(this, msgs);
		mListView.setAdapter(adapter);
		Log.i(TAG, "msgs.toString() "+msgs.toString());
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				Intent intent = new Intent(StoreTask.this,RightContent.class);
				CarInfoClient cic = msgs.get(position);
				intent.putExtra("car_num", cic.getCarCode());
				intent.putExtra("userName", userName);
				intent.putExtra("rfidCode", cic.getRfidCode());
				intent.putExtra("id", cic.getWorkflowId());
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
			}
		});
	}
	// 在新线程里通过rfidcode获取json
	class BacAsync extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpUtils.getJsonFromHttp(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();pd = null;
			if(result!=null){
				//判断网络异常
				if(Constants.HTTP_ERROR_INFO.equals(result)){
					showToast(result);
					return;
				}
				JsonReturn jr = null;
				try {
					jr = new Gson().fromJson(result, JsonReturn.class);
					
					Log.i("five", "rfid result:"+result);
				} catch (JsonSyntaxException e) {
					Toast.makeText(context, "返回数据异常", 0).show();
					e.printStackTrace();
					return;
				}
				if(jr!=null){
					int sta = Integer.parseInt(jr.getReturnMsgMap().getReturnAjaxState());
					if (sta == 3) {
						Bean b = jr.getBean();
						System.out.println("bean-->"+b);
						if("0".equalsIgnoreCase(b.getType()) && "4".equalsIgnoreCase(b.getStatus())){
							showToast("采购 已完成");return;
						}else if("1".equalsIgnoreCase(b.getType()) && "4".equalsIgnoreCase(b.getStatus())){
							showToast("销售 已完成");return;
						}
						if(b!=null && b.getStatus()!=null && Integer.valueOf(b.getStatus())<2){
							showToast("车辆未过磅");return;
						}
						if(b!=null && b.getStatus()!=null && Integer.valueOf(b.getStatus())>4){
							showToast("已完成");return;
						}
						if(b!=null && b.getStatus()==null){
							showToast("RFID卡 未激活");return;
						}
						CarInfoClient infoClient = new CarInfoClient();
						infoClient.setCarCode(b.getCarCode());
						infoClient.setItemName(b.getItemName());
//						infoClient.setStatus(jr.getStatus());
						infoClient.setStatus(b.getStatus());
						infoClient.setStorehouseName(b.getStorehouseName());
						infoClient.setCode(b.getCode());
						infoClient.setStorehouseCode(b.getStorehouseCode());
//						infoClient.setType(jr.getType());
						infoClient.setType(b.getType());
						infoClient.setUri(jr.getUrl());
						infoClient.setWorkflowId(b.getWorkflowId());
						infoClient.setRfidCode(jr.getRfidCode());
						
						if(!TextUtils.isEmpty(jr.getBean().getDoorNumber())){
							infoClient.setDoorNumber(jr.getBean().getDoorNumber());
						}
						
						Log.i("five", "门的编号:"+jr.getBean().getDoorNumber());
						
						
						Intent intent = new Intent(StoreTask.this, StoreTask.class);//类似刷新 重新加载查询数据
						boolean isExists = false;	// 判断当前sqlite数据是否存在
						//如果存在 就更新
						for(int i=0;i<msgs.size();i++){
							if (msgs.get(i).getWorkflowId().equals(infoClient.getWorkflowId())) {
								isExists = true; 
								lightLine = i;//记录醒目行
								dbService.update(infoClient);
								showToast(infoClient.getCarCode()+" "+infoClient.getStorehouseName()+" "+doormap.get(infoClient.getDoorNumber()));
								continue;
							}
						}
						
						//如果不存在 则刷新加载
						if (!isExists) {
							dbService.insert(infoClient);
							lightLine = msgs.size();//最后一行显示被选中
						}
						intent.putExtra("lightLine", lightLine);
						startActivity(intent);
						
						
					} else if (sta == 2) {
						showToast(jr.getReturnMsgMap().getMsg()+" 此卡可能不存在");
					}
				}else{
					showToast("解析出错");
				}
				
			}else{
				showToast("网络异常,请稍后再试!");
			}
			
		}
	}
	//显示弹toast
	public void showToast(String text) {
		Toast.makeText(StoreTask.this, text, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	//listview适配器
	class MlistAdapter extends BaseAdapter {

		Context context;
		List<CarInfoClient> list;

		public MlistAdapter(Context context, List<CarInfoClient> list) {
			this.context = context;
			this.list = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View mView = LayoutInflater.from(context).inflate(R.layout.list_items, null);
			TextView tv = (TextView) mView.findViewById(R.id.msgtitle);
			CarInfoClient cic = list.get(position);
			String stastr = StringUtils.Num2Str(cic.getType(),cic.getStatus());
			if(lightLine!=-1&&position==lightLine){
				mListView.setSelection(lightLine);
				tv.setText(Html.fromHtml("<font color=#0000ff>"+cic.getCarCode() + "---" + stastr+"</font>"));
			}else{
				tv.setText(cic.getCarCode() + "---" + stastr);
			}
			return mView;
		}

	}
	/**
	 * @author Administrator
	 * 接收NFC消息：
	 * dongqiwu在
	 * */
	private class MainNfcHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
    	if (msg.what==2) {
    		rfidGuid=(String)msg.obj;
			//txt_code.setText((String)msg.obj);
			//m_nCount++;
			//txt_scancount.setText("Scanning:"+String.valueOf(m_nCount));
			//将结果保存到数据库：
			//清空状态：
			new Handler().postDelayed(new Runnable() {
				public void run() {
					//txt_code.setText("");
				}
			}, 1000);
    	}
    
    	super.handleMessage(msg);
    }
	};

}
