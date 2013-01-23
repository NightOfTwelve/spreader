package com.nali.spreader.util.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nali.spreader.util.data.ConverterHelper.*;

public class CommonCachedReads<P, V, OP, OV, CP, CV> extends CommonReads<P, V> {
	private Converter<OV, CV> ovToCv;
	private Converter<CV, V> cvToV;
	private Converter<P, OP> pToOp;
	private Converter<P, CP> pToCp;
	private CommonReads<OP, OV> reads;
	private CommonWrites<CP, CV> writes;

	public CommonCachedReads(Converter<OV, CV> ovToCv, Converter<CV, V> cvToV, Converter<P, OP> pToOp,
			Converter<P, CP> pToCp, CommonReads<OP, OV> reads, CommonWrites<CP, CV> writes) {
		super();
		this.ovToCv = ovToCv;
		this.cvToV = cvToV;
		this.pToOp = pToOp;
		this.pToCp = pToCp;
		this.reads = reads;
		this.writes = writes;
	}
	
	public static class SameValue<P, OP, CP, V> extends CommonCachedReads<P, V, OP, V, CP, V> {
		public SameValue(Converter<P, OP> pToOp, Converter<P, CP> pToCp,
				CommonReads<OP, V> reads, CommonWrites<CP, V> writes) {
			super(null, null, pToOp, pToCp, reads, writes);
		}
	}
	public static class AllSame<P, V> extends CommonCachedReads<P, V, P, V, P, V> {
		public AllSame(CommonReads<P, V> reads, CommonWrites<P, V> writes) {
			super(null, null, null, null, reads, writes);
		}
	}

	public Read<P, Map<String, List<V>>> readKeyListMap() {
		return new ReadCache<P, Map<String, List<V>>, OP, Map<String, List<OV>>, CP, Map<String, List<CV>>>(
					reads.readKeyListMap(), writes.writeKeyListMap(), pToOp, pToCp,
					ovToCv == null ? null : new KeyListMapConverter<OV, CV>(ovToCv),
					cvToV == null ? null : new KeyListMapConverter<CV, V>(cvToV));
	}

	public Read<P, Map<String, V>> readKeyValueMap() {
		return new ReadCache<P, Map<String, V>, OP, Map<String, OV>, CP, Map<String, CV>>(
				reads.readKeyValueMap(), writes.writeKeyValueMap(), pToOp, pToCp,
				ovToCv == null ? null : new KeyValueMapConverter<OV, CV>(ovToCv),
				cvToV == null ? null : new KeyValueMapConverter<CV, V>(cvToV));
	}

	public Read<P, List<V>> readList() {
		return new ReadCache<P, List<V>, OP, List<OV>, CP, List<CV>>(
				reads.readList(), writes.writeList(), pToOp, pToCp,
				ovToCv == null ? null : new ListConverter<OV, CV>(ovToCv),
				cvToV == null ? null : new ListConverter<CV, V>(cvToV));
	}

	public Read<P, Set<V>> readSet() {
		return new ReadCache<P, Set<V>, OP, Set<OV>, CP, Set<CV>>(
				reads.readSet(), writes.writeSet(), pToOp, pToCp,
				ovToCv == null ? null : new SetConverter<OV, CV>(ovToCv),
				cvToV == null ? null : new SetConverter<CV, V>(cvToV));
	}

	@Override
	public Read<P, V> readValue() {
		return new ReadCache<P, V, OP, OV, CP, CV>(
				reads.readValue(), writes.writeValue(), pToOp, pToCp,
				ovToCv,
				cvToV);
	}
	
	private class KeyListMapConverter<F, T> implements Converter<Map<String, List<F>>, Map<String, List<T>>> {
		private Converter<F, T> c;
		public KeyListMapConverter(Converter<F, T> c) {
			super();
			this.c = c;
		}
		@Override
		public Map<String, List<T>> tran(Map<String, List<F>> from) {
			return convertMapList(from, c);
		}
	}
	
	private class KeyValueMapConverter<F, T> implements Converter<Map<String, F>, Map<String, T>> {
		private Converter<F, T> c;
		public KeyValueMapConverter(Converter<F, T> c) {
			super();
			this.c = c;
		}
		@Override
		public Map<String, T> tran(Map<String, F> from) {
			return convert(from, c);
		}
	}
	
	private class ListConverter<F, T> implements Converter<List<F>, List<T>> {
		private Converter<F, T> c;
		public ListConverter(Converter<F, T> c) {
			super();
			this.c = c;
		}
		@Override
		public List<T> tran(List<F> from) {
			return convert(from, c);
		}
	}
	
	private class SetConverter<F, T> implements Converter<Set<F>, Set<T>> {
		private Converter<F, T> c;
		public SetConverter(Converter<F, T> c) {
			super();
			this.c = c;
		}
		@Override
		public Set<T> tran(Set<F> from) {
			return convert(from, c);
		}
	}
}
