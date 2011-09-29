package com.nali.spreader.words;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.nali.spreader.util.TxtFileUtil;

public class Txt {
	/**
	 * 后缀
	 */
	public static final List<String> suffix;
	
	/**
	 * 前缀
	 */
	public static final List<String> prefix; 
	static {
		try {
			suffix = readList("txt/suffix.txt");
			prefix = readList("txt/prefix.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static URL getUrl(String path) throws IOException {
		URL resource = Mars.class.getClassLoader().getResource(path);
		if(resource==null) {
			throw new IllegalArgumentException("file does not exist:" + path);
		}
		return resource;
	}
	
	private static List<String> readList(String path) throws IOException {
		return new ArrayList<String>(TxtFileUtil.read(getUrl(path)));
	}
}
