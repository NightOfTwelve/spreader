package com.nali.spreader.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class PerformanceLogger {//TODO remove static
	private static Logger logger = Logger.getLogger(PerformanceLogger.class);
	private static ThreadLocal<Long> th = new ThreadLocal<Long>();
	
	public static void traceStart() {
		start(Level.TRACE);
	}
	
	public static void trace(String name) {
		log(Level.TRACE, name);
	}
	
	public static void debugStart() {
		start(Level.DEBUG);
	}
	
	public static void debug(String name) {
		log(Level.DEBUG, name);
	}
	
	public static void infoStart() {
		start(Level.INFO);
	}
	
	public static void info(String name) {
		log(Level.INFO, name);
	}
	
	public static void warnStart() {
		start(Level.WARN);
	}
	
	public static void warn(String name) {
		log(Level.WARN, name);
	}
	
	public static void errorStart() {
		start(Level.ERROR);
	}
	
	public static void error(String name) {
		log(Level.ERROR, name);
	}
	
	
	public static void fatalStart() {
		start(Level.FATAL);
	}
	
	public static void fatal(String name) {
		log(Level.FATAL, name);
	}
	
	public static void start(Priority lv) {
		if(logger.isEnabledFor(lv)) {
			th.set(System.currentTimeMillis());
		}
	}
	
	public static void log(Priority lv, String name) {
		if(logger.isEnabledFor(lv)) {
			Long start = th.get();
			if(start==null) {
				logger.error("illegal call for [" + name + "], as method \"start()\" hasn't been called yet.");
				return;
			}
			th.remove();
			logger.log(lv, name + "'s execute millis is:" + (System.currentTimeMillis()-start));
		}
	}
}
