package com.nali.spreader.client.android.tencent.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.ClientReportInfo;
import acs.ClientReportParam;
import acs.JceRequestType;
import acs.ReqHandshake;
import acs.ReqHeader;
import acs.ReqReportClientData;

import com.nali.spreader.client.android.tencent.config.Network;
import com.nali.spreader.client.android.tencent.config.ReportType;
import com.nali.spreader.client.android.tencent.config.TencentParamsContext;
import com.nali.spreader.client.android.tencent.service.ITencentAppCenterSevice;
import com.nali.spreader.client.android.tencent.utils.DownloadParamsUtil;
import com.nali.spreader.client.android.tencent.utils.Phone;
import com.nali.spreader.util.random.NumberRandomer;
import com.qq.jce.wup.UniAttribute;
import com.qq.jce.wup.UniPacket;
import com.qq.taf.jce.JceInputStream;
import com.tencent.android.qqdownloader.data.DownloadInfo;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TencentAppCenterSevice implements ITencentAppCenterSevice {
	private static final Logger logger = Logger
			.getLogger(TencentAppCenterSevice.class);
	private static final NumberRandomer maxSpeedRandom = new NumberRandomer(
			100, 200);
	private static final String AQQMM = "AQQMM_34C";
	private String androidVersion = "Android4.0.4";
	@Value("${spreader.tx.app.download.mDownType}")
	private byte mDownType;
	@Value("${spreader.tx.app.header.version}")
	private int version;
	@Value("${spreader.tx.app.header.businessId}")
	private int businessId;
	@Value("${spreader.tx.app.header.qq}")
	private long qq;
	@Value("${spreader.tx.app.header.apiLevel}")
	private int apiLevel;
	@Value("${spreader.tx.app.header.densityDpi}")
	private int densityDpi;
	@Value("${spreader.tx.app.header.resolutionX}")
	private int resolutionX;
	@Value("${spreader.tx.app.header.resolutionY}")
	private int resolutionY;
	@Value("${spreader.tx.app.header.channel}")
	private int channel;
	@Value("${spreader.tx.app.header.outSideChannel}")
	private int outSideChannel;
	private static final NumberRandomer useTimeRandom = new NumberRandomer(
			10000, 59999);

	@Override
	public String getAppDownloadPost(String mPageNoPath, int mProductID,
			int mFileID, String mUrl, String clientIP, int mTotalSize,
			int mStatPosition, String mSearchInfo, int p20, int p21,
			int mVersionCode, String pack, int mCategoryId, int mTopicId) {
		TencentParamsContext ctx = new TencentParamsContext(
				System.currentTimeMillis() - 10 * 1000L);
		TencentParamsContext.setTencentParamsContext(ctx);
		DownloadInfo paramDownloadInfo = getDownloadInfo(mPageNoPath,
				mProductID, mFileID, mUrl, clientIP, mTotalSize, mStatPosition,
				mSearchInfo, mVersionCode, mCategoryId, mTopicId);
		try {
			byte[] downReport = getDownloadReport(paramDownloadInfo, clientIP);
			StringBuilder builder = new StringBuilder();
			builder.append(Base64.encodeBase64String(downReport));
			// byte[] userOpReport = getUserOperationReport(mProductID, p20,
			// p21);
			builder.append("\r\n");
			byte[] installReport = getAppInstallReport(paramDownloadInfo, pack);
			builder.append(Base64.encodeBase64String(installReport));
			return builder.toString();
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			TencentParamsContext.remove();
		}
		// builder.append(Base64.encodeBase64String(userOpReport));
		return null;
	}

	private DownloadInfo getDownloadInfo(String mPageNoPath, int mProductID,
			int mFileID, String mUrl, String clientIP, int mTotalSize,
			int mStatPosition, String mSearchInfo, int mVersionCode,
			int mCategoryId, int mTopicId) {
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		DownloadInfo paramDownloadInfo = new DownloadInfo();
		paramDownloadInfo.mPageNoPath = mPageNoPath;// 2
		paramDownloadInfo.mProductID = mProductID;// 4
		paramDownloadInfo.mFileID = mFileID;// 5
		paramDownloadInfo.mDownType = mDownType;// 8
		paramDownloadInfo.mUrl = mUrl;
		paramDownloadInfo.mStarttime = ctx.getTime();// 11
		int usedTime = useTimeRandom.get();
		ctx.timeGo(usedTime);
		paramDownloadInfo.mEndtime = ctx.getTime();// 12
		paramDownloadInfo.mUsedTime = usedTime;// 13
		paramDownloadInfo.mTotalSize = mTotalSize;// 16
		paramDownloadInfo.mPackageName = String.valueOf(mProductID);// 4
		paramDownloadInfo.mBookId = mProductID;// 4
		paramDownloadInfo.mStatPosition = mStatPosition;// 25
		paramDownloadInfo.mSearchInfo = mSearchInfo;
		paramDownloadInfo.mVersionCode = mVersionCode;
		paramDownloadInfo.mCategoryId = mCategoryId;
		paramDownloadInfo.mTopicId = mTopicId;
		return paramDownloadInfo;
	}

	private ReqHeader getReqHeader() {
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		ReqHeader localReqHeader = new ReqHeader();
		localReqHeader.guid = ctx.getGuid();
		localReqHeader.qua = getQua(resolutionX, resolutionY);
		localReqHeader.version = version;
		localReqHeader.businessId = businessId;
		localReqHeader.sid = null;
		localReqHeader.qq = qq;
		localReqHeader.apiLevel = apiLevel;
		localReqHeader.densityDpi = densityDpi;
		localReqHeader.resolutionX = resolutionX;
		localReqHeader.resolutionY = resolutionY;
		localReqHeader.machineUniqueId = ctx.getMachineUniqueId();
		localReqHeader.channel = channel;
		localReqHeader.networkType = (byte) Network.WIFI.getId();
		localReqHeader.outSideChannel = outSideChannel;
		return localReqHeader;
	}

	public String getQua(int widthPixels, int heightPixels) {
		StringBuilder stringbuilder = new StringBuilder();
		String s2 = stringbuilder.append(AQQMM).append("/").append(0x53136)
				.append("&na_2/000000").append("&ADR&")
				.append(widthPixels / 16).append("").append(heightPixels / 16)
				.append("").append("14").append("&").append(Phone.get())
				.append("&").append(androidVersion).append("&0").append("&V3")
				.toString();
		return s2;
	}

	// TODO
	private String getDownIP(String apkUrl) {
		try {
			String str = InetAddress.getByName(new URI(apkUrl).getHost())
					.getHostAddress();
			return str;
		} catch (Exception e) {
			logger.error("get apk IP error,", e);
		}
		return null;
	}

	private byte[] getAppInstallReport(DownloadInfo paramDownloadInfo,
			String pack) {
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		ArrayList localArrayList = new ArrayList();
		ClientReportInfo localClientReportInfo = new ClientReportInfo();
		localArrayList.add(localClientReportInfo);
		localClientReportInfo.type = ReportType.appInstall.getId();
		localClientReportInfo.params = new ArrayList();
		ClientReportParam localClientReportParam = new ClientReportParam();
		localClientReportInfo.params.add(localClientReportParam);
		DownloadParamsUtil.a(localClientReportParam, (byte) 0,
				paramDownloadInfo.mProductID);
		DownloadParamsUtil.a(localClientReportParam, (byte) 1,
				paramDownloadInfo.mFileID);
		DownloadParamsUtil.a(localClientReportParam, (byte) 2, pack);
		DownloadParamsUtil.a(localClientReportParam, (byte) 3,
				paramDownloadInfo.mVersionCode);
		ctx.timeGo(useTimeRandom.get());
		DownloadParamsUtil.a(localClientReportParam, (byte) 4,
				ctx.getTime() / 1000L);
		return param2Byte(localArrayList);
	}

	private byte[] getUserOperationReport(int mProductID, int p20, int p21) {
		ArrayList arraylist = new ArrayList();
		ClientReportInfo clientreportinfo = new ClientReportInfo();
		arraylist.add(clientreportinfo);
		clientreportinfo.type = ReportType.userOperation.getId();
		clientreportinfo.params = new ArrayList();
		ClientReportParam clientreportparam = new ClientReportParam();
		clientreportparam.p4 = new HashMap();
		clientreportinfo.params.add(clientreportparam);
		DownloadParamsUtil.a(clientreportparam, (byte) 3, (byte) 1);
		DownloadParamsUtil.a(clientreportparam, (byte) 0, p20);
		DownloadParamsUtil.a(clientreportparam, (byte) 1, p21);
		DownloadParamsUtil.a(clientreportparam, (byte) 2, 1);
		DownloadParamsUtil.a(clientreportparam, (byte) 4, (long) mProductID);
		return param2Byte(arraylist);
	}

	private byte[] getDownloadReport(DownloadInfo paramDownloadInfo,
			String clientIP) {
		ArrayList arraylist = new ArrayList();
		ClientReportInfo clientreportinfo = new ClientReportInfo();
		arraylist.add(clientreportinfo);
		clientreportinfo.type = ReportType.download.ordinal();
		clientreportinfo.params = new ArrayList();
		ClientReportParam clientreportparam = new ClientReportParam();
		clientreportinfo.params.add(clientreportparam);
		DownloadParamsUtil.a(clientreportparam, (byte) 0, Network.WIFI.getId());
		// (0 3) 0
		// 这个key，3表示WIFI
		DownloadParamsUtil.a(clientreportparam, (byte) 1, clientIP);// cc.w P4
																	// IP
		DownloadParamsUtil.a(clientreportparam, (byte) 2,
				paramDownloadInfo.mPageNoPath);// p4
		DownloadParamsUtil.a(clientreportparam, (byte) 4,
				paramDownloadInfo.mProductID);// P4
		DownloadParamsUtil.a(clientreportparam, (byte) 5,
				paramDownloadInfo.mFileID);// P2
		DownloadParamsUtil.a(clientreportparam, (byte) 3, 1);// p2
		DownloadParamsUtil.a(clientreportparam, (byte) 7,
				paramDownloadInfo.mUrl);// p4
		DownloadParamsUtil.a(clientreportparam, (byte) 27,
				getDownIP(paramDownloadInfo.mUrl));// p4
		// IP
		DownloadParamsUtil.a(clientreportparam, (byte) 8,
				paramDownloadInfo.mDownType);// p1
		DownloadParamsUtil.a(clientreportparam, (byte) 9, (byte) 1);// p1
		DownloadParamsUtil.a(clientreportparam, (byte) 10, 0);// p2
		DownloadParamsUtil.a(clientreportparam, (byte) 11,
				paramDownloadInfo.mStarttime);// p3
		DownloadParamsUtil.a(clientreportparam, (byte) 12,
				paramDownloadInfo.mEndtime);// p3
		DownloadParamsUtil.a(clientreportparam, (byte) 13,
				paramDownloadInfo.mUsedTime);// p2
		int speed = getDownloadSpeed(paramDownloadInfo.mTotalSize,
				paramDownloadInfo.mUsedTime);
		DownloadParamsUtil.a(clientreportparam, (byte) 14, getMaxSpeed(speed));// p2
		DownloadParamsUtil.a(clientreportparam, (byte) 15, speed);
		DownloadParamsUtil.a(clientreportparam, (byte) 16,
				paramDownloadInfo.mTotalSize);
		if (!StringUtils.isEmpty(paramDownloadInfo.mSearchInfo)) {
			ArrayList arraylist2 = new ArrayList();
			String as1[] = StringUtils.split(paramDownloadInfo.mSearchInfo,
					"]lIl]lIl");
			if (as1 != null && as1.length > 3) {
				for (int l = 0; l < as1.length; l++)
					arraylist2.add(as1[l]);
				DownloadParamsUtil.a(clientreportparam, (byte) 19, as1[0]);// p4
				DownloadParamsUtil.a(clientreportparam, (byte) 20, as1[1]);// p4
				DownloadParamsUtil.a(clientreportparam, (byte) 21, as1[2]);// p4
				DownloadParamsUtil.a(clientreportparam, (byte) 22, as1[3]);// p4
			}
		}
		DownloadParamsUtil.a(clientreportparam, (byte) 26, (byte) 1);// p1
		DownloadParamsUtil.a(clientreportparam, (byte) 25,
				paramDownloadInfo.mStatPosition);// p2
		if (paramDownloadInfo.mCategoryId > 0) {
			DownloadParamsUtil.a(clientreportparam, (byte) 24,
					paramDownloadInfo.mCategoryId);// p2
		}
		if (paramDownloadInfo.mTopicId > 0) {
			DownloadParamsUtil.a(clientreportparam, (byte) 23,
					paramDownloadInfo.mTopicId);// p2
		}
		return param2Byte(arraylist);
	}

	public int getMaxSpeed(int speed) {
		int t = maxSpeedRandom.get();
		return speed * t / 100;
	}

	private int getDownloadSpeed(int size, int useTime) {
		return (int) ((1000L * (long) size) / (long) useTime);
	}

	private byte[] param2Byte(ArrayList paramArrayList) {
		UniPacket localUniPacket = new UniPacket();
		a(localUniPacket, JceRequestType.reportClientData.toString());
		ReqReportClientData localReqReportClientData = new ReqReportClientData();
		// System.out.println(paramArrayList);// TODO print
		localReqReportClientData.setReports(paramArrayList);
		a(localUniPacket, localReqReportClientData, true, false);
		return localUniPacket.encode();
	}

	private void a(UniPacket paramUniPacket, String paramString) {
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		paramUniPacket.setRequestId(ctx.getRequestId());
		paramUniPacket.setEncodeName("UTF-8");
		paramUniPacket.setServantName("dloader");
		paramUniPacket.setFuncName(paramString);
	}

	private void a(UniPacket paramUniPacket, Object paramObject,
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

	private ReqHeader a(UniPacket paramUniPacket, boolean paramBoolean1,
			boolean paramBoolean2) {
		ReqHeader localReqHeader = getReqHeader();
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
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public String getReport(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		JceInputStream in = new JceInputStream(bytes);
		in.setServerEncoding("utf8");
		UniPacket up = new UniPacket();
		up.setEncodeName("UTF-8");
		up.decode(bytes);
		sb.append(up.getFuncName());
		sb.append("\r\n");
		sb.append("<header>");
		sb.append("\r\n");
		sb.append(up.get("reqHeader"));
		sb.append("\r\n");
		sb.append("requestId:" + up.getRequestId());
		sb.append("\r\n");
		try {
			Object obj = up.get("body");
			byte[] by = (byte[]) obj;
			GZIPInputStream i = new GZIPInputStream(
					new ByteArrayInputStream(by));
			UniAttribute ua = new UniAttribute();
			byte[] bs = IOUtils.toByteArray(i);
			ua.decode(bs);
			sb.append(ua.get("b"));
		} catch (Exception e) {
			logger.debug(" print Report error", e);
			sb.append(up.get("body"));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		String filename = "hs1";
		byte[] bb = FileUtils.readFileToByteArray(new File(filename));
		TencentAppCenterSevice s = new TencentAppCenterSevice();
		String x = s.getReport(bb);
		System.out.println(x);
	}

	@Override
	public byte[] getHandshake() {
		UniPacket localUniPacket = new UniPacket();
		a(localUniPacket, JceRequestType.handshake.toString());
		ReqHandshake localReqHandshake = new ReqHandshake();
//		localReqHandshake.setSIMEI(str);TODO
		localReqHandshake.setSROM("15");// Build.VERSION.SDK
		a(localUniPacket, localReqHandshake, false, true);
		return localUniPacket.encode();
	}
}
