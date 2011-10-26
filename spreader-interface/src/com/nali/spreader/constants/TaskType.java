package com.nali.spreader.constants;

public enum TaskType {//TODO
	weiboNormal(101, 30, 1),//TODO 1->20
	weiboRegister(102, 80, 1),
	;
	private Integer id;
	//front
	private int passiveFetchPercent;
	private int passiveFetchSize;
	//backend
	
	private TaskType(Integer id, int passiveFetchPercent, int passiveFetchSize) {
		this.id = id;
		this.passiveFetchPercent = passiveFetchPercent;
		this.passiveFetchSize = passiveFetchSize;
	}
	public Integer getId() {
		return id;
	}
	public int getPassiveFetchPercent() {
		return passiveFetchPercent;
	}
	public int getPassiveFetchSize() {
		return passiveFetchSize;
	}
	public String getPassiveTaskReceiverBeanName() {
		return name() + "PassiveTaskReceiver";
	}
	public String getPassiveTaskSenderBeanName() {
		return name() + "PassiveTaskSender";
	}
}
