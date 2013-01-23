package com.nali.spreader.util.data.impl;

import java.net.URL;

import com.nali.spreader.util.data.Converter;

public class TxtToRedisParameterConverters {
	private String redisPrefix;
	private String txtDir;
	private ClassLoader cl;
	public final Converter<String, URL> pToOp = new Converter<String, URL>() {
		@Override
		public URL tran(String name) {
			return cl.getResource(txtDir + name + ".txt");
		}
	};
	public final Converter<String, String> pToCp = new Converter<String, String>() {
		@Override
		public String tran(String name) {
			return redisPrefix + name;
		}
	};
	public TxtToRedisParameterConverters(String redisPrefix, String txtDir, ClassLoader cl) {
		super();
		this.redisPrefix = redisPrefix;
		if(txtDir.endsWith("/") || txtDir.endsWith("\\")) {
			this.txtDir = txtDir;
		} else {
			this.txtDir = txtDir + "/";
		}
		this.cl = cl;
	}
	public TxtToRedisParameterConverters(String redisPrefix, String txtDir, Class<?> c) {
		this(redisPrefix, txtDir, c.getClassLoader());
	}
	public TxtToRedisParameterConverters(String redisPrefix, String txtDir) {
		this(redisPrefix, txtDir, Thread.currentThread().getContextClassLoader());
	}

}
