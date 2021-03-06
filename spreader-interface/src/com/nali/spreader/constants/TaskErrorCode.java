package com.nali.spreader.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TaskErrorCode<br>&nbsp;
 * abcde
 * a：异常范围，1,通用异常;2,具体任务异常
 * b：业务类型，0,其他类型;
 * @author sam Created on 2012-2-1
 */
public enum TaskErrorCode {
	runtimeException(10000, "客户端遇到RuntimeException"),
	accountError(10001, "帐号无法使用了  已暂停"),
	unknownPage(10002, "无法解析的页面"),
	ipForbidden(10003, "ip被封"),
	taskCancel(10004, "任务被取消了，一般由于用户关闭客户端导致"),
	accountWarning(10005, "帐号需要输入验证码"),
	contentNotFound(10006, "内容找不到,Content(至少设置好websiteUid,entry)"),
	userHomePageError(10007, "用户首页不能访问 tmp"),
	targetUserAccountError(10008, "目标用户被封，KeyValue<WebsiteId, WebsiteUid>"),
	accountBlock(10009, "帐号无法使用了"),
	needAppeal(10010, "需要申诉"),
	emailAccountError(10011, "邮箱密码错误"),
	emailHasBeenUsed(10012, "邮箱已被使用"),
	retryTooMuch(10013, "重试过多放弃"),
	seemRevised(10014, "似乎改版了"),
	noActiveEmail(10015, "没有收到激活邮件"),
	
	//2开头的微博异常
	//201注册异常
	personIdAbused(20101, "身份证使用太多了"),
	personIdWrong(20102, "身份证号码和姓名对应错误"),
	//202权限异常
	notARobot(20200, "不是一个机器人"),
	accountPwdError(20201, "帐号密码错误"),
	accountReadOnly(20202, "帐号只读"),
	//203其他异常
	targetReadOnly(20303, "目标内容只读"),
	postLimit(20304, "发帖太多了"),
	adError(20305, "被识别成广告了"),
	//3开头的苹果异常
	notAPurchasedApp(30101, "未购买应用"),
	unknownQuetion(30102, "未找到安全问题"),
	;
	private final String code;
	private final String msg;
	private static HashMap<String, String> innerMsgMap = new HashMap<String, String>();
	public static final Map<String, String> msgMap = Collections.unmodifiableMap(innerMsgMap);
	private TaskErrorCode(int code, String msg) {
		this(code+"", msg);
	}
	private TaskErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return name();
	}
	public String getMsg() {
		return msg;
	}
	static {
		TaskErrorCode[] values = TaskErrorCode.values();
		for (TaskErrorCode taskErrorCode : values) {
			innerMsgMap.put(taskErrorCode.getCode(), taskErrorCode.getMsg());
		}
	}
}
