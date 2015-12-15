package com.enjoyor.soft.product.car.model;
/**
 * 车辆即将 装卸货时 返回的详细信息
 *@author hutuanle
 * 2013-6-25上午10:55:08
 */
public class JsonReturn {

	private Bean bean;
	private String deleteIds;
	private String gridResult;
	private String likeStr;
	private ReturnMsgMap returnMsgMap;
	private String rfidCode;
	private String status;
	private String type;
	private String url;
	/*public JsonReturn(Bean bean, String deleteIds, String gridResult,
			String likeStr, ReturnMsgMap returnMsgMap, String rfidCode,
			String status, String type, String url, String workflowId) {
		super();
		this.bean = bean;
		this.deleteIds = deleteIds;
		this.gridResult = gridResult;
		this.likeStr = likeStr;
		this.returnMsgMap = returnMsgMap;
		this.rfidCode = rfidCode;
		this.status = status;
		this.type = type;
		this.url = url;
		this.workflowId = workflowId;
	}*/
	public JsonReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bean getBean() {
		return bean;
	}
	public void setBean(Bean bean) {
		this.bean = bean;
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
	public String getLikeStr() {
		return likeStr;
	}
	public void setLikeStr(String likeStr) {
		this.likeStr = likeStr;
	}
	public ReturnMsgMap getReturnMsgMap() {
		return returnMsgMap;
	}
	public void setReturnMsgMap(ReturnMsgMap returnMsgMap) {
		this.returnMsgMap = returnMsgMap;
	}
	public String getRfidCode() {
		return rfidCode;
	}
	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
