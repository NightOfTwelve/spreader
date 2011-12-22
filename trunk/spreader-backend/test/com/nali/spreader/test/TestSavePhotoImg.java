package com.nali.spreader.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.IPhotoDao;
import com.nali.spreader.data.Photo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestSavePhotoImg {
	@Autowired
	private IPhotoDao dao;

	@Test
	public void test() {
		Photo p = new Photo();
		p.setGender(2);
		List l = dao.getPicTypeByGender(p);
		for(Object o:l) {
			System.out.println(o.toString());
		}
		
	}
}
