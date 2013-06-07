package com.nali.spreader.client.android.wandoujia.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface IWandoujiaAppService {
	/**
	 * 解析豌豆荚加密的包
	 * 
	 * @param report
	 * @return
	 */
	String getEncryptionReport(byte[] report) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException;

	/**
	 * 解析压缩后的包
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	String unGZip(byte[] data) throws IOException;

}
