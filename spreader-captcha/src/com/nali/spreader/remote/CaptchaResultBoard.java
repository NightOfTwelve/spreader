package com.nali.spreader.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaType;
import com.nali.spreader.service.ICaptchaService;

@Service
public class CaptchaResultBoard implements ICaptchaResultBoard {
	private static Logger logger = Logger.getLogger(CaptchaResultBoard.class);
	private static final long EXPIRED_TIME = 1000L*60*5 - 30*1000;
	@Autowired
	private ICaptchaService captchaService;

	@Override
	public String getResult(Long id) {
		Captcha captcha = captchaService.get(id);
		if(captcha == null) {
			logger.warn("captcha's id not exists:" + id);
			return null;
		}
		if(!Captcha.TYPE_FINISH.equals(captcha.getType())) {
			return null;
		}
		return captcha.getResult();
	}

	@Override
	public Long postCaptcha(byte[] data, String typeStr, Long taskId, Integer seq) {
		CaptchaType type = CaptchaType.valueOf(typeStr);
		//TODO type是自动处理类型的处理
		Captcha captcha = new Captcha();
		captcha.setData(data);
		captcha.setType(type.type);
		captcha.setExpireTime(new Date(System.currentTimeMillis() + EXPIRED_TIME));
		captcha.setPostClient(ClientContext.getCurrentContext().getClientId());
		captcha.setTaskId(taskId);
		captcha.setSeq(seq);
		return captchaService.save(captcha);
	}

	@Override
	public List<Map<String, Object>> queryInputStat(Date from, Date to) {
		ClientContext context = ClientContext.getCurrentContext();
		return captchaService.queryInputStat(context.getClientId(), from, to);
	}

}
