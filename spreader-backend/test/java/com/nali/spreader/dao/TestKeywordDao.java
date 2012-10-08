package com.nali.spreader.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dto.ContentKeywordInfoDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-dao.xml")
public class TestKeywordDao {
	@Autowired
	private IKeywordDao keywordDao;

	@Test
	public void testSelectContentKeywordByKids() {
		Long[] k = { 2L, 3L, 9L, 15L };
		List<Long> list = new ArrayList<Long>();
		list.add(2L);
		list.add(3L);
		list.add(9L);
		list.add(15L);
		List<ContentKeywordInfoDto> l = this.keywordDao.selectContentKeywordByKids(list);
		for (ContentKeywordInfoDto dto : l) {
			String kname = dto.getKeywordName();
			String cname = dto.getCategoryName();
			System.out.println(">>>>>>>>>>>>> keywrod:" + kname + ",category:" + cname);
		}
	}
}
