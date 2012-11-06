package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependencyAnalyzer<T> {
	private static final List<Object> ASSEMBLED = Collections.emptyList();
	private Map<T, List<T>> dependsMap;
	private Set<T> assembling = new HashSet<T>();

	public DependencyAnalyzer(Map<T, List<T>> dependsMap) {
		super();
		this.dependsMap = dependsMap;
	}

	public static <T> List<T> getDependsOrder(T toCheck, Map<T, List<T>> dependsMap) {
		return new DependencyAnalyzer<T>(new HashMap<T, List<T>>(dependsMap))
				.getDependsOrder(toCheck);
	}

	public static <T> List<T> getDependsOrder(Map<T, List<T>> dependsMap) {
		return new DependencyAnalyzer<T>(new HashMap<T, List<T>>(dependsMap)).getDependsOrder();
	}

	public List<T> getDependsOrder(T toCheck) {
		List<T> dependsOrder = new ArrayList<T>();
		checkDepends(toCheck, dependsOrder);
		return dependsOrder;
	}

	public List<T> getDependsOrder() {
		List<T> checkOrder = new ArrayList<T>(dependsMap.keySet());
		List<T> dependsOrder = new ArrayList<T>();
		for (T toCheck : checkOrder) {
			checkDepends(toCheck, dependsOrder);
		}
		return dependsOrder;
	}

	void checkDepends(T start, List<T> dependsOrder) {
		List<T> error = checkDepends0(start, dependsOrder);
		if (error != null) {
			throw new AssemblingException(error);
		}
	}

	@SuppressWarnings("unchecked")
	List<T> checkDepends0(T start, List<T> dependsOrder) {
		if (assembling.contains(start)) {
			List<T> error = new LinkedList<T>();
			error.add(start);
			return error;
		}
		List<T> depends = dependsMap.get(start);
		if (isAssembled(depends)) {
			// do nothing
		} else if (isNoDepends(depends)) {
			dependsMap.put(start, (List<T>) ASSEMBLED);
			dependsOrder.add(start);
		} else {
			assembling.add(start);
			for (T depend : depends) {
				List<T> error = checkDepends0(depend, dependsOrder);
				if (error != null) {
					error.add(0, start);
					return error;
				}
			}
			assembling.remove(start);
			dependsMap.put(start, (List<T>) ASSEMBLED);
			dependsOrder.add(start);
		}
		return null;
	}

	private boolean isNoDepends(List<T> depends) {
		return depends == null;
	}

	private boolean isAssembled(List<T> depends) {
		return depends != null && depends.isEmpty();
	}

	public static class AssemblingException extends RuntimeException {
		private static final long serialVersionUID = 5894139053601243001L;

		public AssemblingException(String message) {
			super(message);
		}

		public AssemblingException(List<?> errors) {
			this(getMessage(errors));
		}

		private static String getMessage(List<?> errors) {
			StringBuilder sb = new StringBuilder();
			for (Iterator<?> iterator = errors.iterator(); iterator.hasNext();) {
				Object error = iterator.next();
				sb.append(error);
				if (iterator.hasNext()) {
					sb.append("-->");
				}
			}
			return sb.toString();
		}
	}

	// public static void main(String[] args) {
	// Map<String,List<String>> map = new HashMap<String, List<String>>();
	// map.put("A", Arrays.asList("B", "C"));
	// map.put("B", Arrays.asList("C", "D"));
	// // DependencyAnalyzer<String> da = new DependencyAnalyzer<String>(map);
	// // System.out.println(da.getDependsOrder());
	// System.out.println(getDependsOrder(map));
	// Map<String,List<String>> map2 = new HashMap<String, List<String>>();
	// System.out.println(getDependsOrder("B",
	// Collections.<String,List<String>>emptyMap()));
	// // map.put("D", Arrays.asList("C", "A"));
	// // DependencyAnalyzer<String> d = new DependencyAnalyzer<String>(map);
	// // System.out.println(d.getDependsOrder("B"));
	// }
}
