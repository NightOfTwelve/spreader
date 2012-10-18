package com.nali.spreader.service.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.AverageHelper;
import com.nali.spreader.util.avg.ItemCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("161-application-context-test.xml")
public class TestAverage {
	private static final String KEY_USER_NUMBER = "userNumber";
	private static final String KEY_ROBOT_USER_NUMBER = "robotUserNumber";
	private static final String KEY_ADD_FANS_LIMIT = "addFansLimit";
	private static final String KEY_EXECU_ADD_FANS_LIMIT = "execuAddFansLimit";

	@Test
	public void test() {
		Map<String, Integer> execuParams = CollectionUtils.newHashMap(4);
		execuParams.put(KEY_USER_NUMBER, 5);
		execuParams.put(KEY_ADD_FANS_LIMIT, 4);
		execuParams.put(KEY_ROBOT_USER_NUMBER, 3);
		execuParams.put(KEY_EXECU_ADD_FANS_LIMIT, 5);

		Integer robotUserNumber = execuParams.get(KEY_ROBOT_USER_NUMBER);
		Integer execuLimit = execuParams.get(KEY_EXECU_ADD_FANS_LIMIT);
		List<Long> execuData = getData(robotUserNumber);
		List<ItemCount<Long>> itemCounts = AverageHelper.getItemCounts(execuLimit, execuData);
		// 保证需要关注的次数
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
}
