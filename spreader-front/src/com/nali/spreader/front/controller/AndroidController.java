package com.nali.spreader.front.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nali.spreader.client.android.google.service.IGooglePlayService;
import com.nali.spreader.client.android.tencent.service.ITencentAppCenterService;
import com.nali.spreader.client.android.wandoujia.service.IWandoujiaAppService;
import com.nali.spreader.front.controller.base.BaseController;

@Controller
@RequestMapping(value = "/android")
public class AndroidController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(AndroidController.class);
	@Autowired
	private ITencentAppCenterService tencentAppCenterSevice;
	@Autowired
	private IWandoujiaAppService wandoujiaAppService;
	@Autowired
	private IGooglePlayService googlePlayService;

	@Override
	public String init() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/down", method = RequestMethod.POST)
	public String tencentAppDownload(String mPageNoPath, int mProductID,
			int mFileID, String mUrl, String clientIP, int mTotalSize,
			int mStatPosition, String mSearchInfo, int p20, int p21,
			int mVersionCode, String pack, int mCategoryId, int mTopicId,
			String data, String handshake) {
		String post = tencentAppCenterSevice.getAppDownloadPost(mPageNoPath,
				mProductID, mFileID, mUrl, clientIP, mTotalSize, mStatPosition,
				mSearchInfo, p20, p21, mVersionCode, pack, mCategoryId,
				mTopicId, data, handshake);
		return post;
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/reportdata", method = RequestMethod.POST)
	public String tencentReportData(String report) {
		Assert.notNull(report, "report is null");
		byte[] data = Base64.decodeBase64(report);
		return tencentAppCenterSevice.getReport(data);
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/reportfile")
	public String tencentReportFile(@RequestParam("file") MultipartFile report)
			throws IOException {
		Assert.notNull(report, "report is null");
		return tencentAppCenterSevice.getReport(report.getBytes());
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/reportbytes")
	public String tencentReportFile(HttpServletRequest request)
			throws IOException {
		ServletInputStream in = request.getInputStream();
		byte[] bs = IOUtils.toByteArray(in);
		return tencentAppCenterSevice.getReport(bs);
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/handshake")
	public String tencentHandshake() {
		return tencentAppCenterSevice.getHandshakeReport();
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/yybreport")
	public String yybReportFile(@RequestParam("yybReport") MultipartFile report)
			throws IOException {
		String str = null;
		try {
			str = tencentAppCenterSevice.getReport(report.getBytes());
		} catch (Exception e) {
			logger.error(e, e);
			str = e.toString();
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("data", str);
		return write(m);
	}

	@ResponseBody
	@RequestMapping(value = "/wandoujia/wdjreport")
	public String wdjReportFile(@RequestParam("wdjReport") MultipartFile report)
			throws IOException {
		String str = null;
		try {
			str = wandoujiaAppService.getEncryptionReport(report.getBytes());
		} catch (Exception e) {
			logger.error(e, e);
			str = e.toString();
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("data", str);
		return write(m);
	}

	@ResponseBody
	@RequestMapping(value = "/wandoujia/ungzip")
	public String unGZipReportFile(
			@RequestParam("gzipReport") MultipartFile report)
			throws IOException {
		String str = null;
		try {
			str = wandoujiaAppService.unGZip(report.getBytes());
		} catch (Exception e) {
			logger.error(e, e);
			str = e.toString();
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("data", str);
		return write(m);
	}

	@ResponseBody
	@RequestMapping(value = "/tencent/yybproperties")
	public String yybReportProperties(
			@RequestParam("downReport") MultipartFile downReport,
			@RequestParam("installReport") MultipartFile installReport)
			throws IOException {
		String str = null;
		try {
			str = tencentAppCenterSevice.getDownProperty(downReport.getBytes(),
					installReport.getBytes());
		} catch (Exception e) {
			logger.error(e, e);
			str = e.toString();
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("data", str);
		return write(m);
	}

	@ResponseBody
	@RequestMapping(value = "/googleplay/encryptpsw")
	public String encryptGooglePlayPsw(String email, String psw)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ClassNotFoundException {
		Assert.notNull(email, " email is null");
		Assert.notNull(psw, " psw is null");
		String s = googlePlayService.encryptAccountPassword(email, psw);
		return s;
	}
}
