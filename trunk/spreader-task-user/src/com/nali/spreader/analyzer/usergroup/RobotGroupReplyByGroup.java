package com.nali.spreader.analyzer.usergroup;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.analyzer.other.Words;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotGroupReplyDto;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.ReplyDto;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IRobotGroupContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

/**
 * 分组A回复分组B的微博
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("分组·机器人回复微博")
public class RobotGroupReplyByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<RobotGroupReplyDto> {
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IRobotContentService robotContentService;
	@Autowired
	private IRobotGroupContentService groupContentService;
	@AutowireProductLine
	private TaskProduceLine<ReplyDto> replyWeibo;

	private RobotGroupReplyDto config;

	private Randomer<String> replyWords;
	private Randomer<Integer> fromRandom;
	private Randomer<Integer> toRandom;
	private Randomer<Integer> contentRandom;

	public RobotGroupReplyByGroup() {
		super("${fromGroup}回复${toGroup}的微博");
	}

	@Override
	public String work() {
		Long fromGid = this.getFromUserGroup();
		Long toGid = this.getToUserGroup();
		// 随机获取被回复用户分组的用户信息
		Iterator<User> toIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(toGid,
				toRandom.get());
		while (toIterator.hasNext()) {
			User toUser = toIterator.next();
			// 随机要转发的内容
			List<Long> toContent = groupContentService.findContentIds(config, toUser.getId(),
					contentRandom.get());
			if (toContent.size() > 0) {
				for (Long contentId : toContent) {
					List<Long> refRobotList = robotContentService.findRelatedRobotId(contentId, null);
					// 随机获取回复机器人分组的用户信息
					Iterator<User> fromIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(
							fromGid, fromRandom.get(), refRobotList);
					while (fromIterator.hasNext()) {
						Long refRobot = fromIterator.next().getId();
						ReplyDto replyDto = new ReplyDto(refRobot, contentId, replyWords.get(), false);
						replyWeibo.send(replyDto);
						robotContentService.save(refRobot, contentId, RobotContent.TYPE_REPLY);
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(RobotGroupReplyDto config) {
		this.config = config;
		// 转发分组用户上限
		Range<Integer> fromRange = config.getFromReplyGroup();
		// 被回复分组用户上限
		Range<Integer> toRange = config.getToReplyGroup();
		// 符合回复的微博上下限
		Range<Integer> contentRange = config.getContentCount();
		this.replyWords = Words.defaultReplyWords;
		if (fromRange != null && toRange != null && contentRange != null && fromRange.checkNotNull()
				&& toRange.checkNotNull() && contentRange.checkNotNull()) {
			fromRandom = new NumberRandomer(fromRange.getGte(), fromRange.getLte() + 1);
			toRandom = new NumberRandomer(toRange.getGte(), toRange.getLte() + 1);
			contentRandom = new NumberRandomer(contentRange.getGte(), contentRange.getLte() + 1);
		} else {
			throw new IllegalArgumentException("输入值不完整");
		}
	}
}
