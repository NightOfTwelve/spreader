package com.nali.spreader.constants;

/**
 * TaskErrorCode<br>&nbsp;
 * abcde
 * a：异常范围，1,通用异常;2,具体任务异常
 * b：业务类型，0,其他类型;
 * @author sam Created on 2012-2-1
 */
public enum TaskErrorCode {
	runtimeException(10000),//客户端遇到RuntimeException
	accountError(10001),//帐号无法使用了  已暂停
	unknownPage(10002),//无法解析的页面
	ipForbidden(10003),//ip被封
	taskCancel(10004),//任务被取消了，一般由于用户关闭客户端导致
	accountWarning(10005),//帐号需要输入验证码
	contentNotFound(10006),//内容找不到,Content(至少设置好websiteUid,entry)
	userHomePageError(10007),//用户首页不能访问 tmp
	targetUserAccountError(10008),//目标用户被封，KeyValue<WebsiteId, WebsiteUid>
	accountBlock(10009),//帐号无法使用了
	;
	private final String code;
	private TaskErrorCode(int code) {
		this(code+"");
	}
	private TaskErrorCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return name();
	}
}
