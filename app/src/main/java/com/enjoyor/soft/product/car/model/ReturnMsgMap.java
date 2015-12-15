package com.enjoyor.soft.product.car.model;

public class ReturnMsgMap{

	private String returnAjaxState;
	private String msg;

	public ReturnMsgMap(String returnAjaxState, String msg) {
		super();
		this.returnAjaxState = returnAjaxState;
		this.msg = msg;
	}

	public ReturnMsgMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getReturnAjaxState() {
		return returnAjaxState;
	}

	public void setReturnAjaxState(String returnAjaxState) {
		this.returnAjaxState = returnAjaxState;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ReturnMsgMap [returnAjaxState=" + returnAjaxState + ", msg="
				+ msg + "]";
	}
	
}
