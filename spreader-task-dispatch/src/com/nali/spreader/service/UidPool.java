package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.data.User;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.util.CycleIterator;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

public class UidPool extends AbstractUidPool {//TODO innerPool和notlogin,anyone对应起来
	private static Logger logger = Logger.getLogger(UidPool.class);
	private LinkedHashMap<Long, ClientUid> clients = new LinkedHashMap<Long, UidPool.ClientUid>();//L1
	private Lock clientsLock = new ReentrantLock();
	private Lock sharedTaskLock = new ReentrantLock();
	private AtomicInteger fetchCount = new AtomicInteger();
	private int clientUidSize=10;
	private long clientExpiredTime=1000*60*20;
	@Autowired
	private IUidPoolService uidPoolService;

	private InnerPool currentInnerPool;
	private InnerPool nextInnderPool;
	private ThreadLocal<Long> batchIdLocal = new ThreadLocal<Long>();
	
	private boolean sayEnd;//是否log过任务结束了，避免大量日志出现
	
	@Override
	protected void init() {
		super.init();
		currentInnerPool = new InnerPool(priorityTasks, priorityNormalSize);
		nextInnderPool = new InnerPool(tasks, normalSize);
	}
	
	public List<ClientTask> getClientTask(Long clientId) {
		ClientUid clientUid;
		clientsLock.lock();
		try {
			clientUid = clients.remove(clientId);
			if(clientUid==null) {
				clientUid = new ClientUid(clientId);
			}
			clientUid.setLastTime(System.currentTimeMillis());
			clients.put(clientId, clientUid);
		} finally {
			clientsLock.unlock();
		}
		
		List<ClientTask> tasks = new ArrayList<ClientTask>();
		List<ItemCount<KeyValuePair<Long, Long>>> normal = clientUid.getNormal();//ASK noraml first or anyone first
		assignTasks(clientId, tasks, normal);
		sharedTaskLock.lock();
		try {
			List<ItemCount<Long>> notLogin;
			List<ItemCount<Long>> anyone;
			notLogin = getNotLogin();
			anyone = getAnyone();
			assignTasks(clientId, tasks, User.UID_NOT_LOGIN, notLogin, null);
			assignTasks(clientId, tasks, User.UID_ANYONE, anyone, clientUid);
		} finally {
			sharedTaskLock.unlock();
			clearBatchId();
		}
		//TODO handle empty list
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
	
	private void assignTasks(Long clientId, List<ClientTask> tasks, List<ItemCount<KeyValuePair<Long, Long>>> uidActionCounts) {
		for (ItemCount<KeyValuePair<Long, Long>> itemCount : uidActionCounts) {
			Integer count = itemCount.getCount();
			if(count==0) {
				continue;
			}
			KeyValuePair<Long, Long> uidAction = itemCount.getItem();
			Long uid = uidAction.getKey();
			Long actionId = uidAction.getValue();
			List<ClientTask> actionTasks = uidPoolService.assignTasks(getBatchId(clientId), uid, actionId, taskType.getId(), count);
			tasks.addAll(actionTasks);
			countPlus(actionTasks.size());
		}
	}

	private void assignTasks(Long clientId, List<ClientTask> tasks, Long uid, List<ItemCount<Long>> itemCounts, ClientUid clientUid) {
		CycleIterator<Long> uidIter = null;
		if(clientUid!=null) {//it's anyone task
			List<Long> uids = clientUid.getUids();
			if(uids==null || uids.size()==0) {
//				logger.error("not bind uids");//TODO fetch some uid?
				return;
			}
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

	public void bindUidToClient(ClientUid clientUid) {
		InnerPool currentInnerPool = this.currentInnerPool;
		boolean bindRlt = currentInnerPool.bindUidToClient(clientUid);
		if(bindRlt==true) {
			return;
		}
		clientsLock.lock();
		try {
			Iterator<Entry<Long, ClientUid>> it = clients.entrySet().iterator();
			if (it.hasNext()) {
				Entry<Long, ClientUid> entry = it.next();
				ClientUid old = entry.getValue();
				if(System.currentTimeMillis()-old.getLastTime()>clientExpiredTime) {
					clients.remove(entry.getKey());
					clientUid.receiveFrom(old);
					if(logger.isInfoEnabled()) {
						logger.info("receive uid to client:" + old.clientId + old.getUids() + "-->" + clientUid.clientId);
					}
					return;
				}
			}
		} finally {
			clientsLock.unlock();
		}
		boolean moved = moveToNextPool(currentInnerPool);//TODO handle linkpool
		if(moved) {
			clientUid.bindUid();
//			bindUidToClient(clientUid);
		} else {
			//TODO to the end
			if(sayEnd==false) {
				synchronized (this) {
					if(sayEnd==false) {
						logger.info("normal task pools are empty now:"+taskType.getId());//notlogin&anyone task maybe not empty
						sayEnd=true;
					}
				}
			}
		}
	}
	
	private boolean moveToNextPool(InnerPool currentInnerPool) {
		if(currentInnerPool!=this.currentInnerPool) {
			return true;
		}
		if(nextInnderPool==null) {
			return false;
		}
		clientsLock.lock();
		try {
			if(nextInnderPool!=null) {//ASK need synchronized??
				nextInnderPool.init(clients.values());
				this.currentInnerPool = nextInnderPool;
				nextInnderPool = null;
				return true;
			} else {
				return false;
			}
		} finally {
			clientsLock.unlock();
		}
	}

	private List<ItemCount<Long>> getAnyone() {
		if(priorityAnyoneAverage.hasNext()) {
			return priorityAnyoneAverage.next();
		} else if(anyoneAverage.hasNext()) {
			return anyoneAverage.next();
		} else {
			return Collections.emptyList();
		}
	}
	
	private List<ItemCount<Long>> getNotLogin() {
		if(priorityNotLoginAverage.hasNext()) {
			return priorityNotLoginAverage.next();
		} else if(notLoginAverage.hasNext()) {
			return notLoginAverage.next();
		} else {
			return Collections.emptyList();
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

	private class ClientUid {
		private List<Long> backupUids;
		private List<Long> uids;
		private Average<KeyValuePair<Long, Long>> uidActionAverage;
		private long lastTime;
		private InnerPool linkedPool;
		public final Long clientId;
		
		public ClientUid(ClientUid old) {
			this.clientId = old.clientId;
			this.backupUids = old.backupUids;
			this.uids = old.uids;
			this.lastTime = old.lastTime;
		}

		public ClientUid(Long clientId) {
			this.clientId = clientId;
		}

		public void receiveFrom(ClientUid old) {
			if(old.linkedPool!=null) {//ASK this.linkedPool!=old.linkedPool???
				if(old.backupUids!=null) {
					old.linkedPool.removeReserve(old.backupUids);
				}
			}
			bind(old.uids, old.uidActionAverage);
		}
		
		public void bind(List<Long> uids, Average<KeyValuePair<Long, Long>> uidActionAverage) {
			if(this.uids!=null) {
				backupUids = getMergedUids();
				if(backupUids.size()>clientUidSize) {
					if(linkedPool!=null) {
						linkedPool.removeReserve(backupUids.subList(clientUidSize, backupUids.size()));
					}
					backupUids = backupUids.subList(0, clientUidSize);
				}
			}
			this.uids = uids;
			this.uidActionAverage = uidActionAverage;
		}
		
		public List<Long> getMergedUids() {
			List<Long> list1=uids;
			if(list1==null) {
				list1 = Collections.emptyList();
			}
			List<Long> list2=backupUids;
			if(list2==null) {
				list2 = Collections.emptyList();
			}
			List<Long> rlt = new ArrayList<Long>(list1.size()+list2.size());
			rlt.addAll(list1);
			rlt.addAll(list2);
			return rlt;
		}
		
		private boolean hasNext() {
			return uidActionAverage!=null && uidActionAverage.hasNext();
		}

		public synchronized List<ItemCount<KeyValuePair<Long, Long>>> getNormal() {
			if(!hasNext()) {
				bindUid();
			}
			if(!hasNext()) {
				return Collections.emptyList();
			}
			return uidActionAverage.next();
		}

		private void bindUid() {
			if(linkedPool!=null) {
				List<Long> mergedUids = getMergedUids();
				Average<KeyValuePair<Long, Long>> actionAverage = linkedPool.fetchReserveAction(mergedUids);
				uids = mergedUids;
				backupUids = null;
				uidActionAverage = actionAverage;
				linkedPool=null;
				if(uidActionAverage.hasNext()) {
					return;
				}
			}
			bindUidToClient(this);
		}

		public long getLastTime() {
			return lastTime;
		}

		public void setLastTime(long lastTime) {
			this.lastTime = lastTime;
		}

		public List<Long> getUids() {
			if(uids==null) {
				return Collections.emptyList();
			}
			return uids;
		}

		public void linkToPool(InnerPool linkedPool) {
			this.linkedPool = linkedPool;
		}

	}
	
	private class InnerPool {
		private Lock selfLock = new ReentrantLock();
		private boolean inited=false;
		private Map<Long, List<ItemCount<Long>>> normalTasks;
		private Map<Long, List<ItemCount<Long>>> reservedTasks;
		private int batchSize;
		
		public InnerPool(Map<Long, List<ItemCount<Long>>> normalTasks, int batchSize) {
			super();
			this.normalTasks = normalTasks;
			this.batchSize = batchSize;
			reservedTasks = new HashMap<Long, List<ItemCount<Long>>>();
		}
		
		public void init(Collection<ClientUid> clients) {
			selfLock.lock();//ASK 堵塞？
			//TODO lock something
			try {
				for (ClientUid client : clients) {
					List<Long> uids = client.getMergedUids();
					if(!uids.isEmpty()) {//TODO dodododo
						for (Long uid : uids) {
							List<ItemCount<Long>> userActions = normalTasks.remove(uid);
							if(userActions!=null) {
								reservedTasks.put(uid, userActions);
							}
						}
						client.linkToPool(this);
					}
					
					//map
				}
				//check backupuids
				//check uids
				inited=true;
			} finally {
				selfLock.unlock();
			}
		}

		public void removeReserve(List<Long> uids) {
			selfLock.lock();
			try {
				for (Long uid : uids) {
					List<ItemCount<Long>> userActions = reservedTasks.remove(uid);
					if(userActions!=null) {
						normalTasks.put(uid, userActions);
					}
				}
			} finally {
				selfLock.unlock();
			}
		}
		
		public Average<KeyValuePair<Long, Long>> fetchReserveAction(List<Long> uids) {
			selfLock.lock();
			try {
				List<ItemCount<KeyValuePair<Long, Long>>> uidActions = new ArrayList<ItemCount<KeyValuePair<Long, Long>>>();
				for (Long uid : uids) {
					List<ItemCount<Long>> actionCounts = reservedTasks.remove(uid);
					if(actionCounts!=null) {
						for (ItemCount<Long> actionCount : actionCounts) {
							KeyValuePair<Long, Long> item = new KeyValuePair<Long, Long>(uid, actionCount.getItem());
							ItemCount<KeyValuePair<Long, Long>> itemCount = new ItemCount<KeyValuePair<Long,Long>>(actionCount.getCount(), item);
							uidActions.add(itemCount);
						}
					}
				}
				return Average.startFromBatchSize(uidActions, batchSize);
			} finally {
				selfLock.unlock();
			}
		}
		
		public boolean bindUidToClient(ClientUid clientUid) {
			if(!inited) {
				throw new IllegalStateException("cannot be use before inited");
			}
			selfLock.lock();
			try {
				Iterator<Entry<Long, List<ItemCount<Long>>>> tasksIterator = normalTasks.entrySet().iterator();
				if (tasksIterator.hasNext()) {
					List<ItemCount<KeyValuePair<Long, Long>>> uidActions = new ArrayList<ItemCount<KeyValuePair<Long, Long>>>();
					List<Long> uids = new ArrayList<Long>(clientUidSize);
					for (int i = 0; i < clientUidSize; i++) {
						if (tasksIterator.hasNext()) {
							Entry<Long, List<ItemCount<Long>>> entry = tasksIterator.next();
							tasksIterator.remove();
							Long uid = entry.getKey();
							List<ItemCount<Long>> actionCounts = entry.getValue();
							uids.add(uid);
							for (ItemCount<Long> actionCount : actionCounts) {
								KeyValuePair<Long, Long> item = new KeyValuePair<Long, Long>(uid, actionCount.getItem());
								ItemCount<KeyValuePair<Long, Long>> itemCount = new ItemCount<KeyValuePair<Long,Long>>(actionCount.getCount(), item);
								uidActions.add(itemCount);
							}
						} else {
							break;
						}
					}
					Average<KeyValuePair<Long, Long>> uidActionAverage=Average.startFromBatchSize(uidActions, batchSize);
					clientUid.bind(uids, uidActionAverage);
					if(logger.isInfoEnabled()) {
						logger.info("bind uid to client:" + uids + "-->" + clientUid.clientId);
					}
					return true;
				}
				return false;
			} finally {
				selfLock.unlock();
			}
		}
	}
	
	public void bindTaskToClients(UidPool old) {
		//TODO 会丢失客户端最近登录情况
		Collection<ClientUid> oldClients = old.clients.values();
		for (ClientUid clientUid : oldClients) {
			Long clientId = clientUid.clientId;
			ClientUid newClientUid = new ClientUid(clientUid);
			clients.put(clientId, newClientUid);
		}
		currentInnerPool.init(clients.values());
	}
	
//	private enum Status {
//		priority,
//		normal,
//		none;
//	}
}
