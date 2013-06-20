package com.nali.spreader.util.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Log4jTestHelper {
	public static final String DEBUG = "DEBUG";
	public static final String INFO = "INFO";
	public static final String WARN = "WARN";
	public static final String ERROR = "ERROR";

	public static void init() {
		init(DEBUG);
	}
	
	public static void init(String level) {
		String text = getText(level);
		byte[] bs = text.getBytes();
		Properties properties = new Properties();
		try {
			properties.load(new ByteArrayInputStream(bs));
		} catch (IOException e) {
			throw new Error(e);
		}
		PropertyConfigurator.configure(properties);
	}

	private static String getText(String level) {
		return "" +
			"log4j.rootLogger=" + level + ",stdout\r\n" +
			"log4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\n" +
			"log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\n" +
			"log4j.appender.stdout.layout.ConversionPattern=[test]%d %5p (%c:%L) - %m%n\r\n";
	}
}
