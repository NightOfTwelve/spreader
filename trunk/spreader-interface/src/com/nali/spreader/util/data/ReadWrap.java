package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;

//P==Parameter, OP=OriginalParameter, CP=CacheParameter
public class ReadWrap<P, V, OP, OV> implements Read<P, V> {
	private Read<OP, OV> oRead;
	Converter<P, OP> pToOp;
	Converter<OV, V> ovToV;
	public ReadWrap(Read<OP, OV> oRead, Converter<P, OP> pToOp, Converter<OV, V> ovToV) {
		super();
		this.oRead = oRead;
		this.pToOp = ConverterHelper.notNull(pToOp);
		this.ovToV = ConverterHelper.notNull(ovToV);
	}
	
	@Override
	public V read(P p) throws DataAccessException {
		OP op = pToOp.tran(p);
		OV ov = oRead.read(op);
		return ovToV.tran(ov);
	}

}
