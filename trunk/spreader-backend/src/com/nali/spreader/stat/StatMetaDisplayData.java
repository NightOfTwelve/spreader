package com.nali.spreader.stat;

import java.util.List;

public class StatMetaDisplayData {
	private BaseStatMeta baseStatMeta;
	public StatMetaDisplayData(BaseStatMeta baseStatMeta) {
		super();
		this.baseStatMeta = baseStatMeta;
	}
	public String getName() {
		return baseStatMeta.getName();
	}
	public String getDisName() {
		return baseStatMeta.getDisName();
	}
	public String getExtendCode() {
		ExtendStatMeta extendMeta = baseStatMeta.getExtendMeta();
		return extendMeta==null?null:extendMeta.getCode();
	}
	public List<String> getColumnNames() {
		return baseStatMeta.getColumnNames();
	}
	public List<String> getColumnDisNames() {
		return baseStatMeta.getColumnDisNames();
	}
}
