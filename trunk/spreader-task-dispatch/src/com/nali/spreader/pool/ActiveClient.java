package com.nali.spreader.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.nali.spreader.util.OffReadWriteLock;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

public class ActiveClient {
	private static Logger logger = Logger.getLogger(ActiveClient.class);
	private static final Iterator<UidActionCount> EMPTY_ITERATOR = Collections.<UidActionCount>emptyList().iterator();
	private final ChannelConfig config;
	private Long clientId;
	private long lastTime;
	private UsedUids usedUids;
	private OffReadWriteLock offReadWriteLock;
	
	private UidPoolStage linkedStage;
	private Iterator<UidActionCount> boundUidActionCount;
	private Iterator<UidActionCount> reserveUidActionCount;
	private Average<UidAction> uidActionAverage = Average.empty();
	private StatusHolder uidActionStatusHolder = new StatusHolder();
	private ReentrantLock selfLock = new ReentrantLock();
	
	public ActiveClient(ActiveClient old, ChannelConfig channelConfig, OffReadWriteLock offReadWriteLock) {
		super();
		this.config = channelConfig;
		this.offReadWriteLock = offReadWriteLock;
		this.clientId = old.clientId;
		this.lastTime = old.lastTime;
		this.usedUids = old.usedUids;
	}
	
	public ActiveClient(Long clientId, UsedUids usedUids, ChannelConfig channelConfig, OffReadWriteLock offReadWriteLock) {
		super();
		this.clientId = clientId;
		this.config = channelConfig;
		this.usedUids = usedUids;
		this.offReadWriteLock = offReadWriteLock;
		lastTime = System.currentTimeMillis();
	}

	public UsedUids getUsedUids() {
		return usedUids;
	}
	
	public AssignedTasks assignTasks() {
		selfLock.lock();
		try {
			return getTasks();
		} finally {
			selfLock.unlock();
		}
	}
	
	private AssignedTasks getTasks() {
		if(end()) {
			return null;
		}
		List<ItemCount<UidAction>> normal = getNormal();
		List<ItemCount<Long>> anyone = linkedStage.getAnyone();
		List<ItemCount<Long>> notLogin = linkedStage.getNotLogin();
		if(normal.size()+anyone.size()+notLogin.size()>0) {
			if(logger.isInfoEnabled()) {
				if(normal.size()+anyone.size()+notLogin.size() < config.allFetchSize -1) {
					logger.info("not fully fetch, clientId:" + clientId +
							",normal.size():" + normal.size() +
							",anyone.size():" + anyone.size() +
							",notLogin.size():" + notLogin.size() +
							",allFetchSize:" + config.allFetchSize
							);
				}
			}
			return new AssignedTasks(normal, anyone, notLogin);
		}
		if(nextStage()==false) {
			logger.info("no more task for " + clientId);
		}
		return getTasks();
	}
	
	private boolean end() {
		return linkedStage==null;
	}

	private List<ItemCount<UidAction>> getNormal() {
		if(uidActionAverage.hasNext() || fillUidActionAverage()) {
			return uidActionAverage.next();
		}
		return Collections.emptyList();
	}
	
	private boolean fillUidActionAverage() {
		List<UidActionCount> uidActionCounts = pollUidActionCount(config.uidSize);
		if(uidActionCounts.size()==0) {
			return false;
		}
		List<ItemCount<UidAction>> items = new ArrayList<ItemCount<UidAction>>();
		for (UidActionCount uidActionCount : uidActionCounts) {
			List<ItemCount<Long>> actionCounts = uidActionCount.getActionCounts();
			for (ItemCount<Long> itemCount : actionCounts) {
				items.add(new ItemCount<UidAction>(itemCount.getCount(), new UidAction(uidActionCount.getUid(), itemCount.getItem())));
			}
		}
		uidActionAverage = Average.startFromBatchSize(items, linkedStage.getNormalFetchSize());
		return true;
	}
	
	private boolean nextStage() {
		boolean effect = offReadWriteLock.lockWrite();
		if(!effect) {
			linkedStage=null;//mark end
			return false;
		}
		try {
			bindStage(linkedStage.toNextStage());
			return linkedStage!=null;
		} finally {
			offReadWriteLock.unlockWrite();
		}
	}
	
	public void bindStage(UidPoolStage linkedStage) {
		this.linkedStage = linkedStage;
		if(linkedStage!=null) {
			uidActionStatusHolder.resetStatus();
			StageClient stageClient = linkedStage.getStageClient(clientId);
			if(stageClient!=null) {
				boundUidActionCount = stageClient.getBoundUidActionCountList().iterator();
				reserveUidActionCount = stageClient.getReserveUidActionCountList().iterator();
				return;
			}
		}
		boundUidActionCount = EMPTY_ITERATOR;
		reserveUidActionCount = EMPTY_ITERATOR;
	}

	private List<UidActionCount> pollUidActionCount(int count) {
		boolean effect = offReadWriteLock.lockRead();
		if(!effect) {
			return Collections.emptyList();
		}
		try {
			if(uidActionStatusHolder.isLast()) {
				return Collections.emptyList();
			}
			List<UidActionCount> items = new ArrayList<UidActionCount>(count);
			do {
				Status status = uidActionStatusHolder.getStatus();
				int fetchCount = status.pollUidActionCount(this, items, count);
				count -= fetchCount;
				if(count==0) {
					break;
				}
			} while (uidActionStatusHolder.nextStatus());
			return items;
		} finally {
			offReadWriteLock.unlockRead();
		}
//		if(boundUidActionCount.hasNext()) {
//			return boundUidActionCount.next();
//		}
//		UidActionCount uidActionCount = null;
//		if(reserveUidActionCount.hasNext()) {//TODO add lock,remove backup
//			uidActionCount = reserveUidActionCount.next();
//		}
//		if(uidActionCount==null) {
//			uidActionCount = linkedStage.pollWeakReservedUidActionCount(clientId);
//		}
//		if(uidActionCount==null) {
//			uidActionCount = linkedStage.pollFreeUidActionCount();
//			if(uidActionCount!=null) {
//				usedUids.addUsed(uidActionCount.getUid());
//				return uidActionCount;
//			}
//		}
//		if(uidActionCount==null) {
//			uidActionCount = linkedStage.pollOtherWeakReservedUidActionCount();
//		}
//		if(uidActionCount!=null) {
//			usedUids.addReserveBack(uidActionCount.getUid());
//			return uidActionCount;
//		}
	}
	
