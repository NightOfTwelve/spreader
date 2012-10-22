package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 关键字查出微博并执行回复和关注的配置
 * 
 * @author xiefei
 * 
 */
public class KeywordReplyAndAddConfig implements Serializable {
	private static final long serialVersionUID = 2331722293272578783L;
	// 默认被关注上限
	public static final int DEFAULT_ADD_LIMIT = 1;
	// 默认执行关注的上限
	public static final int DEFAULT_EXECU_ADD_LIMIT = 1;
	@PropertyDescription("关键字")
	private List<String> keywords;
	@PropertyDescription("关键字分类")
	private List<String> categories;
	@PropertyDescription("新鲜度(分钟)")
	private Integer effective;
	@PropertyDescription("关注后是否需要回复微博")
	private Boolean needReply;
	@PropertyDescription("机器人执行关注并回复的间隔时间(分钟)")
	private Integer execuInterval;
	@PropertyDescription("机器人已关注的次数上限(超过此上限该机器人不再分派任务)")
	private Long attentionLimit;
	@PropertyDescription("微博作者被关注的次数上限(单次)")
	private Integer addCount;
	@PropertyDescription("机器人执行关注的次数上限(单次)")
	private Integer execuAddCount;
	@PropertyDescription("是否包含图片")
	private Boolean isPic;
	@PropertyDescription("是否包含音频")
	private Boolean isAudio;
	@PropertyDescription("是否包含视频")
	private Boolean isVideo;
	@PropertyDescription("@数量")
	private Range<Integer> atCount;
	@PropertyDescription("内容长度")
	private Range<Integer> contentLength;
	@PropertyDescription("转发数")
	private Range<Integer> refCount;
	@PropertyDescription("回复数")
	private Range<Integer> replyCount;
	@PropertyDescription("语句（留空则使用默认语句回复）")
	private List<String> words;

	public PostWeiboContentDto getPostWeiboContentDto(Long[] keywords) {
		PostWeiboContentDto query = new PostWeiboContentDto();
		query.setKeywords(keywords);
		// 是否图片
		query.setIsPic(this.getIsPic());
		// 是否音频
		query.setIsAudio(this.getIsAudio());
		// 是否视频
		query.setIsVideo(this.getIsVideo());
		// 内容长度
		query.setContentLength(this.getContentLength());
		// 处理内容的新鲜度,发布时间
		Integer effTime = this.getEffective();
		if (effTime == null) {
			effTime = PostWeiboConfig.DEFAULT_EFFTIME;
		}
		Range<Date> pubDate = new Range<Date>();
		pubDate.setGte(DateUtils.addMinutes(new Date(), -1 * effTime));
		query.setPubDate(pubDate);
		query.setRefCount(this.getRefCount());
		query.setReplyCount(this.getReplyCount());
		return query;
	}

	public Long getAttentionLimit() {
		return attentionLimit;
	}

	public void setAttentionLimit(Long attentionLimit) {
		this.attentionLimit = attentionLimit;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Integer getExecuInterval() {
		return execuInterval;
	}

	public void setExecuInterval(Integer execuInterval) {
		this.execuInterval = execuInterval;
	}

	public Boolean getNeedReply() {
		return needReply;
	}

	public void setNeedReply(Boolean needReply) {
		this.needReply = needReply;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public Integer getAddCount() {
		return addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	public Integer getExecuAddCount() {
		return execuAddCount;
	}

	public void setExecuAddCount(Integer execuAddCount) {
		this.execuAddCount = execuAddCount;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Integer getEffective() {
		return effective;
	}

	public void setEffective(Integer effective) {
		this.effective = effective;
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

	public Range<Integer> getAtCount() {
		return atCount;
	}

	public void setAtCount(Range<Integer> atCount) {
		this.atCount = atCount;
	}

	public Range<Integer> getContentLength() {
		return contentLength;
	}

	public void setContentLength(Range<Integer> contentLength) {
		this.contentLength = contentLength;
	}

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
}