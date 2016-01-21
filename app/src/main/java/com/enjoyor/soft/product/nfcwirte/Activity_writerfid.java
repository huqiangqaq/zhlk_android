package com.enjoyor.soft.product.nfcwirte;

import cilico.tools.Nfcreceive;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * nfcRFID写入：
 * 
 * @author Administrator 2015-07-27
 * **/
public class Activity_writerfid extends Activity implements OnClickListener {

	// 定义接收的hander
	private Handler mnfcHandler = new MainNfcHandler();
	public EditText textcode, textwirtecode;
	public TextView textreadcode;
	public Button bstwrite, bstread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_writerfid);
		Nfcreceive.m_handler = mnfcHandler;
		initview();
	}

	private void initview() {
		textcode = (EditText) findViewById(R.id.textnfccode);
		textwirtecode = (EditText) findViewById(R.id.textwirtecode);
		textreadcode = (TextView) findViewById(R.id.textreadcode);
		bstwrite = (Button) findViewById(R.id.bstnfcwrite);
		bstread = (Button) findViewById(R.id.bstnfcread);
		bstwrite.setOnClickListener(this);
		bstread.setOnClickListener(this);
	}

	/**
	 * @author Administrator 接收NFC消息： dongqiwu
	 * */
	private class MainNfcHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 2) {
				textcode.setText((String) msg.obj);
			}
			super.handleMessage(msg);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bstnfcwrite:
			if (!"".equals("textwirtecode.getText().toString()")) {
				if (Nfcreceive.WriteSigOneBlock(Constants.PASSWORD,
						Constants.ADD, textwirtecode.getText().toString())) {
					Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();

				}
			}else
			{
				Toast.makeText(this, "请输入写卡内容", Toast.LENGTH_SHORT).show();

			}
			break;
		case R.id.bstnfcread:
			String code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
					Constants.ADD);
			Log.i("sdfsf",code);
			if (!"".equals(code)) {
				textreadcode.setText(code);
			} else {
				Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	};
}
