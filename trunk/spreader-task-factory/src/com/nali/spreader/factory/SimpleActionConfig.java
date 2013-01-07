package com.nali.spreader.factory;

public enum SimpleActionConfig {
	fetchWeiboStarUser(1L),
	fetchWeiboUserMainPage(2L),
	fetchUserAttentions(7L),//3有人用
	//registerRobotUserEmail(8L),
	registerWeibo(9L),
	activeWeibo(10L),
	fetchWeiboContent(11L),
	postWeiboContent(12L),
	addUserFans(13L),
	forwardWeiboContent(14L),
	replyWeibo(15L),
	uploadAvatar(16L),
	addWeiboTag(17L),
	updateRobotUserInfo(18L),
	firstTimeGuide(19L),
	weiboAppeal(21L),
	confirmWeiboAppeal(22L),
	fetchKeywordEntrance(23L),
	fetchKeyword(24L),
	fetchKeywordContent(25L),
	fetchNotice(26L),
	
	//apple
	registerApple(2001L),
	activeApp(2002L),
	//2003 是apple登录actionId
	downloadApp(2004L),
	registerWebApple(2005L),
	registerCnApple(2006L),
	commentApple(2007L),
	returnVisitApple(2008L),
	//ximalaya
	registerXimalaya(3001L),
	addFansXimalaya(3002L),
	// 3003喜马拉雅登录被占用
	importXimalaya(3004L),
	recordXimalayaByNickName(3005L),
	recordXimalayaByUid(3006L),
	;
	private Long actionId;
	private String taskCode;
	private SimpleActionConfig(String taskCode, Long actionId) {
		this.actionId = actionId;
		setTaskCode(taskCode);//TODO 对code加密
	}
	private SimpleActionConfig(Long actionId) {
		this.actionId = actionId;
		setTaskCode(name());
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
