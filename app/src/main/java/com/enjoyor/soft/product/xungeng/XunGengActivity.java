package com.enjoyor.soft.product.xungeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.Header;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.barcode.Scanner;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HtlClientCallBack;
import com.enjoyor.soft.common.HtlClientUtils;
import com.enjoyor.soft.product.megcenter.model.MsgJsonReturn;
import com.google.gson.Gson;

//import com.htl.Zxing.decoding.CaptureActivity;

/**
 * 巡更
 * 
 * @author Administrator
 * 
 */
public class XunGengActivity extends Activity {

	/** 本测试程序是采用AudioTrack播放一个固定频率的声音发提示音的，你可以播放系统音或者播放一段音频 */
	private final int duration = 1; // seconds
	private final int sampleRate = 2000;
	private final int numSamples = duration * sampleRate;
	private final double sample[] = new double[numSamples];
	private final double freqOfTone = 1600; // hz

	private final byte generatedSnd[] = new byte[2 * numSamples];
	String stock_numer;
	EditText code;
	private Handler mHandler = new MainHandler();

	private static final String BARCODE_ACTION = "com.barcode.sendBroadcast";
	private static final String BARCODE_PARAM = "BARCODE";
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		IntentFilter intentFilter = new IntentFilter(BARCODE_ACTION);
		
		registerReceiver(barcodeBroadcastReceiver, intentFilter);
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(barcodeBroadcastReceiver);
		super.onDestroy();
	}

	int count = 0;
	private BroadcastReceiver barcodeBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(BARCODE_ACTION.equals(action)){
				String barcode = intent.getStringExtra(BARCODE_PARAM);
				code.setText(barcode);
				//tv_count.setText(String.valueOf(count++));
				//dealScanCode(barcode);
				
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_xungeng);
		code = (EditText) findViewById(R.id.editxungencode);
		// 二维码签到扫描
		findViewById(R.id.btn_qd).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 打开二维码扫描签到：
				// startActivityForResult(new
				// Intent(XunGengActivity.this,CaptureActivity.class), 1002);
				//String gbkcode="";	
				if (!"".equals(code.getText().toString())) { 
					Uploadxungen(code.getText().toString());
				} 
				else 
				{
					showToast("请扫描条码");
				}
			}
		});
		findViewById(R.id.btn_record).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(XunGengActivity.this,
						ChaCangRecordActivity.class);
				intent.putExtra("webhead", getIntent()
						.getStringExtra("webhead"));
				intent.putExtra("userName",
						getIntent().getStringExtra("userName"));
				startActivity(intent);
			}
		});

	}
	/**
     * 字符串编码转换的实现方法
     * @param str    待转换的字符串
     * @param newCharset    目标编码
     */
    public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if(str != null) {
            //用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes("GB2312");
            return new String(bs, newCharset);    //用新的字符编码生成字符串
        }
        return null;
    }

	Context context = this;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1002 && resultCode == RESULT_OK && data != null) {
			// 扫描获取到条码数据（下面的代码为通过摄像头解析所得，（1）目前改为通过广播的形式扫描获取条码数据：）
			// (2)也可以改为通过串口直接获取条码数据：
			stock_numer = data.getStringExtra("resultCode");
			Log.i("five", stock_numer);
			AlertDialog.Builder ab = new AlertDialog.Builder(context);
			ab.setMessage("仓房号:" + stock_numer);
			ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					final ProgressDialog pd = new ProgressDialog(context);
					pd.setMessage("正在提交...");
					pd.show();
					String url = getIntent().getStringExtra("webhead");
					url += Constants.URL_XG_POST;
					// userName=test&password=admin&likeStr=111
					url += "userName=" + getIntent().getStringExtra("userName")
							+ "&password="
							+ getIntent().getStringExtra("password")
							+ "&likeStr=" + stock_numer;
					Log.i("five", url);
					HtlClientUtils.getContent(url, null, true,
							new HtlClientCallBack() {

								@Override
								public void contentResponse(boolean isSuccess,
										int statusCode, Header[] headers,
										byte[] responseBody) {
									if (pd != null)
										pd.dismiss();
									if (isSuccess) {
										MsgJsonReturn mjr = new Gson()
												.fromJson(new String(
														responseBody),
														MsgJsonReturn.class);
										if ("3".equalsIgnoreCase(mjr
												.getReturnMsgMap()
												.getReturnAjaxState())) {
											showToast(mjr.getReturnMsgMap()
													.getMsg());
											code.setText("");
										} else {
											showToast(mjr.getReturnMsgMap()
													.getMsg());
											code.setText("");
										}
									} else {
										showToast("服务器返回异常,请重试");
									}
								}
							});
				}
			}).setNegativeButton("取消", null).create().show();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void showToast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	// **************************************代码修改巡更(2015-07-27)*********************************
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getRepeatCount() == 0)
		{
			if (keyCode == 4) {
				finish();
			}
			if ((keyCode == 211) || (keyCode == 212)) {
				// 扫描开始
				Scanner.Read();
			}
		}
		return true;
	}

	@Override
	protected void onStart() {
		// 赋值handle句柄
		Scanner.m_handler = mHandler;
		// 初始化扫描头
		Scanner.InitSCA();
		super.onStart();
	}

	private class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case Scanner.BARCODE_READ: {
				// 显示读到的条码
				code.setText((String) msg.obj);
				// 光标移到编辑框最后一行
				// 如果没有选择静音模式,播放声音
				play();
				break;
			}
			case Scanner.BARCODE_NOREAD: {

				break;
			}
			default:
				break;
			}
		}
	};

	void genTone() {
		// fill out the array
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (double dVal : sample) {
			short val = (short) (dVal * 32767);
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
		}

	}

	void playSound() {

		AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, numSamples,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd, 0, numSamples);
		audioTrack.play();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		audioTrack.release();

	}

	void play() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				genTone();
				playSound();

			}
		});
		thread.start();
	}

	public void Uploadxungen(String code) {
		stock_numer = code;// data.getStringExtra("resultCode");
		Log.i("five", stock_numer);
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setMessage("仓房号:" + stock_numer);
		ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				final ProgressDialog pd = new ProgressDialog(context);
				pd.setMessage("正在提交...");
				pd.show();
				String url = getIntent().getStringExtra("webhead");
				url += Constants.URL_XG_POST;
				// userName=test&password=admin&likeStr=111
				url += "userName=" + getIntent().getStringExtra("userName")
						+ "&password=" + getIntent().getStringExtra("password")
						+ "&likeStr=" + stock_numer;
				Log.i("five", url);
				HtlClientUtils.getContent(url, null, true,
						new HtlClientCallBack() {

							@Override
							public void contentResponse(boolean isSuccess,
									int statusCode, Header[] headers,
									byte[] responseBody) {
								if (pd != null)
									pd.dismiss();
								if (isSuccess) {
									MsgJsonReturn mjr = new Gson().fromJson(
											new String(responseBody),
											MsgJsonReturn.class);
									if ("3".equalsIgnoreCase(mjr
											.getReturnMsgMap()
											.getReturnAjaxState())) {
										showToast(mjr.getReturnMsgMap()
												.getMsg());
									} else {
										showToast(mjr.getReturnMsgMap()
												.getMsg());
									}
								} else {
									showToast("服务器返回异常,请重试");
								}
							}
						});
			}
		}).setNegativeButton("取消", null).create().show();

	}

}
