package com.nali.spreader.front;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.test.model.Book;
import com.nali.spreader.front.test.model.User;

@Service
public class TestService implements ITestService{
	@Autowired
	private ITestService2 t2;
	
	@PostConstruct
	public void init() {
		System.out.println("init TestService");
	}
	
	public ITestService2 forTest() {
		return t2;
	}

	@Override
	public String echo(int i) {
		return i+"";
	}

	@Override
	public Book findBook(String name, int page) {
		Book book = new Book();
		book.setName(name);
		book.setPage(page);
		return book;
	}

	@Override
	public String getString() {
		return "i'm a string.";
	}

	@Override
	public User getUserWithBook(Date birthday) {
		User user = new User();
		user.setAge(11);
		user.setName("wahaha");
		user.setBirthday(birthday);
		Book book = new Book();
		book.setName("100000 why");
		book.setPage(10000);
		user.setBook(book);
		return user;
	}

}
