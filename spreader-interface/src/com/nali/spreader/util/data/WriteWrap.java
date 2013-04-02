package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;

//P==Parameter, OP=OriginalParameter, CP=CacheParameter
public class WriteWrap<P, V, OP, OV> extends ReadWrap<P, V, OP, OV> implements Write<P, V> {
	private Write<OP, OV> oWrite;
	private Converter<V, OV> vToOv;
	protected WriteWrap(Write<OP, OV> oWrite, Converter<P, OP> pToOp, Converter<OV, V> ovToV, Converter<V, OV> vToOv) {
		super(oWrite, pToOp, ovToV);
		this.oWrite = oWrite;
		this.vToOv = ConverterHelper.notNull(vToOv);
	}
	public WriteWrap(Write<OP, OV> oWrite, Converter<P, OP> pToOp, ConverterPair<OV, V> valueTraner) {
		this(oWrite, pToOp, valueTraner==null? null : valueTraner.poster(), valueTraner==null? null : valueTraner.receiver());
	}

	@Override
	public void write(P p, V v) throws DataAccessException {
		OP op = pToOp.tran(p);
		oWrite.write(op, vToOv.tran(v));
	}

}
