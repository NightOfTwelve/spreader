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

import com.nali.spreader.analyzer.other.Words;
import com.nali.spreader.config.ConfigDataUtil;
import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.ReplyAndForwardConfig;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.ForwardDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.ReplyDto;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.AvgRandomer;
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
	private TaskProduceLine<ReplyDto> replyWeibo;
	@AutowireProductLine
	private TaskProduceLine<ForwardDto> forwardWeiboContent;
	@Autowired
	private IGlobalUserService globalUserService;
	private Set<Long> allKeywords;
	private Integer postInterval;
	private Randomer<String> replyWords;

	public ReplyAndForward() {
		super("转发回复${fromGroup}的微博");
	}

	@Override
	public String work() {
		Long gid = getFromUserGroup();
		Iterator<Long> iter = userGroupFacadeService.queryAllGrouppedUser(gid);
		Long[] uids = globalUserService.findPostContentUids(config.getvType(), config.getFans(),
				config.getArticles());
		Long[] sendKeywords = getKeywordArrays(allKeywords);
		List<Long> allContent = contentService.findContentIdByPostContentDto(config
				.getPostWeiboContentDto(sendKeywords, uids));
		Set<Long> contentUidSet = new HashSet<Long>();
		Set<Long> contentIdSet = new HashSet<Long>();
		while (iter.hasNext()) {
			Long uid = iter.next();
			if (sendKeywords.length == 0) {
				sendKeywords = getUserKeyordArrays(uid);
				allContent = contentService.findContentIdByPostContentDto(config
						.getPostWeiboContentDto(sendKeywords, uids));
			}
			// 排除掉的内容
			Set<Long> existsContent = getExistsContent(uid);
			existsContent.addAll(contentIdSet);
			// 随机取出发送的微博内容
			List<Long> sendContent = RandomUtil.randomItemsReadOnly(allContent, existsContent,
					postRandom.get());
			WeightRandomer<Integer> wr = getReplyAndForwardWeight(replyRandomer.get(),
					forwardRandomer.get());
			Date postTime = new Date();
			for (Long contentId : sendContent) {
				Content c = contentService.getContentById(contentId);
				if (c != null) {
					Long contentUid = c.getUid();
					// 排重，同一作者的内容不重复操作
					if (!contentUidSet.contains(contentUid)) {
						Integer op = wr.get();
						work(op, c, uid, postTime);
						contentUidSet.add(contentUid);
						postTime = DateUtils.addMinutes(postTime, postInterval);
					}
					contentIdSet.add(c.getId());
				}
			}
		}
		return null;
	}

	private void work(Integer op, Content c, Long uid, Date postTime) {
		if (ReplyAndForwardConfig.REPLY.equals(op)) {// 回复
			sendReplyWeibo(c, uid, false, postTime);
			robotContentService.save(uid, c.getId(), RobotContent.TYPE_REPLY);
		} else if (ReplyAndForwardConfig.FORWARD.equals(op)) {// 转发
			ForwardDto dto = new ForwardDto(c.getId(), uid, postTime);
			forwardWeiboContent.send(dto);
			robotContentService.save(uid, c.getId(), RobotContent.TYPE_FORWARD);
		} else if (ReplyAndForwardConfig.REPLYFORWARD.equals(op)) {// 回复并转发
			sendReplyWeibo(c, uid, true, postTime);
			robotContentService.save(uid, c.getId(), RobotContent.TYPE_REPLY);
			robotContentService.save(uid, c.getId(), RobotContent.TYPE_FORWARD);
		}
	}

	private Long[] getKeywordArrays(Set<Long> set) {
		Long[] sizeArr = new Long[set.size()];
		return set.toArray(sizeArr);
	}

	private Long[] getUserKeyordArrays(Long uid) {
		Long[] arr = keywordService.userKeywordArray(uid);
		if (arr.length == 0) {
			return keywordService.defaultKeywordArray();
		}
		return arr;
	}

	private Set<Long> getExistsContent(Long uid) {
		List<Long> existsReplyContent = robotContentService.findRelatedContentId(uid,
				RobotContent.TYPE_REPLY);
		List<Long> existsForwardContent = robotContentService.findRelatedContentId(uid,
				RobotContent.TYPE_FORWARD);
		List<Long> existsContent = new ArrayList<Long>();
		existsContent.addAll(existsReplyContent);
		existsContent.addAll(existsForwardContent);
		return new HashSet<Long>(existsContent);
	}

	@Override
	public void init(ReplyAndForwardConfig config) {
		this.config = config;
		Range<Integer> replyRange = config.getReplyPer();
		Range<Integer> forwardRange = config.getForwardPer();
		Range<Integer> postRange = config.getPostNumber();
		this.replyRandomer = ConfigDataUtil.createNotNullGteLteRandomer(replyRange);
		this.forwardRandomer = ConfigDataUtil.createNotNullGteLteRandomer(forwardRange);
		this.postRandom = ConfigDataUtil.createGteLteRandomer(postRange, true);
		// 关键字
		List<String> keywords = this.config.getKeywords();
		// 分类
		List<String> categories = this.config.getCategories();
		allKeywords = this.keywordService.createMergerKeyword(keywords, categories);
		postInterval = config.getPostInterval();
		if (postInterval == null) {
			postInterval = PostWeiboConfig.DEFAULT_INTERVAL;
		}
		if (config.getWords() == null || config.getWords().size() == 0) {
			replyWords = Words.defaultReplyWords;
		} else {
			replyWords = new AvgRandomer<String>(config.getWords());
		}
	}

	/**
	 * 获取比例
	 * 
	 * @param replyWeight
	 * @param forwardWeight
	 * @return
	 */
	private WeightRandomer<Integer> getReplyAndForwardWeight(int replyWeight, int forwardWeight) {
		int tmp = (replyWeight + forwardWeight) - 100;
		WeightRandomer<Integer> wr = new WeightRandomer<Integer>();
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

	private void sendReplyWeibo(Content c, Long uid, Boolean needForward, Date postTime) {
		ReplyDto replyDto = new ReplyDto();
		replyDto.setContentId(c.getId());
		replyDto.setText(replyWords.get());
		replyDto.setStartTime(postTime);
		replyDto.setForward(needForward);
		replyDto.setRobotId(uid);
		this.replyWeibo.send(replyDto);
	}
}
