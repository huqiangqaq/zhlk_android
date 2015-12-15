package com.enjoyor.soft.product.car.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enjoyor.soft.product.car.model.CarInfoClient;
import com.enjoyor.soft.product.car.dao.DBService;

public class CarService implements DBService<CarInfoClient>{
	private DBOpenHelper dh;
	public CarService(Context context){
		dh = new DBOpenHelper(context);
	}
	//单个查询
	@Override
	public CarInfoClient query(String carCode){
		SQLiteDatabase db = dh.getReadableDatabase();
		CarInfoClient cic = null;
		Cursor c = db.rawQuery("select * from storeStatus where carCode=?", new String[]{carCode});
		if(c.moveToNext()){
			cic = new CarInfoClient(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9),c.getString(10),c.getString(11));
		}
		c.close();
		db.close();
		return cic;
	}
	//批量查询
	public List<CarInfoClient> queryList(){
		List<CarInfoClient> cics = new ArrayList<CarInfoClient>();
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor c = db.rawQuery("select * from storeStatus",null);
		System.out.println("批量查询");
		while(c.moveToNext()){
			cics.add(new CarInfoClient(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10),c.getString(11)));
		}
		c.close();
		db.close();
		return cics;
	}
	//插入
	public void insert(CarInfoClient cic){
		SQLiteDatabase db = dh.getWritableDatabase();
		db.execSQL("insert into storeStatus (carCode, itemName, storehouseName, type, status, workflowId, uri, storehouseCode, code,rfidCode,doornumber) values (?,?,?,?,?,?,?,?,?,?,?)",
				new String[]{cic.getCarCode(), cic.getItemName(), cic.getStorehouseName(), cic.getType(), cic.getStatus(), cic.getWorkflowId(), cic.getUri(), cic.getStorehouseCode(), cic.getCode(), cic.getRfidCode(), cic.getDoorNumber()});
		db.close();

		Log.i("five", "插入成功");
	}
	//更新
	public void update(CarInfoClient cic){
		SQLiteDatabase db = dh.getWritableDatabase();
		db.execSQL("update storeStatus set status=?,doornumber=?"
				+ "where rfidCode=?",
				new String[]{cic.getStatus(),cic.getDoorNumber(),cic.getRfidCode()});
		db.close();
		Log.i("five", "更新完成");
	}


	//删除 已完成 状态的数据
	public void del(){
		SQLiteDatabase db = dh.getWritableDatabase();
		db.execSQL("delete from storeStatus where status in (?)", new String[]{"4"});
		db.close();
		Log.i("five", "删除完成");
	}
	//清空数据
	public void clearList(){
		SQLiteDatabase db = dh.getWritableDatabase();
//		db.execSQL("delete from storeStatus ", null);
		db.delete("storeStatus", null, null);
		db.close();
	}
}
