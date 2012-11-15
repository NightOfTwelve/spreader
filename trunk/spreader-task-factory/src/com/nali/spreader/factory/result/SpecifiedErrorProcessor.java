package com.nali.spreader.factory.result;

import java.lang.reflect.TypeVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.util.reflect.GenericInfo;

public abstract class SpecifiedErrorProcessor<R, TM extends TaskMeta, RP extends ResultProcessor<?, TM>> implements ErrorProcessor<R, TM>{
	private TM taskMeta;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Autowired
	public void setContext(ApplicationContext context) {
		GenericInfo genericInfo = GenericInfo.get(getClass());
		TypeVariable rpGenericVariable = SpecifiedErrorProcessor.class.getTypeParameters()[2];
		Class<ResultProcessor> rpClass = (Class<ResultProcessor>) genericInfo.getGeneric(rpGenericVariable);
		ResultProcessor rp = context.getBean(rpClass);
		taskMeta = (TM) rp.getTaskMeta();
	}
	
	@Override
	public TM getTaskMeta() {
		return taskMeta;
	}

}
