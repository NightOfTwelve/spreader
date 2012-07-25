package com.nali.spreader.service;

import java.util.Date;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.ContentReplay;
import com.nali.spreader.data.Notice;
import com.nali.spreader.dto.NoticeAtWeiboView;
import com.nali.spreader.dto.NoticeCommentView;
import com.nali.spreader.dto.NoticeQueryParams;
import com.nali.spreader.dto.NoticeView;

/**
 * 系统通知相关service
 * 
 * @author xiefei
 * 
 */
public interface INoticeService {
	/**
	 * 获取最后执行时间
	 * 
	 * @param uid
	 * @param updateTime
	 * @return
	 */
	Date getLastFetchTime(Long uid, Date updateTime);

	/**
	 * 分配一个转发内容ID
	 * 
	 * @param replay
	 * @return
	 */
	Long assignContentReplayId(ContentReplay replay);

	/**
	 * 分配一个ContentReplay
	 * 
	 * @param replay
	 * @return
	 */
	ContentReplay assignContentReplay(ContentReplay replay);

	/**
	 * 保存一个通知
	 * 
	 * @param n
	 * @return
	 */
	Long assignNoticeId(Notice n);

	/**
	 * 分页查询消息
	 * 
	 * @param param
	 * @return
	 */
	PageResult<NoticeView> queryNoticePageResult(NoticeQueryParams param);

	/**
	 * 查询回复评论的消息
	 * 
	 * @param replayId
	 * @return
	 */
	NoticeCommentView queryNoticeComment(Long replayId);

	/**
	 * 获取‘@’微博
	 * 
	 * @param noticeId
	 * @return
	 */
	NoticeAtWeiboView queryNoticeAtWeiboView(Long noticeId);
}
