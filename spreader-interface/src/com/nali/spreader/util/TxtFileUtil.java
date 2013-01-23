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
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.dao.DataRetrievalFailureException;

public class TxtFileUtil {
	private static Pattern kvPattern=Pattern.compile("\\s*=\\s*");
	
	public static void distinct(URL[] srcs, File dest, String srcCode) {
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
	
	public static List<Entry<String, List<String>>> readKeyList(URL src) {
		return readKeyList(src, ",");
	}

	public static List<Entry<String, List<String>>> readKeyList(URL src, final String splitPattern) {
		final List<Entry<String, List<String>>> rlt = new ArrayList<Entry<String, List<String>>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.add(new KeyValuePair<String, List<String>>(key, Arrays.asList(value.split(splitPattern))));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static Map<String, List<String>> readKeyListMap(URL src) {
		return readKeyListMap(src, ",");
	}

	public static Map<String, List<String>> readKeyListMap(URL src, final String splitPattern) {
		final Map<String, List<String>> rlt = new HashMap<String, List<String>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.put(key, Arrays.asList(value.split(splitPattern)));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}

	public static Map<String, String> readKeyValueMap(URL src) {
		final Map<String, String> rlt = new HashMap<String, String>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.put(key, value);
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static List<Entry<String, String>> readKeyValue(URL src) {
		final List<Entry<String, String>> rlt = new ArrayList<Entry<String,String>>();
		PairHandler pairHandler = new PairHandler() {
			@Override
			public void handle(String key, String value) {
				rlt.add(new KeyValuePair<String, String>(key, value));
			}};
		readKeyValue(src, pairHandler);
		return rlt;
	}
	
	public static Set<String> read(URL src) {
		final Set<String> rlt = new LinkedHashSet<String>();
		readCollection(src, rlt);
		return rlt;
	}
	
	public static List<String> readList(URL src) {
		final List<String> rlt = new ArrayList<String>();
		readCollection(src, rlt);
		return rlt;
	}
	
	public static void readCollection(URL src, final Collection<String> col) {
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				col.add(line);
			}};
		read(src, lineHandler);
	}
	
	public static void writeKeyListMap(Map<String, List<String>> datas, File dest) {
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
	
	public static void writeKeyValueMap(Map<String, String> datas, File dest) {
		final Set<Entry<String, String>> entrySet = datas.entrySet();
		LineMaker<Entry<String, String>> lineMaker = new LineMaker<Entry<String,String>>() {
			@Override
			public String format(Entry<String, String> t) {
				return t.getKey()+"="+t.getValue();
			}};
		write(entrySet, lineMaker, dest);
	}

	public static void write(Collection<String> datas, File dest) {
		LineMaker<String> lineMaker = new LineMaker<String>() {
			@Override
			public String format(String t) {
				return t;
			}};
		write(datas, lineMaker, dest);
	}

	public static <T> void write(Iterable<T> datas, LineMaker<T> lineMaker, File dest) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(dest);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
			Iterator<T> it = datas.iterator();
			while (it.hasNext()) {
				T data = it.next();
				writer.write(lineMaker.format(data));
				if (it.hasNext()) {
					writer.newLine();
				}
			}
		} catch (IOException e) {
			throw new DataRetrievalFailureException(e.getMessage(), e);
		} finally {
			close(out);
		}
	}
	
	private static void close(OutputStream o) {
		if(o==null)return;
		try {
			o.close();
		} catch (IOException e) {
			throw new DataRetrievalFailureException(e.getMessage(), e);
		}
	}
	
	private static void close(InputStream i) {
		if(i==null)return;
		try {
			i.close();
		} catch (IOException e) {
			throw new DataRetrievalFailureException(e.getMessage(), e);
		}
	}
	
	public static String readValue(URL src) {
		InputStream fi = null;
		try {
			fi = src.openStream();
			return IOUtils.toString(fi, "utf-8");
		} catch (IOException e) {
			throw new DataRetrievalFailureException(e.getMessage(), e);
		} finally {
			close(fi);
		}
	}
	
	public static void read(URL src, LineHandler handler) {
		InputStream fi = null;
		try {
			fi = src.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(fi, "utf-8"));
			String line;
			while ((line = reader.readLine())!=null) {
				line=line.trim();
				if("".equals(line)) {
					continue;
				}
				handler.handle(line);
			}
		} catch (IOException e) {
			throw new DataRetrievalFailureException(e.getMessage(), e);
		} finally {
			close(fi);
		}
	}
	
	private static void readKeyValue(URL src, final PairHandler pairHandler) {
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				String[] pair = kvPattern.split(line, 2);
				if(pair.length==1) {
					if(!(line.trim().startsWith("#") || "".equals(line.trim()))) {
						throw new IllegalArgumentException("not a key value pair:" + line);
					}
				} else {
					pairHandler.handle(pair[0].trim(), pair[1].trim());
				}
			}};
		read(src, lineHandler);
	}
	
	public static interface PairHandler {
		void handle(String key, String value);
	}

	public static interface LineHandler {
		void handle(String line);
	}
	
	public static interface LineMaker<T> {
		String format(T t);
	}
}
