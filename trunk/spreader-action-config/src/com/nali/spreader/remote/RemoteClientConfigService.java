package com.nali.spreader.remote;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.dao.ICrudClientConfigDao;
import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientConfig;
import com.nali.spreader.model.ClientConfigExample;
import com.nali.spreader.model.ClientConfigExample.Criteria;

@Service
public class RemoteClientConfigService implements IRemoteClientConfigService {
	@Autowired
	private ICrudClientConfigDao crudClientConfigDao;

	@Override
	public String readClientConfig(String configName, String configMD5) {
		ClientContext ctx = ClientContext.getCurrentContext();
		Long clientId = ctx.getClientId();
		Assert.notNull(clientId, "clientId must no null");
		return readClientConfig(configName, configMD5, clientId, 0);
	}

	@Override
	public String readGroupConfig(Long groupId, String configName,
			String configMD5) {
		return readClientConfig(configName, configMD5, groupId, 1);
	}

	private String readClientConfig(String configName, String configMD5,
			Long id, Integer type) {
		Assert.notNull(configName, "configName must no null");
		Assert.notNull(type, "type must no null");
		ClientConfigExample ce = new ClientConfigExample();
		Criteria cri = ce.createCriteria();
		cri.andClientIdEqualTo(id);
		cri.andConfigNameEqualTo(configName);
		cri.andTypeEqualTo(type);
		List<ClientConfig> cfgList = crudClientConfigDao
				.selectByExampleWithBLOBs(ce);
		if (cfgList.size() > 0) {
			ClientConfig cc = cfgList.get(0);
			String md5 = cc.getConfigMd5();
			if (StringUtils.isEmpty(configMD5) || !configMD5.equals(md5)) {
				String cfg = cc.getClientConfig();
				return cfg;
			}
		}
		return null;
	}
}
