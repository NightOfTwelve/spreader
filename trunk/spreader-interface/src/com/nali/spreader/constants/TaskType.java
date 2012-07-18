package com.nali.spreader.constants;

public enum TaskType {//TODO @see Channel
	weiboNormal(101, 20),
	weiboRegister(102, 1, 50),
	weiboInstant(103, 1),
	weiboFetch(104, 10),
	
	appNormal(201, 1, 50),
	;
	private Integer id;
	//front
	private int passiveFetchSize;
	private int minPriorityTaskCount;
	//backend
	
	private TaskType(Integer id, int passiveFetchSize) {
		this(id, passiveFetchSize, 300);
	}

	private TaskType(Integer id, int passiveFetchSize, int minPriorityTaskCount) {
		this.id = id;
		this.passiveFetchSize = passiveFetchSize;
		this.minPriorityTaskCount = minPriorityTaskCount;
	}
	public int getFetchSize() {//TODO
		return passiveFetchSize;
	}
	public Integer getId() {
		return id;
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
	public int getMinPriorityTaskCount() {
		return minPriorityTaskCount;
	}
}
