package com.nali.spreader.words;

import java.util.Map;
import java.util.Map.Entry;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.TxtFileUtil;

public class Mars {

	private static final String MARS = "txt/mars.txt";
	private static final String SPLIT = "txt/split.txt";
	private static Map<Character, Character> marsMap;
	private static Map<Character, String> splitMap;

	static {
		try {
			Map<String, String> marsStrMap = TxtFileUtil.readKeyValueMap(Txt.getUrl(MARS));
			marsMap = CollectionUtils.newHashMap(marsStrMap.size());
			for (Entry<String, String> entry : marsStrMap.entrySet()) {
				marsMap.put(entry.getKey().charAt(0), entry.getValue().charAt(0));
			}
			Map<String, String> splitStrMap = TxtFileUtil.readKeyValueMap(Txt.getUrl(SPLIT));
			splitMap = CollectionUtils.newHashMap(splitStrMap.size());
			for (Entry<String, String> entry : splitStrMap.entrySet()) {
				splitMap.put(entry.getKey().charAt(0), entry.getValue());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toMars(String src) {
		StringBuilder sb = new StringBuilder(src.length());
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			Character mars = marsMap.get(ch);
			if(mars==null) {
				sb.append(ch);
			} else {
				sb.append(mars);
			}
		}
		return sb.toString();
	}
	
	public static String split(String src) {
		StringBuilder sb = new StringBuilder(src.length());
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			String split = splitMap.get(ch);
			if(split==null) {
				sb.append(ch);
			} else {
				sb.append(split);
			}
		}
		return sb.toString();
	}
	
	public static String tran(String src) {
		StringBuilder sb = new StringBuilder(src.length());
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			Character mars = marsMap.get(ch);
			if(mars!=null) {
				sb.append(mars);
			} else {
				String split = splitMap.get(ch);
				if(split!=null) {
					sb.append(split);
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
}
