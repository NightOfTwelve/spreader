package com.nali.spreader.errorprocess;

import org.springframework.stereotype.Component;

import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;

@Component
public class WeiboAppealErrorProcessor extends TargetUserAccountErrorProcessor {
	@AutowireProductLine
	private TaskProduceLine<Long> doWeiboAppeal;
	
	@Override
	public String getErrorCode() {
		return TaskErrorCode.needAppeal.getCode();
	}

	@Override
	protected void afterRemove(User user) {
		super.afterRemove(user);
		doWeiboAppeal.send(user.getId());
	}

}
