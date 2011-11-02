package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.spreader.config.UserDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;

public interface IUserDao {

	Long assignUser(User user);

	/**
	 * key是用户id
	 * value是网站用户id
	 */
	List<KeyValue<Long, Long>> findUidToWebsiteUidMapByDto(UserDto dto);

	Date getAndTouchLastFetchTime(Long uid);

	Long insertContent(Content content);
}
