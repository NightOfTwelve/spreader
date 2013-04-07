package com.nali.spreader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudClientConfigDao;
import com.nali.spreader.model.ClientConfig;
import com.nali.spreader.model.ClientConfigExample;

@Service
public class ClitentConfigServiceImpl implements IClitentConfigService {
	@Autowired
	private ICrudClientConfigDao crudClientConfigDao;

	@Override
	public PageResult<ClientConfig> queryPageData(Long clientId, Limit limit) {
		ClientConfigExample exa = new ClientConfigExample();
		ClientConfigExample.Criteria c = exa.createCriteria();
		if (clientId != null) {
			c.andClientIdEqualTo(clientId);
		}
		int count = crudClientConfigDao.countByExample(exa);
		exa.setLimit(limit);
		List<ClientConfig> list = crudClientConfigDao.selectByExample(exa);
		return new PageResult<ClientConfig>(list, limit, count);
	}

	@Override
	public void saveClientConfigs(Long id, Long clientId, String configName,
			int configType, String cfgs, String cfgMD5) {
		if (id != null) {
			ClientConfig exists = crudClientConfigDao.selectByPrimaryKey(id);
			exists.setClientConfig(cfgs);
			exists.setConfigName(configName);
			exists.setConfigType(configType);
			exists.setClientId(clientId);
			exists.setConfigMd5(cfgMD5);
			crudClientConfigDao.updateByPrimaryKeySelective(exists);
		} else {
			ClientConfig cc = new ClientConfig();
			cc.setClientId(clientId);
			cc.setClientConfig(cfgs);
			cc.setConfigName(configName);
			cc.setConfigType(configType);
			cc.setConfigMd5(cfgMD5);
			try {
				crudClientConfigDao.insertSelective(cc);
			} catch (DuplicateKeyException e) {
				ClientConfig cfg = getConfigByClientId(clientId, configName);
				saveClientConfigs(cfg.getId(), clientId, configName,
						configType, cfgs, cfgMD5);
			}
		}
	}

	@Override
	public ClientConfig getConfigByClientId(Long clientId, String configName) {
		ClientConfigExample exa = new ClientConfigExample();
		ClientConfigExample.Criteria c = exa.createCriteria();
		c.andClientIdEqualTo(clientId);
		c.andConfigNameEqualTo(configName);
		return crudClientConfigDao.selectByExample(exa).get(0);
	}

	@Override
	public ClientConfig getConfigById(Long id) {
		return crudClientConfigDao.selectByPrimaryKey(id);
	}
}