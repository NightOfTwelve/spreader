package com.nali.spreader.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.nali.spreader.pool.NodeQueue.Node;

public class UidBackupPool {
	private UidNodeQueue nodeQueue;
	private OwnerQueues ownerQueues;
	private int poolLimit;
	
	public UidBackupPool(int clientLimit, int clientRemoveSize, int poolLimit) {
		super();
		this.poolLimit = poolLimit;
		nodeQueue = new UidNodeQueue();
		ownerQueues = new OwnerQueues(clientLimit, clientRemoveSize);
	}
	public UidBackupPool(int clientLimit, int poolLimit) {
		this(clientLimit, clientLimit/4, poolLimit);
	}
	public UidBackupPool() {
		this(-1, -1);
	}
	
	public List<Long> goInto(Long clientId, Long uid) {
		UidPoolNodeOwner owner = UidPoolNodeOwner.genNodeAndOwner(clientId, uid);
		nodeQueue.addFirst(owner.getNode());
		ownerQueues.push(owner);
		return Collections.emptyList();//TODO
	}
	
	public Long pollEarliestUid(long maxTime) {
		UidPoolNode node = nodeQueue.pollLast(maxTime);
		if(node==null) {
			return null;
		}
		UidPoolNodeOwner owner = node.getOwner();
		ownerQueues.remove(owner);
		return node.getUid();
	}
	
	public Long pollEarliestUid() {
		UidPoolNode node = nodeQueue.pollLast();
		if(node==null) {
			return null;
		}
		UidPoolNodeOwner owner = node.getOwner();
		ownerQueues.remove(owner);
		return node.getUid();
	}
	
	public Long pollRecentUid(Long clientId) {
		UidPoolNodeOwner owner = ownerQueues.poll(clientId);
		if(owner==null) {
			return null;
		}
		UidPoolNode node = owner.getNode();
		nodeQueue.remove(node);
		return node.getUid();
	}
	
	public boolean removeUid(Long uid) {
		UidPoolNode uidNode = nodeQueue.getUidNode(uid);
		if(uidNode==null) {
			return false;
		}
		nodeQueue.remove(uidNode);
		UidPoolNodeOwner owner = uidNode.getOwner();
		ownerQueues.remove(owner);
		return true;
	}
	
	public Iterator<BoundUid> descendingIterator() {
		return new Iterator<BoundUid>() {
			private UidPoolNode node = nodeQueue.getLast();
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public BoundUid next() {
				if(node==null) {
					throw new NoSuchElementException();
				}
				UidPoolNode curNode = node;
				node = node.getPrev();
				return new BoundUid(curNode.getUid(), curNode.getOwner().getClientId(), curNode.getTime());
			}
			
			@Override
			public boolean hasNext() {
				return node!=null;
			}
		};
	}
	
	public Iterator<BoundUid> iterator() {
		return new Iterator<BoundUid>() {
			private UidPoolNode node = nodeQueue.getHead();
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public BoundUid next() {
				if(node==null) {
					throw new NoSuchElementException();
				}
				UidPoolNode curNode = node;
				node = node.getNext();
				return new BoundUid(curNode.getUid(), curNode.getOwner().getClientId(), curNode.getTime());
			}
			
			@Override
			public boolean hasNext() {
				return node!=null;
			}
		};
	}
	
	public List<Long> uids() {
		ArrayList<Long> uids = new ArrayList<Long>();
		UidPoolNode node = nodeQueue.getHead();
		while(node!=null) {
			uids.add(node.getUid());
			node = node.getNext();
		}
		return uids;
	}
	@Override
	public String toString() {
		return nodeQueue.getNodes().toString();
	}
}

class OwnerQueue extends NodeQueue<UidPoolNodeOwner> {}

class OwnerQueues {
	private ConcurrentMap<Long, OwnerQueue> queues = new ConcurrentHashMap<Long, OwnerQueue>();
	private final int clientLimit;
	private final int clientRemoveSize;
	
