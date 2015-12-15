package com.enjoyor.soft.product.car.model;

import java.io.Serializable;

/**
 * 货车信息
 * @author hutuanle
 *
 */
public class CarInfoClient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 车牌号
	 */
	private String carCode; 
	/**
	 *  品种
	 */
	private String itemName;
	/**
	 * 仓库号
	 */
	private String storehouseName;
	/**
	 *  0采购 1销售
	 */
	private String type;
	/**
	 * 0采购未卸货 	1采购卸货中 2采购卸货完成
	 * 
	 * 3销售未装货 	4销售装货中 5销售装货完成
	 */
	private String status;
	/**
	 * 流程id
	 */
	private String workflowId;
	/**
	 * 提交状态路径
	 */
	private String uri;
	/**
	 * 仓库编号
	 */
	private String storehouseCode;
	/**
	 * 卸货单号
	 */
	private String code;
	/**
	 * rfid code
	 */
	private String rfidCode;
	
	private String doorNumber;
	
	public String getDoorNumber() {
		return doorNumber;
	}
	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}
	
	public CarInfoClient(String carCode, String itemName,
			String storehouseName, String type, String status,
			String workflowId, String uri, String storehouseCode, String code,
			String rfidCode, String doorNumber) {
		super();
		this.carCode = carCode;
		this.itemName = itemName;
		this.storehouseName = storehouseName;
		this.type = type;
		this.status = status;
		this.workflowId = workflowId;
		this.uri = uri;
		this.storehouseCode = storehouseCode;
		this.code = code;
		this.rfidCode = rfidCode;
		this.doorNumber = doorNumber;
	}
	public String getRfidCode() {
		return rfidCode;
	}
	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}
	public CarInfoClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getStorehouseCode() {
		return storehouseCode;
	}

	public void setStorehouseCode(String storehouseCode) {
		this.storehouseCode = storehouseCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}



	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	@Override
	public String toString() {
		return "CarInfoClient [carCode=" + carCode + ", itemName=" + itemName
				+ ", storehouseName=" + storehouseName + ", type=" + type
				+ ", status=" + status + ", workflowId=" + workflowId
				+ ", uri=" + uri + ", storehouseCode=" + storehouseCode
				+ ", code=" + code + ", rfidCode=" + rfidCode + "]";
	}

	
	
	
}
