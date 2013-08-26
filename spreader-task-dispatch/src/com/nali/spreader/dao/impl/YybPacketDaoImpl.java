package com.nali.spreader.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.IYybPacketDao;
import com.nali.spreader.dto.YybPacketAdvCount;
import com.nali.spreader.dto.YybPacketClientIpCount;
import com.nali.spreader.dto.YybPacketPhoneCount;
import com.nali.spreader.util.collection.CollectionUtils;

@Repository
public class YybPacketDaoImpl implements IYybPacketDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	private Map<String, Object> getCondition(Integer productId,
			String postDate, Long clientId, Limit limit) {
		Map<String, Object> p = CollectionUtils.newHashMap(4);
		p.put("productId", productId);
		p.put("postDate", postDate);
		p.put("clientId", clientId);
		p.put("limit", limit);
		return p;
	}

	@Override
	public List<YybPacketAdvCount> getAdvCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		Map<String, Object> p = getCondition(productId, postDate, clientId,
				limit);
		return sqlMap.queryForList("spreader_yyb_packet.queryAdvCount", p);
	}

	@Override
	public List<YybPacketClientIpCount> getClientIpCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		Map<String, Object> p = getCondition(productId, postDate, clientId,
				limit);
		return sqlMap.queryForList("spreader_yyb_packet.queryIpCount", p);
	}

	@Override
	public List<YybPacketPhoneCount> getPhoneCount(Integer productId,
			String postDate, Long clientId, Limit limit) {
		Map<String, Object> p = getCondition(productId, postDate, clientId,
				limit);
		return sqlMap.queryForList("spreader_yyb_packet.queryPhoneCount", p);
	}

	@Override
	public int countAdv(Integer productId, String postDate, Long clientId) {
		List<YybPacketAdvCount> list = getAdvCount(productId, postDate,
				clientId, null);
		return list.size();
	}

	@Override
	public int countIp(Integer productId, String postDate, Long clientId) {
		List<YybPacketClientIpCount> list = getClientIpCount(productId,
				postDate, clientId, null);
		return list.size();
	}

	@Override
	public int countPhone(Integer productId, String postDate, Long clientId) {
		List<YybPacketPhoneCount> list = getPhoneCount(productId, postDate,
				clientId, null);
		return list.size();
	}
}
