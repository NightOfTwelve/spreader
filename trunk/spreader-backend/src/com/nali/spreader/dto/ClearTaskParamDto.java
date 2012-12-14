package com.nali.spreader.dto;

import java.io.Serializable;

public class ClearTaskParamDto implements Serializable {
	private static final long serialVersionUID = -1086781652463073971L;
	private String tables;
	private String columns;
	private Integer days;

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}
