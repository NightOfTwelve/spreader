package com.nali.spreader.job.thread;

import org.apache.log4j.Logger;

import com.nali.spreader.dto.UpdateUserTagParam;
import com.nali.spreader.service.ICategoryKeyWordService;

public class SyncUpdateUserTagThread implements Runnable {
	// 关键字ID
	Long keywordId;
	// 修改的分类ID
	Long newCategoryId;
	// 未修改前的分类ID
	Long oldCategoryId;
	private ICategoryKeyWordService ckService;
	private static final Logger logger = Logger.getLogger(SyncUpdateUserTagThread.class);

	public SyncUpdateUserTagThread(UpdateUserTagParam params, ICategoryKeyWordService ckService) {
		if (params == null) {
			params = new UpdateUserTagParam();
		}
		this.keywordId = params.getKeywordId();
		this.newCategoryId = params.getNewCategoryId();
		this.oldCategoryId = params.getOldCategoryId();
		this.ckService = ckService;
	}

	@Override
	public void run() {
		try {
			// 首先执行UserTag表的更新
			this.ckService.updateUserTagCategory(keywordId, newCategoryId);
			// 完成后更新Keyword为新分类
			this.ckService.changeBinding(keywordId, newCategoryId);
		} catch (Exception e) {
			// 执行回滚操作
			String rollbackLog = this.ckService.keywordAndCategoryRollBackInfo(keywordId,
					oldCategoryId);
			logger.error(rollbackLog, e);
		} finally {
			// final 更新状态为可更新
			this.ckService.updateKeywordExecutableByRollback(keywordId);
		}
	}
}
