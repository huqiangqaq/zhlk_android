package com.enjoyor.soft.product.wenshidu.model;

public class Bean {
	/**
	 * 温度计code
	 */
	private String code;
	/**
	 * 注释
	 */
	private String comments;
	/**
	 * 创建日期
	 */
	private String createDate;
	private String deleted;
	/**
	 * 数据库里的id
	 */
	private String id;
	/**
	 * 仓库code
	 */
	private String storeCode;
	/**
	 * 仓库名称
	 */
	private String storeName;
	
	
	public Bean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bean(String code, String comments, String createDate,
			String deleted, String id, String storeCode, String storeName) {
		super();
		this.code = code;
		this.comments = comments;
		this.createDate = createDate;
		this.deleted = deleted;
		this.id = id;
		this.storeCode = storeCode;
		this.storeName = storeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStoreCode() {
		return storeCode;
	}


	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	@Override
	public String toString() {
		return "Bean [code=" + code + ", comments=" + comments
				+ ", createDate=" + createDate + ", deleted=" + deleted
				+ ", id=" + id + ", storeCode=" + storeCode + ", storeName="
				+ storeName + "]";
	}
	
}
