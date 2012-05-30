package com.nali.spreader.analyzer;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotForwardListDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.group.config.UserGroupMetaInfo;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

@Component
@ClassDescription("分组·转发指定微博")
public class RobotForwardByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer, Configable<RobotForwardListDto> {
	private static Logger logger = Logger.getLogger(RobotForwardByGroup.class);
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IRobotContentService robotContentService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Content>> forwardWeiboContent;

	private Randomer<Integer> random;
	private List<String> urlList;

	public RobotForwardByGroup() {
		super(UserGroupMetaInfo.FROM_GROUP + "转发微博");
	}

	@Override
	public void init(RobotForwardListDto config) {
		Range<Integer> range = config.getCount();
		List<String> urlList = config.getUrlList();
		if (range != null && range.checkNotNull() && urlList!=null && !urlList.isEmpty()) {
			random = new NumberRandomer(range.getGte(), range.getLte()+1);
			this.urlList = urlList;
		} else {
			throw new IllegalArgumentException("输入值不完整");
		}
	}

	@Override
	public String work() {
		Long fromGroup = this.getFromUserGroup();
		for (String url : urlList) {
			Content content = contentService.parseUrl(url);
			if(content==null) {
				logger.error("illegal url:" + url);
				continue;
			}
			List<Long> existRobotIds = robotContentService.findRelatedRobotId(content.getId(), null);
			Iterator<User> robots = userGroupFacadeService.queryLimitedRandomGrouppedUser(fromGroup, random.get(), existRobotIds);
			while (robots.hasNext()) {
				User robot = robots.next();
				robotContentService.save(robot.getId(), content.getId(), RobotContent.TYPE_FORWARD);
				forwardWeiboContent.send(new KeyValue<Long, Content>(robot.getId(), content));
			}
		}
		return null;
	}

}
