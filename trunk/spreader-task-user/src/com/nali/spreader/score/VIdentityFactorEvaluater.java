package com.nali.spreader.score;

import java.math.BigDecimal;

import com.nali.spreader.data.User;

public class VIdentityFactorEvaluater implements IUserFactorScoreEvaluater {

	@Override
	public BigDecimal evaluate(BigDecimal value, User user) {
		Integer vType = user.getVType();
		if(vType != null) {
			
		}
		return null;
	}

	@Override
	public BigDecimal getMaxValue() {
		return null;
	}
}
