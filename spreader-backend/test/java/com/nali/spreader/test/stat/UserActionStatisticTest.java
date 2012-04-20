package com.nali.spreader.test.stat;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.lang.DateUtils;
import com.nali.lang.StringUtils;
import com.nali.spreader.group.service.IUserActionStatistic;
import com.nali.stat.dc.exception.DataCollectionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class UserActionStatisticTest {
	
	@Autowired
	private IUserActionStatistic userActionStatistic;
	
	private Long[] testUids = new Long[]{238746L, 238949L};
	
	private Date[] hours = new Date[30];
	
	{
		Date now = new Date();
		for(int i = 0; i < hours.length; i++) {
			hours[i] = DateUtils.addHours(now, i);
		}
	}
	
	@Test
	public void testIncr() {
		try {
			this.userActionStatistic.incr("addUserFans", 1, testUids[0]);
			this.userActionStatistic.incr("addUserFans", 2, testUids[1]);
			long[] value = this.userActionStatistic.getCount("addUserFans", 1, testUids);
			Assert.assertEquals(1L, value[0]);
			Assert.assertEquals(2L, value[1]);
		}
		catch (DataCollectionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGet() {
		try {
			for(int  i = 1 ; i <= hours.length; i++) {
				this.userActionStatistic.incr("addUserFans", i, hours[i-1], testUids[0]);
				this.userActionStatistic.incr("addUserFans", 2*i, hours[i-1], testUids[1]);
			}
			long[] sums = this.userActionStatistic.getCount("addUserFans", 3, new Date(0), hours[hours.length - 1], testUids);
			System.out.println(StringUtils.objectToString(sums));
			Assert.assertEquals(410, sums[0]);
			Assert.assertEquals(820, sums[1]);
		} catch (DataCollectionException e) {
			e.printStackTrace();
		}
		
	}
	
	@After
	public void tearDown() {
//		try {
//			for(Date hour : hours) {
//				this.userActionStatistic.setValue("addUserFans", 0, hour, testUids);
//			}
//		} catch (DataCollectionException e) {
//			e.printStackTrace();
//		}
	}
}
