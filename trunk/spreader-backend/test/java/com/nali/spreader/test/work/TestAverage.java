package com.nali.spreader.test.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dto.UserContentsDto;
import com.nali.spreader.model.ReplyDto;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.AverageHelper;
import com.nali.spreader.util.avg.ItemCount;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.workshop.other.AddUserFans;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestAverage {
	private static final String KEY_USER_NUMBER = "userNumber";
	private static final String KEY_ROBOT_USER_NUMBER = "robotUserNumber";
	private static final String KEY_ADD_FANS_LIMIT = "addFansLimit";
	private static final String KEY_EXECU_ADD_FANS_LIMIT = "execuAddFansLimit";


	public void test() {
		Map<String, Integer> execuParams = CollectionUtils.newHashMap(4);
		execuParams.put(KEY_USER_NUMBER, 1);
		execuParams.put(KEY_ADD_FANS_LIMIT, 2);
		execuParams.put(KEY_ROBOT_USER_NUMBER, 5);
		execuParams.put(KEY_EXECU_ADD_FANS_LIMIT, 3);

		Integer robotUserNumber = execuParams.get(KEY_ROBOT_USER_NUMBER);
		Integer execuLimit = execuParams.get(KEY_EXECU_ADD_FANS_LIMIT);
		List<Long> execuData = getData(robotUserNumber);
		List<ItemCount<Long>> itemCounts = AverageHelper.getItemCounts(execuLimit, execuData);
		Average<Long> avg = AverageHelper.selectAverageByParam(execuParams, itemCounts);
		int ii = 1;
		while (avg.hasNext()) {
			System.out.println(">>>>第" + ii + "次");
			List<ItemCount<Long>> li = avg.next();
			try {
				for (ItemCount<Long> it : li) {
					Integer c = it.getCount();
					Long item = it.getItem();
					if (c > 1) {
						throw new RuntimeException("ITEM:" + item + ",count:" + c + ",出现重复关注");
					}
					System.out.println("ITEM:" + item + ",count:" + c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ii++;
		}
	}

	@Test
	public void testWork() {
		Map<String, Integer> execuParams = CollectionUtils.newHashMap(4);
		execuParams.put(KEY_USER_NUMBER, 8);
		execuParams.put(KEY_ADD_FANS_LIMIT, 2);
		execuParams.put(KEY_ROBOT_USER_NUMBER, 5);
		execuParams.put(KEY_EXECU_ADD_FANS_LIMIT, 3);

		Integer robotUserNumber = execuParams.get(KEY_ROBOT_USER_NUMBER);
		Integer execuLimit = execuParams.get(KEY_EXECU_ADD_FANS_LIMIT);
		List<Long> execuData = getData(robotUserNumber);
		List<Map<String, Long>> allContent = getAllData(1);
		List<UserContentsDto> readyData = this.getUserContentsDtoData(allContent);
		List<ItemCount<Long>> itemCounts = AverageHelper.getItemCounts(execuLimit, execuData);
		Average<Long> avg = AverageHelper.selectAverageByParam(execuParams, itemCounts);
		Set<UserContentsDto> ucExists = new HashSet<UserContentsDto>();
		while (avg.hasNext()) {
			List<ItemCount<Long>> items = avg.next();
			List<UserContentsDto> workData = RandomUtil.randomItems(readyData, ucExists, 1);
			if (!CollectionUtils.isEmpty(workData)) {
				UserContentsDto uc = workData.get(0);
				Long uid = uc.getUid();
				List<Long> contents = uc.getContents();
				Map<Long, Set<Long>> existsReyply = new HashMap<Long, Set<Long>>();
				Map<Long, Set<Long>> existsAdd = new HashMap<Long, Set<Long>>();
				for (ItemCount<Long> item : items) {
					Long robotId = item.getItem();
					int count = item.getCount();
					Long contentId = RandomUtil.randomItem(contents);
					if (count > 0) {
						existsAdd = addFans(uid, robotId, existsAdd, new Date());
						System.out.println("ADD>>>>uid:" + uid + ",robot:" + robotId);
						if (true) {
							existsReyply = replyWeibo(robotId, contentId, existsReyply, new Date());
							System.out.println("REPLY>>>>robot:" + robotId + ",contentId"
									+ contentId);
						}
					}
				}
				ucExists.add(uc);
			}
		}
	}

	private List<UserContentsDto> getUserContentsDtoData(List<Map<String, Long>> data) {
		List<UserContentsDto> result = new ArrayList<UserContentsDto>();
		Map<Long, Set<Long>> map = getTransformData(data, "uid", "contentId");
		if (map == null) {
			return result;
		}
		if (map.isEmpty()) {
			return result;
		}
		for (Map.Entry<Long, Set<Long>> m : map.entrySet()) {
			Long uid = m.getKey();
			Set<Long> contentSet = m.getValue();
			List<Long> cidList = new ArrayList<Long>(contentSet);
			UserContentsDto dto = new UserContentsDto();
			dto.setUid(uid);
			dto.setContents(cidList);
			result.add(dto);
		}
		return result;
	}

	private Map<Long, Set<Long>> getTransformData(List<Map<String, Long>> data, String groupKey,
			String itemKey) {
		Map<Long, Set<Long>> readyMap = new HashMap<Long, Set<Long>>();
		if (CollectionUtils.isEmpty(data)) {
			return readyMap;
		}
		Set<Long> contentSet = new HashSet<Long>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Long> map = data.get(i);
			Long groupKeyId = map.get(groupKey);
			Long itemKeyId = map.get(itemKey);
			if (!readyMap.containsKey(groupKeyId)) {
				contentSet = new HashSet<Long>();
			}
			contentSet.add(itemKeyId);
			readyMap.put(groupKeyId, contentSet);
		}
		return readyMap;
	}

	/**
	 * 判断是否需要生成任务
	 * 
	 * @param robotId
	 * @param contentId
	 * @param existsMap
	 * @return
	 */
	private boolean isExists(Long toId, Long execuId, Map<Long, Set<Long>> existsMap) {
		if (!existsMap.containsKey(toId)) {
			return false;
		} else {
			Set<Long> set = existsMap.get(toId);
			if (set == null) {
				return false;
			}
			return set.contains(execuId);
		}
	}

	/**
	 * 检查是否已经回复了该微博，没有就执行回复并记录
	 * 
	 * @param robotId
	 * @param contentId
	 * @param exists
	 * @return
	 */
	private Map<Long, Set<Long>> replyWeibo(Long robotId, Long contentId,
			Map<Long, Set<Long>> exists, Date startTime) {
		if (!isExists(robotId, contentId, exists)) {
			replyWeibo(robotId, contentId, "", startTime);
			Set<Long> set = exists.get(robotId);
			if (set == null) {
				set = new HashSet<Long>();
			}
			set.add(contentId);
			exists.put(robotId, set);
		}
		return exists;
	}

	/**
	 * 执行回复的基本操作，不判断是否已经回复该微博
	 * 
	 * @param robotId
	 * @param contentId
	 * @param replyText
	 */
	private void replyWeibo(Long robotId, Long contentId, String replyText, Date startTime) {
		ReplyDto dto = new ReplyDto();
		dto.setContentId(contentId);
		dto.setRobotId(robotId);
		dto.setForward(false);
		dto.setText(replyText);
		dto.setStartTime(startTime);
	}

	/**
	 * 记录已经生成该机器人是否已经关注了该用户的任务
	 * 
	 * @param uid
	 * @param robotId
	 * @param exists
	 * @param startTime
	 * @return
	 */
	private Map<Long, Set<Long>> addFans(Long uid, Long robotId, Map<Long, Set<Long>> exists,
			Date startTime) {
		if (!isExists(uid, robotId, exists)) {
			addFans(uid, robotId, startTime);
			Set<Long> set = exists.get(uid);
			if (set == null) {
				set = new HashSet<Long>();
			}
			set.add(robotId);
			exists.put(uid, set);
		}
		return exists;
	}

	/**
	 * 机器人执行加粉的操作
	 * 
	 * @param robotId
	 * @param uid
	 */
	private void addFans(Long uid, Long robotId, Date addStartTime) {
		AddUserFans.AddFansDto dto = new AddUserFans.AddFansDto();
		dto.setRobotUid(robotId);
		dto.setUid(uid);
		dto.setStartTime(addStartTime);
	}

	private List<Long> getData(int size) {
		List<Long> list = new ArrayList<Long>();
		if (size > 0) {
			for (int rid = 1; rid <= size; rid++) {
				Long ridL = (long) rid;
				list.add(ridL);
			}
		}
		return list;
	}

	private List<Map<String, Long>> getAllData(int size) {
		List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				Map<String, Long> m = new HashMap<String, Long>();
				m.put("uid", (long) RandomUtils.nextInt(10));
				m.put("contents", RandomUtils.nextLong());
				list.add(m);
			}
		}
		return list;
	}
}
