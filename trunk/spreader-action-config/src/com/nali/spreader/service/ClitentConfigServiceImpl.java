package com.nali.spreader.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
		List<ClientConfig> list = crudClientConfigDao
				.selectByExampleWithBLOBs(exa);
		return new PageResult<ClientConfig>(list, limit, count);
	}

	@Override
	public void saveClientConfigs(Long id, Long clientId, String configName,
			int configType, String cfgs, String cfgMD5, int clientType,
			String note) {
		if (id != null) {
			ClientConfig exists = crudClientConfigDao.selectByPrimaryKey(id);
			exists.setClientConfig(cfgs);
			exists.setConfigName(configName);
			exists.setConfigType(configType);
			exists.setConfigMd5(cfgMD5);
			exists.setNote(note);
			crudClientConfigDao.updateByPrimaryKeySelective(exists);
		} else {
			ClientConfig cc = new ClientConfig();
			if (clientType == ClientConfig.CONFIG_TYPE_CLIENT) {
				cc.setClientId(clientId);
			} else {
				cc.setClientId(ClientConfig.GROUP_DEFAULT_CLIENT_ID);
			}
			cc.setClientConfig(cfgs);
			cc.setConfigName(configName);
			cc.setConfigType(configType);
			cc.setConfigMd5(cfgMD5);
			cc.setType(clientType);
			cc.setNote(note);
			try {
				crudClientConfigDao.insertSelective(cc);
			} catch (DuplicateKeyException e) {
				ClientConfig cfg = getConfigByClientId(clientId, configName,
						clientType);
				saveClientConfigs(cfg.getId(), clientId, configName,
						configType, cfgs, cfgMD5, clientType, note);
			}
		}
	}

	@Override
	public ClientConfig getConfigByClientId(Long clientId, String configName,
			int clientType) {
		ClientConfigExample exa = new ClientConfigExample();
		ClientConfigExample.Criteria c = exa.createCriteria();
		c.andClientIdEqualTo(clientId);
		c.andConfigNameEqualTo(configName);
		c.andTypeEqualTo(clientType);
		List<ClientConfig> list = crudClientConfigDao
				.selectByExampleWithBLOBs(exa);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public ClientConfig getConfigById(Long id) {
		return crudClientConfigDao.selectByPrimaryKey(id);
	}

	private boolean groupConfigNameIsValid(Long id, String configName) {
		if (StringUtils.isBlank(configName)) {
			return false;
		}
		ClientConfig cc = getConfigByClientId(
				ClientConfig.GROUP_DEFAULT_CLIENT_ID, configName,
				ClientConfig.CONFIG_TYPE_GROUP);
		if (cc != null) {
			Long existsId = cc.getId();
			if (existsId.equals(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private boolean clientConfigNameIsValid(Long id, String configName,
			Long clientId) {
		if (StringUtils.isBlank(configName)) {
			return false;
		}
		if (clientId == null) {
			return false;
		}
		ClientConfig cc = getConfigByClientId(clientId, configName,
				ClientConfig.CONFIG_TYPE_CLIENT);
		if (cc != null) {
			Long existsId = cc.getId();
			if (existsId.equals(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public void deleteConfig(Long id) {
		Assert.notNull(id, " configId is null");
		crudClientConfigDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean configNameIsValid(Long id, String configName,
			int configType, Long clientId) {
		if (configType == ClientConfig.CONFIG_TYPE_CLIENT) {
			return clientConfigNameIsValid(id, configName, clientId);
		}
		if (configType == ClientConfig.CONFIG_TYPE_GROUP) {
			return groupConfigNameIsValid(id, configName);
		}
		return false;
	}
}
