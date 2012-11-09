package com.nali.spreader.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.data.User;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.pool.ActiveClient.AssignedTasks;
import com.nali.spreader.service.IUidPoolService;
import com.nali.spreader.util.CycleIterator;
import com.nali.spreader.util.OffReadWriteLock;
import com.nali.spreader.util.avg.ItemCount;

public class UidPool extends AbstractUidPool {
	private static Logger logger = Logger.getLogger(UidPool.class);
	private OffReadWriteLock offLock = new OffReadWriteLock();
	private AtomicInteger fetchCount = new AtomicInteger();
	private long clientExpiredTime=1000*60*30;
	private ChannelConfig config;//TODO
	private UidBackupPool backupPool;
	private ConcurrentHashMap<Long, ActiveClient> activeClients = new ConcurrentHashMap<Long, ActiveClient>();
	private UidPoolStage priorityStage;
	private UidPoolStage normalStage;
	
	@Autowired
	private IUidPoolService uidPoolService;
	private ThreadLocal<Long> batchIdLocal = new ThreadLocal<Long>();
	
	public List<ClientTask> getClientTask(Long clientId) {
		ActiveClient client;
		client = activeClients.get(clientId);
		if(client==null) {
			client = new ActiveClient(clientId, new UsedUids(), config, offLock);
			client.bindStage(priorityStage);
			ActiveClient exists = activeClients.putIfAbsent(clientId, client);
			if(exists!=null) {
				client=exists;
			}
		}
		AssignedTasks assignedTasks = client.assignTasks();
		if(assignedTasks==null) {
			return Collections.emptyList();
		}
		List<ClientTask> tasks = new ArrayList<ClientTask>();
		try {
			assignTasks(clientId, tasks, assignedTasks.normal);
			assignTasks(clientId, tasks, User.UID_NOT_LOGIN, assignedTasks.notLogin, null);
			if(assignedTasks.anyone.size()>0) {
				if(assignedTasks.normal.size()>0) {
					Set<Long> uids = new HashSet<Long>();
					for (ItemCount<UidAction> uidActionCount : assignedTasks.normal) {
						uids.add(uidActionCount.getItem().getUid());
					}
					assignTasks(clientId, tasks, User.UID_ANYONE, assignedTasks.anyone, uids);
				} else {
					logger.error("not bind uids");//TODO fetch some uid?
				}
			}
		} finally {
			clearBatchId();
		}
		client.setLastTime(System.currentTimeMillis());
		return tasks;
	}

	private void clearBatchId() {
		batchIdLocal.remove();
	}
	
	private Long getBatchId(Long clientId) {
		Long batchId = batchIdLocal.get();
		if(batchId==null) {
			batchId = uidPoolService.getBatchId(taskType.getId(), clientId);
			batchIdLocal.set(batchId);
		}
		return batchId;
	}
	
	private void assignTasks(Long clientId, List<ClientTask> tasks, List<ItemCount<UidAction>> normal) {
		for (ItemCount<UidAction> itemCount : normal) {
			Integer count = itemCount.getCount();
			if(count==0) {
				continue;
			}
			UidAction uidAction = itemCount.getItem();
			Long uid = uidAction.getUid();
			Long actionId = uidAction.getActionId();
			List<ClientTask> actionTasks = uidPoolService.assignTasks(getBatchId(clientId), uid, actionId, taskType.getId(), count);
			tasks.addAll(actionTasks);
			countPlus(actionTasks.size());
		}
	}

	private void assignTasks(Long clientId, List<ClientTask> tasks, Long uid, List<ItemCount<Long>> itemCounts, Set<Long> uids) {
		CycleIterator<Long> uidIter = null;
		if(uids!=null) {//it's anyone task
			uidIter = new CycleIterator<Long>(uids);
		}
		for (ItemCount<Long> itemCount : itemCounts) {
			Integer count = itemCount.getCount();
			if(count==0) {
				continue;
			}
			List<ClientTask> actionTasks = uidPoolService.assignTasks(getBatchId(clientId), uid, itemCount.getItem(), taskType.getId(), count);
			if(uidIter!=null) {
				for (ClientTask actionTask : actionTasks) {
					actionTask.setUid(uidIter.next());
				}
			}
			tasks.addAll(actionTasks);
			countPlus(actionTasks.size());
		}
	}

