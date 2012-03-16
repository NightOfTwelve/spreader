package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class RobotReplyListDto implements Serializable {
	private static final long serialVersionUID = 8013619439084496922L;
	@PropertyDescription("回复人数")
	private Range<Integer> count;
	@PropertyDescription("微博地址")
	private List<String> urlList;
	@PropertyDescription("语句（留空则使用默认语句回复）")
	private List<String> words;
	@PropertyDescription("回复并转发百分比")
	private Integer needForwardPercent;
	public Range<Integer> getCount() {
		return count;
	}
	public void setCount(Range<Integer> count) {
		this.count = count;
	}
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	public Integer getNeedForwardPercent() {
		return needForwardPercent;
	}
	public void setNeedForwardPercent(Integer needForwardPercent) {
		this.needForwardPercent = needForwardPercent;
	}
}
