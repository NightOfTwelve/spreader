package com.nali.spreader.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DateFormats extends DateFormat {//TODO 推广到所有Format？
	private static final long serialVersionUID = 2429511287708952034L;
	private List<DateFormat> formasts;
	
	public DateFormats(String... patterns) {
		this(getFormasts(patterns));
	}

	private static List<DateFormat> getFormasts(String[] patterns) {
		List<DateFormat> rlt = new ArrayList<DateFormat>(patterns.length);
		for (String pattern : patterns) {
			rlt.add(new SimpleDateFormat(pattern));
		}
		return rlt;
	}

	public DateFormats(DateFormat... formasts) {
		this(Arrays.asList(formasts));
	}
	
	public DateFormats(List<DateFormat> formasts) {
		this.formasts = formasts;
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		return formasts.get(0).format(date, toAppendTo, fieldPosition);
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		Date rlt = null;
		Iterator<DateFormat> it = formasts.iterator();
		while (it.hasNext()) {
			DateFormat df = it.next();
			rlt = df.parse(source, pos);
			if(rlt!=null) {
				break;
			}
		}
		return rlt;
	}
	
}