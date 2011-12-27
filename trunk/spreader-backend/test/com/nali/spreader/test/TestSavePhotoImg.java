package com.nali.spreader.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dto.UploadAvatarDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestSavePhotoImg {
	@AutowireProductLine
	private TaskProduceLine<UploadAvatarDto> uploadUserAvatar;
	
	@Test
	public void test() {
		UploadAvatarDto dto = new UploadAvatarDto();
		dto.setRobotId(12L);
		dto.setGender(2);
		dto.setUid(12L);
		uploadUserAvatar.send(dto);
		
	}
}
