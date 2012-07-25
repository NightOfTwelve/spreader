package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.spreader.dto.NoticeAtWeiboView;
import com.nali.spreader.dto.NoticeCommentView;
import com.nali.spreader.dto.NoticeQueryParams;
import com.nali.spreader.dto.NoticeView;

/**
 * 系统通知相关dao
 * 
 * @author xiefei
 * 
 */
public interface INoticeDao {
	/**
	 * 获取并设置最新更新时间
	 * 
	 * @param uid
	 * @param updateTime
	 * @return
	 */
	Date getAndSetLastFetchTime(Long uid, Date updateTime);

	/**
	 * 查询消息
	 * 
	 * @param param
	 * @return
	 */
	List<NoticeView> getNoticeViewList(NoticeQueryParams param);

	/**
	 * 统计消息总数
	 * 
	 * @param param
	 * @return
	 */
	int countNotice(NoticeQueryParams param);

	/**
	 * 根据回复ID查询消息详细内容
	 * 
	 * @param replayId
	 * @return
	 */
	NoticeCommentView getNoticeCommentView(Long replayId);

	/**
	 * 返回@微博内容
	 * 
	 * @param noticeId
	 * @return
	 */
	NoticeAtWeiboView getNoticeAtWeiboView(Long noticeId);

}
