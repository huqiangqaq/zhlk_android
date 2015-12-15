package com.enjoyor.soft.product.megcenter.model;

public class MsgBean {

	/**消息code*/
	private String informationCode;
	/**消息name*/
	private String informationName;
	/**消息content*/
	private String content;
	/**消息发起人id*/
	private String sourceId;
	/**消息发起人*/
	private String source;
	/**消息发起时间*/
	private String infoCreateDate;
	/**用户id*/
	private String userId;
	/**用户name*/
	private String userName;
	/**发送状态*/
	private String sendState;
	/**发送时间*/
	private String sendTime;
	
	
	public MsgBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MsgBean(String informationCode, String informationName,
			String content, String sourceId, String source,
			String infoCreateDate, String userId, String userName,
			String sendState, String sendTime) {
		super();
		this.informationCode = informationCode;
		this.informationName = informationName;
		this.content = content;
		this.sourceId = sourceId;
		this.source = source;
		this.infoCreateDate = infoCreateDate;
		this.userId = userId;
		this.userName = userName;
		this.sendState = sendState;
		this.sendTime = sendTime;
	}
	public String getInformationCode() {
		return informationCode;
	}
	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}
	public String getInformationName() {
		return informationName;
	}
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInfoCreateDate() {
		return infoCreateDate;
	}
	public void setInfoCreateDate(String infoCreateDate) {
		this.infoCreateDate = infoCreateDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "MsgBean [informationCode=" + informationCode
				+ ", informationName=" + informationName + ", content="
				+ content + ", sourceId=" + sourceId + ", source=" + source
				+ ", infoCreateDate=" + infoCreateDate + ", userId=" + userId
				+ ", userName=" + userName + ", sendState=" + sendState
				+ ", sendTime=" + sendTime + "]";
	}
	
	
	
}
