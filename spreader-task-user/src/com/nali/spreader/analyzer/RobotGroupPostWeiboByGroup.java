package com.nali.spreader.analyzer;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotGroupPostWeiboDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IRobotGroupContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

/**
 * 机器人分组发微博
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("分组·机器人分组发微博")
public class RobotGroupPostWeiboByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<RobotGroupPostWeiboDto> {
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IRobotContentService robotContentService;
	@Autowired
	private IRobotGroupContentService groupContentService;
	@AutowireProductLine
	TaskProduceLine<KeyValue<Long, String>> postWeiboContent;
	@Autowired
	private IContentService contentService;

	private RobotGroupPostWeiboDto config;

	private Randomer<Integer> fromRandom;
	private Randomer<Integer> toRandom;
	private Randomer<Integer> contentRandom;

	public RobotGroupPostWeiboByGroup() {
		super("${fromGroup}发送${toGroup}的微博");
	}

	@Override
	public String work() {
		Long fromGid = this.getFromUserGroup();
		Long toGid = this.getToUserGroup();
		Iterator<User> toIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(toGid,
				toRandom.get());
		while (toIterator.hasNext()) {
			User toUser = toIterator.next();
			List<Long> toContent = groupContentService.findContentIds(config, toUser.getId(),
					contentRandom.get());
			if (toContent.size() > 0) {
				for (Long contentId : toContent) {
					Content c = this.contentService.getContentById(contentId);
					String text = null;
					if (c != null) {
						text = c.getContent();
					}
					List<Long> refRobotList = robotContentService.findRelatedRobotId(contentId, null);
					Iterator<User> fromIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(
							fromGid, fromRandom.get(), refRobotList);
					while (fromIterator.hasNext()) {
						Long refRobot = fromIterator.next().getId();
						postWeiboContent.send(new KeyValue<Long, String>(refRobot, text));
						robotContentService.save(refRobot, contentId, RobotContent.TYPE_POST);
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(RobotGroupPostWeiboDto config) {
		this.config = config;
		Range<Integer> fromRange = config.getFromPostGroup();
		Range<Integer> toRange = config.getToPostGroup();
		Range<Integer> contentRange = config.getContentCount();
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
