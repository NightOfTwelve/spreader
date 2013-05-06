package com.nali.spreader.client.android.tencent.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import acs.JceRequestType;
import acs.ReqHeader;
import acs.ReqReportClientData;

import com.nali.spreader.client.android.tencent.config.TencentParamsContext;
import com.qq.jce.wup.UniAttribute;
import com.qq.jce.wup.UniPacket;

public class PacketUtils {
	public static byte[] param2Byte(ArrayList paramArrayList) {
		UniPacket localUniPacket = new UniPacket();
		a(localUniPacket, JceRequestType.reportClientData.toString());
		ReqReportClientData localReqReportClientData = new ReqReportClientData();
		// System.out.println(paramArrayList);// TODO print
		localReqReportClientData.setReports(paramArrayList);
		a(localUniPacket, localReqReportClientData, true, false);
		return localUniPacket.encode();
	}

	private static void a(UniPacket paramUniPacket, String paramString) {
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		paramUniPacket.setRequestId(ctx.getRequestId());
		paramUniPacket.setEncodeName("UTF-8");
		paramUniPacket.setServantName("dloader");
		paramUniPacket.setFuncName(paramString);
	}

	private static void a(UniPacket paramUniPacket, Object paramObject,
			boolean paramBoolean1, boolean paramBoolean2) {
		a(paramUniPacket, paramBoolean1, paramBoolean2);
		if (paramObject != null) {
			if (paramBoolean1) {
				UniAttribute localUniAttribute = new UniAttribute();
				localUniAttribute.put("b", paramObject);
				paramObject = a(localUniAttribute, paramUniPacket);
			}
			paramUniPacket.put("body", paramObject);
		}
	}

	private static ReqHeader a(UniPacket paramUniPacket, boolean paramBoolean1,
			boolean paramBoolean2) {
		ReqHeader localReqHeader = new ReqHeader();// TODOgetReqHeader()
		int i = 0;
		if (paramBoolean1)
			i = (byte) 1;
		if (paramBoolean2)
			i = (byte) (i | 0x2);
		localReqHeader.setMask(i);
		paramUniPacket.put("reqHeader", localReqHeader);
		return localReqHeader;
	}

	private static Object a(UniAttribute uniattribute, UniPacket unipacket) {
		byte abyte0[] = uniattribute.encode();
		return ado_b(abyte0);
	}

	private static byte[] ado_b(byte abyte0[]) {
		ByteArrayOutputStream bytearrayoutputstream1 = new ByteArrayOutputStream();
		GZIPOutputStream gzipoutputstream;
		try {
			gzipoutputstream = new GZIPOutputStream(bytearrayoutputstream1);
			gzipoutputstream.write(abyte0, 0, abyte0.length);
			gzipoutputstream.close();
			return bytearrayoutputstream1.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
