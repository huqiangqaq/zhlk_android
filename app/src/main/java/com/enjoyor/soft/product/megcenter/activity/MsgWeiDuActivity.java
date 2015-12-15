package com.enjoyor.soft.product.megcenter.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.enjoyor.soft.R;
import com.enjoyor.soft.product.car.service.MsgDb;
import com.enjoyor.soft.product.megcenter.model.Msg;
import com.enjoyor.soft.product.megcenter.model.MsgBean;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-7-3下午02:05:57
 * File Name: 未读消息
 * Last version: 1.0
 * Last Update Date: 2013-7-3
 * Change Log:
 */
public class MsgWeiDuActivity extends Activity {

	ListView weidu_listView;
	MsgAdapter adapter = null;
	List<Msg> msgs;//未读消息
	MsgDb msDb;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msgweiduactivity);
		
		weidu_listView = (ListView) findViewById(R.id.msg_weidu_listView);
		msDb = new MsgDb(this);
		msgs = new ArrayList<Msg>();
		msgs = msDb.queryListWeidu();
		msgs = turnList(msgs);
		adapter = new MsgAdapter(msgs,this);
		weidu_listView.setAdapter(adapter);
		weidu_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				final Msg m = msgs.get(position);
				MsgBean mb = m.getMsgBean();
				
				AlertDialog.Builder ab = new AlertDialog.Builder(MsgWeiDuActivity.this);
//				ab.setTitle("系统提示"+":"+mb.getInformationName());
				ab.setTitle(mb.getSource()+":"+mb.getInformationName());
				ab.setMessage(mb.getContent()+"\n\n"+"        "+mb.getSendTime());
				ab.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if("0".equals(m.getCheckState())){
							m.setCheckState("1");
							msDb.update(m);
						}
						msgs.remove(position);
						adapter.notifyDataSetInvalidated();
					}
				});
				ab.setCancelable(true);
				ab.create().show();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(1001, 101, 2, "刷新未读消息");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 101:
			finish();
			startActivity(new Intent(MsgWeiDuActivity.this, MergeActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("five", "未   读消息 resume");
		adapter.notifyDataSetInvalidated();
		super.onResume();
	}
	//待优化
	public List<Msg> turnList(List<Msg> msgs){
		List<Msg> msgs2 = new ArrayList<Msg>();
		for(int i=msgs.size()-1;i>=0;i--){
			msgs2.add(msgs.get(i));
		}
		return msgs2;
	}
}
