package com.nali.spreader.factory.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.util.MapList;

public abstract class AbstractConfigService<K> implements IConfigService<K> {
	private MapList<ConfigableType, ConfigableInfo> typeInfos = new MapList<ConfigableType, ConfigableInfo>();
	private Map<String, ConfigableInfo> configableInfos = new HashMap<String, ConfigableInfo>();

	protected void registerConfigableInfo(String name, Class<?> clazz) {
		ConfigableInfo configableInfo = DescriptionResolve.getConfigableInfo(clazz, name);
		ExtendBinder extendBinder = getConfigableCenter().getExtendBinder(name);
		if(extendBinder!=null) {
			configableInfo.setExtendType(extendBinder.getExtenderName());
			configableInfo.setExtendMeta(extendBinder.getExtendMeta());
		}
		ConfigableType configableType = getConfigableType(clazz);
		configableInfo.setConfigableType(configableType);
		typeInfos.put(configableType, configableInfo);
		configableInfos.put(name, configableInfo);
	}
	
	protected ConfigableType getConfigableType(Class<?> clazz) {
		if (SpecialConfigable.class.isAssignableFrom(clazz)) {
			SpecialConfigableType annotation = AnnotationUtils.findAnnotation(clazz, SpecialConfigableType.class);
			if(annotation==null) {
				throw new IllegalArgumentException("@SpecialConfigableType doesnot present at class:" + clazz);
			}
			return annotation.value();
		}
		return ConfigableType.normal;
	}

	@Override
	public List<ConfigableInfo> listConfigableInfo(ConfigableType configableType) {
		return typeInfos.getMap().get(configableType);
	}

	@Override
	public ConfigableInfo getConfigableInfo(String name) {
		return configableInfos.get(name);
	}

	@Override
	public ConfigDefinition getConfigDefinition(String name) {
		return getConfigableCenter().getConfigDefinition(name);
	}
	
	protected abstract IConfigableCenter getConfigableCenter();

}
