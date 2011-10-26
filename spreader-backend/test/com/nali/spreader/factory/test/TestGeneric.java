package com.nali.spreader.factory.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;

@SuppressWarnings("serial")
public class TestGeneric {
	public static void main(String[] args) {
		Class<?>[] resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(A.class, List.class);
		System.out.println(Arrays.asList(resolveTypeArguments));
		
		resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(B.class, Map.class);
		System.out.println(Arrays.asList(resolveTypeArguments));
	}
	class A extends ArrayList<String> {
		
	}
	class B extends HashMap<String, List<String>> {
		
	}
}
