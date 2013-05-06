package com.nali.spreader.client.android.tencent.utils;

import java.util.HashMap;

import acs.ClientReportParam;

public class DownloadParamsUtil {
	public static void a(ClientReportParam paramClientReportParam,
			byte paramByte1, byte paramByte2) {
		if (paramClientReportParam.p1 == null)
			paramClientReportParam.p1 = new HashMap();
		paramClientReportParam.p1.put(Byte.valueOf(paramByte1),
				Byte.valueOf(paramByte2));
	}

	public static void a(ClientReportParam paramClientReportParam,
			byte paramByte, int paramInt) {
		if (paramClientReportParam.p2 == null)
			paramClientReportParam.p2 = new HashMap();
		paramClientReportParam.p2.put(Byte.valueOf(paramByte),
				Integer.valueOf(paramInt));
	}

	public static void a(ClientReportParam paramClientReportParam,
			byte paramByte, long paramLong) {
		if (paramClientReportParam.p3 == null)
			paramClientReportParam.p3 = new HashMap();
		paramClientReportParam.p3.put(Byte.valueOf(paramByte),
				Long.valueOf(paramLong));
	}

	public static void a(ClientReportParam paramClientReportParam,
			byte paramByte, String paramString) {
		if (paramClientReportParam.p4 == null)
			paramClientReportParam.p4 = new HashMap();
		paramClientReportParam.p4.put(Byte.valueOf(paramByte), paramString);
	}
}