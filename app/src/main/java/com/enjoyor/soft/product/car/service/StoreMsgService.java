package com.enjoyor.soft.product.car.service;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.product.megcenter.activity.MergeActivity;
import com.enjoyor.soft.product.megcenter.model.Msg;
import com.enjoyor.soft.product.megcenter.model.MsgBean;
import com.enjoyor.soft.product.megcenter.model.MsgJsonReturn;
import com.google.gson.Gson;

/**仓库管理后台轮询获取新任务
 * 通知栏显示提醒
 * @author hutuanle
 *
 */
public class StoreMsgService extends Service {
	//定义通知相关组件
	private Notification mNotification;
	private NotificationManager mNotificationManager;
	PendingIntent contentIntent = null;
	private int NOTIFICATION_ID=1000;
	boolean isStarted;
	String message = "";//消息变量
	String MSG = null;	//当前消息
	int MSG_ID = 0;
	Gson gson = null;
	MsgDb msgService = null;
	String username,password;
	Context context = this;
	public static final int interval = 10000;//10秒间隔轮询
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		isStarted = true;
		System.out.println("onStartCommand...");
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("服务启动");
		msgService = new MsgDb(getBaseContext());
		mNotification = new Notification();
		mNotification.icon = android.R.drawable.ic_menu_manage;//通知图标
		mNotification.tickerText = "粮库通知";
		Intent intent = new Intent(StoreMsgService.this, MergeActivity.class);
		intent.putExtra("trans", true);
		contentIntent = PendingIntent.getActivity(StoreMsgService.this, 0, intent, 0);
		//默认声音和振动
		//file:///sdcard-ext/notice.mp3
		mNotification.sound = Uri.parse("file:///storage/sdcard0/Music/ttys.ogg");
		//mNotification.sound = Uri.parse("file:///system/media/audio/ringtones/Noises1.ogg"); 
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;//图标自动消失
//		mNotification.defaults |= Notification.DEFAULT_LIGHTS;
//		mNotification.defaults = Notification.DEFAULT_SOUND;//|Notification.DEFAULT_VIBRATE
//		mNotification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		isStarted = true;
		gson = new Gson();
		new NewOne().start();
	}
	
	//获取数据线程
	class NewOne extends Thread{
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			while(isStarted){
				try {
					Thread.sleep(interval);//10s查询一次
					Log.i("five", "10s查询一次");
					MSG = getServerMSG();
					System.out.println("10s查询:MSG == message "+ MSG.equals(message));
//					System.out.println(TextUtils.isEmpty(MSG));
					if(!MSG.equalsIgnoreCase(message)){
						MsgJsonReturn mjr = gson.fromJson(MSG, MsgJsonReturn.class);
						if(mjr!=null){
							if("3".equals(mjr.getReturnMsgMap().getReturnAjaxState())){
								List<MsgBean> msglist = mjr.getList();
								System.out.println("----------------------------");
								
								if(msglist!=null&&msglist.size()!=0){
									for(MsgBean mb:msglist){
										msgService.insert(new Msg(mb, "0"));//插入sqlite数据库
									}
									if(msglist.size()==1){
										MsgBean m = msglist.get(0);
										System.out.println("size == 1");
										mNotification.setLatestEventInfo(StoreMsgService.this, "有新消息:", m.getSource()+":"+m.getInformationName()+"\n"+m.getSendTime(), contentIntent);
									}else{
										System.out.println("size == else");
										mNotification.setLatestEventInfo(StoreMsgService.this, "有新消息:","有 "+msglist.size()+ "条未读消息", contentIntent);
									}
								}
								mNotificationManager.notify(NOTIFICATION_ID++, mNotification);
							}else{
								System.out.println(mjr.getReturnMsgMap().getMsg());
							}
						}else{
//							showToast("连接出错或解析错误");
							System.out.println("连接出错或解析错误");
						}
					}
					message = MSG;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isStarted = false;
		System.out.println("服务断开");
		super.onDestroy();
	}
	/*public void showToast(String message){
		Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
	}*/
	
	//从服务器获取新消息方法  
	private String getServerMSG() {
		String reStr = "";
		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(Constants.WEBURIHEAD+"personalinfoAjax!receive.htm?state=1&userId=402880883f662110013f6661f5e30006&userName="+Constants.username+"&password="+Constants.password);
		HttpGet get = new HttpGet(Constants.getWebHead(context)+Constants.URL_MSG);
		
		try {
			HttpResponse httpResponse = client.execute(get);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				reStr = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("接收到的 reStr ："+reStr+reStr.length());
		return reStr;
	}
}
