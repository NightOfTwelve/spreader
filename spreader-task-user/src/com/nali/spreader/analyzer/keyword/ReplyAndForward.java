package com.nali.spreader.analyzer.keyword;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.ConfigDataUtil;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.ReplyAndForwardConfig;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.ReplyWeiboDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;

@Component
@ClassDescription("关键字·回复并转发")
public class ReplyAndForward extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<ReplyAndForwardConfig> {
	private ReplyAndForwardConfig config;
	// 回复Randomer
	private Randomer<Integer> replyRandomer;
	// 转发Randomer
	private Randomer<Integer> forwardRandomer;
	// 发帖随机数
	private Randomer<Integer> postRandom;
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IRobotContentService robotContentService;
	@AutowireProductLine
	private TaskProduceLine<ReplyWeiboDto> replyWeibo;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Content>> forwardWeiboContent;
	@Autowired
	private IGlobalUserService globalUserService;

	public ReplyAndForward() {
		super("转发回复${fromGroup}的微博");
	}

	@Override
	public String work() {
		// 关键字
		List<String> keywords = this.config.getKeywords();
		// 分类
		List<String> categories = this.config.getCategories();
		// 整合所有的关键字列表
		Set<Long> allKeywords = this.keywordService.createMergerKeyword(keywords, categories);
		// 用户分组ID
		Long gid = this.getFromUserGroup();
		if (gid == null) {
			throw new IllegalArgumentException("gid is null");
		}
		// 获取分组下所有用户
		Iterator<GrouppedUser> iter = this.userGroupFacadeService.queryAllGrouppedUser(gid);
		Long[] uids = this.globalUserService.findPostContentUids(this.config.getvType(),
				this.config.getFans(), this.config.getArticles());
		while (iter.hasNext()) {
			GrouppedUser gu = iter.next();
			Long uid = gu.getUid();
			Long[] sendKeywords = this.keywordService.createSendKeywordList(allKeywords, uid);
			// 获取所有的微博内容
			List<Long> allContent = this.contentService.findContentIdByPostContentDto(this.config
					.getPostWeiboContentDto(sendKeywords, uids));
			// 已回复的内容
			List<Long> existsReplyContent = this.robotContentService.findRelatedContentId(uid,
					RobotContent.TYPE_REPLY);
			List<Long> existsForwardContent = this.robotContentService.findRelatedContentId(uid,
					RobotContent.TYPE_FORWARD);
			List<Long> existsContent = new ArrayList<Long>();
			if (!CollectionUtils.isEmpty(existsReplyContent)) {
				existsContent.addAll(existsReplyContent);
			}
			if (!CollectionUtils.isEmpty(existsForwardContent)) {
				existsContent.addAll(existsForwardContent);
			}
			// 随机取出发送的微博内容
			List<Long> sendContent = RandomUtil.randomItemsUnmodify(allContent, existsContent,
					this.postRandom.get());
			int replyWeight = this.replyRandomer.get();
			int forwardWeight = this.forwardRandomer.get();
			WeightRandomer<String> wr = this.getReplyAndForwardWeight(replyWeight, forwardWeight);
			if (!CollectionUtils.isEmpty(sendContent)) {
				Set<Long> contentIdSet = new HashSet<Long>();
				for (Long contentId : sendContent) {
					Content c = this.contentService.getContentById(contentId);
					Long contentUid = c.getUid();
					// 排重，同一作者的内容不重复操作
					if (!contentIdSet.contains(contentUid)) {
						String op = wr.get();
						// 回复
						if (ReplyAndForwardConfig.REPLY.equals(op)) {
							this.sendReplyWeibo(c, uid, false);
							this.robotContentService.save(uid, contentId, RobotContent.TYPE_REPLY);
							contentIdSet.add(contentUid);
						}
						// 转发
						if (ReplyAndForwardConfig.FORWARD.equals(op)) {
							KeyValue<Long, Content> kv = new KeyValue<Long, Content>();
							kv.setKey(uid);
							kv.setValue(c);
							this.forwardWeiboContent.send(kv);
							this.robotContentService
									.save(uid, contentId, RobotContent.TYPE_FORWARD);
							contentIdSet.add(contentUid);
						}
						// 回复并转发
						if (ReplyAndForwardConfig.REPLYFORWARD.equals(op)) {
							this.sendReplyWeibo(c, uid, true);
							this.robotContentService.save(uid, contentId, RobotContent.TYPE_REPLY);
							this.robotContentService
									.save(uid, contentId, RobotContent.TYPE_FORWARD);
							contentIdSet.add(contentUid);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(ReplyAndForwardConfig config) {
		this.config = config;
		Range<Integer> replyRange = config.getReplyPer();
		Range<Integer> forwardRange = config.getForwardPer();
		Range<Integer> postRange = config.getPostNumber();
		this.replyRandomer = ConfigDataUtil.createGteLteRandomer(replyRange, true);
		this.forwardRandomer = ConfigDataUtil.createGteLteRandomer(forwardRange, true);
		this.postRandom = ConfigDataUtil.createGteLteRandomer(postRange, true);
	}

	/**
	 * 获取比例
	 * 
	 * @param replyWeight
	 * @param forwardWeight
	 * @return
	 */
	private WeightRandomer<String> getReplyAndForwardWeight(int replyWeight, int forwardWeight) {
		int tmp = (replyWeight + forwardWeight) - 100;
		WeightRandomer<String> wr = new WeightRandomer<String>();
		if (tmp >= 0) {
			wr.add(ReplyAndForwardConfig.REPLY, replyWeight - tmp);
			wr.add(ReplyAndForwardConfig.FORWARD, forwardWeight - tmp);
			wr.add(ReplyAndForwardConfig.REPLYFORWARD, tmp);
		} else {
			wr.add(ReplyAndForwardConfig.REPLY, replyWeight);
			wr.add(ReplyAndForwardConfig.FORWARD, forwardWeight);
			wr.add(ReplyAndForwardConfig.DONOTHING, 100 - replyWeight - forwardWeight);
		}
		return wr;
	}

	/**
	 * 发送间隔
	 * 
	 * @return
	 */
	private Date getPostInterval() {
		Integer postInterval = this.config.getPostInterval();
		if (postInterval == null) {
			postInterval = ReplyAndForwardConfig.DEFAULT_INTERVAL;
		}
		return DateUtils.addMinutes(new Date(), postInterval);
	}

	private void sendReplyWeibo(Content c, Long uid, Boolean needForward) {
		ReplyWeiboDto replyDto = new ReplyWeiboDto();
		replyDto.setContentId(c.getId());
		replyDto.setText(c.getContent());
		replyDto.setStartTime(getPostInterval());
		replyDto.setNeedForward(needForward);
		replyDto.setUid(uid);
		this.replyWeibo.send(replyDto);
	}
}
