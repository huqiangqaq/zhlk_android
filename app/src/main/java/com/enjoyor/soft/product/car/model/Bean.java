package com.enjoyor.soft.product.car.model;


/** 
 * function: 车辆bean实体类
 * @author   hutuanle
 * @contact  qq:354777464
 * @mail     androidhtl@163.com
 * 2014年7月12日   下午3:02:32	
 */
public class Bean{
	private String carCode;
	private String code;
	private String companyName;
	private String createDate;
	private String deleted;
	private String doorNumber;
	private String endDate;
	private String id;
	private String itemName;
	private String noticeCode;
	private String receivePerson;
	private String startDate;
	private String status;
	private String storehouseCode;
	private String storehouseName;
	private String type;
	private String updateDate;

	private String workflowId;
	
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getDoorNumber() {
		return doorNumber;
	}
	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}
	public String getReceivePerson() {
		return receivePerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public Bean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStorehouseCode() {
		return storehouseCode;
	}
	public void setStorehouseCode(String storehouseCode) {
		this.storehouseCode = storehouseCode;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public String toString() {
		return "Bean [carCode=" + carCode + ", code=" + code + ", companyName="
				+ companyName + ", createDate=" + createDate + ", deleted="
				+ deleted + ", endDate=" + endDate + ", id=" + id
				+ ", itemName=" + itemName + ", noticeCode=" + noticeCode
				+ ", startDate=" + startDate + ", status=" + status
				+ ", storehouseCode=" + storehouseCode + ", storehouseName="
				+ storehouseName + ", type=" + type + ", updateDate="
				+ updateDate + "]";
	}
	
	
}
