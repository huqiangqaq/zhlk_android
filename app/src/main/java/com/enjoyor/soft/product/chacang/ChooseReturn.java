package com.enjoyor.soft.product.chacang;

import java.util.Map;

public class ChooseReturn {

	ReturnMsgMap returnMsgMap;
	Map<String, String> storeHouseMap;
	
	public ReturnMsgMap getReturnMsgMap() {
		return returnMsgMap;
	}
	public void setReturnMsgMap(ReturnMsgMap returnMsgMap) {
		this.returnMsgMap = returnMsgMap;
	}
	public Map<String, String> getStoreHouseMap() {
		return storeHouseMap;
	}
	public void setStoreHouseMap(Map<String, String> storeHouseMap) {
		this.storeHouseMap = storeHouseMap;
	}
	
}
class ReturnMsgMap{
	String returnAjaxState;

	public String getReturnAjaxState() {
		return returnAjaxState;
	}

	public void setReturnAjaxState(String returnAjaxState) {
		this.returnAjaxState = returnAjaxState;
	}
}
