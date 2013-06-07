package com.nali.spreader.client.android.wandoujia.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.nali.spreader.client.android.wandoujia.service.IWandoujiaAppService;

@Service
public class WandoujiaAppServiceImpl implements IWandoujiaAppService {
	private static byte[] keys = { 45, 96, 32, 92, 78, 10, 15, 93, 119, 86, 54,
			111, 116, 56, 42, 84 };

	@Override
	public String getEncryptionReport(byte[] report)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] decryptdb = decrypt(report, keys);
		String str = null;
		try {
			str = unGZip(decryptdb);
		} catch (Exception e) {
			str = new String(decryptdb);
		}
		return str;
	}

	private byte[] decrypt(byte data[], byte key[])
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretkeyspec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretkeyspec, new IvParameterSpec(
				new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
		return cipher.doFinal(data);
	}

	@Override
	public String unGZip(byte[] data) throws IOException {
		if (null == data || 0 == data.length) {
			return "";
		}
		byte[] b = null;
		ByteArrayInputStream bis = null;
		GZIPInputStream gzip = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new ByteArrayInputStream(data);
			gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
		} finally {
			if (null != baos) {
				baos.close();
			}
			if (null != gzip) {
				gzip.close();
			}
			if (null != bis) {
				bis.close();
			}
		}
		return new String(b, "utf-8");
	}
}
