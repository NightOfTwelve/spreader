package com.nali.spreader.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.INoticeDao;
import com.nali.spreader.dto.NoticeAtWeiboView;
import com.nali.spreader.dto.NoticeCommentView;
import com.nali.spreader.dto.NoticeQueryParams;
import com.nali.spreader.dto.NoticeView;

@Repository
public class NoticeDaoImpl implements INoticeDao {
	@Autowired
	private RedisTemplate<String, Date> redisTemplate;
	private static final String FETCH_DATE = "fetch_date_";
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public Date getAndSetLastFetchTime(Long uid, Date updateTime) {
		return redisTemplate.opsForValue().getAndSet(FETCH_DATE + uid, updateTime);
	}

	@Override
	public List<NoticeView> getNoticeViewList(NoticeQueryParams param) {
		return this.sqlMap.queryForList("spreader_notice.selectNoticeInfo", param);
	}

	@Override
	public int countNotice(NoticeQueryParams param) {
		return (Integer) this.sqlMap.queryForObject("spreader_notice.selectNoticeInfoCount", param);
	}

	@Override
	public NoticeCommentView getNoticeCommentView(Long replayId) {
		return (NoticeCommentView) this.sqlMap.queryForObject(
				"spreader_notice.noticeCommentReplay", replayId);
	}

	@Override
	public NoticeAtWeiboView getNoticeAtWeiboView(Long noticeId) {
		return (NoticeAtWeiboView) this.sqlMap.queryForObject("spreader_notice.noticeAtWeibo",
				noticeId);
	}
}
