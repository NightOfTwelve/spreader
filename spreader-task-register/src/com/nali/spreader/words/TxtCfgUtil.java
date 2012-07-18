package com.nali.spreader.words;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.CompositeRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;

public class TxtCfgUtil {

	/**
	 * e.g.
		27=王,李,张
		28=刘,陈,杨,黄,赵,吴,周
		24=徐,孙,马,朱,胡,郭,何,高,林,罗,郑,梁
		31=谢,宋,唐,许,韩,冯,邓,曹,彭,曾,肖,田
	 */
	public static Randomer<String> loadWeightWords(String src) throws IOException {
		WeightRandomer<Randomer<String>> tmpRandomer = new WeightRandomer<Randomer<String>>();
		List<Entry<String, List<String>>> properties = TxtFileUtil.readKeyList(Txt.getUrl(src));
		for (Entry<String, List<String>> entry : properties) {
			String key = entry.getKey();
			Randomer<String> names = new AvgRandomer<String>(entry.getValue());
			Integer count = Integer.valueOf(key);
			tmpRandomer.add(names, count);
		}
		return new CompositeRandomer<String>(tmpRandomer);
	}
}
