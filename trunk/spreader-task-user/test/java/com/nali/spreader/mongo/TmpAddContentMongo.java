package com.nali.spreader.mongo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.IContentMassDataDao;
import com.nali.spreader.dao.ICrudContentDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test-dal.xml")
public class TmpAddContentMongo {
	@Autowired
	private ICrudContentDao crudContentDao;
	@Autowired
	private IContentMassDataDao contentMassDataDao;
	@Autowired
	private IKeywordDao keywordDao;

	@Test
	public void test() {
		ContentExample example = new ContentExample();
		int start = 0;
		int limit = 100;
		List<Content> list = null;
		int rows = 0;
		int i = 1;
		do {
			Limit lit = Limit.newInstanceForLimit(start, limit);
			example.setLimit(lit);
			list = this.crudContentDao.selectByExampleWithoutBLOBs(example);
			for (Content c : list) {
				Long cid = c.getId();
				List<Long> keywords = this.keywordDao.selectKeywordByContent(cid);
				if(!CollectionUtils.isEmpty(keywords)) {
					c.setKeywords((Long[]) keywords.toArray());
				}
				this.contentMassDataDao.insertContent(c);
			}
			rows = list.size();
			start = start + limit + 1;
			System.out.println("次数:" + i);
			i++;
		} while (rows > 0);
	}
}