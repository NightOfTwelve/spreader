package com.nali.spreader.dto;

import java.util.ArrayList;
import java.util.List;

import com.nali.spreader.util.random.WeightRandomer;

/**
 * 封装上传头像调用的参数
 * 
 * @author xiefei
 * 
 */
public class UploadAvatarDto {
	// 用户ID
	private Long uid;
	// 机器人ID
	private Long robotId;
	// 性别
	private Integer gender;
	// 是否设置成功
	private Boolean success;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRobotId() {
		return robotId;
	}

	public void setRobotId(Long robotId) {
		this.robotId = robotId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public static void main(String arge[]) {
		List<String> l = new ArrayList<String>();
		l.add("a");
		l.add("b");
		l.add("c");
		List<String> l2 = new ArrayList<String>();
		l2.add("e");
		l2.add("f");
		l2.add("g");
		List<String> l3 = new ArrayList<String>();
		l3.add("h");
		l3.add("i");
		l3.add("j");
		WeightRandomer<List<String>> wr = new WeightRandomer<List<String>>();
		wr.add(l, 30);
		wr.add(l2, 40);
		wr.add(l3, 30);
		List<String> x = wr.get();
		System.out.println(x);
	}
}
