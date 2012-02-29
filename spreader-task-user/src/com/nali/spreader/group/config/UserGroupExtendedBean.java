package com.nali.spreader.group.config;

import com.nali.spreader.factory.config.extend.ExtendedBean;

public interface UserGroupExtendedBean extends ExtendedBean {

	void setFromUserGroup(Long fromUserGroup);

	void setToUserGroup(Long toUserGroup);

}
