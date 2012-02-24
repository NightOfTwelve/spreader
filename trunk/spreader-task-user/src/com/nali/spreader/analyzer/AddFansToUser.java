package com.nali.spreader.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.BaseUserDto;
import com.nali.spreader.config.CategoryUserMatchDto;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;

@Component
@ClassDescription("关注用户")
public class AddFansToUser implements RegularAnalyzer,Configable<CategoryUserMatchDto> {
	private Integer websiteId = Website.weibo.getId();
	private static Logger logger = Logger.getLogger(AddFansToUser.class);
	private IUserService userService;
	private CategoryUserMatchDto dto;
	@Autowired
	private IGlobalUserService globalUserService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> addUserFans;
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void work() {
		List<User> users = findUserFansInfoByDto(dto.getUser(), dto.getCategory(), dto.getWebsiteId(), false);//目前websiteId会强制为微博
		List<User> robots = findUserFansInfoByDto(dto.getRobot(), dto.getCategory(), dto.getWebsiteId(), true);
		List<Long> robotIds = ids(robots);
		if(users.size()==0 || robots.size()==0) {
			logger.warn("not enough users in category:" + dto.getCategory());
			return;
		}
		double robotRate = dto.getRobotRate();
		//关注上限
		Long maxUserCount = dto.getMaxUserValue();
		for (User user : users) {
			long needRobotCount = (long)Math.ceil(nvl(user.getFans()).doubleValue() * robotRate) - nvl(user.getRobotFans()).longValue();
			//如果关注上限不为null,则取needRobotCount，maxUserCount中较小的值
			if(maxUserCount != null) {
				needRobotCount = Math.min(needRobotCount, maxUserCount);
			}
			Long toUid = user.getId();
			List<Long> existsIdList = globalUserService.findRelationUserId(toUid, UserRelation.TYPE_ATTENTION, true);
			Set<Long> existsIds = new HashSet<Long>(existsIdList);
			
			Iterator<Long> idIter = robotIds.iterator();
			long i = 0;
			while (i < needRobotCount && idIter.hasNext()) {
				Long id = idIter.next();
				if (!existsIds.contains(id)) {
					addUserFans.send(new KeyValue<Long, Long>(id, toUid));
					i++;
				}
			}
			if(logger.isInfoEnabled()) {
				logger.info(i + " robots are going to follow user:" + toUid);
			}
		}
	}

	private Number nvl(Number n) {
		return n==null?0:n;
	}

	private List<Long> ids(List<User> robots) {
		List<Long> ids = new ArrayList<Long>(robots.size());
		for (User robot : robots) {
			ids.add(robot.getId());
		}
		return ids;
	}

	private List<User> findUserFansInfoByDto(BaseUserDto dto, String category, Integer websiteId, Boolean isRobot) {
		UserDto userDto = UserDto.genUserDtoFrom(dto);
		userDto.setCategories(Arrays.asList(category));
		userDto.setIsRobot(isRobot);
		userDto.setWebsiteId(websiteId);
		return userService.findUserFansInfoByDto(userDto);
	}

	@Override
	public void init(CategoryUserMatchDto dto) {
		this.dto = dto;
	}

}
