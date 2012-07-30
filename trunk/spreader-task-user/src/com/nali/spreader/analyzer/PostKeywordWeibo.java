package com.nali.spreader.analyzer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.config.Range;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.WeiboContentDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;

@Component
@ClassDescription("关键字·发微博")
public class PostKeywordWeibo extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<PostWeiboConfig> {
	private PostWeiboConfig config;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IRobotContentService robotContentService;
	@AutowireProductLine
	private TaskProduceLine<WeiboContentDto> postWeiboContent;
	// 发帖随机数
	private Randomer<Integer> postRandom;

	public PostKeywordWeibo() {
		super("发送${fromGroup}的微博");
	}

	@Override
	public String work() {
		// 关键字
		List<String> keywords = this.config.getKeywords();
		// 分类
		List<String> categories = this.config.getCategories();
		// 整合所有的关键字列表
		List<String> allKeywords = this.keywordService.createKeywordList(keywords, categories);
		// 用户分组ID
		Long gid = this.getFromUserGroup();
		if (gid == null) {
			throw new IllegalArgumentException("gid is null");
		}
		// 获取分组下所有用户
		Iterator<GrouppedUser> iter = this.userGroupFacadeService.queryAllGrouppedUser(gid);
		while (iter.hasNext()) {
			GrouppedUser gu = iter.next();
			Long uid = gu.getUid();
			List<String> sendKeywords = this.keywordService.createSendKeywordList(allKeywords, uid);
			// 获取所有的微博内容
			List<Long> allContent = this.contentService.findContentIdByPostContentDto(this.config
					.getPostWeiboContentDto(sendKeywords));
			// 已发送的内容
			List<Long> existsContent = this.robotContentService.findRelatedContentId(uid,
					RobotContent.TYPE_POST);
			// 随机取出发送的微博内容
			List<Long> sendContent = RandomUtil.randomItemsUnmodify(allContent, existsContent,
					this.postRandom.get());
			if (!CollectionUtils.isEmpty(sendContent)) {
				for (Long cid : sendContent) {
					Content c = this.contentService.getContentById(cid);
					c.setUid(uid);
					Integer postInterval = config.getPostInterval();
					if (postInterval == null) {
						postInterval = PostWeiboConfig.DEFAULT_INTERVAL;
					}
					Date postTime = DateUtils.addMinutes(new Date(), postInterval);
					WeiboContentDto param = WeiboContentDto.getWeiboContentDto(c, postTime);
					this.postWeiboContent.send(param);
					this.robotContentService.save(uid, cid, RobotContent.TYPE_POST);
				}
			}
		}
		return null;
	}

	@Override
	public void init(PostWeiboConfig config) {
		this.config = config;
		Range<Integer> postRange = config.getPostNumber();
		this.postRandom = this.keywordService.createRandomer(postRange,
				PostWeiboConfig.DEFAULT_POSTNUM_GTE, PostWeiboConfig.DEFAULT_POSTNUM_LTE);
	}
}
