package com.nali.spreader.constants;

public enum TaskType {//TODO @see Channel
	//一般来说服务端fetchsize等于客户端(取任务缓存，线程数，线程池缓存)相加再加1
	weiboNormal(101, 12, 3, 100, 1000),//客户端配置 1, 5, 5 (取任务缓存，线程数，线程池缓存)
	weiboRegister(102, 1),
	weiboInstant(103, 1),
	weiboFetch(104, 22, 5, 300, 3000),//客户端配置 1, 10, 10 (取任务缓存，线程数，线程池缓存)
	
	appNormal(201, 1),
	appSlow(202, 1, 3, 50, 50),
	;
	private Integer id;
	//front
	private int passiveFetchSize;
	private int uidSize;
	private int minPriorityTaskCount;
	private int uidBackupPoolSize;
	//backend
	
	private TaskType(Integer id, int passiveFetchSize) {
		this(id, passiveFetchSize, 5, 50, 500);
	}

	private TaskType(Integer id, int passiveFetchSize, int uidSize, int minPriorityTaskCount, int uidBackupPoolSize) {
		this.id = id;
		this.passiveFetchSize = passiveFetchSize;
		this.uidSize = uidSize;
		this.minPriorityTaskCount = minPriorityTaskCount;
		this.uidBackupPoolSize = uidBackupPoolSize;
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

	public int getUidSize() {
		return uidSize;
	}

	public int getUidBackupPoolSize() {
		return uidBackupPoolSize;
	}
}