	public OwnerQueues(int clientLimit, int clientRemoveSize) {
		this.clientLimit = clientLimit;
		this.clientRemoveSize = clientRemoveSize;
	}

	public void push(UidPoolNodeOwner owner) {
		OwnerQueue queue = queues.get(owner.getClientId());
		if(queue==null) {
			queue = new OwnerQueue();
			OwnerQueue exists = queues.putIfAbsent(owner.getClientId(), queue);
			if(exists!=null) {
				queue=exists;
			}
		}
		queue.addFirst(owner);
	}
	
	public UidPoolNodeOwner poll(Long clientId) {
		OwnerQueue queue = queues.get(clientId);
		if(queue==null) {
			return null;
		}
		UidPoolNodeOwner poll = queue.poll();
		if(queue.isEmpty()) {
			queues.remove(clientId);//不是线程安全的
		}
		return poll;
	}
	
	public void remove(UidPoolNodeOwner owner) {
		Long clientId = owner.getClientId();
		OwnerQueue queue = queues.get(clientId);
		queue.remove(owner);
		if(queue.isEmpty()) {
			queues.remove(clientId);//不是线程安全的
		}
	}
}

class UidNodeQueue extends NodeQueue<UidPoolNode> {
	private static Logger logger = Logger.getLogger(UidBackupPool.class);
	private RetrievableHashSet<UidPoolNode> nodes = new RetrievableHashSet<UidPoolNode>();
	
	public UidPoolNode pollLast(long maxTime) {
		UidPoolNode last = getLast();
		if(last==null || last.getTime() > maxTime) {
			return null;
		} else {
			return pollLast();
		}
	}
	
	public UidPoolNode getUidNode(Long uid) {
		UidPoolNode node = new UidPoolNode(null, uid, 0L);
		return nodes.retrieve(node);
	}

	@Override
	public void remove(UidPoolNode node) {
		super.remove(node);
		nodes.remove(node);
	}

	@Override
	public void addFirst(UidPoolNode node) {
		super.addFirst(node);
		if(nodes.add(node)==false) {//for test
			logger.error("double add!!", new RuntimeException());
		}
	}

	@Override
	public UidPoolNode poll() {
		UidPoolNode poll = super.poll();
		if(poll!=null) {
			nodes.remove(poll);
		}
		return poll;
	}

	@Override
	public UidPoolNode pollLast() {
		UidPoolNode poll = super.pollLast();
		if(poll!=null) {
			nodes.remove(poll);
		}
		return poll;
	}
	
	public Set<UidPoolNode> getNodes() {
		return nodes;
	}
	
}

class UidPoolNode extends Node<UidPoolNode> {
	private UidPoolNodeOwner owner;
	private Long uid;
	private long time;
	
	public UidPoolNode(UidPoolNodeOwner owner, Long uid, long time) {
		super();
		this.owner = owner;
		this.uid = uid;
		this.time = time;
	}
	
	public UidPoolNodeOwner getOwner() {
		return owner;
	}

	public Long getUid() {
		return uid;
	}

	public long getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		return uid.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UidPoolNode) {
			UidPoolNode target = (UidPoolNode) obj;
			return uid.equals(target.uid);
		}
		return false;
	}

	@Override
	public String toString() {
		return "<" + uid +
				"," + owner.getClientId() +
				">";
	}
}

class UidPoolNodeOwner extends Node<UidPoolNodeOwner> {
	private Long clientId;
	private UidPoolNode node;
	
	private UidPoolNodeOwner(Long clientId) {
		super();
		this.clientId = clientId;
	}
	
	public static UidPoolNodeOwner genNodeAndOwner(Long clientId, Long uid) {
		UidPoolNodeOwner owner = new UidPoolNodeOwner(clientId);
		UidPoolNode node = new UidPoolNode(owner, uid, System.currentTimeMillis());
		owner.node = node;
		return owner;
	}
	
	public Long getClientId() {
		return clientId;
	}
	public UidPoolNode getNode() {
		return node;
	}
}