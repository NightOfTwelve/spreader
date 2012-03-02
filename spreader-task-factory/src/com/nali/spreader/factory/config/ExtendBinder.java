package com.nali.spreader.factory.config;

class ExtendBinder {
	private String extenderName;
	private Object extendMeta;
	public ExtendBinder(String extenderName, Object extendMeta) {
		super();
		this.extenderName = extenderName;
		this.extendMeta = extendMeta;
	}
	public String getExtenderName() {
		return extenderName;
	}
	public void setExtenderName(String extenderName) {
		this.extenderName = extenderName;
	}
	public Object getExtendMeta() {
		return extendMeta;
	}
	public void setExtendMeta(Object extendMeta) {
		this.extendMeta = extendMeta;
	}
}
