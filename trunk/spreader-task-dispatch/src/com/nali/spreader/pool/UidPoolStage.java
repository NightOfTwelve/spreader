package com.nali.spreader.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.nali.spreader.util.ConstructingMap;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

public class UidPoolStage {
	private static Logger logger = Logger.getLogger(UidPoolStage.class);
	private UidBackupPool backupPool;
	private long expireTime;
	private long expireRemoveTime;
	private int normalFetchSize;
	private Map<Long, List<ItemCount<Long>>> uidActionCountMap;
	private Average<Long> anyoneAverage;
	private Average<Long> notLoginAverage;
	private Map<Long, ActiveClient> activeClients;
	
	private Lock uidBindLock = new ReentrantLock();
	private Map<Long, StageClient> clientsMap;
	private LinkedList<UidActionCount> freeUidActionCount;
	private UidBackupPool weakReservedUids = new UidBackupPool();
	private Map<Long, UidActionCount> weakReservedUidActionCount = new HashMap<Long, UidActionCount>();
	private boolean isInited;
	private UidPoolStage nextStage;

	public UidPoolStage(UidBackupPool backupPool, long expireTime, long expireRemoveTime, int normalFetchSize, Map<Long, List<ItemCount<Long>>> uidActionCountMap,
			Average<Long> anyoneAverage, Average<Long> notLoginAverage, Map<Long, ActiveClient> activeClients) {
		super();
		this.backupPool = backupPool;
		this.expireTime = expireTime;
		this.expireRemoveTime = expireRemoveTime;
		this.normalFetchSize = normalFetchSize;
		this.uidActionCountMap = uidActionCountMap;
		this.anyoneAverage = anyoneAverage;
		this.notLoginAverage = notLoginAverage;
		this.activeClients = activeClients;
	}	
	public StageClient getStageClient(Long clientId) {
		return clientsMap.get(clientId);
	}
	
	public UidActionCount pollFreeUidActionCount() {//TODO lock
		return freeUidActionCount.poll();
	}
	
	public UidActionCount pollWeakReservedUidActionCount(Long clientId) {//TODO lock
		Long uid = weakReservedUids.pollRecentUid(clientId);
		if(uid!=null) {
			return weakReservedUidActionCount.remove(uid);
		} else {
			return null;
		}
	}
	
	public UidActionCount pollOtherWeakReservedUidActionCount() {//TODO lock
		Long uid = weakReservedUids.pollEarliestUid();
		if(uid!=null) {
			return weakReservedUidActionCount.remove(uid);
		} else {
			return null;
		}
	}
	
	public UidPoolStage toNextStage() {
		if(nextStage==null) {
			return null;
		}
		nextStage.checkInit();
		return nextStage;
	}
	
	public void checkInit() {
		if(isInited==false) {
			init(false);
			isInited=true;
			logger.info("inited a stage");//TODO more info
		}
	}
	
	public void init(boolean forceBackup) {
		ConstructingMap<Long, StageClient> stageClients = ConstructingMap.getConstructingMap(StageClient.class);
		
		handleOldClientsMap(forceBackup, stageClients);
		handleBackupPool(stageClients);
		
		//create clientsMap
		clientsMap = stageClients.getMap();
		//create freeUidActionCount
		freeUidActionCount = new LinkedList<UidActionCount>();
		for (Entry<Long, List<ItemCount<Long>>> entry : uidActionCountMap.entrySet()) {
			freeUidActionCount.add(new UidActionCount(entry.getKey(), entry.getValue()));
		}
	}
	
	private void handleOldClientsMap(boolean forceBackup, ConstructingMap<Long, StageClient> stageClients) {
		for (Entry<Long, ActiveClient> entry : activeClients.entrySet()) {
			UsedUids usedUids = entry.getValue().getUsedUids();
			Long clientId = entry.getKey();
			List<Long> reserveBacks = usedUids.popReserveBacks();
			for (Long reserveBack : reserveBacks) {
				backupPool.removeUid(reserveBack);
			}
			StageClient stageClient = stageClients.get(clientId);
			List<UidActionCount> boundUidActionCountList = new ArrayList<UidActionCount>();
			Set<Long> uids = usedUids.getUids();
			for (Iterator<Long> iterator = uids.iterator(); iterator.hasNext();) {
				Long boundUid = iterator.next();
				List<ItemCount<Long>> actionCounts = uidActionCountMap.remove(boundUid);
				if(actionCounts!=null) {
					boundUidActionCountList.add(new UidActionCount(boundUid, actionCounts));
				} else if(forceBackup) {
					backupPool.goInto(clientId, boundUid);
					iterator.remove();
				}
			}
			stageClient.setBoundUidActionCountList(boundUidActionCountList);
		}
		if(forceBackup) {
			backupPool.trim(expireRemoveTime);
		}
	}

	private void handleBackupPool(ConstructingMap<Long, StageClient> stageClients) {
		Iterator<BoundUid> boundUidIterator = backupPool.descendingIterator();
		boolean weakReserve = true;
		while (boundUidIterator.hasNext()) {
			BoundUid boundUid = boundUidIterator.next();
			Long uid = boundUid.getUid();
			Long clientId = boundUid.getClientId();
			List<ItemCount<Long>> actionCounts = uidActionCountMap.remove(uid);
			if(actionCounts==null) {
				continue;
			}
			if(weakReserve) {
				if(boundUid.getTime()<expireTime) {
					weakReservedUids.goInto(clientId, uid);
					weakReservedUidActionCount.put(uid, new UidActionCount(uid, actionCounts));
					continue;
				} else {
					weakReserve=false;
				}
			}
			StageClient stageClient = stageClients.get(clientId);
			stageClient.addReserveUidActionCount(new UidActionCount(uid, actionCounts));
		}
	}

	public UidPoolStage getNextStage() {
		return nextStage;
	}

	public void setNextStage(UidPoolStage nextStage) {
		this.nextStage = nextStage;
	}

	public List<ItemCount<Long>> getAnyone() {
		if(anyoneAverage.hasNext()) {
			return anyoneAverage.next();
		} else {
			return Collections.emptyList();
		}
	}
	
	public List<ItemCount<Long>> getNotLogin() {
		if(notLoginAverage.hasNext()) {
			return notLoginAverage.next();
		} else {
			return Collections.emptyList();
		}
	}
	public int getNormalFetchSize() {
		return normalFetchSize;
	}
	public Lock getUidBindLock() {
		return uidBindLock;
	}
	public String peekFree() {//for check
		if(freeUidActionCount==null) {
			return "not init:" + uidActionCountMap.keySet();
		}
		return freeUidActionCount.toString();
	}
	public String peekStage() {//for check
		if(clientsMap==null) {
			return "not init";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<Long, StageClient> entry : clientsMap.entrySet()) {
			sb.append(entry.getKey()).append('\t').append(entry.getValue()).append("\r\n");
		}
		return sb.toString();
	}
	public String peekWeak() {
		if(weakReservedUids==null) {
			return "not init";
		}
		return weakReservedUids.toString();
	}
	
}
