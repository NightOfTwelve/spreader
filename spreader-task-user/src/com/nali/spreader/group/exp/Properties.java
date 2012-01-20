package com.nali.spreader.group.exp;


public enum Properties {
	category(1, "分类"), score(2, "评分"), fans(4, "粉丝数"), attentions(
			8, "关注数"), articles(16, "文章数"), gender(32, "性别"), robotFans(64,
			"机器人粉丝数"), constellation(128, "星座"), birthDay(256, "出生日期"), isRobot(
			512, "是否机器人"), vType(1024, "是否认证"), nickName(2048, "昵称"), nationality(
			4096, "国家"), province(8192, "省份"), city(16384, "城市"), introduction(
			32768, "个人简介");

	private int propVal;
	private String propName;

	private Properties(int propVal, String propName) {
		this.propVal = propVal;
		this.propName = propName;
	}

	public int getPropVal() {
		return propVal;
	}

	public void setPropVal(int propVal) {
		this.propVal = propVal;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}
}
