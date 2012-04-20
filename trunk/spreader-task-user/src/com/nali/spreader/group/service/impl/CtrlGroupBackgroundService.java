package com.nali.spreader.group.service.impl;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.threadpool.CommonFixedThreadPool;
import com.nali.common.threadpool.ThreadPool;
import com.nali.spreader.data.CtrlGroupConflict;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.service.ICtrlGroupBackgroundService;
import com.nali.spreader.group.service.ICtrlGroupTask;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

@Service
public class CtrlGroupBackgroundService implements ICtrlGroupBackgroundService,InitializingBean, DisposableBean{
	
	private ThreadPool threadPool;
	
	private int threadCount;
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	@Autowired
	private ICtrlGroupTask ctrlGroupTask;
	
	
	@Override
	public void submitCtrlUserGroup(UserGroup userGroup, DataIterator<GrouppedUser> iterator) {
		this.threadPool.submit(new CtrlGroupBackGroudTask(userGroup.getGid(), iterator, ctrlGroupTask));
	}

	@Override
	public void destroy() throws Exception {
		this.threadPool.shutdown();
	}
	
	
	
	public static class CtrlGroupBackGroudTask implements Runnable {
		private DataIterator<GrouppedUser> iterator;
		private ICtrlGroupTask ctrlGroupTask;
		private long gid;
		
		public CtrlGroupBackGroudTask(long gid,  DataIterator<GrouppedUser> iterator, ICtrlGroupTask ctrlGroupTask) {
			this.gid = gid;
			this.ctrlGroupTask = ctrlGroupTask;
			this.iterator = iterator;
		}

		@Override
		public void run() {
			try{
				while(iterator.hasNext()) {
					List<GrouppedUser> grouppedUsers = iterator.next();
					List<Long> uids = GrouppedUser.getUids(grouppedUsers);
					this.ctrlGroupTask.changeCtrlUserGroup(uids, gid);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class CtrlGroupConflictTask implements Runnable {
		private DataIterator<CtrlGroupConflict> iterator;
		private ICtrlGroupTask ctrlGroupTask;
		
		public CtrlGroupConflictTask(DataIterator<CtrlGroupConflict> iterator, ICtrlGroupTask ctrlGroupTask) {
			this.ctrlGroupTask = ctrlGroupTask;
			this.iterator = iterator;
		}
		@Override
		public void run() {
			
			while(iterator.hasNext()) {
				List<CtrlGroupConflict> conflicts = iterator.next();
				this.ctrlGroupTask.resolveConflict(conflicts);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(threadCount == 0) {
			this.threadCount = 5;
		}
		
		this.threadPool = new CommonFixedThreadPool(30, "CtrlGroupBackGroudPool");
	}

	@Override
	public void resolveAll(DataIterator<CtrlGroupConflict> iterator) {
		this.threadPool.submit(new CtrlGroupConflictTask(iterator, ctrlGroupTask));
	}
}
