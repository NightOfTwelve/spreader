package com.nali.spreader.front;

import java.util.List;
import java.util.Map;

import com.nali.spreader.front.test.model.Book;

public interface ITestService2 {

	Integer getWrapper(int i);
	
	List<Long> steps(long i);
	
	int countString(List<String> strs);
	
	int countBook(List<Book> books);
	
	List<Book> splitBook(int i);
	
	Map<String, Book> library(List<Book> books);

	String[] toArray(List<String> strs);
	
	List<Integer> toList(int[] is);
	
	Book[] toBookArray(List<Book> books);
	
	List<Book> mapToList(Map<String, Book> books);

}
