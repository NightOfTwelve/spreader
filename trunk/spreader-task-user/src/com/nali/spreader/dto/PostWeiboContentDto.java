package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nali.spreader.config.Range;

/**
 * 筛选微博内容条件
 * 
 * @author xiefei
 * 
 */
public class PostWeiboContentDto implements Serializable {

	private static final long serialVersionUID = 6786639746848467960L;
	// 关键字列表
	private List<String> sendKeywords;
	// 转发数
	private Range<Integer> atCount;
	// 是否图片
	private Boolean isPic;
	// 是否音频
	private Boolean isAudio;
	// 是否视频
	private Boolean isVideo;
	// 加V类型
	private Integer vType;
	// 内容长度
	private Range<Integer> contentLength;
	// 粉丝数
	private Range<Long> fans;
	// 文章数
	private Range<Long> articles;
	// 发布时间
	private Range<Date> pubDate;
	// 是否有筛选用户的标识符
	private Boolean userCondition;
	// 转发数
	private Range<Integer> refCount;
	// 回复数
	private Range<Integer> replyCount;

	public Range<Integer> getRefCount() {
		return refCount;
	}

	public void setRefCount(Range<Integer> refCount) {
		this.refCount = refCount;
	}

	public Range<Integer> getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Range<Integer> replyCount) {
		this.replyCount = replyCount;
	}

	public Boolean getUserCondition() {
		return userCondition;
	}

	public void setUserCondition(Boolean userCondition) {
		this.userCondition = userCondition;
	}

	public Range<Integer> getContentLength() {
		return contentLength;
	}

	public void setContentLength(Range<Integer> contentLength) {
		this.contentLength = contentLength;
	}

	public List<String> getSendKeywords() {
		return sendKeywords;
	}

	public void setSendKeywords(List<String> sendKeywords) {
		this.sendKeywords = sendKeywords;
	}

	public Range<Integer> getAtCount() {
		return atCount;
	}

	public void setAtCount(Range<Integer> atCount) {
		this.atCount = atCount;
	}

	public Boolean getIsPic() {
		return isPic;
	}

	public void setIsPic(Boolean isPic) {
		this.isPic = isPic;
	}

	public Boolean getIsAudio() {
		return isAudio;
	}

	public void setIsAudio(Boolean isAudio) {
		this.isAudio = isAudio;
	}

	public Boolean getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Boolean isVideo) {
		this.isVideo = isVideo;
	}

	public Integer getvType() {
		return vType;
	}

	public void setvType(Integer vType) {
		this.vType = vType;
	}

	public Range<Long> getFans() {
		return fans;
	}

	public void setFans(Range<Long> fans) {
		this.fans = fans;
	}

	public Range<Long> getArticles() {
		return articles;
	}

	public void setArticles(Range<Long> articles) {
		this.articles = articles;
	}

	public Range<Date> getPubDate() {
		return pubDate;
	}

	public void setPubDate(Range<Date> pubDate) {
		this.pubDate = pubDate;
	}
}
