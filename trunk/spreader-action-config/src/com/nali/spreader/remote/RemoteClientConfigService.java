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

@Service
public class RemoteClientConfigService implements IRemoteClientConfigService {
	@Autowired
	private ICrudClientConfigDao crudClientConfigDao;

	@Override
	public String readClientConfig(String configName, String configMD5) {
		return readClientConfig(configName, configMD5, 0);
	}

	@Override
	public String readClientConfig(String configName, String configMD5,
			Integer type) {
		ClientContext ctx = ClientContext.getCurrentContext();
		Long clientId = ctx.getClientId();
		Assert.notNull(clientId, "clientId must no null");
		Assert.notNull(configName, "configName must no null");
		Assert.notNull(type, "type must no null");
		ClientConfigExample ce = new ClientConfigExample();
		ClientConfigExample.Criteria cri = ce.createCriteria();
		cri.andClientIdEqualTo(clientId);
		cri.andConfigNameEqualTo(configName);
		cri.andTypeEqualTo(type);
		List<ClientConfig> cfgList = crudClientConfigDao
				.selectByExampleWithBLOBs(ce);
		if (cfgList.size() > 0) {
			ClientConfig cc = cfgList.get(0);
			String md5 = cc.getConfigMd5();
			String cfg = cc.getClientConfig();
			if (StringUtils.isEmpty(configMD5)) {
				return cfg;
			} else {
				if (!configMD5.equals(md5)) {
					return cfg;
				}
			}
		}
		return null;
	}
}
