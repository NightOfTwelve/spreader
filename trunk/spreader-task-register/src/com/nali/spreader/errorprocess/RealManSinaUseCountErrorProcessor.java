package com.nali.spreader.errorprocess;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.result.DefaultErrorProcessor;
import com.nali.spreader.service.IRealManService;

/**
 * KeyValue<String, String> key 表示身份证号码 value 表示姓名
 * 
 * @author xiefei
 * 
 */
@Component
public class RealManSinaUseCountErrorProcessor extends
		DefaultErrorProcessor<KeyValue<String, String>> {
	private static final Logger LOGGER = Logger.getLogger(RealManSinaUseCountErrorProcessor.class);
	@Autowired
	private IRealManService realManService;

	@Override
	public String getErrorCode() {
		return TaskErrorCode.personIdAbused.getCode();
	}

	@Override
	public void handleError(KeyValue<String, String> errorObject,
			Map<String, Object> contextContents, Long uid, Date errorTime) {
		String realId = errorObject.getKey();
		String realName = errorObject.getValue();
		Long realManId = this.realManService.getRealManIdByUK(realId, realName);
		if (realManId == null) {
			LOGGER.error("realMan is not find, realId=" + realId + ",realName=" + realName);
			return;
		}
		this.realManService.updateIsForbidBySina(realManId, true);
	}
}