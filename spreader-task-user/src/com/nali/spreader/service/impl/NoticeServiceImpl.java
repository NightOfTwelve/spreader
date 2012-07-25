package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudContentReplayDao;
import com.nali.spreader.dao.ICrudNoticeDao;
import com.nali.spreader.dao.INoticeDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentReplay;
import com.nali.spreader.data.ContentReplayExample;
import com.nali.spreader.data.ContentReplayExample.Criteria;
import com.nali.spreader.data.Notice;
import com.nali.spreader.dto.NoticeAtWeiboView;
import com.nali.spreader.dto.NoticeCommentView;
import com.nali.spreader.dto.NoticeQueryParams;
import com.nali.spreader.dto.NoticeView;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.INoticeService;

@Service
public class NoticeServiceImpl implements INoticeService {
	@Autowired
	private INoticeDao noticeDao;
	@Autowired
	private ICrudContentReplayDao curdContentReplayDao;
	@Autowired
	private ICrudNoticeDao crudNoticeDao;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IGlobalUserService globalUserService;

	@Override
	public Date getLastFetchTime(Long uid, Date updateTime) {
		Assert.notNull(uid, "uid is null");
		return noticeDao.getAndSetLastFetchTime(uid, updateTime);
	}

	@Override
	public Long assignContentReplayId(ContentReplay replay) {
		Assert.notNull(replay, "replay is null");
		// 获取评论中的微博内容
		Content refContent = replay.getRefContent();
		Long fromWebsiteUid = replay.getFromWebsiteUid();
		if (replay.getFromUid() == null) {
			Long fromUid = this.globalUserService.getOrAssignUid(Website.weibo.getId(),
					fromWebsiteUid);
			replay.setFromUid(fromUid);
		}
		// 直接回复微博的评论
		if (refContent != null) {
			Content content = this.contentService.assignContent(refContent);
			replay.setContentId(content.getId());
			replay.setToUid(content.getUid());
		} else {
			// 评论的回复
			ContentReplay refReplay = replay.getRefReplay();
			Long refReyplayId = assignContentReplayId(refReplay);
			replay.setRefId(refReyplayId);
			replay.setContentId(replay.getContentId());
		}
		return saveContentReplay(replay);
	}

	/**
	 * 保存回复
	 * 
	 * @param replay
	 * @return
	 */
	private Long saveContentReplay(ContentReplay replay) {
		Assert.notNull(replay, "replay is null");
		Long fromUid = replay.getFromUid();
		Long toUid = replay.getToUid();
		Long contentId = replay.getContentId();
		Long refId = replay.getRefId();
		// fromUid,toUid 必须不为空，否则分配null
		if (fromUid != null && toUid != null) {
			ContentReplayExample example = new ContentReplayExample();
			Criteria c = example.createCriteria();
			c.andFromUidEqualTo(fromUid);
			c.andToUidEqualTo(toUid);
			if (contentId != null) {
				c.andContentIdEqualTo(contentId);
			}
			if (refId != null) {
				c.andRefIdEqualTo(refId);
			}
			Date pubDate = replay.getPubDate();
			if (pubDate != null) {
				c.andPubDateEqualTo(replay.getPubDate());
			}
			c.andWebsiteIdEqualTo(Website.weibo.getId());
			List<ContentReplay> tmpList = this.curdContentReplayDao.selectByExample(example);
			if (!CollectionUtils.isEmpty(tmpList)) {
				ContentReplay cr = tmpList.get(0);
				replay.setId(cr.getId());
				this.curdContentReplayDao.updateByPrimaryKeySelective(replay);
				return cr.getId();
			} else {
				return this.curdContentReplayDao.insertSelective(replay);
			}
		} else {
			return null;
		}
	}

	@Override
	public Long assignNoticeId(Notice n) {
		Assert.notNull(n, "Notice is null");
		return this.crudNoticeDao.insertSelective(n);
	}

	@Override
	public PageResult<NoticeView> queryNoticePageResult(NoticeQueryParams param) {
		Assert.notNull(param, "NoticeQueryParams is null");
		List<NoticeView> list = this.noticeDao.getNoticeViewList(param);
		int count = this.noticeDao.countNotice(param);
		return new PageResult<NoticeView>(list, param.getLit(), count);
	}

	@Override
	public ContentReplay assignContentReplay(ContentReplay replay) {
		Long replayId = this.assignContentReplayId(replay);
		return this.curdContentReplayDao.selectByPrimaryKey(replayId);
	}

	@Override
	public NoticeCommentView queryNoticeComment(Long replayId) {
		Assert.notNull(replayId, "replayId is null");
		return this.noticeDao.getNoticeCommentView(replayId);
	}

	@Override
	public NoticeAtWeiboView queryNoticeAtWeiboView(Long noticeId) {
		Assert.notNull(noticeId, "noticeId is null");
		return this.noticeDao.getNoticeAtWeiboView(noticeId);
	}
}
