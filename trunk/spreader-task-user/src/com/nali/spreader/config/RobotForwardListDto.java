package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class RobotForwardListDto implements Serializable {
	private static final long serialVersionUID = 8013619439084496922L;
	@PropertyDescription("转发次数")
	private Range<Integer> count;
	@PropertyDescription("微博地址")
	private List<String> urlList;
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
}
