package com.nali.spreader.util.test;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.ContextConfiguration;

public class PojoClassRunner extends BlockJUnit4ClassRunner {

	public PojoClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
		ContextConfiguration annotation = klass.getAnnotation(ContextConfiguration.class);
		if(annotation!=null) {
			String[] value = annotation.value();
			if(value!=null && value.length>0) {
				URL cfg = klass.getResource(value[0]);
				PropertyConfigurator.configure(cfg);
			}
			return;
		}
		URL cfg = klass.getResource("/log4j.properties");
		if(cfg!=null) {
			PropertyConfigurator.configure(cfg);
		}
	}

}
