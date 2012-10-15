package com.nali.spreader.front.test.captcha;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.ICaptchaDao;
import com.nali.spreader.dao.ICrudCaptchaDao;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaExample;
import com.nali.spreader.util.SpecialDateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test_captcha.xml")
public class TestPic {
	@Autowired
	private ICrudCaptchaDao captchaDao;
	@Autowired
	private ICaptchaDao captchaDao2;
	
	@Test
	public void testSave() throws Exception {
		Captcha captcha = new Captcha();
		captcha.setData("null".getBytes());
		captcha.setPostClient(1L);
		captcha.setExpireTime(new Date());
		captcha.setSeq(1);
		captcha.setTaskId(1L);
		Long id = captchaDao2.insertCaptcha(captcha);
		System.out.println(id);
	}
	
	@Test
	public void testClientIdOut() throws Exception {
		Long clientId = 20L;
		CaptchaExample example = new CaptchaExample();
		example.createCriteria().andHandleClientEqualTo(clientId).andTypeEqualTo(0)
			.andExpireTimeGreaterThan(SpecialDateUtil.afterToday(0)).andExpireTimeLessThan(SpecialDateUtil.afterToday(1));
		out("C:\\Documents and Settings\\sam\\桌面\\captcha-out\\", example);
	}
	
	@Test
	public void testSeqOut() throws Exception {
		FKCaptchaExample example = new FKCaptchaExample();
		example.createCriteria().and("char_length(result)<4").andSeqEqualTo(1)
			.andExpireTimeGreaterThan(SpecialDateUtil.afterToday(-1)).andExpireTimeLessThan(SpecialDateUtil.afterToday(0));
		out("C:\\Documents and Settings\\sam\\桌面\\captcha-out3\\", example);
	}
	
	@Test
	public void testTaskIdOut() throws Exception {
		CaptchaExample example = new CaptchaExample();
		example.createCriteria().andTaskIdEqualTo(23714896L);
		out("C:\\Documents and Settings\\sam\\桌面\\captcha-out2\\", example);
	}
	
	private void out(String dir, CaptchaExample example) throws IOException {
		List<Captcha> list = captchaDao.selectByExampleWithBLOBs(example);
		for (Captcha captcha : list) {
			byte[] data = captcha.getData();
			Long id = captcha.getId();
			String result = captcha.getResult();
			result=result==null?"":result;
			FileOutputStream out = new FileOutputStream(dir + id + "-" + result.replace('\\', '-') + ".jpg");
			out.write(data);
			out.close();
		}
		System.out.println("############" + list.size());
	}
	
	static class CaptchaCriteria extends CaptchaExample.Criteria {
		public CaptchaCriteria and(String condition) {
			super.addCriterion(condition);
			return this;
		}
	}
	
	static class FKCaptchaExample extends CaptchaExample {
		@Override
		protected CaptchaCriteria createCriteriaInternal() {
			return new CaptchaCriteria();
		}

		@Override
		public CaptchaCriteria createCriteria() {
			return (CaptchaCriteria) super.createCriteria();
		}
	}
	
}
