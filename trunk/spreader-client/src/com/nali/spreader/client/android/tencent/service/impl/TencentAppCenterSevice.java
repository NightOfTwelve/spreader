package com.nali.spreader.client.android.tencent.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import acs.ClientReportInfo;
import acs.ClientReportParam;
import acs.JceRequestType;
import acs.ReqHandshake;
import acs.ReqHeader;
import acs.ReqReportClientData;
import acs.ResHandshake;

import com.nali.spreader.client.android.tencent.config.Network;
import com.nali.spreader.client.android.tencent.config.ReportType;
import com.nali.spreader.client.android.tencent.config.TencentParamsContext;
import com.nali.spreader.client.android.tencent.service.ITencentAppCenterSevice;
import com.nali.spreader.client.android.tencent.utils.ReportParamsUtil;
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
	private static final String androidVersion = "Android4.0.4";
	private static final String BUILD_VERSION_SDK = "15";
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
			int mVersionCode, String pack, int mCategoryId, int mTopicId,
			String machineUniqueId, int requestId, String phoneName,
			String handshake) {
		Assert.notNull(handshake, " handshake is null ");
		int guid = getGuid(handshake);
		TencentParamsContext ctx = new TencentParamsContext(machineUniqueId,
				requestId, phoneName, guid,
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

	private int getGuid(String handshake) {
		byte[] bytes = Base64.decodeBase64(handshake);
		UniPacket up = new UniPacket();
		up.setEncodeName("UTF-8");
		up.decode(bytes);
		ResHandshake hs = up.get("body");
		if (hs != null) {
			return hs.getNGUID();
		} else {
			throw new IllegalArgumentException(" ResHandshake is null");
		}
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
		TencentParamsContext ctx = TencentParamsContext.getCurrentContext();
		String s2 = stringbuilder.append(AQQMM).append("/").append(0x53136)
				.append("&na_2/000000").append("&ADR&")
				.append(widthPixels / 16).append("").append(heightPixels / 16)
				.append("").append("14").append("&").append(ctx.getPhoneName())
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
		ReportParamsUtil.a(localClientReportParam, (byte) 0,
				paramDownloadInfo.mProductID);
		ReportParamsUtil.a(localClientReportParam, (byte) 1,
				paramDownloadInfo.mFileID);
		ReportParamsUtil.a(localClientReportParam, (byte) 2, pack);
		ReportParamsUtil.a(localClientReportParam, (byte) 3,
				paramDownloadInfo.mVersionCode);
		ctx.timeGo(useTimeRandom.get());
		ReportParamsUtil.a(localClientReportParam, (byte) 4,
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
		ReportParamsUtil.a(clientreportparam, (byte) 3, (byte) 1);
		ReportParamsUtil.a(clientreportparam, (byte) 0, p20);
		ReportParamsUtil.a(clientreportparam, (byte) 1, p21);
		ReportParamsUtil.a(clientreportparam, (byte) 2, 1);
		ReportParamsUtil.a(clientreportparam, (byte) 4, (long) mProductID);
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
		ReportParamsUtil.a(clientreportparam, (byte) 0, Network.WIFI.getId());
		// (0 3) 0
		// 这个key，3表示WIFI
		ReportParamsUtil.a(clientreportparam, (byte) 1, clientIP);// cc.w P4
																	// IP
		ReportParamsUtil.a(clientreportparam, (byte) 2,
				paramDownloadInfo.mPageNoPath);// p4
		ReportParamsUtil.a(clientreportparam, (byte) 4,
				paramDownloadInfo.mProductID);// P4
		ReportParamsUtil.a(clientreportparam, (byte) 5,
				paramDownloadInfo.mFileID);// P2
		ReportParamsUtil.a(clientreportparam, (byte) 3, 1);// p2
		ReportParamsUtil.a(clientreportparam, (byte) 7, paramDownloadInfo.mUrl);// p4
		ReportParamsUtil.a(clientreportparam, (byte) 27,
				getDownIP(paramDownloadInfo.mUrl));// p4
		// IP
		ReportParamsUtil.a(clientreportparam, (byte) 8,
				paramDownloadInfo.mDownType);// p1
		ReportParamsUtil.a(clientreportparam, (byte) 9, (byte) 1);// p1
		ReportParamsUtil.a(clientreportparam, (byte) 10, 0);// p2
		ReportParamsUtil.a(clientreportparam, (byte) 11,
				paramDownloadInfo.mStarttime);// p3
		ReportParamsUtil.a(clientreportparam, (byte) 12,
				paramDownloadInfo.mEndtime);// p3
		ReportParamsUtil.a(clientreportparam, (byte) 13,
				paramDownloadInfo.mUsedTime);// p2
		int speed = getDownloadSpeed(paramDownloadInfo.mTotalSize,
				paramDownloadInfo.mUsedTime);
		ReportParamsUtil.a(clientreportparam, (byte) 14, getMaxSpeed(speed));// p2
		ReportParamsUtil.a(clientreportparam, (byte) 15, speed);
		ReportParamsUtil.a(clientreportparam, (byte) 16,
				paramDownloadInfo.mTotalSize);
		if (!StringUtils.isEmpty(paramDownloadInfo.mSearchInfo)) {
			ArrayList arraylist2 = new ArrayList();
			String as1[] = StringUtils.split(paramDownloadInfo.mSearchInfo,
					"]lIl]lIl");
			if (as1 != null && as1.length > 3) {
				for (int l = 0; l < as1.length; l++)
					arraylist2.add(as1[l]);
				ReportParamsUtil.a(clientreportparam, (byte) 19, as1[0]);// p4
				ReportParamsUtil.a(clientreportparam, (byte) 20, as1[1]);// p4
				ReportParamsUtil.a(clientreportparam, (byte) 21, as1[2]);// p4
				ReportParamsUtil.a(clientreportparam, (byte) 22, as1[3]);// p4
			}
		}
		ReportParamsUtil.a(clientreportparam, (byte) 26, (byte) 1);// p1
		ReportParamsUtil.a(clientreportparam, (byte) 25,
				paramDownloadInfo.mStatPosition);// p2
		if (paramDownloadInfo.mCategoryId > 0) {
			ReportParamsUtil.a(clientreportparam, (byte) 24,
					paramDownloadInfo.mCategoryId);// p2
		}
		if (paramDownloadInfo.mTopicId > 0) {
			ReportParamsUtil.a(clientreportparam, (byte) 23,
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
		UniPacket up = getUniPacket(bytes);
		sb.append(up.getFuncName());
		sb.append("\r\n");
		sb.append("<header>");
		sb.append("\r\n");
		sb.append(up.get("reqHeader"));
		sb.append("\r\n");
		sb.append("requestId:" + up.getRequestId());
		sb.append("\r\n");
		sb.append(getObject(up));
		return sb.toString();
	}

	private UniPacket getUniPacket(byte[] bytes) {
		UniPacket up = new UniPacket();
		up.setEncodeName("UTF-8");
		up.decode(bytes);
		return up;
	}

	private Object getObject(UniPacket up) {
		try {
			Object obj = up.get("body");
			byte[] by = (byte[]) obj;
			GZIPInputStream i = new GZIPInputStream(
					new ByteArrayInputStream(by));
			UniAttribute ua = new UniAttribute();
			byte[] bs = IOUtils.toByteArray(i);
			ua.decode(bs);
			return ua.get("b");
		} catch (Exception e) {
			logger.debug(" print Report error", e);
			return up.get("body");
		}
	}

	private ClientReportParam getClientReportParam(ReqReportClientData data) {
		ArrayList list = data.getReports();
		ClientReportInfo cri = (ClientReportInfo) list.get(0);
		ArrayList params = cri.getParams();
		return (ClientReportParam) params.get(0);
	}

	@Override
	public String getDownUrl(String host, byte[] down, byte[] install) {
		ReqReportClientData downData = (ReqReportClientData) getObject(getUniPacket(down));
		ReqReportClientData installData = (ReqReportClientData) getObject(getUniPacket(install));
		return getDownUrl(host, downData, installData);
	}

	private String getDownUrl(String host, ReqReportClientData down,
			ReqReportClientData install) {
		StringBuilder url = new StringBuilder(host);
		url.append("/spreader-front/android/tencent/down?");
		ClientReportParam downParam = getClientReportParam(down);
		ClientReportParam installParam = getClientReportParam(install);
		url.append("mPageNoPath=");
		url.append(downParam.p4.get((byte) 2));
		url.append("&mProductID=");
		url.append(downParam.p2.get((byte) 4));
		url.append("&mFileID=");
		url.append(downParam.p2.get((byte) 5));
		url.append("&mUrl=");
		url.append(downParam.p4.get((byte) 7));
		url.append("&clientIP=");
		url.append(downParam.p4.get((byte) 1));
		url.append("&mTotalSize=");
		url.append(downParam.p2.get((byte) 16));
		url.append("&mStatPosition=");
		url.append(downParam.p2.get((byte) 25));
		url.append("&mSearchInfo=");
		url.append("&p20=0");
		url.append("&p21=0");
		url.append("&mVersionCode=");
		url.append(installParam.p2.get((byte) 3));
		url.append("&pack=");
		url.append(installParam.p4.get((byte) 2));
		url.append("&mCategoryId=");
		url.append(downParam.p2.get((byte) 24));
		url.append("&mTopicId=0");
		url.append("&machineUniqueId=8888888888888");
		url.append("&requestId=9");
		url.append("&phoneName=ZTTTTTTTTT");
		url.append("&guid=1000099");
		return url.toString();
	}

	@Override
	public String getHandshakeReport() {
		TencentParamsContext ctx = new TencentParamsContext();
		TencentParamsContext.setTencentParamsContext(ctx);
		TencentParamsContext curr = TencentParamsContext.getCurrentContext();
		StringBuilder sb = new StringBuilder();
		try {
			String machineUniqueId = curr.getMachineUniqueId();
			int requestId = curr.getRequestId();
			byte[] handshake = getHandshake(machineUniqueId);
			sb.append(Base64.encodeBase64String(handshake));
			sb.append("\r\n");
			sb.append(machineUniqueId);
			sb.append("\r\n");
			sb.append(requestId);
			sb.append("\r\n");
			sb.append(curr.getPhoneName());
		} catch (Exception e) {
			logger.error("getHandshake error", e);
		} finally {
			TencentParamsContext.remove();
		}
		return sb.toString();
	}

	private byte[] getHandshake(String machineUniqueId) {
		UniPacket localUniPacket = new UniPacket();
		a(localUniPacket, JceRequestType.handshake.toString());
		ReqHandshake localReqHandshake = new ReqHandshake();
		localReqHandshake.setSIMEI(machineUniqueId);
		localReqHandshake.setSROM(BUILD_VERSION_SDK);
		a(localUniPacket, localReqHandshake, false, true);
		return localUniPacket.encode();
	}

	@Override
	public String getDownProperty(byte[] down, byte[] install) {
		ReqReportClientData downData = (ReqReportClientData) getObject(getUniPacket(down));
		ReqReportClientData installData = (ReqReportClientData) getObject(getUniPacket(install));
		ClientReportParam downParam = getClientReportParam(downData);
		ClientReportParam installParam = getClientReportParam(installData);
		StringBuilder build = new StringBuilder();
		build.append("path=");
		build.append(downParam.p4.get((byte) 2));
		build.append("\r\n");
		build.append("id=");
		build.append(downParam.p2.get((byte) 4));
		build.append("\r\n");
		build.append("fileid=");
		build.append(downParam.p2.get((byte) 5));
		build.append("\r\n");
		build.append("urlapk=");
		build.append(downParam.p4.get((byte) 7));
		build.append("\r\n");
		build.append("size=");
		build.append(downParam.p2.get((byte) 16));
		build.append("\r\n");
		build.append("stat=");
		build.append(downParam.p2.get((byte) 25));
		build.append("\r\n");
		build.append("search=");
		build.append("\r\n");
		build.append("p20=0");
		build.append("\r\n");
		build.append("p21=0");
		build.append("\r\n");
		build.append("version=");
		build.append(installParam.p2.get((byte) 3));
		build.append("\r\n");
		build.append("pack=");
		build.append(installParam.p4.get((byte) 2));
		build.append("\r\n");
		build.append("categoryid=");
		build.append(downParam.p2.get((byte) 24));
		build.append("\r\n");
		build.append("topicid=0");
		return build.toString();
	}
}
