package com.nali.spreader.dao.mongo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.IContentMassDataDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("161-application-context-test.xml")
public class TestContentMassDataDao {
	@Autowired
	private IContentMassDataDao contentMassDataDao;
	
	@Test
	public void testSelectContentKeywords() {
		List<Long> list = this.contentMassDataDao.selectContentKeywords(13593L);
		for(Long key:list) {
			System.out.println(key);
		}
	}
}
