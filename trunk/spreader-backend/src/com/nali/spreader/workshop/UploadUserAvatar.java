package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUploadAvatarService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class UploadUserAvatar extends SingleTaskMachineImpl implements
		PassiveWorkshop<Long, KeyValue<Long, KeyValue<Long, Boolean>>> {
	private static final MessageLogger logger = LoggerFactory
			.getLogger(UploadUserAvatar.class);
	@Autowired
	private IUploadAvatarService uploadService;
	@Autowired
	private IGlobalUserService globalUserService;
	// 默认分类为通用
	private static Integer GENERA = 3;

	public UploadUserAvatar() {
		super(SimpleActionConfig.uploadAvatar, Website.weibo, Channel.normal);
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		User user = globalUserService.getUserById(uid);
		Integer gender = GENERA;
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
		if (StringUtils.isNotEmpty(purl)) {
			exporter.setProperty("uid", uid);
			exporter.setProperty("purl", purl);
			exporter.setProperty("pid", pid);
			exporter.send(uid, SpecialDateUtil.afterToday(1));
		} else {
			logger.warn("URL为空，不执行任务", new Exception());
			return;
		}
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
