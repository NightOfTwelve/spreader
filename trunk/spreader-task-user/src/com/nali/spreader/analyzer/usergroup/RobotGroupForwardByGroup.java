package com.nali.spreader.analyzer.usergroup;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotGroupForwardDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IRobotGroupContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

/**
 * 分组A转发分组B的微博
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("分组·机器人转发微博")
public class RobotGroupForwardByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<RobotGroupForwardDto> {
//	private static final MessageLogger logger = LoggerFactory.getLogger(RobotGroupForwardByGroup.class);
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IRobotContentService robotContentService;
	@Autowired
	private IRobotGroupContentService groupContentService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> forwardWeiboContent;

	private RobotGroupForwardDto config;

	private Randomer<Integer> fromRandom;
	private Randomer<Integer> toRandom;
	private Randomer<Integer> contentRandom;

	public RobotGroupForwardByGroup() {
		super("${fromGroup}转发${toGroup}的微博");
	}

	@Override
	public String work() {
		Long fromGid = this.getFromUserGroup();
		Long toGid = this.getToUserGroup();
		// 随机获取被转发用户分组的用户信息
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
					// 随机获取转发机器人分组的用户信息
					Iterator<User> fromIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(fromGid,
							fromRandom.get(),refRobotList);
					while (fromIterator.hasNext()) {
						Long refRobot = fromIterator.next().getId();
						forwardWeiboContent.send(new KeyValue<Long, Long>(refRobot, contentId));
						robotContentService.save(refRobot, contentId, RobotContent.TYPE_FORWARD);
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(RobotGroupForwardDto config) {
		this.config = config;
		// 转发分组用户上限
		Range<Integer> fromRange = config.getFromForwardGroup();
		// 被转发分组用户上限
		Range<Integer> toRange = config.getToForwardGroup();
		// 符合转发的微博上下限
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
