package com.nali.spreader.workshop.notice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentReplay;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.Notice;
import com.nali.spreader.data.NoticeTypeEnum;
import com.nali.spreader.data.User;
import com.nali.spreader.dto.FetchNoticeDto;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.INoticeService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.workshop.other.FetchNewUserInfo;
import com.nali.spreader.workshop.other.WeiboRobotUserHolder;

@Component
public class FetchNotice extends SingleTaskMachineImpl implements
		SinglePassiveTaskProducer<KeyValue<Long, Date>>,
		ContextedResultProcessor<List<FetchNoticeDto>, SingleTaskMeta> {
	private static final Logger logger = Logger.getLogger(FetchNotice.class);
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private WeiboRobotUserHolder robotUserHolder;
	@Autowired
	private IContentService contentService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private FetchNewUserInfo fetchNewUserInfo;

	private Date lastTime;

	public FetchNotice() {
		super(SimpleActionConfig.fetchNotice, Website.weibo, Channel.fetch);
		ContextMeta contextMeta = new ContextMeta("websiteUid");
		setContextMeta(contextMeta);
	}

	@Override
	public void work(KeyValue<Long, Date> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		Date lastFetchTime = data.getValue();
		if (lastFetchTime == null) {
			lastFetchTime = this.lastTime;
		}
		work(uid, null, lastFetchTime, exporter);
	}

	private void work(Long uid, Long robotId, Date lastFetchTime, SingleTaskExporter exporter) {
		if (robotId == null) {
			robotId = robotUserHolder.getRobotUid();
		}
		Long websiteUid = globalUserService.getWebsiteUid(uid);
		if (websiteUid == null) {
			logger.warn("not found user:" + uid);
			return;
		}
		exporter.setProperty("websiteUid", websiteUid);
		exporter.send(robotId, SpecialDateUtil.afterToday(2));
	}

	@Override
	public void handleResult(Date updateTime, List<FetchNoticeDto> result,
			Map<String, Object> contextContents, Long uid) {
		this.lastTime = this.noticeService.getLastFetchTime(uid, updateTime);
		if (!CollectionUtils.isEmpty(result)) {
			for (FetchNoticeDto dto : result) {
				Integer noticeType = dto.getNoticeType();
				Object refData = dto.getRefData();
				if (refData == null) {
					logger.warn("uid:" + uid + ",noticeType:" + noticeType
							+ ",refData is null, continue");
					continue;
				} else {
					this.createNotice(noticeType, refData, uid);
				}
			}
		}
	}

	/**
	 * 根据消息类型创建一条消息
	 * 
	 * @param noticeType
	 * @param refData
	 * @param robotId
	 */
	private void createNotice(Integer noticeType, Object refData, Long robotId) {
		try {
			// 处理评论,包括评论微博，评论回复，@到的评论
			if (NoticeTypeEnum.commentWeibo.getNocticeType().equals(noticeType)
					|| NoticeTypeEnum.commentReplay.getNocticeType().equals(noticeType)
					|| NoticeTypeEnum.atComment.getNocticeType().equals(noticeType)) {
				ContentReplay replay = (ContentReplay) refData;
				replay.setToUid(robotId);
				replay = settingContentReplayUid(replay);
				Long replayId = this.noticeService.assignContentReplayId(replay);
				saveNotice(null, replayId, replay.getToUid(), replay.getFromUid(), noticeType);
				return;
			}
			// 处理@到的微博
			if (NoticeTypeEnum.atWeibo.getNocticeType().equals(noticeType)) {
				Content content = (Content) refData;
				content = settingContentUid(content);
				content.setType(Content.TYPE_WEIBO);
				content.setWebsiteId(Website.weibo.getId());
				Long contentId = this.contentService.assignContentId(content);
				saveNotice(contentId, null, robotId, content.getUid(), noticeType);
			}
			// TODO 其它消息类型待处理
			return;
		} catch (ClassCastException e) {
			logger.warn("noticeType:" + noticeType, e);
		}
	}

	/**
	 * 保存一条消息记录
	 * 
	 * @param contentId
	 * @param replayId
	 * @param toUid
	 * @param fromUid
	 * @param noticeType
	 */
	private void saveNotice(Long contentId, Long replayId, Long toUid, Long fromUid,
			Integer noticeType) {
		User fromUser = this.globalUserService.getUserById(fromUid);
		Notice n = new Notice();
		n.setFromUid(fromUser.getId());
		n.setFromWebsiteUid(fromUser.getWebsiteUid());
		n.setIsFromRobot(fromUser.getIsRobot());
		n.setNoticeType(noticeType);
		n.setReplayId(replayId);
		n.setContentId(contentId);
		n.setToUid(toUid);
		n.setWebsiteId(Website.weibo.getId());
		this.noticeService.assignNoticeId(n);
	}

	/**
	 * 设置内容的UID，如果不存在则爬取USER
	 * 
	 * @param content
	 * @return
	 */
	private Content settingContentUid(Content content) {
		if (content == null) {
			return null;
		}
		Long contentWebsiteUid = content.getWebsiteUid();
		Long contentUid = this.fetchNewUserInfo.fetchUser(contentWebsiteUid);
		content.setUid(contentUid);
		Content refContent = content.getRefContent();
		if (refContent != null) {
			refContent = settingContentUid(refContent);
		}
		content.setRefContent(refContent);
		return content;
	}

	/**
	 * 设置回复的UID，如果不存在则爬取USER
	 * 
	 * @param replay
	 * @return
	 */
	private ContentReplay settingContentReplayUid(ContentReplay replay) {
		Long fromWebsiteUid = replay.getFromWebsiteUid();
		Long fromUid = this.fetchNewUserInfo.fetchUser(fromWebsiteUid);
		replay.setFromUid(fromUid);
		Content content = settingContentUid(replay.getRefContent());
		if(content != null) {
			replay.setToUid(content.getUid());
		}
		replay.setRefContent(content);
		ContentReplay refContentReplay = replay.getRefReplay();
		if (refContentReplay != null) {
			refContentReplay = settingContentReplayUid(refContentReplay);
		}
		replay.setRefReplay(refContentReplay);
		return replay;
	}
}
