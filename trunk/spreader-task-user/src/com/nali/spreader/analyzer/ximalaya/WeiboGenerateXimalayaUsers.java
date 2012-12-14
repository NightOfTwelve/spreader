package com.nali.spreader.analyzer.ximalaya;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.GenXimalayaUsersConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.service.IGlobalUserService;

@Component
@ClassDescription("喜马拉雅·用微博帐号生成用户")
public class WeiboGenerateXimalayaUsers implements RegularAnalyzer,
		Configable<GenXimalayaUsersConfig> {
	private int genCount;
	private String[] gNames;
	@Autowired
	private IUserGroupInfoService groupInfoService;
	@Autowired
	private IGlobalUserService globalUserService;
	@AutowireProductLine
	private TaskProduceLine<List<Long>> genXimalayaUsersByWeibo;

	@Override
	public String work() {
		List<Long> gids = groupInfoService.getGidsByGroupName(gNames);
		Set<Long> excludeUids = groupInfoService.queryExcludeGroupUsers(gids);
		List<Long> genUids = globalUserService.getUidsByLastUidAndLimit(genCount);
		@SuppressWarnings("unchecked")
		List<Long> subUids = (List<Long>) CollectionUtils.subtract(genUids, excludeUids);
		genXimalayaUsersByWeibo.send(subUids);
		return "预计生成：" + genCount + "个,用户分组过滤：" + (genUids.size() - subUids.size()) + "个";
	}

	@Override
	public void init(GenXimalayaUsersConfig config) {
		if (config.getGenCount() == null || config.getGenCount().intValue() <= 0) {
			throw new IllegalArgumentException(" genCount is null or <= 0");
		}
		genCount = config.getGenCount().intValue();
		List<String> userGroups = config.getExcludeUserGroup();
		if (userGroups != null) {
			String[] size = new String[userGroups.size()];
			gNames = userGroups.toArray(size);
		} else {
			gNames = ArrayUtils.EMPTY_STRING_ARRAY;
		}
	}
}