	public int getFetchCount() {
		return fetchCount.intValue();
	}
	
	protected void countPlus(int count) {
		if(count==0) {
			return;
		}
		fetchCount.addAndGet(count);
	}
	
	public void initForOrigin(UidBackupPool backupPool, ChannelConfig channelConfig) {
		config = channelConfig;
		this.backupPool = backupPool;
		init();
	}
	
	public void initFromOld(UidPool old, ChannelConfig channelConfig) {
		config = channelConfig;
		old.freeze();
		backupPool = old.backupPool;
		long expireTime = System.currentTimeMillis() - clientExpiredTime;
		for (Entry<Long, ActiveClient> entry : old.activeClients.entrySet()) {
			Long clientId = entry.getKey();
			ActiveClient oldClient = entry.getValue();
			if(oldClient.getLastTime()<expireTime) {
				UsedUids usedUids = oldClient.getUsedUids();
				if(usedUids.popReserveBacks().size()!=0) {//for check
					logger.error("still having reserve!!");//因为刷新时间远小于过期时间，所以到这里早就刷新了
				}
				Set<Long> uids = usedUids.getUids();
				for (Long uid : uids) {
					backupPool.goInto(clientId, uid);//TODO add expire time
				}
			} else {
				activeClients.put(clientId, new ActiveClient(oldClient, config, offLock));//会丢失last time
			}
		}
		init();
	}
	
	private void init() {
		long expireTime = System.currentTimeMillis() - clientExpiredTime;
		priorityStage = new UidPoolStage(backupPool, expireTime, priorityNormalSize, priorityTasks,
				priorityAnyoneAverage, priorityNotLoginAverage, activeClients);
		normalStage = new UidPoolStage(backupPool, expireTime, normalSize, tasks,
				anyoneAverage, notLoginAverage, activeClients);
		priorityStage.setNextStage(normalStage);
		priorityStage.init(true);
		for (ActiveClient c : activeClients.values()) {//TODO foreach activeClients 2 times
			c.bindStage(priorityStage);
		}
	}

	private void freeze() {
		offLock.turnOff();
	}
	
	public String peek(Long clientId) {//for check
		boolean effect = offLock.lockWrite();
		if(effect==false) {
			return "shut downing:" + hashCode();
		}
		try {
			if(clientId==null) {
				StringBuilder sb = new StringBuilder("clientId\tusingUids\treserveBacks");
				for(Entry<Long, ActiveClient> entry : activeClients.entrySet()) {
					sb.append("\r\n").append(entry.getKey())
					.append('\t').append(toString(entry.getValue().getUsedUids()));
				}
				sb.append("\r\n\r\nbackup:\r\n").append(backupPool);
				sb.append("\r\n\r\nprior:\r\n")
					.append(priorityStage.peekStage()).append("\r\n")
					.append(priorityStage.peekFree()).append("\r\n")
					.append(priorityStage.peekWeak()).append("\r\n");
				sb.append("\r\nnormal:\r\n")
				.append(normalStage.peekStage()).append("\r\n")
				.append(normalStage.peekFree()).append("\r\n")
				.append(normalStage.peekWeak()).append("\r\n");
				return sb.toString();
			} else {
				ActiveClient activeClient = activeClients.get(clientId);
				return toString(activeClient.getUsedUids());
			}
		} finally {
			offLock.unlockWrite();
		}
	}

	private String toString(UsedUids usedUids) {
		return usedUids.getUids()+"\t"+usedUids.getReserveBacks();
	}
	
}
