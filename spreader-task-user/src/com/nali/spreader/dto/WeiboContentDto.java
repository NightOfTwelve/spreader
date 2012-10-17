package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

import com.nali.spreader.data.Content;

/**
 * 发微博内容的转换
 * 
 * @author xiefei
 * 
 */
public class WeiboContentDto implements Serializable {

	private static final long serialVersionUID = -21521429194563467L;
	private String audioUrl;
	private String videoUrl;
	private String picUrl;
	private Long robotUid;
	private String text;
	private Date postTime;
	private boolean forceTime=false;//强制发帖时间

	/**
	 * 构造一个发微博的DTO
	 * 
	 * @param c
	 * @param postTime
	 * @return
	 */
	public static WeiboContentDto getWeiboContentDto(Long robotId, Content c, Date postTime) {
		WeiboContentDto dto = new WeiboContentDto();
		dto.setAudioUrl(c.getAudioUrl());
		dto.setPicUrl(c.getPicUrl());
		dto.setVideoUrl(c.getVideoUrl());
		dto.setPostTime(postTime);
		dto.setRobotUid(robotId);
		dto.setText(c.getContent());
		return dto;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getRobotUid() {
		return robotUid;
	}

	public void setRobotUid(Long robotUid) {
		this.robotUid = robotUid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public boolean isForceTime() {
		return forceTime;
	}

	public void setForceTime(boolean forceTime) {
		this.forceTime = forceTime;
	}
}
