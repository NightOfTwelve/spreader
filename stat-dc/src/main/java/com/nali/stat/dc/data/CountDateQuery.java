package com.nali.stat.dc.data;

import java.util.Date;

public class CountDateQuery extends CountQuery<QueryDateUnit> {

	public CountDateQuery addQuery(String name, String id, Date fromDate, Date toDate, int limit) {
		QueryDateUnit queryUnit =  this.createCountQueryUnit();
		queryUnit.setFromDate(fromDate);
		queryUnit.setToDate(toDate);
		super.addQuery(name, id, limit, queryUnit);
		return this;
	}

	public CountDateQuery addQuery(String name, String id, Date fromDate, Date toDate) {
		return addQuery(name, id, fromDate, toDate, 1);
	}

	public static CountDateQuery newQuery() {
		return new CountDateQuery();
	}

	public String toString() {
		return "CountDateQuery: " + this.queries.toString();
	}

	@Override
	protected QueryDateUnit createCountQueryUnit() {
		return new QueryDateUnit();
	}
}