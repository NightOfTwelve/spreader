package com.nali.spreader.util.avg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AverageHelper {
	public static final String KEY_USER_NUMBER = "userNumber";
	public static final String KEY_ROBOT_USER_NUMBER = "robotUserNumber";
	public static final String KEY_ADD_FANS_LIMIT = "addFansLimit";
	public static final String KEY_EXECU_ADD_FANS_LIMIT = "execuAddFansLimit";

	/**
	 * 检查参数并修正后自动匹配Average模式
	 * 
	 * @param param
	 * @param itemCounts
	 * @return
	 */
	public static <T> Average<T> selectAverageByParam(Map<String, Integer> param,
			List<ItemCount<T>> itemCounts) {
		param = correctParams(param);
		Integer userNumber = param.get(KEY_USER_NUMBER);
		Integer robotUserNumber = param.get(KEY_ROBOT_USER_NUMBER);
		Integer addLimit = param.get(KEY_ADD_FANS_LIMIT);
		Integer execuLimit = param.get(KEY_EXECU_ADD_FANS_LIMIT);
		Average<T> avg;
		// 保证需要关注的次数
		if ((robotUserNumber * execuLimit - userNumber * addLimit) >= 0) {
			avg = Average.startFromBatchSize(itemCounts, addLimit);
		} else {
			// 保证每个人都关注到
			avg = Average.startFromBatchCount(itemCounts, userNumber);
		}
		return avg;
	}

	/**
	 * 修正参数
	 * 
	 * @param param
	 * @return
	 */
	public static Map<String, Integer> correctParams(Map<String, Integer> param) {
		Integer robotUserNumber = param.get(KEY_ROBOT_USER_NUMBER);
		Integer addLimit = param.get(KEY_ADD_FANS_LIMIT);
		if (addLimit > robotUserNumber) {
			param.put(KEY_ADD_FANS_LIMIT, robotUserNumber);
		}
		return param;
	}

	/**
	 * 构造执行组成员数据
	 * 
	 * @param execuTimes
	 *            每个item需执行的次数
	 * @param execuItems
	 *            所有成员集合
	 * @return
	 */
	public static <T> List<ItemCount<T>> getItemCounts(int execuTimes, List<T> execuItems) {
		List<ItemCount<T>> result = new ArrayList<ItemCount<T>>();
		for (T t : execuItems) {
			ItemCount<T> item = new ItemCount<T>(execuTimes, t);
			result.add(item);
		}
		return result;
	}
}
