package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IUploadAvatarService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class UploadUserAvatar extends SingleTaskMachineImpl implements
		PassiveWorkshop<Long, KeyValue<Long, KeyValue<Long, Boolean>>> {
	private static final Logger logger = Logger
			.getLogger(UploadUserAvatar.class);
	@Autowired
	private IUploadAvatarService uploadService;
	private IUserService userService;

	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	public UploadUserAvatar() {
		super(SimpleActionConfig.uploadAvatar, Website.weibo, Channel.normal);
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		User user = userService.getUserById(uid);
		Integer gender = 3;
		if (user != null) {
			gender = user.getGender();
		}
		Map<List<Photo>, Integer> dataMap = uploadService
				.createWeightMap(gender);
		List<Photo> dataList = uploadService.findPhotoListByWeight(dataMap);
		Photo avatar = uploadService.randomAvatarUrl(dataList);
		Long pid = null;
		String purl = null;
		if (avatar != null) {
			pid = avatar.getId();
			purl = avatar.getPicUrl();
		}
		Map<String, Object> contents = CollectionUtils.newHashMap(5);
		contents.put("uid", uid);
		contents.put("purl", purl);
		contents.put("pid", pid);
		exporter.createTask(contents, uid, SpecialDateUtil.afterToday(1));
		logger.info("URL:" + purl);
	}

	@Override
	public void handleResult(Date updateTime,
			KeyValue<Long, KeyValue<Long, Boolean>> keyValue) {
		// 如果上传成功则需要更新USER表
		if (keyValue.getValue().getValue()) {
			Long pid = keyValue.getValue().getKey();
			Long uid = keyValue.getKey();
			uploadService.updateUserAvatarUrl(uid, pid);
		} else {
			logger.warn("上传头像不成功");
		}
	}
}
