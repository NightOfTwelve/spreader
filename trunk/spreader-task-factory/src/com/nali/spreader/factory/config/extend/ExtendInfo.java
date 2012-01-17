package com.nali.spreader.factory.config.extend;

public class ExtendInfo {
	private Long id;
	private String extendType;
	private String extendConfig;
	public ExtendInfo(Long id, String extendType, String extendConfig) {
		super();
		this.id = id;
		this.extendType = extendType;
		this.extendConfig = extendConfig;
	}
	public boolean isExtend() {
		return extendType!=null && !extendType.equals("");
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExtendType() {
		return extendType;
	}
	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}
	public String getExtendConfig() {
		return extendConfig;
	}
	public void setExtendConfig(String extendConfig) {
		this.extendConfig = extendConfig;
	}
}
