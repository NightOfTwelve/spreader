package com.nali.spreader.test.bean;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class TBuff {

	@PostConstruct
	public void i() {
//		System.out.println("buff");
	}
}
