package com.nali.spreader.client.android.tencent.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.client.android.tencent.service.ITencentStatisticsService;
import com.nali.spreader.dao.ICrudYybPacketDao;
import com.nali.spreader.dao.IYybPacketDao;
import com.nali.spreader.dto.YybPacketAdvCount;
import com.nali.spreader.dto.YybPacketClientIpCount;
import com.nali.spreader.dto.YybPacketPhoneCount;
import com.nali.spreader.model.YybPacket;
import com.nali.spreader.model.YybPacketExample;

@Service
public class TencentStatisticsService implements ITencentStatisticsService {
	@Autowired
	private ICrudYybPacketDao crudYybPacketDao;
	@Autowired
	private IYybPacketDao yybPacketDao;

	@Override
	public PageResult<YybPacket> getYybPostRecord(Integer productId,
			String postDate, Long clientId, Limit limit) {
		YybPacketExample exa = new YybPacketExample();
		YybPacketExample.Criteria cri = exa.createCriteria();
		if (productId != null) {
			cri.andProductIdEqualTo(productId);
		}
		if (StringUtils.isNotBlank(postDate)) {
			cri.andPostDateEqualTo(postDate);
		}
		if (clientId != null) {
			cri.andClientIdEqualTo(clientId);
		}
		int count = crudYybPacketDao.countByExample(exa);
		exa.setLimit(limit);
		List<YybPacket> list = crudYybPacketDao.selectByExample(exa);
		return new PageResult<YybPacket>(list, limit, count);
	}

	@Override
	public PageResult<YybPacketAdvCount> getAdvCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		List<YybPacketAdvCount> list = yybPacketDao.getAdvCount(productId,
				postDate, clientId, limit);
		int count = yybPacketDao.countAdv(productId, postDate, clientId);
		return new PageResult<YybPacketAdvCount>(list, limit, count);
	}

	@Override
	public PageResult<YybPacketClientIpCount> getIpCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		List<YybPacketClientIpCount> list = yybPacketDao.getClientIpCount(
				productId, postDate, clientId, limit);
		int count = yybPacketDao.countAdv(productId, postDate, clientId);
		return new PageResult<YybPacketClientIpCount>(list, limit, count);
	}

	@Override
	public PageResult<YybPacketPhoneCount> getPhoneCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		List<YybPacketPhoneCount> list = yybPacketDao.getPhoneCount(productId,
				postDate, clientId, limit);
		int count = yybPacketDao.countPhone(productId, postDate, clientId);
		return new PageResult<YybPacketPhoneCount>(list, limit, count);
	}
}
