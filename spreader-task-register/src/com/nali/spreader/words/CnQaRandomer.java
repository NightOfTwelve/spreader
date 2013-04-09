package com.nali.spreader.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.CommonPattern;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;

public class CnQaRandomer {
	private static final String APP_QA_TXT = "txt/app-qa-cn.txt";
	private static final Character[] keys=new Character[] {'1', '2', '3',};
	private static Map<Character, Qa> qas;
	
	static {
		try {
			initQa();
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	public static Qa getQa(String key) {
		if(key.length()!=1) {
			throw new IllegalArgumentException("key.length()!=1, key:"+key);
		}
		return qas.get(key.charAt(0));
	}
	
	public static class Qa extends AvgRandomer<KeyValuePair<String, Randomer<String>>> {
		public Qa(Collection<KeyValuePair<String, Randomer<String>>> datas) {
			super(datas);
		}
	}
	
	static abstract class Answers implements Randomer<String> {
		@Override
		public List<String> multiGet(int count) {
			throw new UnsupportedOperationException();
		}
		@Override
		public Randomer<String> mirror() {
			throw new UnsupportedOperationException();
		}
	}

	private static void initQa() throws IOException {
		List<Entry<String, String>> kvs = TxtFileUtil.readKeyValue(Txt.getUrl(APP_QA_TXT));
		Map<String, Randomer<String>> commonStrings=new HashMap<String, Randomer<String>>();
		addOutCommonStrings(commonStrings);
		Map<Character,List<KeyValuePair<String,Randomer<String>>>> qaLists = new HashMap<Character, List<KeyValuePair<String, Randomer<String>>>>();
		for (Entry<String, String> kv : kvs) {
			String key = kv.getKey();
			String value = kv.getValue();
			if(key.charAt(1)!='.') {
				commonStrings.put(key, new AvgRandomer<String>(Arrays.asList(CommonPattern.comma.split(value))));
			} else {
				char no = key.charAt(0);
				List<KeyValuePair<String, Randomer<String>>> qaList = qaLists.get(no);
				if(qaList==null) {
					if(Arrays.binarySearch(keys, no)!=-1) {
						qaList = new ArrayList<KeyValuePair<String,Randomer<String>>>();
						qaLists.put(no, qaList);
					} else {
						throw new IllegalArgumentException("unknown key:"+key);
					}
				}
				Randomer<String> as;
				if(value.charAt(0)=='&') {
					as = commonStrings.get(value.substring(1));
					if(as==null) {
						throw new IllegalArgumentException("unknown value:"+value);
					}
				} else {
					as = new AvgRandomer<String>(
							Arrays.asList(CommonPattern.comma.split(value))
							);
				}
				qaList.add(new KeyValuePair<String, Randomer<String>>(key.substring(2), as));
			}
		}
		qas = CollectionUtils.newHashMap(qaLists.size());
		for (Entry<Character, List<KeyValuePair<String, Randomer<String>>>> qaListEntry : qaLists.entrySet()) {
			Character key = qaListEntry.getKey();
			List<KeyValuePair<String, Randomer<String>>> qaList = qaListEntry.getValue();
			qas.put(key, new Qa(qaList));
		}
	}
	
	private static void addOutCommonStrings(Map<String, Randomer<String>> commonStrings) {
		commonStrings.put("cities", new Answers() {
			@Override
			public String get() {
				Area prov = RandomUtil.randomItem(CnCities.getProvinceAreas());
				Area city = RandomUtil.randomItem(prov.getSubAreas());
				return city.getName();
			}});
		commonStrings.put("fullname", new Answers() {
			@Override
			public String get() {
				return CnPersons.getLastName()+CnPersons.getFirstName(Math.random()<0.5);
			}});
		commonStrings.put("firstname", CnPersons.lastNameRandomer);
		commonStrings.put("streets", new Answers() {
			@Override
			public String get() {
				return CnAdress.streetName();
			}});
	}
}
