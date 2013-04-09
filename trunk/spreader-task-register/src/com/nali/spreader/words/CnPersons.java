package com.nali.spreader.words;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;

public class CnPersons {
	private static Logger logger = Logger.getLogger(CnPersons.class);
	private static final String FILE_PINYIN = "txt/py-all.txt";
	private static final String FILE_LAST_NAME_PINYIN = "txt/py-last.txt";
	// Randomers
	static Randomer<String> lastNameRandomer;
	private static Randomer<String> maleFirstNameRandomer;
	private static Randomer<String> maleEnNameRandomer;
	private static Randomer<String> femaleFirstNameRandomer;
	private static Randomer<String> femaleEnNameRandomer;

	private static Map<String, String> lastNamePinyinMap;
	private static Map<Character, List<String>> commonPinyinMap;
	
	static {
		try {
			init();
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * @return <姓, 名> <lastName, firstName>
	 */
	public static KeyValuePair<String, String> parseName(String name) {
		if (name==null||name.length()==0) {
			throw new IllegalArgumentException("name is empty");
		}
		if (name.length()==1) {
			return new KeyValuePair<String, String>(null, name);
		}
		if (name.length()==2) {
			return new KeyValuePair<String, String>(name.substring(0, 1), name.substring(1));
		}
		if (name.length()>3) {
			return new KeyValuePair<String, String>(name.substring(0, name.length()-2), name.substring(name.length()-2));
		}
		//name.length()==3
		String testLastName = name.substring(0, 1);
		String py = lastNamePinyinMap.get(testLastName);
		if(py!=null) {
			return new KeyValuePair<String, String>(testLastName, name.substring(1));
		} else {
			testLastName = name.substring(0, 2);
			py = lastNamePinyinMap.get(testLastName);
			if(py!=null) {
				return new KeyValuePair<String, String>(testLastName, name.substring(2));
			} else {
				logger.error("cannot parse name:" + name);
				return new KeyValuePair<String, String>(name.substring(0, 1), name.substring(1));
			}
		}
	}
	
	/**
	 * 姓
	 */
	public static String getLastName() {
		return lastNameRandomer.get();
	}
	
	/**
	 * 名
	 */
	public static String getFirstName(boolean isMale) {
		if(isMale) {
			return maleFirstNameRandomer.get();
		} else {
			return femaleFirstNameRandomer.get();
		}
	}
	
	/**
	 * English name
	 */
	public static String getEnName(boolean isMale) {
		if(isMale) {
			return maleEnNameRandomer.get();
		} else {
			return femaleEnNameRandomer.get();
		}
	}
	
	/**
	 * 查拼音
	 * @param isLastName 是否是姓
	 */
	public static String getNamePinyin(String name, boolean isLastName) {
		if(isLastName) {
			return getLastNamePinyin(name);
		} else {
			return getCommonPinyin(name, false);
		}
	}
	
	private static String getLastNamePinyin(String lastName) {
		String py = lastNamePinyinMap.get(lastName);
		if (py == null) {
			logger.error("not found last name's pinyin:" + lastName);
			return getCommonPinyin(lastName, false);
		}
		return py;
	}

	private static String getCommonPinyin(String firstName, boolean strict) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < firstName.length(); i++) {
			char ch = firstName.charAt(i);
			String py = RandomUtil.randomItem(commonPinyinMap.get(ch));
			if (py == null) {
				logger.error("not found pinyin:" + ch);
				if(strict) {
					return null;
				}
				continue;
			}
			appendCap(sb, py);
		}
		return sb.toString();
	}
	
    private static StringBuilder appendCap(StringBuilder sb, String str) {
		return sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
	}
	
	private static void init() throws IOException {
		lastNameRandomer=TxtCfgUtil.loadWeightWords("txt/top-last.txt");
		maleFirstNameRandomer = loadAvgRandomer("txt/boy.txt");
		maleEnNameRandomer = loadKeyAsAvgRandomer("txt/en-boy.txt");
		femaleFirstNameRandomer = loadAvgRandomer("txt/girl.txt");
		femaleEnNameRandomer = loadKeyAsAvgRandomer("txt/en-girl.txt");
		
		lastNamePinyinMap = TxtFileUtil.readKeyValueMap(Txt.getUrl(FILE_LAST_NAME_PINYIN));
		initCommonPinyinMap();
		
	}
	
	private static void initCommonPinyinMap() throws IOException {
		Map<String, List<String>> pinyinMap = TxtFileUtil.readKeyListMap(Txt.getUrl(FILE_PINYIN));
		commonPinyinMap = CollectionUtils.newHashMap(pinyinMap.size());
		for (Entry<String, List<String>> entry : pinyinMap.entrySet()) {
			commonPinyinMap.put(entry.getKey().charAt(0), entry.getValue());
		}
	}
	
	private static Randomer<String> loadAvgRandomer(String path) throws IOException {
		Set<String> datas = TxtFileUtil.read(Txt.getUrl(path));
		return new AvgRandomer<String>(datas);
	}
	
	private static Randomer<String> loadKeyAsAvgRandomer(String path) throws IOException {
		Map<String, String> datas = TxtFileUtil.readKeyValueMap(Txt.getUrl(path));
		return new AvgRandomer<String>(datas.keySet());
	}
	
}
