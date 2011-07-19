package com.nali.spreader.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.nali.spreader.front.test.model.Book;

@Service
public class TestService2 implements ITestService2 {

	@Override
	public int countBook(List<Book> books) {
		System.out.println(books.get(0).getName());
		System.out.println(books.get(0).getPage());
		return books.size();
	}

	@Override
	public int countString(List<String> strs) {
		System.out.println(strs.get(0));
		return strs.size();
	}

	@Override
	public Integer getWrapper(int i) {
		return i+1;
	}

	@Override
	public Map<String, Book> library(List<Book> books) {
		HashMap<String, Book> rlt = new HashMap<String, Book>();
		for (Book book : books) {
			rlt.put(book.getName(), book);
		}
		return rlt;
	}

	@Override
	public List<Book> splitBook(int i) {
		List<Book> rlt = new ArrayList<Book>();
		for (int j = 0; j < Math.min(i, 10); j++) {
			Book b = new Book("book"+j, 1);
			rlt.add(b);
		}
		return rlt;
	}

	@Override
	public List<Long> steps(long i) {
		List<Long> rlt = new LinkedList<Long>();
		for (int j = 0; j < Math.min(i, 10); j++) {
			rlt .add(j*i/Math.min(i, 10));
		}
		return rlt;
	}

	@Override
	public String[] toArray(List<String> strs) {
		String[] ss=new String[strs.size()];
		return strs.toArray(ss);
	}

	@Override
	public Book[] toBookArray(List<Book> books) {
		Book[] ss=new Book[books.size()];
		return books.toArray(ss);
	}

	@Override
	public List<Integer> toList(int[] is) {
		List<Integer> rlt = new LinkedList<Integer>();
		for (int j = 0; j < is.length; j++) {
			rlt .add(j);
		}
		return rlt;
	}

	@Override
	public List<Book> mapToList(Map<String, Book> books) {
		List<Book> list = new ArrayList<Book>();
		for (Entry<String, Book> entry : books.entrySet()) {
			//TODO
		}
		return list;
	}
}
