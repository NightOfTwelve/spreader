package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

import com.nali.common.model.Limit;

public class ContentQueryParamsDto implements Serializable {

	private static final long serialVersionUID = 7106488306283460918L;
	/**
	 * 微博类型
	 */
	private String categoryName;
	/**
	 * 微博发布时间 起始
	 */
	private Date sPubDate;
	/**
	 * 微博发布时间 结束
	 */
	private Date ePubDate;
	/**
	 * 微博爬取时间 起始
	 */
	private Date sSyncDate;
	/**
	 * 微博爬取时间 结束
	 */
	private Date eSyncDate;

	private Limit limit;
	/**
	 * 微博作者
	 */
	private String userName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getsPubDate() {
		return sPubDate;
	}

	public void setsPubDate(Date sPubDate) {
		this.sPubDate = sPubDate;
	}

	public Date getePubDate() {
		return ePubDate;
	}

	public void setePubDate(Date ePubDate) {
		this.ePubDate = ePubDate;
	}

	public Date getsSyncDate() {
		return sSyncDate;
	}

	public void setsSyncDate(Date sSyncDate) {
		this.sSyncDate = sSyncDate;
	}

	public Date geteSyncDate() {
		return eSyncDate;
	}

	public void seteSyncDate(Date eSyncDate) {
		this.eSyncDate = eSyncDate;
	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}
}