	public static class AssignedTasks {
		List<ItemCount<UidAction>> normal;
		List<ItemCount<Long>> anyone;
		List<ItemCount<Long>> notLogin;
		public AssignedTasks(List<ItemCount<UidAction>> normal, List<ItemCount<Long>> anyone,
				List<ItemCount<Long>> notLogin) {
			super();
			this.normal = normal;
			this.anyone = anyone;
			this.notLogin = notLogin;
		}
		public List<ItemCount<UidAction>> getNormal() {
			return normal;
		}
		public List<ItemCount<Long>> getAnyone() {
			return anyone;
		}
		public List<ItemCount<Long>> getNotLogin() {
			return notLogin;
		}
	}
	
	private static final List<Status> statusLink = Arrays.asList(
			new BoundStatus(),
			new ReserveStatus(),
			new WeakReservedStatus(),
			new FreeStatus(),
			new OtherWeakStatus(),
			new EndStatus()
			);
	private class StatusHolder {
		private Iterator<Status> iterator;
		private Status status;
		{
			resetStatus();
		}
		public Status getStatus() {
			return status;
		}
		public void resetStatus() {
			iterator = statusLink.iterator();
			status = iterator.next();
		}
		public boolean nextStatus() {
			if(iterator.hasNext()) {
				status = iterator.next();
				return true;
			} else {
				return false;
			}
		}
		public boolean isLast() {
			if(iterator.hasNext()) {
				return false;
			} else {
				return true;
			}
		}
	}
	private abstract static class Status {
		public int pollUidActionCount(ActiveClient client, List<UidActionCount> items, int count) {
			for (int i = 0; i < count; i++) {
				UidActionCount uidActionCount = pollUidActionCount(client);
				if(uidActionCount==null) {
					return i;
				} else {
					items.add(uidActionCount);
				}
			}
			return count;
		}
		public abstract UidActionCount pollUidActionCount(ActiveClient client);
	}
	private static class BoundStatus extends Status {
		@Override
		public UidActionCount pollUidActionCount(ActiveClient client) {
			if(client.boundUidActionCount.hasNext()) {
				return client.boundUidActionCount.next();
			}
			return null;
		}
	}
	private static class ReserveStatus extends Status {
		@Override
		public UidActionCount pollUidActionCount(ActiveClient client) {
			UidActionCount uidActionCount = null;
			if(client.reserveUidActionCount.hasNext()) {//TODO add lock,remove backup
				uidActionCount = client.reserveUidActionCount.next();
				client.usedUids.addReserveBack(uidActionCount.getUid());
			}
			return uidActionCount;
		}
	}
	private static class WeakReservedStatus extends Status {
		@Override
		public UidActionCount pollUidActionCount(ActiveClient client) {
			UidActionCount uidActionCount = client.linkedStage.pollWeakReservedUidActionCount(client.clientId);
			if(uidActionCount!=null) {
				client.usedUids.addReserveBack(uidActionCount.getUid());
			}
			return uidActionCount;
		}
		@Override
		public int pollUidActionCount(ActiveClient client, List<UidActionCount> items, int count) {
			Lock uidBindLock = client.linkedStage.getUidBindLock();
			uidBindLock.lock();
			try {
				return super.pollUidActionCount(client, items, count);
			} finally {
				uidBindLock.unlock();
			}
		}
	}
	private static class FreeStatus extends Status {
		@Override
		public UidActionCount pollUidActionCount(ActiveClient client) {
			UidActionCount uidActionCount = client.linkedStage.pollFreeUidActionCount();
			if(uidActionCount!=null) {
				client.usedUids.addUsed(uidActionCount.getUid());
			}
			return uidActionCount;
		}
		@Override
		public int pollUidActionCount(ActiveClient client, List<UidActionCount> items, int count) {
			Lock uidBindLock = client.linkedStage.getUidBindLock();
			uidBindLock.lock();
			try {
				return super.pollUidActionCount(client, items, count);
			} finally {
				uidBindLock.unlock();
			}
		}
	}
	private static class OtherWeakStatus extends Status {
		@Override
		public UidActionCount pollUidActionCount(ActiveClient client) {
			UidActionCount uidActionCount = client.linkedStage.pollOtherWeakReservedUidActionCount();
			if(uidActionCount!=null) {
				client.usedUids.addReserveBack(uidActionCount.getUid());
			}
			return uidActionCount;
		}
		@Override
		public int pollUidActionCount(ActiveClient client, List<UidActionCount> items, int count) {
			Lock uidBindLock = client.linkedStage.getUidBindLock();
			uidBindLock.lock();
			try {
				return super.pollUidActionCount(client, items, count);
			} finally {
				uidBindLock.unlock();
			}
		}
	}
	private static class EndStatus extends Status {
		@Override
		public int pollUidActionCount(ActiveClient client, List<UidActionCount> items, int count) {
			return 0;
		}
		public UidActionCount pollUidActionCount(ActiveClient client) {
			return null;
		}
	}
	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

}
