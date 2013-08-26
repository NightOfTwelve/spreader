package com.nali.spreader.client.android.tencent.service;

import java.util.Date;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dto.YybPacketAdvCount;
import com.nali.spreader.dto.YybPacketClientIpCount;
import com.nali.spreader.dto.YybPacketPhoneCount;
import com.nali.spreader.model.YybPacket;

/**
 * 腾讯应用宝发送数据统计
 * 
 * @author xiefei
 * 
 */
public interface ITencentStatisticsService {
	/**
	 * 查询应用宝发送的所有数据
	 * 
	 * @param productId
	 * @param postTime
	 * @param clientId
	 * @return
	 */
	PageResult<YybPacket> getYybPostRecord(Integer productId, String postDate,
			Long clientId, Limit limit);

	PageResult<YybPacketAdvCount> getAdvCount(Integer productId,
			String postDate, Long clientId, Limit limit);

	PageResult<YybPacketClientIpCount> getIpCount(Integer productId,
			String postDate, Long clientId, Limit limit);

	PageResult<YybPacketPhoneCount> getPhoneCount(Integer productId,
			String postDate, Long clientId, Limit limit);

}
