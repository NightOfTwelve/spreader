package com.nali.spreader.aop.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.nali.spreader.aop.annotation.AuthAnnotation;
import com.nali.spreader.filter.AccountContext;

/**
 * 日志操作的切点类，预留权限检查
 * 
 * @author xiefei
 * 
 */
@Aspect
@Component
public class OpLogAspect extends BaseAspect {
	private static final Logger logger = Logger.getLogger(OpLogAspect.class);

	// 切点，所有带有AuthAnnotation注解的方法
	@Pointcut("execution (@com.nali.spreader.aop.annotation.AuthAnnotation * * (..))")
	public void authAnnotationPoint() {

	}

	@Around("authAnnotationPoint()")
	public Object aroundAdvice(ProceedingJoinPoint pj) {
		AccountContext ctx = AccountContext.getAccountContext();
		Object o = null;
		try {
			String paramStr = getParamsString(pj.getArgs());
			String userName = ctx.getUserName();
			HttpServletRequest request = ctx.getRequest();
			String url = request.getRequestURI();
			Method method = getMethod(pj);
			AuthAnnotation auth = AnnotationUtils.findAnnotation(method,
					AuthAnnotation.class);
			String opName = auth.opName();
			// 方法调用前支持权限检查，以后扩展 TODO
			o = pj.proceed();
			// 记录日志
			recordLog(opName, url, paramStr, userName);
		} catch (NoSuchMethodException e) {
			logger.error("get Method error", e);
		} catch (Throwable t) {
			logger.error("proceed error", t);
		}
		return o;
	}
}
