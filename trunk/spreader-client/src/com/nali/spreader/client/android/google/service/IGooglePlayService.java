package com.nali.spreader.client.android.google.service;

import java.lang.reflect.InvocationTargetException;

public interface IGooglePlayService {
	/**
	 * 加密googleplay登录的密码
	 * 
	 * @param email
	 * @param psw
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	String encryptAccountPassword(String email, String psw)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, ClassNotFoundException;

}
