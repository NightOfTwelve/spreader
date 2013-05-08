package com.nali.spreader.client.android.tencent.service;

/**
 * 腾讯应用市场相关服务
 * 
 * @author xiefei
 * 
 */
public interface ITencentAppCenterSevice {

	String getAppDownloadPost(String mPageNoPath, int mProductID, int mFileID,
			String mUrl, String clientIP, int mTotalSize, int mStatPosition,
			String mSearchInfo, int p20, int p21, int mVersionCode,
			String pack, int mCategoryId, int mTopicId, String machineUniqueId,
			int requestId, String phoneName, int guid);

	String getReport(byte[] bytes);

	String getHandshakeReport();
}
