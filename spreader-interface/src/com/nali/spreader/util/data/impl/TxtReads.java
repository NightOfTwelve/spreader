package com.nali.spreader.util.data.impl;

import static com.nali.spreader.util.data.ConverterHelper.convert;
import static com.nali.spreader.util.data.ConverterHelper.convertMapList;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.data.CommonReads;
import com.nali.spreader.util.data.Converter;
import com.nali.spreader.util.data.Read;

public class TxtReads<V> extends CommonReads<URL, V> {
	private Converter<String, V> converter;

	public TxtReads(Converter<String, V> converter) {
		super();
		this.converter = converter;
	}

	public Read<URL, Map<String, List<V>>> readKeyListMap() {
		return new Read<URL, Map<String,List<V>>>() {
			@Override
			public Map<String, List<V>> read(URL p) throws DataAccessException {
				Map<String, List<String>> map;
				try {
					map = TxtFileUtil.readKeyListMap(p);
				} catch (DataRetrievalFailureException e) {
					throw new DataRetrievalFailureException("fail get data @" + p, e);
				}
				return convertMapList(map, converter);
			}
		};
	}

	public Read<URL, Map<String, V>> readKeyValueMap() {
		return new Read<URL, Map<String,V>>() {
			@Override
			public Map<String, V> read(URL p) throws DataAccessException {
				Map<String, String> map;
				try {
					map = TxtFileUtil.readKeyValueMap(p);
				} catch (DataRetrievalFailureException e) {
					throw new DataRetrievalFailureException("fail get data @" + p, e);
				}
				return convert(map, converter);
			}
		};
	}

	public Read<URL, List<V>> readList() {
		return new Read<URL, List<V>>() {
			@Override
			public List<V> read(URL p) throws DataAccessException {
				try {
					return convert(TxtFileUtil.readList(p), converter);
				} catch (DataRetrievalFailureException e) {
					throw new DataRetrievalFailureException("fail get data @" + p, e);
				}
			}
		};
	}

	public Read<URL, Set<V>> readSet() {
		return new Read<URL, Set<V>>() {
			@Override
			public Set<V> read(URL p) throws DataAccessException {
				try {
					return convert(TxtFileUtil.read(p), converter);
				} catch (DataRetrievalFailureException e) {
					throw new DataRetrievalFailureException("fail get data @" + p, e);
				}
			}
		};
	}
	
	public Read<URL, V> readValue() {
		return new Read<URL, V>() {
			@Override
			public V read(URL p) throws DataAccessException {
				try {
					return converter.tran(TxtFileUtil.readValue(p));
				} catch (DataRetrievalFailureException e) {
					throw new DataRetrievalFailureException("fail get data @" + p, e);
				}
			}
		};
	}
}
