package com.nali.spreader.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class TxtFileUtil {
	private static Pattern kvPattern=Pattern.compile("\\s*=\\s*");
	
	public static void distinct(URL[] srcs, File dest, String srcCode) throws IOException {
		final Set<String> rlt = new LinkedHashSet<String>();
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				rlt.add(line);
			}};
		for (URL src : srcs) {
			read(src, lineHandler);
		}
		write(rlt, dest);
	}
	
	public static List<Entry<String, List<String>>> readKeyList(URL src) throws IOException {
		return readKeyList(src, ",");
	}

	public static List<Entry<String, List<String>>> readKeyList(URL src, final String splitPattern) throws IOException {
		final List<Entry<String, List<String>>> rlt = new ArrayList<Entry<String, List<String>>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.add(new KeyValuePair<String, List<String>>(key, Arrays.asList(value.split(splitPattern))));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static Map<String, List<String>> readKeyListMap(URL src) throws IOException {
		return readKeyListMap(src, ",");
	}

	public static Map<String, List<String>> readKeyListMap(URL src, final String splitPattern) throws IOException {
		final Map<String, List<String>> rlt = new HashMap<String, List<String>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.put(key, Arrays.asList(value.split(splitPattern)));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}

	public static Map<String, String> readKeyValueMap(URL src) throws IOException {
		final Map<String, String> rlt = new HashMap<String, String>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.put(key, value);
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static List<Entry<String, String>> readKeyValue(URL src) throws IOException {
		final List<Entry<String, String>> rlt = new ArrayList<Entry<String,String>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.add(new KeyValuePair<String, String>(key, value));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static Set<String> read(URL src) throws IOException {
		final Set<String> rlt = new LinkedHashSet<String>();
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				rlt.add(line);
			}};
		read(src, lineHandler);
		return rlt;
	}
	
	public static void writeKeyListMap(Map<String, List<String>> datas, File dest) throws IOException {
		final Set<Entry<String, List<String>>> entrySet = datas.entrySet();
		LineMaker<Entry<String, List<String>>> lineMaker = new LineMaker<Entry<String, List<String>>>() {
			@Override
			public String format(Entry<String, List<String>> t) {
				return join(new StringBuilder().append(t.getKey()).append('='), t.getValue()).toString();
			}
			private StringBuilder join(StringBuilder sb, List<String> values) {
				Iterator<String> it = values.iterator();
				while (it.hasNext()) {
					String value = it.next();
					sb.append(value);
					if(it.hasNext()) {
						sb.append(',');
					}
				}
				return sb;
			}};
		write(entrySet, lineMaker, dest);
	}
	
	public static void writeKeyValueMap(Map<String, String> datas, File dest) throws IOException {
		final Set<Entry<String, String>> entrySet = datas.entrySet();
		LineMaker<Entry<String, String>> lineMaker = new LineMaker<Entry<String,String>>() {
			@Override
			public String format(Entry<String, String> t) {
				return t.getKey()+"="+t.getValue();
			}};
		write(entrySet, lineMaker, dest);
	}

	public static void write(Collection<String> datas, File dest) throws IOException {
		LineMaker<String> lineMaker = new LineMaker<String>() {
			@Override
			public String format(String t) {
				return t;
			}};
		write(datas, lineMaker, dest);
	}

	public static <T> void write(Iterable<T> datas, LineMaker<T> lineMaker, File dest) throws IOException {
		OutputStream out = new FileOutputStream(dest);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));

		try {
			Iterator<T> it = datas.iterator();
			while (it.hasNext()) {
				T data = it.next();
				writer.write(lineMaker.format(data));
				if (it.hasNext()) {
					writer.newLine();
				}
			}
		} finally {
			writer.close();
		}
	}
	
	private static void read(URL src, LineHandler handler) throws IOException {
		InputStream fi = src.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(fi, "utf-8"));
		
		try {
			String line;
			while ((line = reader.readLine())!=null) {
				if("".equals(line)) {
					continue;
				}
				handler.handle(line);
			}
		} finally {
			reader.close();
		}
	}
	
	private static void readKeyValue(URL src, final PairHandler pairHandler) throws IOException {
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				String[] pair = kvPattern.split(line, 2);
				if(pair.length==1) {
					if(!line.trim().startsWith("#")) {
						throw new IllegalArgumentException("not a key value pair:" + line);
					}
				}
				pairHandler.handle(pair[0], pair[1]);
			}};
		read(src, lineHandler);
	}
	
	private static interface PairHandler {
		void handle(String key, String value);
	}

	private static interface LineHandler {
		void handle(String line);
	}
	
	private static interface LineMaker<T> {
		String format(T t);
	}
}
