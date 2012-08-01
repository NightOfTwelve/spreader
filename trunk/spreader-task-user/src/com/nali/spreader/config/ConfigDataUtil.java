package com.nali.spreader.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;

public class ConfigDataUtil {
	public static List<String> getKeywords(List<String> defaultKeywords, List<String> randomKeywords, Randomer<Integer> keywordRandomer) {
		if(defaultKeywords==null) {
			defaultKeywords = Collections.emptyList();
		}
		if(keywordRandomer!=null && randomKeywords!=null && randomKeywords.size()!=0) {
			List<String> randomList = RandomUtil.randomItems(randomKeywords, keywordRandomer.get());
			List<String> rlt = new ArrayList<String>(randomList.size()+defaultKeywords.size());
			rlt.addAll(defaultKeywords);
			rlt.addAll(randomList);
			return rlt;
		} else {
			return defaultKeywords;
		}
	}
	
	public static NumberRandomer createGteLteRandomer(Range<Integer> range, boolean checkNotNull) {
		if (range != null && range.checkNotNull()) {
			return new NumberRandomer(range.getGte(), range.getLte() + 1);
		} else if(checkNotNull) {
			throw new IllegalArgumentException("range is missing:" + range);
		} else {
			return null;
		}
	}
}
