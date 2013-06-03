package com.nali.spreader.front.controller;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nali.spreader.client.android.tencent.service.ITencentAppCenterSevice;

@Controller
@RequestMapping(value = "/android")
public class AndroidController {
	@Autowired
	private ITencentAppCenterSevice tencentAppCenterSevice;

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
}
