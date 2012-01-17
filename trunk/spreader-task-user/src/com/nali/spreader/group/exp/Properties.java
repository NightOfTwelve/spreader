package com.nali.spreader.group.exp;


public enum Properties {
	website(1, "网站"), category(2, "分类"), score(4, "评分"), fans(8, "粉丝数"), attentions(
			16, "关注数"), articles(32, "文章数"), gender(64, "性别"), robotFans(128,
			"机器人粉丝数"), constellation(256, "星座"), birthDay(512, "出生日期"), isRobot(
			1024, "是否机器人"), vType(2056, "是否认证"), nickName(4112, "昵称"), nationality(
			8224, "国家"), province(16448, "省份"), city(32896, "城市"), introduction(
			65792, "个人简介");

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
