package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.BaseUserDto;
import com.nali.spreader.config.CategoryUserMatchDto;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
@ClassDescription("建立用户关注")
public class AddUserFans extends SingleTaskComponentImpl implements RegularTaskProducer,Configable<CategoryUserMatchDto> {
	private static Logger logger = Logger.getLogger(AddUserFans.class);
	private IUserService userService;
	private CategoryUserMatchDto dto;
	@Autowired
	private IGlobalUserService globalUserService;

	public AddUserFans() {
		super(SimpleActionConfig.addUserFans, Website.weibo, Channel.normal);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void work(TaskExporter exporter) {
		List<User> users = findUserFansInfoByDto(dto.getUser(), dto.getCategory(), dto.getWebsiteId(), false);//目前websiteId会强制为微博
		List<User> robots = findUserFansInfoByDto(dto.getRobot(), dto.getCategory(), dto.getWebsiteId(), true);
		List<Long> robotIds = ids(robots);
		if(users.size()==0 || robots.size()==0) {
			logger.warn("not enough users in category:" + dto.getCategory());
			return;
		}
		double robotRate = dto.getRobotRate();
		for (User user : users) {
			long needRobotCount = (long)Math.ceil(user.getFans() * robotRate) - user.getRobotFans();
			Long toUid = user.getId();
			List<Long> existsIdList = globalUserService.findRelationUserId(toUid, UserRelation.TYPE_ATTENTION, true);
			Set<Long> existsIds = new HashSet<Long>(existsIdList);
			
			Iterator<Long> idIter = robotIds.iterator();
			long i = 0;
			while (i < needRobotCount && idIter.hasNext()) {
				Long id = idIter.next();
				if (!existsIds.contains(id)) {
					genTask(exporter, id, user);
					i++;
				}
			}
			if(logger.isInfoEnabled()) {
				logger.info(i + " robots are going to follow user:" + toUid);
			}
		}
	}

	private void genTask(TaskExporter exporter, Long uid, User user) {
		Map<String,Object> content = CollectionUtils.newHashMap(3);
		content.put("id", uid);
		content.put("uid", user.getId());
		content.put("websiteUid", user.getWebsiteUid());
		exporter.createTask(content, uid, SpecialDateUtil.afterToday(3));
	}

	private List<Long> ids(List<User> robots) {
		List<Long> ids = new ArrayList<Long>(robots.size());
		for (User robot : robots) {
			ids.add(robot.getId());
		}
		return ids;
	}

	private List<User> findUserFansInfoByDto(BaseUserDto dto, String category, Integer websiteId, Boolean isRobot) {
		UserDto userDto = new UserDto();
		userDto.setCategories(Arrays.asList(category));
		userDto.setIsRobot(isRobot);
		userDto.setWebsiteId(websiteId);
		
		userDto.setArticles(dto.getArticles());
		userDto.setAttentions(dto.getAttentions());
		userDto.setBirthdayYear(dto.getBirthdayYear());
		userDto.setCity(dto.getCity());
		userDto.setFans(dto.getFans());
		userDto.setGender(dto.getGender());
		userDto.setProvince(dto.getProvince());
		userDto.setvType(dto.getvType());
		return userService.findUserFansInfoByDto(userDto);
	}

	@Override
	public void init(CategoryUserMatchDto dto) {
		this.dto = dto;
	}

}
