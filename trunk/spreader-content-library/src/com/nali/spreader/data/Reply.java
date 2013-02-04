package com.nali.spreader.data;

import java.io.Serializable;

public class Reply implements Serializable {
	private static final long serialVersionUID = 6463020910942239002L;

	private Long id;

	private String content;

	private Integer atCount;

	private Integer reReplyCount;

	private Integer topicCount;

	private Integer contentLength;

	private Integer useCount;

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAtCount() {
		return atCount;
	}

	public void setAtCount(Integer atCount) {
		this.atCount = atCount;
	}

	public Integer getReReplyCount() {
		return reReplyCount;
	}

	public void setReReplyCount(Integer reReplyCount) {
		this.reReplyCount = reReplyCount;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getContentLength() {
		return contentLength;
	}

	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
