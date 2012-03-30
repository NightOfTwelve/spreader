package com.nali.spreader.stat;

import java.util.List;

public class BaseStatMeta {
	private String name;
	private String disName;
	private String sqlmap;
	private ExtendStatMeta extendMeta;
	private List<String> columnNames;
	private List<String> columnDisNames;
	
	public boolean check() {
		return name!=null&&disName!=null&&sqlmap!=null&&columnNames!=null&&columnDisNames!=null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisName() {
		return disName;
	}
	public void setDisName(String disName) {
		this.disName = disName;
	}
	public ExtendStatMeta getExtendMeta() {
		return extendMeta;
	}
	public void setExtendMeta(ExtendStatMeta extendMeta) {
		this.extendMeta = extendMeta;
	}
	public String getSqlmap() {
		return sqlmap;
	}
	public void setSqlmap(String sqlmap) {
		this.sqlmap = sqlmap;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<String> getColumnDisNames() {
		return columnDisNames;
	}
	public void setColumnDisNames(List<String> columnDisNames) {
		this.columnDisNames = columnDisNames;
	}
}
