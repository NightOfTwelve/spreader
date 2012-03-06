package com.nali.spreader.factory.config.extend;

public interface Exender<ExtendMeta, ExtendConfig> {
	String name();
	void extend(ExtendedBean obj, Long sid);
	ExtendMeta getExtendMeta(ExtendedBean obj);
	void saveExtendConfig(Long sid, ExtendConfig extendConfig);
	ExtendConfig getExtendConfig(Long sid);
}
