package com.nali.spreader.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.model.GrouppedUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-service.xml")
public class TestContentService {
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	
	public void test() {

	}

}
