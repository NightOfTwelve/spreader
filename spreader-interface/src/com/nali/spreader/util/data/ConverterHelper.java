package com.nali.spreader.util.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.nali.common.util.CollectionUtils;

public class ConverterHelper {
	private final static Converter<Object, Object> emptyConverter = new Converter<Object, Object>() {
		@Override
		public Object tran(Object from) {
			return from;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <V1, V2> Converter<V1, V2> notNull(Converter<V1, V2> c) {
		if(c==null) {
			return (Converter<V1, V2>) emptyConverter;
		}
		return c;
	}
	
	public static <V1, V2> Set<V2> convert(Set<V1> ori, Converter<V1, V2> c) {
		Set<V2> rlt = CollectionUtils.newHashSet(ori.size());
		for (V1 v : ori) {
			V2 v2 = c.tran(v);
			rlt.add(v2);
		}
		return rlt;
	}
	public static <V1, V2> List<V2> convert(List<V1> ori, Converter<V1, V2> c) {
		List<V2> rlt = new ArrayList<V2>();
		for (V1 v : ori) {
			V2 v2 = c.tran(v);
			rlt.add(v2);
		}
		return rlt;
	}
	public static <V1, V2> Map<String, V2> convert(Map<String, V1> map, Converter<V1, V2> c) {
		Map<String, V2> rlt = CollectionUtils.newHashMap(map.size());
		for (Entry<String, V1> entry : map.entrySet()) {
			V2 v2 = c.tran(entry.getValue());
			rlt.put(entry.getKey(), v2);
		}
		return rlt;
	}
	public static <V1, V2> Map<String, List<V2>> convertMapList(Map<String, List<V1>> ori, Converter<V1, V2> c) {
		Map<String, List<V2>> rlt = CollectionUtils.newHashMap(ori.size());
		for (Entry<String, List<V1>> entry : ori.entrySet()) {
			List<V2> v2 = convert(entry.getValue(), c);
			rlt.put(entry.getKey(), v2);
		}
		return rlt;
	}
}
