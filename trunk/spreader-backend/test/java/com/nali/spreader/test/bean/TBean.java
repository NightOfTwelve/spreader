package com.nali.spreader.test.bean;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TBean {

	@PostConstruct
	public void i() {
		System.out.println("bean");
	}
}
