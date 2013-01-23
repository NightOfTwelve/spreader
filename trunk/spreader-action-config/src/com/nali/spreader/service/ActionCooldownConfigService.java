package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.IActionCooldownConfigDao;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.random.RandomUtil;

@Service
public class ActionCooldownConfigService implements IActionCooldownConfigService {
	private static Logger logger = Logger.getLogger(ActionCooldownConfigService.class);
	private static final long DAY_MILLIS = DateUtils.MILLIS_PER_DAY;
	private static final long HOUR_MILLIS = DateUtils.MILLIS_PER_HOUR;
	private static final int TIME_OFFSET = TimeZone.getDefault().getRawOffset();
	private static final int DEFAULT_ID=0;
	private ThreeDayHourCooldowns defaultCooldowns;
	@Autowired
	private IActionCooldownConfigDao actionCooldownConfigDao;
	private volatile long expiredTime;
	private AtomicBoolean refreshFlag = new AtomicBoolean(false);
	
	@PostConstruct
	public void postConstruct() {
		defaultCooldowns = new ThreeDayHourCooldowns();
		defaultCooldowns.today = genCooldown(DEFAULT_ID);
		defaultCooldowns.tomorrow = genCooldown(DEFAULT_ID);
		defaultCooldowns.yesterday = genCooldown(DEFAULT_ID);
		defaultCooldowns.todayMillis = SpecialDateUtil.getExactTodayMillis();
		expiredTime = defaultCooldowns.todayMillis + DAY_MILLIS;
	}

	@Override
	public int[] getHourCooldowns(Date date, int id) {
		checkExpired();
		return defaultCooldowns.get(date);
	}
	
	private void checkExpired() {
		long current = System.currentTimeMillis();
		if(current>expiredTime) {
			if(refreshFlag.compareAndSet(false, true)) {
				if (current > expiredTime) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								refresh();
							} finally {
								refreshFlag.set(false);
							}
						}
					}).start();
				}
			}
		}
	}

	protected void refresh() {
		defaultCooldowns = next(defaultCooldowns, DEFAULT_ID);
	}

	protected int[] genCooldown(int id) {
		List<Double> rates = actionCooldownConfigDao.getDownloadRate(id);
		int maxCount = actionCooldownConfigDao.getMaxDownloadPerHour(id);
		int[] cds = new int[rates.size()];
		for (int i = 0; i < rates.size(); i++) {
			double rate = rates.get(i);
			int count = (int) (rate * maxCount);
			int frequency = (int) (HOUR_MILLIS / count);//假设操作不耗时间，如果需要考虑则再减去操作耗时就是间隔
			cds[i] = RandomUtil.popple(frequency, frequency/10);
		}
		return cds;
	}
	
	private ThreeDayHourCooldowns next(ThreeDayHourCooldowns old, int id) {
		ThreeDayHourCooldowns rlt = new ThreeDayHourCooldowns();
		rlt.today = old.tomorrow;
		rlt.yesterday = old.today;
		rlt.todayMillis = old.todayMillis + DAY_MILLIS;
		rlt.tomorrow = genCooldown(id);
		return rlt;
	}

	static class ThreeDayHourCooldowns {
		int[] yesterday;
		int[] today;
		int[] tomorrow;
		long todayMillis;

		public int[] get(Date date) {
			long time = date.getTime();
			long dayOffset = (time + TIME_OFFSET) % DAY_MILLIS;
			if (dayOffset != 0) {
				logger.warn("illegal date argument:" + date);
				time -= dayOffset;
			}
			if(time>todayMillis) {
				if(time-DAY_MILLIS!=todayMillis) {
					logger.warn("illegal tomorrow time:" + time);
				}
				return tomorrow;
			}
			if(time<todayMillis) {
				if(time+DAY_MILLIS!=todayMillis) {
					logger.warn("illegal yesterday time:" + time);
				}
				return yesterday;
			}
			return today;
		}
	}
}
