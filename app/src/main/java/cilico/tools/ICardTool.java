package cilico.tools;

import com.enjoyor.soft.common.Constants;

import android.content.Context;
import android.widget.Toast;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:33:15
 * File Name: RFID扫描工具类
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class ICardTool {

	// 是否寻卡过
	public static boolean xunka(Context context) {
		boolean isX = false;
		// 寻卡
		String strUI = "";
		String t = I2CTools.ReadUID();
		// length不等0 则寻卡成功
		if (t.length() != 0) {
			strUI += ("<UID> : " + t + "\n");
//			showToast(context,strUI + "寻卡成功!");
			isX = true;
		} else {
			strUI += ("ERROR:");
			showToast(context,strUI + "寻卡失败"+"\n"+"请放置好IC卡的位置:后盖左上方");
			isX = false;
		}
		return isX;
	}
	// 固定地址读取IC卡号读ic卡号
	public static String duIc(Context context) {
		byte[] passw = I2CTools.stringToBytes("FFFFFFFFFFFF");
		byte[] buffer = new byte[16];
		int add = Constants.ADD;//50
		int t;
		t = I2CTools.ReadBlock(buffer, passw, (byte) 0x60, (byte) add);
		if (t == 0) {
//			showToast(context,"成功:" + new String(buffer));
			return new String(buffer);
		} else {
//			showToast(context,"失败");
			return null;
		}
	}
	public static void showToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
