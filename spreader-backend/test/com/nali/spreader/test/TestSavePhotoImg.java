package com.nali.spreader.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.service.IAvatarFileManageService;
import com.nali.spreader.utils.TimeHelper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestSavePhotoImg {
	@Autowired
	private IAvatarFileManageService afService;

	@Test
	public void test() {
		String webDav = "http://192.168.3.61:8080/slide/files/";
		afService.synAvatarFileDataBase(webDav, TimeHelper.string2Date("20111124"), new Date());
	}
}
