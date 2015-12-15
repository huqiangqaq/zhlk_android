package com.enjoyor.soft.product.chacang;

/**
 *========================================
 * 说明：仓号选择实体类
 * 作者：胡团乐
 * 项目：zhlk_android
 * 时间：2014年10月14日 下午2:18:32
 *========================================
 */
public class ChooseNOBean {

	String storeNo;
	String storeName;
	
	public ChooseNOBean(String storeNo, String storeName) {
		super();
		this.storeNo = storeNo;
		this.storeName = storeName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}
