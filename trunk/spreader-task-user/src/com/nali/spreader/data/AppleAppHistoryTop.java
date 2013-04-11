package com.nali.spreader.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * APP历史排名
 * 
 * @author xiefei
 * 
 */
public class AppleAppHistoryTop implements Serializable {
	private static final long serialVersionUID = -4170820691690234221L;
	private Long appId;
	private int genreId;
	private String createDate;
	private Long rankCount;
	private Long updateCount;
	// iphone ipad
	private int popId;
	private List<Map<String, Object>> ranking;

	public int getPopId() {
		return popId;
	}

	public void setPopId(int popId) {
		this.popId = popId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getRankCount() {
		return rankCount;
	}

	public void setRankCount(Long rankCount) {
		this.rankCount = rankCount;
	}

	public Long getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Long updateCount) {
		this.updateCount = updateCount;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<Map<String, Object>> getRanking() {
		return ranking;
	}

	public void setRanking(List<Map<String, Object>> ranking) {
		this.ranking = ranking;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
