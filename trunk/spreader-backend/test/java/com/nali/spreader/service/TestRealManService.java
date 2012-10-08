package com.nali.spreader.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.data.RealMan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-service.xml")
public class TestRealManService {
	@Autowired
	private IRealManService realManService;
	private Logger LOGGER = Logger.getLogger(TestRealManService.class);

	@Test
	public void test() {
//		int size = realManService.getEffectiveRealMan(100).size();
//		LOGGER.debug("list size=" + size);
//		RealMan rm = this.realManService.getRealManById(3L);
//		LOGGER.debug("RealManName:" + rm.getRealName());
//		this.realManService.updateIsForbidBySina(3L, false);
//		this.realManService.updateIsReal(3L, true);
//		this.realManService.updateSinaUseCount(3L);
		Long id = this.realManService.getRealManIdByUK("220122198707182516", "朱雪松");
		LOGGER.debug("id:"+id);
	}
}