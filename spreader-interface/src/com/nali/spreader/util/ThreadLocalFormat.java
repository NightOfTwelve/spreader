package com.nali.spreader.util;

import java.lang.reflect.Constructor;
import java.text.Format;

public class ThreadLocalFormat<F extends Format> {
	private final Constructor<F> con;
	private final Object[] conArgs;
	private ThreadLocal<F> tl = new ThreadLocal<F>() {
		@Override
		protected F initialValue() {
			try {
				return con.newInstance(conArgs);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
	}};
	
	public ThreadLocalFormat(Class<F> clazz, Object... conArgs) {
		this(getConstructor(clazz, conArgs), conArgs);
	}

	public ThreadLocalFormat(Constructor<F> con, Object[] conArgs) {
		this.con = con;
		this.conArgs = conArgs;
	}
	
	public F getFormat() {
		return tl.get();
	}
	
	private static <F> Constructor<F> getConstructor(Class<F> clazz, Object[] conArgs) {
		Class<?>[] types = new Class<?>[conArgs.length];
		for (int i = 0; i < conArgs.length; i++) {
			types[i] = conArgs[i].getClass();
		}
		try {
			return clazz.getConstructor(types);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
