package com.nali.spreader.front.test.model;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = -4518755354813093441L;

	private String name;
	private int page;
	public Book() {
		super();
	}
	public Book(String name, int page) {
		super();
		this.name = name;
		this.page = page;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
