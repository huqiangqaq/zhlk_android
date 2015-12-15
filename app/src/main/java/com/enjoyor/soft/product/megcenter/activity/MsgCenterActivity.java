package com.enjoyor.soft.product.megcenter.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
 * Create Date: 2013-6-26下午04:12:11
 * File Name: 消息中心 已读消息
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class MsgCenterActivity extends Activity {

	private ListView msg_listview;
	MsgAdapter adapter = null;
	List<Msg> msgs;//已读消息
	MsgDb msDb;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msgcenteractivity);
		msDb = new MsgDb(MsgCenterActivity.this);
		msgs = new ArrayList<Msg>();
		msgs = msDb.queryListYidu();
		msgs = turnList(msgs);
		msg_listview = (ListView) findViewById(R.id.msg_listView);
		adapter = new MsgAdapter(msgs,this);
		msg_listview.setAdapter(adapter);
		msg_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Msg m = msgs.get(position);
				MsgBean mb = m.getMsgBean();
				AlertDialog.Builder ab = new AlertDialog.Builder(MsgCenterActivity.this);
				ab.setTitle(mb.getSource()+":"+mb.getInformationName());
				ab.setMessage(mb.getContent()+"\n\n"+"        "+mb.getSendTime());
				ab.setPositiveButton("确定", null);
				m.setCheckState("1");
				msDb.update(m);
				ab.setCancelable(true);
				ab.create().show();
			}
		});
		
	}
	
	//待优化排序
	public List<Msg> turnList(List<Msg> msgs){
		List<Msg> msgs2 = new ArrayList<Msg>();
		for(int i=msgs.size()-1;i>=0;i--){
			msgs2.add(msgs.get(i));
		}
		return msgs2;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		adapter.notifyDataSetInvalidated();
		System.out.println("已读消息 resume");
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(1001, 101, 2, "清空已读消息列表");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 101:
			msgs.clear();
			msDb.clearTab();
			adapter.notifyDataSetInvalidated();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
