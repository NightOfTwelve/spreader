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
	accountError(10001),//帐号无法使用了
	unknownPage(10002),//无法解析的页面
	ipForbidden(10003),//ip被封
	taskCancel(10004),//任务被取消了，一般由于用户关闭客户端导致
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
