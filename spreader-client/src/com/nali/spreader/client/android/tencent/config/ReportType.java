package com.nali.spreader.client.android.tencent.config;

public enum ReportType {
	download("download", 0), downloadError("downloadError", 1), patchDownload(
			"patchDownload", 2), userAccess("userAccess", 3), userOperation(
			"userOperation", 4), networkSwitch("networkSwitch", 5), appMd5Error(
			"appMd5Error", 6), topicAccess("topicAccess", 7), appInstall(
			"appInstall", 8), advOperation("advOperation", 9), apkFileMD5Check(
			"apkFileMD5Check", 10), searchOperation("searchOperation", 11), updateAppsCount(
			"updateAppsCount", 12), retryDownload("retryDownload", 13), userPageError(
			"userPageError", 14), userDownloadAction("userDownloadAction", 15);
	private String name;
	private int id;

	private ReportType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
