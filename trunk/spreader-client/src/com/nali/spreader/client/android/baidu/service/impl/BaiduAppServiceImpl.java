package com.nali.spreader.client.android.baidu.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.client.android.baidu.service.IBaiduAppService;

@Service
public class BaiduAppServiceImpl implements IBaiduAppService {
	private final static Logger logger = Logger
			.getLogger(BaiduAppServiceImpl.class);

	// static {
	// try {
	// // System.loadLibrary("base64encoder_v1_4");
	// System.load("/usr/lib/libbase64encoder_v1_4.so");
	// } catch (Exception e) {
	// logger.error(" load lib base64encoder_v1_4 error ", e);
	// }
	// }

	@Override
	public String getJavaLibPath() {
		return System.getProperty("java.library.path");
	}

	@Override
	public String B64Encode(String data) {
		Assert.notNull(data, " encode data is null");
		System.load("/usr/lib/libstdc++.so");
		System.load("/usr/lib/libm.so");
		System.load("/usr/lib/libc.so");
		System.load("/usr/lib/libdl.so");
		System.load("/usr/lib/libbase64encoder_v1_4.so");
		byte encode[] = nativeB64Encode(data.getBytes());
		return urlEncoder(new String(encode));
	}

	private String urlEncoder(String s) {
		String x = "";
		try {
			x = URLEncoder.encode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return x;
	}

	private final native byte[] nativeB64Encode(byte data[]);
}
