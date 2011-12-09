package com.nali.spreader.factory.passive;

import java.lang.reflect.Type;
import java.util.Set;

import com.nali.spreader.util.autowire.ProxyAnnotationResolver;

/**
	@Deprecated 所有相关的依赖都不能用@Autowired
 */
@Deprecated
public class ProductLineAnnotationResolver extends ProxyAnnotationResolver<AutowireProductLine, PassiveObject> {
	private PassiveProducerManager passiveProducerManager;

	@Override
	protected Object wrap(PassiveObject proxied, Set<String> beanNames, Type type) {
		String beanName = beanNames.iterator().next();
		return passiveProducerManager.getProduceLine(beanName, proxied, type);
	}

	public void setPassiveProducerManager(PassiveProducerManager passiveProducerManager) {
		this.passiveProducerManager = passiveProducerManager;
	}
}
