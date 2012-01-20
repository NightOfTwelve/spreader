package com.nali.spreader.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class TestJackson {
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		List<Integer> value = new ArrayList<Integer>();
		value.add(1);
		value.add(2);
		String json = jacksonMapper.writeValueAsString(value);
		System.out.println(json);
		
		Object readValue = jacksonMapper.readValue(json, TypeFactory.collectionType(List.class, Long.class));
		System.out.println(readValue);
		System.out.println(readValue.getClass());
		System.out.println(((List<?>)readValue).get(0).getClass());
		
		Foo foo = new Foo();
		foo.name = "test";
		Xoo xoo = new Xoo();
		xoo.name = "test2";
		foo.x = xoo;
		xoo.f = foo;
//		System.out.println(jacksonMapper.writeValueAsString(foo)); //error
		System.out.println(jacksonMapper.writeValueAsString(new Date()));
		System.out.println(jacksonMapper.readValue("1321514663578", Date.class));
	}
}
