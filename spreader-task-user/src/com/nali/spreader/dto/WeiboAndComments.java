package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 包含微博内容，评论列表及评论列表的页数
 * 
 * @author xiefei
 * 
 */
public class WeiboAndComments implements Serializable {
	private static final long serialVersionUID = 5708374475158392680L;

	private int page;

	private String weibo;

	private List<String> comments;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
}
