package com.nali.spreader.serialize;

import java.io.InputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

public class XstreamSerializer implements ISerializer{
	private XStream xstream = new XStream();
	
	public XstreamSerializer() {
		
	}
	
	public XstreamSerializer(String clazzAlias, Class<?> clazz) {
		this.xstream.alias(clazzAlias, clazz);
	}
	
	public XstreamSerializer(String clazzAlias, Class<?> clazz, String listAlias) {
		this.xstream.alias(clazzAlias, clazz);
		this.xstream.alias(listAlias, ArrayList.class);
	}
	
	public void addAlias(String name, Class<?> type) {
		this.xstream.alias(name, type);
	}
	

	@Override
	public <T> T toBean(String content) throws Exception {
		return (T) this.xstream.fromXML(content);
	}

	@Override
	public <T> T toBean(InputStream ins) throws Exception {
		return (T) this.xstream.fromXML(ins);
	}

	@Override
	public <T> String toString(T object) throws Exception {
		return this.xstream.toXML(object);
	}
}
