package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;

//P==Parameter, OP=OriginalParameter, CP=CacheParameter
public class ReadCache<P, V, OP, OV, CP, CV> implements Read<P, V> {
	private Read<OP, OV> oRead;
	private Write<CP, CV> cache;
	private Converter<OV, CV> ovToCv;
	private Converter<CV, V> cvToV;
	private Converter<P, OP> pToOp;
	private Converter<P, CP> pToCp;
	
	public ReadCache(Read<OP, OV> oRead, Write<CP, CV> cache,
			Converter<P, OP> pToOp, Converter<P, CP> pToCp,
			Converter<OV, CV> ovToCv, Converter<CV, V> cvToV)
			 {
		super();
		this.oRead = oRead;
		this.cache = cache;
		this.ovToCv = ConverterHelper.notNull(ovToCv);
		this.cvToV = ConverterHelper.notNull(cvToV);
		this.pToOp = ConverterHelper.notNull(pToOp);
		this.pToCp = ConverterHelper.notNull(pToCp);
	}
	
	@Override
	public V read(P p) throws DataAccessException {
		CP cp = pToCp.tran(p);
		CV cv = cache.read(cp);
		if(cv==null) {
			OP op = pToOp.tran(p);
			OV ov = oRead.read(op);
			cv = ovToCv.tran(ov);
			cache.write(cp, cv);//TODO handle null cv
		}
		return cvToV.tran(cv);
	}

}
