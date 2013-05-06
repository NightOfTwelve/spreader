package com.nali.spreader.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.client.android.tencent.service.ITencentAppCenterSevice;

@Controller
@RequestMapping(value = "/android")
public class AndroidController {
	@Autowired
	private ITencentAppCenterSevice tencentAppCenterSevice;

	@ResponseBody
	@RequestMapping(value = "/tencent/down")
	public String tencentAppDownload(String mPageNoPath, int mProductID,
			int mFileID, String mUrl, String clientIP, int mTotalSize,
			int mStatPosition, String mSearchInfo, int p20, int p21,
			int mVersionCode, String pack) {
		String post = tencentAppCenterSevice.getAppDownloadPost(mPageNoPath,
				mProductID, mFileID, mUrl, clientIP, mTotalSize, mStatPosition,
				mSearchInfo, p20, p21, mVersionCode, pack);
		return post;
	}

}
