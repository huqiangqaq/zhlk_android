package com.enjoyor.soft.product.wenshidu.model;

import com.enjoyor.soft.product.car.model.ReturnMsgMap;


public class ScanBean {
	Bean bean;
	String code;
	String deleteIds;
	String gridResult;
	String likeResult;
	ReturnMsgMap returnMsgMap;
	String storename;
	public ScanBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScanBean(Bean bean, String code, String deleteIds,
			String gridResult, String likeResult, ReturnMsgMap returnMsgMap,
			String storename) {
		super();
		this.bean = bean;
		this.code = code;
		this.deleteIds = deleteIds;
		this.gridResult = gridResult;
		this.likeResult = likeResult;
		this.returnMsgMap = returnMsgMap;
		this.storename = storename;
	}
	public Bean getBean() {
		return bean;
	}
	public void setBean(Bean bean) {
		this.bean = bean;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeleteIds() {
		return deleteIds;
	}
	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
	public String getGridResult() {
		return gridResult;
	}
	public void setGridResult(String gridResult) {
		this.gridResult = gridResult;
	}
	public String getLikeResult() {
		return likeResult;
	}
	public void setLikeResult(String likeResult) {
		this.likeResult = likeResult;
	}
	public ReturnMsgMap getReturnMsgMap() {
		return returnMsgMap;
	}
	public void setReturnMsgMap(ReturnMsgMap returnMsgMap) {
		this.returnMsgMap = returnMsgMap;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	@Override
	public String toString() {
		return "ScanBean [bean=" + bean + ", code=" + code + ", deleteIds="
				+ deleteIds + ", gridResult=" + gridResult + ", likeResult="
				+ likeResult + ", returnMsgMap=" + returnMsgMap
				+ ", storename=" + storename + "]";
	}

	
	
	
}
