package com.nali.spreader.dao;

import java.util.List;

import com.nali.common.model.Limit;
import com.nali.spreader.dto.YybPacketAdvCount;
import com.nali.spreader.dto.YybPacketClientIpCount;
import com.nali.spreader.dto.YybPacketPhoneCount;

/**
 * 自定义dao
 * 
 * @author xiefei
 * 
 */
public interface IYybPacketDao {

	List<YybPacketAdvCount> getAdvCount(Integer productId, String postDate,
			Long clientId, Limit limit);

	List<YybPacketClientIpCount> getClientIpCount(Integer productId,
			String postDate, Long clientId, Limit limit);

	List<YybPacketPhoneCount> getPhoneCount(Integer productId, String postDate,
			Long clientId, Limit limit);

	int countAdv(Integer productId, String postDate, Long clientId);

	int countIp(Integer productId, String postDate, Long clientId);

	int countPhone(Integer productId, String postDate, Long clientId);
}
