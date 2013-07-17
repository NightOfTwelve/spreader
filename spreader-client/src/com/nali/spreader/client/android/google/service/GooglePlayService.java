package com.nali.spreader.client.android.google.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Service;

import android.content.ContentResolver;

@Service
public class GooglePlayService implements IGooglePlayService {
	private static final String encryptKey = "AAAAgMom/1a/v0lblO2Ubrt60J2gcuXSljGFQXgcyZWveWLEwo6prwgi3iJIZdodyhKZQrNWp5nKJ3srRXcUW+F1BD3baEVGcmEgqaLZUNBjm057pKRI16kB0YppeGx5qIQ5QjKzsR8ETQbKLNWgRY0QRNVz34kMJR3P/LgHax/6rmf5AAAAAwEAAQ==";

	@Override
	public String encryptAccountPassword(String email, String psw)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, ClassNotFoundException {
		String encryptPsw = null;
		Method mth = Class.forName(
				"com.google.android.gsf.loginservice.PasswordEncrypter")
				.getDeclaredMethod(
						"encryptString",
						new Class[] { String.class, ContentResolver.class,
								String.class });
		mth.setAccessible(true);
		String emailPsw = getEmailAndPsw(email, psw);
		encryptPsw = (String) mth.invoke(null, new Object[] { emailPsw, null,
				encryptKey });
		return encryptPsw;
	}

	private String getEmailAndPsw(String email, String psw) {
		return (new StringBuilder()).append(email).append("\0").append(psw)
				.toString();
	}

	public static void main(String[] args) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		GooglePlayService s = new GooglePlayService();
		String email = "xx@aa.com";
		String psw = "t111111";
		String x = s.encryptAccountPassword(email, psw);
		System.out.println(x);
	}
}
