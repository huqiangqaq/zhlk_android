package com.enjoyor.soft.product.car.service;

import java.util.ArrayList;
import java.util.List;

import com.enjoyor.soft.product.car.dao.DBService;
import com.enjoyor.soft.product.megcenter.model.Msg;
import com.enjoyor.soft.product.megcenter.model.MsgBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26下午09:54:36
 * File Name: 消息查询数据实现类
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class MsgDb implements DBService<Msg>{

	private DBOpenHelper dh;
	
	public MsgDb(Context context) {
		dh = new DBOpenHelper(context);
	}

	@Override
	public Msg query(String informationCode) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getReadableDatabase();
		Msg m = null;
		String sql = "select * from MSGCenter where informationCode=?";
		Cursor c = db.rawQuery(sql, new String[]{informationCode});
		if(c.moveToNext()){
			m =  new Msg( new MsgBean(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),
					c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10)), c.getString(11));
		}
		c.close();
		db.close();
		return m;
	}
	//批量查询未读的
	public List<Msg> queryByState(){
		SQLiteDatabase db = dh.getReadableDatabase();
		List<Msg> list = new ArrayList<Msg>();
		String sql = "select * from MSGCenter where checkState=?";
		Cursor c = db.rawQuery(sql, new String[]{"0"});
		while(c.moveToNext()){
			list.add(new Msg( new MsgBean(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),
					c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10)), c.getString(11)));
		}
		c.close();
		db.close();
		return list;
	}
	//批量查询所有
	@Override
	public List<Msg> queryList() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getReadableDatabase();
		List<Msg> list = new ArrayList<Msg>();
		String sql = "select * from MSGCenter";
		Cursor c = db.rawQuery(sql, null);
		while(c.moveToNext()){
			list.add(new Msg( new MsgBean(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),
					c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10)), c.getString(11)));
		}
		c.close();db.close();
		return list;
	}

	//查询已读
	public List<Msg> queryListYidu() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getReadableDatabase();
		List<Msg> list = new ArrayList<Msg>();
		String sql = "select * from MSGCenter where checkState=?";
		Cursor c = db.rawQuery(sql, new String[]{"1"});
		while(c.moveToNext()){
			list.add(new Msg( new MsgBean(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),
					c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10)), c.getString(11)));
		}
		c.close();db.close();
		return list;
	}
	//查询未读
	public List<Msg> queryListWeidu() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getReadableDatabase();
		List<Msg> list = new ArrayList<Msg>();
		String sql = "select * from MSGCenter where checkState=?";
		Cursor c = db.rawQuery(sql, new String[]{"0"});
		while(c.moveToNext()){
			list.add(new Msg( new MsgBean(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),
					c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10)), c.getString(11)));
		}
		c.close();db.close();
		return list;
	}
	
	@Override
	public void insert(Msg m) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getWritableDatabase();
		MsgBean msg = m.getMsgBean();
		String sql = "insert into MSGCenter (informationCode, informationName,content, sourceId, source," +
				"infoCreateDate, userId, userName,sendState, sendTime,checkState) values (?,?,?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql, new String[]{msg.getInformationCode(),msg.getInformationName(),msg.getContent(),msg.getSourceId(),
				msg.getSource(),msg.getInfoCreateDate(),msg.getUserId(),msg.getUserName(),msg.getSendState(),msg.getSendTime(),m.getCheckState()});
		db.close();
	}

	//更新已读状态
	@Override
	public void update(Msg msg) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dh.getWritableDatabase();
		String sql = "update MSGCenter set checkState=? where informationCode=?";
		db.execSQL(sql, new String[]{msg.getCheckState(),msg.getMsgBean().getInformationCode()});
		db.close();
	}

	/*//删除某一条
	public boolean deleteByTime(Msg msg){
		SQLiteDatabase db = dh.getWritableDatabase();
		try {
			db.delete("MSGCenter", "sendTime=?", new String[]{msg.getMsgBean().getSendTime()});
			System.out.println("deletexxx"+msg.getMsgBean().getSendTime());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}finally{
			db.close();
		}
	}*/
	//清空已读消息
	public boolean clearTab(){
		SQLiteDatabase db = dh.getWritableDatabase();
		try {
			db.delete("MSGCenter", "checkState=?", new String[]{"1"});
			db.execSQL("delete from MSGCenter where checkState = 1");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}finally{
			db.close();
		}
	}

}
