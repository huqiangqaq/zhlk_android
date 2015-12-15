package com.enjoyor.soft.product.megcenter.activity;

import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.enjoyor.soft.R;

public class MergeActivity extends TabActivity {
	TabHost tabHost;
	RadioGroup rg;
	NotificationManager mNotificationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_tabhost);
		
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//点击后取消多个消息通知
		boolean ifNotification = getIntent().getBooleanExtra("trans", false);
		if(ifNotification){
			mNotificationManager.cancel(0);
		}
		
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("one").setIndicator("ONE").setContent(new Intent(MergeActivity.this, MsgWeiDuActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("two").setIndicator("TWO").setContent(new Intent(MergeActivity.this, MsgCenterActivity.class)));
		tabHost.setCurrentTab(0);
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {
				case R.id.rb1:
					tabHost.setCurrentTab(0);
					break;
				case R.id.rb2:
					tabHost.setCurrentTab(1);
					break;
				default:
					break;
				}
			}
		});
	}
	
}
