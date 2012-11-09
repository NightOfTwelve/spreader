package com.nali.spreader.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class ConstructingMap<K, V> {
	private Map<K, V> map;
	protected Constructor<V> constructor;
	
	public ConstructingMap(Constructor<V> constructor) {
		this(new HashMap<K, V>(), constructor);
	}

	public ConstructingMap(Map<K, V> map, Constructor<V> constructor) {
		super();
		this.map = map;
		this.constructor = constructor;
	}
	
	public static <K, V> ConstructingMap<K, V> getConstructingMap(Class<K> kClass, Class<V> vClass) {
		return new KeyAssociatedConstructingMap<K, V>(kClass, vClass);
	}
	
	public static <K, V> ConstructingMap<K, V> getConstructingMap(Class<V> vClass) {
		return new ZeroConstructingMap<K, V>(vClass);
	}
	
	public static <K, V> ConstructingMap<K, V> getConstructingMap(Map<K, V> map, Class<K> kClass, Class<V> vClass) {
		return new KeyAssociatedConstructingMap<K, V>(map, kClass, vClass);
	}
	
	public static <K, V> ConstructingMap<K, V> getConstructingMap(Map<K, V> map, Class<V> vClass) {
		return new ZeroConstructingMap<K, V>(map, vClass);
	}

	public V get(K k) {
		V v = map.get(k);
		if(v==null) {
			v = newValue(k);
			map.put(k, v);
		}
		return v;
	}

	protected abstract V newValue(K k);

	static class ZeroConstructingMap<K, V> extends ConstructingMap<K, V> {
		private static final Class<?>[] zeroParameterType = new Class<?>[0];
		private static final Object[] zeroParameter = new Object[0];

		private static <T> Constructor<T> getConstructor(Class<T> vClass) {
			try {
				return vClass.getConstructor(zeroParameterType);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}

		public ZeroConstructingMap(Class<V> vClass) {
			super(getConstructor(vClass));
		}

		public ZeroConstructingMap(Map<K, V> map, Class<V> vClass) {
			super(map, getConstructor(vClass));
		}

		@Override
		protected V newValue(K k) {
			try {
				return constructor.newInstance(zeroParameter);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
		
	}
	
	static class KeyAssociatedConstructingMap<K, V> extends ConstructingMap<K, V> {

		private static <K, V> Constructor<V> getConstructor(Class<K> kClass, Class<V> vClass) {
			try {
				return vClass.getConstructor(kClass);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}

		public KeyAssociatedConstructingMap(Class<K> kClass, Class<V> vClass) {
			super(getConstructor(kClass, vClass));
		}

		public KeyAssociatedConstructingMap(Map<K, V> map, Class<K> kClass, Class<V> vClass) {
			super(map, getConstructor(kClass, vClass));
		}

		@Override
		protected V newValue(K k) {
			try {
				return constructor.newInstance(k);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public Map<K, V> getMap() {
		return map;
	}
}
