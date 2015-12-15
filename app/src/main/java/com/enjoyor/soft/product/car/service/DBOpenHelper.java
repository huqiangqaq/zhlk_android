package com.enjoyor.soft.product.car.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static String DBNAME = "zhlk_store";
	private static int version = 1;
	
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS storeStatus (carId Integer primary key autoincrement," +
				"carCode varchar,itemName varchar,storehouseName varchar,type varchar,status varchar,workflowId varchar," +
				"uri varchar,storehouseCode varchar,code varchar,rfidCode varchar,doornumber varchar)");
		db.execSQL("CREATE TABLE IF NOT EXISTS MSGCenter (msgId Integer primary key autoincrement,informationCode varchar," +
				"informationName varchar,content varchar,sourceId varchar,source varchar,infoCreateDate varchar,userId varchar," +
				"userName varchar,sendState varchar,sendTime varchar,checkState varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
