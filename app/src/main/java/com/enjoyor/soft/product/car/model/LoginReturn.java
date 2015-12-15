package com.enjoyor.soft.product.car.model;


public class LoginReturn {

	String fileUUid;
	ReturnMsgMap returnMsgMap;
	
	public LoginReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginReturn(String fileUUid, ReturnMsgMap returnMsgMap) {
		super();
		this.fileUUid = fileUUid;
		this.returnMsgMap = returnMsgMap;
	}
	public String getFileUUid() {
		return fileUUid;
	}
	public void setFileUUid(String fileUUid) {
		this.fileUUid = fileUUid;
	}
	public ReturnMsgMap getReturnMsgMap() {
		return returnMsgMap;
	}
	public void setReturnMsgMap(ReturnMsgMap returnMsgMap) {
		this.returnMsgMap = returnMsgMap;
	}
	@Override
	public String toString() {
		return "LoginReturn [fileUUid=" + fileUUid + ", returnMsgMap="
				+ returnMsgMap + "]";
	}
	
	
}
