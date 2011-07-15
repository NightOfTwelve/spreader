package com.nali.spreader.front;

import java.util.Date;

import com.nali.spreader.front.test.model.Book;
import com.nali.spreader.front.test.model.User;

public interface ITestService {

	String getString();
	
	String echo(int i);
	
	Book findBook(String name, int page);
	
	User getUserWithBook(Date birthday);
}
