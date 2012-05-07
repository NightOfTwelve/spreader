package com.nali.spreader.job.thread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.dto.UpdateUserTagParam;
import com.nali.spreader.service.ICategoryKeyWordService;

public class SyncUpdateUserTagThread implements Runnable {
	// 关键字ID
	Long keywordId;
	// 修改的分类ID
	Long newCategoryId;
	// 未修改前的分类ID
	Long oldCategoryId;
	@Autowired
	private ICategoryKeyWordService ckService;
	private static final Logger logger = Logger.getLogger(SyncUpdateUserTagThread.class);

	public SyncUpdateUserTagThread(UpdateUserTagParam params) {
		if (params == null) {
			params = new UpdateUserTagParam();
		}
		this.keywordId = params.getKeywordId();
		this.newCategoryId = params.getNewCategoryId();
		this.oldCategoryId = params.getOldCategoryId();
	}

	@Override
	public void run() {
		try {
			this.ckService.updateUserTagCategory(keywordId, newCategoryId);
			this.ckService.changeBinding(keywordId, newCategoryId);
		} catch (Exception e) {
			// 执行回滚操作
			String rollbackLog = this.dataRollBackInfo();
			logger.debug(rollbackLog);
		} finally {
			// fina 更新状态为可更新
			this.ckService.updateKeywordExecutable(keywordId, true);
		}
	}

	/**
	 * 回滚操作，打印回滚信息
	 * 
	 * @return
	 */
	private String dataRollBackInfo() {
		String log = "关键字取消绑定回滚信息--->";
		StringBuffer buff = new StringBuffer(log);
		buff.append("关键字编号:").append(keywordId);
		int userTagRows = this.ckService.updateUserTagCategory(keywordId, oldCategoryId);
		buff.append(";tb_user_tag表回滚:").append(userTagRows).append("条数据;")
				.append("tb_keyword表回滚状态:");
		boolean isSuccess = this.ckService.changeBinding(keywordId, oldCategoryId);
		buff.append(isSuccess);
		return buff.toString();
	}
}
