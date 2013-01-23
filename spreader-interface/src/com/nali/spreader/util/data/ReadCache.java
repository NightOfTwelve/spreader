package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;

//P==Parameter, OP=OriginalParameter, CP=CacheParameter
@SuppressWarnings("unchecked")
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
		this.ovToCv = ovToCv;
		this.cvToV = cvToV;
		this.pToOp = pToOp;
		this.pToCp = pToCp;
	}
	OP toOp(P p) {
		if(pToOp==null) {
			return (OP) p;
		}
		return pToOp.tran(p);
	}
	CP toCp(P p) {
		if(pToCp==null) {
			return (CP) p;
		}
		return pToCp.tran(p);
	}
	CV toCv(OV ov) {
		if(ovToCv==null) {
			return (CV) ov;
		}
		return ovToCv.tran(ov);
	}
	V toV(CV cv) {
		if(cvToV==null) {
			return (V) cv;
		}
		return cvToV.tran(cv);
	}
	
	@Override
	public V read(P p) throws DataAccessException {
		CP cp = toCp(p);
		CV cv = cache.read(cp);
		if(cv==null) {
			OP op = toOp(p);
			OV ov = oRead.read(op);
			cv = toCv(ov);
			cache.write(cp, cv);//TODO handle null cv
		}
		return toV(cv);
	}

}
