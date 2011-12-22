package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Photo;
import com.nali.spreader.dto.UploadAvatarDto;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IUploadAvatarService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.utils.PhotoHelper;

public class UploadUserAvatar extends SingleTaskMachineImpl implements
		PassiveWorkshop<UploadAvatarDto, UploadAvatarDto> {
	@Autowired
	private IUploadAvatarService uploadService;

	public UploadUserAvatar() {
		super(SimpleActionConfig.uploadAvatar, Website.weibo, Channel.normal);
	}

	@Override
	public void work(UploadAvatarDto dto, SingleTaskExporter exporter) {
		Integer gender = dto.getGender();
		Long robotId = dto.getRobotId();
		if (gender == null) {
			// 默认通用
			gender = 3;
		}
		String headerUrl = PhotoHelper
				.getPropertiesMap("/avatarconfig/webDavService.properties")
				.get("url").toString();
		Map<List<Photo>, Integer> dataMap = uploadService
				.createWeightMap(gender);
		List<Photo> dataList = uploadService.findPhotoListByWeight(dataMap);
		Photo avatar = uploadService.randomAvatarUrl(dataList,
				PhotoHelper.splitUrlEnd(headerUrl));
		Map<String, Object> contents = CollectionUtils.newHashMap(5);
		contents.put("uid", dto.getUid());
		contents.put("gender", dto.getGender());
		contents.put("purl", avatar.getPicUrl());
		contents.put("pid", avatar.getId());
		exporter.createTask(contents, robotId, SpecialDateUtil.afterToday(1));
	}

	@Override
	public void handleResult(Date updateTime, UploadAvatarDto dto) {
		// 如果上传成功则需要更新USER表
		if (dto.getSuccess()) {

		}

	}

}
