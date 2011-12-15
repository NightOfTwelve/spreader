package com.nali.spreader.util.web;

import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.nali.spreader.util.DateFormats;
import com.nali.spreader.util.ThreadLocalFormat;

public class WebBinder implements WebBindingInitializer {
	private ThreadLocalFormat<DateFormats> dateFormats = new ThreadLocalFormat<DateFormats>(
					DateFormats.class, (Object)new String[] {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"});

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormats.getFormat(), true));
	}

}