package com.enjoyor.soft.product.megcenter.model;

import java.util.List;

import com.enjoyor.soft.product.car.model.ReturnMsgMap;


public class MsgJsonReturn {

	private String bean;
	private String deleteIds;
	private String gridResult;
	private String likeStr;
	private List<MsgBean> list;
	private ReturnMsgMap returnMsgMap;
	private String state;
	private String userId;
	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
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
	public List<MsgBean> getList() {
		return list;
	}
	public void setList(List<MsgBean> list) {
		this.list = list;
	}
	public ReturnMsgMap getReturnMsgMap() {
		return returnMsgMap;
	}
	public void setReturnMsgMap(ReturnMsgMap returnMsgMap) {
		this.returnMsgMap = returnMsgMap;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public MsgJsonReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MsgJsonReturn(String bean, String deleteIds, String gridResult,
			String likeStr, List<MsgBean> list, ReturnMsgMap returnMsgMap,
			String state, String userId) {
		super();
		this.bean = bean;
		this.deleteIds = deleteIds;
		this.gridResult = gridResult;
		this.likeStr = likeStr;
		this.list = list;
		this.returnMsgMap = returnMsgMap;
		this.state = state;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "MsgJsonReturn [bean=" + bean + ", deleteIds=" + deleteIds
				+ ", gridResult=" + gridResult + ", likeStr=" + likeStr
				+ ", list=" + list + ", returnMsgMap=" + returnMsgMap
				+ ", state=" + state + ", userId=" + userId + "]";
	}
	
	
	
}
