package com.nali.spreader.analyzer.ximalaya;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("喜马拉雅·导入符合条件的喜马拉雅账户")
public class XimalayaUserImport implements RegularAnalyzer,
		Configable<XimalayaUserImport.QueryXimalayaConfig> {
	private QueryXimalayaConfig config;

	@AutowireProductLine
	private TaskProduceLine<QueryXimalayaConfig> importXimalayaAccount;

	@Override
	public void init(XimalayaUserImport.QueryXimalayaConfig config) {
		this.config = config;
		Integer maxSize = config.getMaxSize();
		if (maxSize == null || maxSize.intValue() <= 0) {
			throw new IllegalArgumentException(" maxSize is null ");
		}
		Integer limitSize = config.getLimitSize();
		if (limitSize == null || limitSize.intValue() <= 0) {
			this.config.setLimitSize(QueryXimalayaConfig.DEFAULT_LIMIT);
		}
	}

	@Override
	public String work() {
		int maxSize = config.getMaxSize();
		int limit = config.getLimitSize();
		int offset = 0;
		do {
			config.setOffset(offset);
			importXimalayaAccount.send(config);
			offset = offset + limit;
		} while (offset < maxSize);
		return null;
	}

	public static class QueryXimalayaConfig implements Serializable {
		private static final long serialVersionUID = -8862800309245390094L;
		public static final int DEFAULT_LIMIT = 1000;
		@PropertyDescription("关键字")
		private String keyword;
		// @PropertyDescription("大于等于的粉丝数")
//		 private Long fansGte;
		// @PropertyDescription("小于等于的粉丝数")
		// private Long fansLte;
		@PropertyDescription("加V类型")
		private Integer vType;
		@PropertyDescription("大于等于的帐号创建时间")
		private Date startCreateTime;
		@PropertyDescription("小于等于的帐号创建时间")
		private Date endCreateTime;
		@PropertyDescription("大于等于的帐号更新时间")
		private Date startUpateTime;
		@PropertyDescription("小于等于的帐号更新时间")
		private Date endUpdateTime;
		@PropertyDescription("一共需要爬取多少条数据")
		private Integer maxSize;
		@PropertyDescription("每次爬取多少条数据")
		private Integer limitSize;
		private Integer offset;

		public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		// public Long getFansGte() {
		// return fansGte;
		// }
		//
		// public void setFansGte(Long fansGte) {
		// this.fansGte = fansGte;
		// }
		//
		// public Long getFansLte() {
		// return fansLte;
		// }
		//
		// public void setFansLte(Long fansLte) {
		// this.fansLte = fansLte;
		// }

		public Integer getMaxSize() {
			return maxSize;
		}

		public void setMaxSize(Integer maxSize) {
			this.maxSize = maxSize;
		}

		public Integer getLimitSize() {
			return limitSize;
		}

		public void setLimitSize(Integer limitSize) {
			this.limitSize = limitSize;
		}

		public Integer getvType() {
			return vType;
		}

		public void setvType(Integer vType) {
			this.vType = vType;
		}

		public Date getStartCreateTime() {
			return startCreateTime;
		}

		public void setStartCreateTime(Date startCreateTime) {
			this.startCreateTime = startCreateTime;
		}

		public Date getEndCreateTime() {
			return endCreateTime;
		}

		public void setEndCreateTime(Date endCreateTime) {
			this.endCreateTime = endCreateTime;
		}

		public Date getStartUpateTime() {
			return startUpateTime;
		}

		public void setStartUpateTime(Date startUpateTime) {
			this.startUpateTime = startUpateTime;
		}

		public Date getEndUpdateTime() {
			return endUpdateTime;
		}

		public void setEndUpdateTime(Date endUpdateTime) {
			this.endUpdateTime = endUpdateTime;
		}
	}
}
