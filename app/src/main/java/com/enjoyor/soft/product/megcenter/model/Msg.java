package com.enjoyor.soft.product.megcenter.model;


public class Msg {
	/**消息bean*/
	private MsgBean msgBean;
	/**消息是否查看过0未查看 1查看过*/
	private String checkState;
	public Msg() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Msg(MsgBean msgBean, String checkState) {
		super();
		this.msgBean = msgBean;
		this.checkState = checkState;
	}
	public MsgBean getMsgBean() {
		return msgBean;
	}
	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	@Override
	public String toString() {
		return "Msg [msgBean=" + msgBean + ", checkState=" + checkState + "]";
	}
	
	
}
