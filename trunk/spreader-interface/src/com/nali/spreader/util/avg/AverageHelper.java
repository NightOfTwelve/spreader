package com.nali.spreader.util.avg;

import java.util.ArrayList;
import java.util.List;

public class AverageHelper {
	/**
	 * 检查参数并修正后自动匹配Average模式
	 * 
	 * @param userNumber
	 *            被关注的人数
	 * @param robotUserNumber
	 *            执行关注的机器人数
	 * @param addFansLimit
	 *            被关注的上限
	 * @param execuAddFansLimit
	 *            执行关注的上限
	 * @param itemCounts
	 * @return
	 */
	public static <T> Average<T> selectAverageByParam(Integer userNumber, Integer robotUserNumber,
			Integer addFansLimit, Integer execuAddFansLimit, List<ItemCount<T>> itemCounts) {
		addFansLimit = Math.min(addFansLimit, robotUserNumber);
		Average<T> avg;
		// 保证需要关注的次数
		if ((robotUserNumber * execuAddFansLimit - userNumber * addFansLimit) >= 0) {
			avg = Average.startFromBatchSize(itemCounts, addFansLimit);
		} else {
			// 保证每个人都关注到
			avg = Average.startFromBatchCount(itemCounts, userNumber);
		}
		return avg;
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
