package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.User;

@Repository
public class UserDao implements IUserDao {
    @Autowired
    private SqlMapClientTemplate sqlMap;
    
	@Override
	public Long assignUser(User user) {
		return (Long) sqlMap.insert("spreader_user.assignUser", user);
	}

}
