package com.nali.spreader.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.avg.ItemCount;

public class UserTasksLink {
	private static Logger logger = Logger.getLogger(UserTasksLink.class);
	private LinkedList<List<ItemCount<Long>>> tasksLink = new LinkedList<List<ItemCount<Long>>>();
	private Map<Long, Integer> actionCountMap = new HashMap<Long, Integer>();

	public void assignTasks(List<UserTaskCount> userTaskList) {
		List<ItemCount<Long>> actionCounts = new ArrayList<ItemCount<Long>>(userTaskList.size());
		for (UserTaskCount userTaskCount : userTaskList) {
			Long actionId = userTaskCount.getActionId();
			Integer count = userTaskCount.getCount();
			Integer priorCount = actionCountMap.get(actionId);
			ItemCount<Long> itemCount;
			if(priorCount!=null) {
				if(count.equals(priorCount)) {
					continue;
				}
				int curCount = count - priorCount;
				if(curCount<0) {
					logger.error("count<priorCount, actionId:" + actionId + ", count:" + count + ", priorCount:" + priorCount);
					continue;
				}
				itemCount = new ItemCount<Long>(curCount, actionId);
			} else {
				itemCount = new ItemCount<Long>(count, actionId);
			}
			actionCounts.add(itemCount);
			actionCountMap.put(actionId, count);
		}
		if(actionCounts.size()!=0) {
			tasksLink.add(actionCounts);
		}
	}
	
	public List<ItemCount<Long>> getCurrentTasks() {
		return tasksLink.peek();
	}
	
}
