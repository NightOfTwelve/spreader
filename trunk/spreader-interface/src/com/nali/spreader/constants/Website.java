package com.nali.spreader.constants;

import java.util.Map;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.EnumUtils;

public enum Website {
	weibo(1, "weibo.com", "微博", "新浪微博", "新浪weibo"),
	
	;

	private final Integer id;
	private String[] descriptions;
	private static Map<Integer, Website> websitesMap;
	private String name;
	
	static{
		Website[] websites = Website.values();
		websitesMap = CollectionUtils.newHashMap(websites.length);
		for(Website website : websites) {
			websitesMap.put(website.getId(), website);
		}
	}

	private Website(Integer id, String name, String... description) {
		this.id = id;
		this.name = name;
		this.descriptions = description;
	}
	
	public String getName() {
		return name;
	}



	public Integer getId() {
		return id;
	}

	public String[] getDescriptions() {
		return descriptions;
	}
	
	public static Website valueOf(int input) {
		return websitesMap.get(input);
	}
	
	public static Website matched(String input) {
		Website[] websites = Website.values();
		for(Website website : websites) {
			if(EnumUtils.matchEnum(input, website.getId(), "", website.getDescriptions())) {
				return website;
			}
		}
		return null;
	}
}
