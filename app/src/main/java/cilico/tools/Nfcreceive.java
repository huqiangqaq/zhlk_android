package cilico.tools;

import com.enjoyor.soft.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Nfcreceive extends Activity {
	static public Handler m_handler = null;
	public static Intent m_intent;
	String strUI = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfcreceive);
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			System.out.println("ACTION_NDEF_DISCOVERED");
			processIntent(getIntent());
			return;
		}
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
			System.out.println("ACTION_TECH_DISCOVERED");
			processIntent(getIntent());
			return;
		}
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
			System.out.println("ACTION_TAG_DISCOVERED");
			Log.i("sdfs",getIntent().toString());
			processIntent(getIntent());
			return;
		}
	}

	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("0x");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	public static byte[] stringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private void processIntent(Intent intent) {
		if (m_handler != null) {
			byte[] myNFCID = control_nfc.getUID(intent);
			if (myNFCID != null) {
				String t = bytesToHexString(myNFCID);
				if (t.length() != 0) {
					strUI = (t);
					Message msg = new Message();
					msg.what = 2;
					msg.obj = strUI;
					m_handler.sendMessage(msg);
					Log.e("idd", t.substring(2));
				} else {
					strUI = ("ERROR\n");
					Message msg = new Message();
					msg.what = 2;
					msg.obj = strUI;
					m_handler.sendMessage(msg);
				}
			}
		}
		m_intent=intent;
		finish();
		onDestroy();// 释放内存
	}
	/**
	 * 读取标签块号：
	 * ***/
	public static String readSigOneBlock(String password, int address) {
		Log.i("sffdsf",m_intent.toString());
		return control_nfc.readSingOneBlock(m_intent, password, address);
	}
	/**
	 * 
	 * 写标签
	 * 
	 * **/
	public static Boolean WriteSigOneBlock(String password, int address,
			String dataE) {
		return control_nfc.WriteSigoneBlock(m_intent, password, address, dataE);
	}

}