package com.nali.stat.dc.data;

import com.nali.common.constant.SystemConstants;
import java.io.Serializable;
import java.util.Date;

public class QueryDateUnit extends QueryUnit implements Serializable {
	private Date fromDate;
	private Date toDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String toString() {
		String result = super.toString();
		return result + ",frodate: " + this.fromDate + ", toDate: " + this.toDate + SystemConstants.LINE_SEPARATOR;
	}
}