package com.nali.spreader.score;

import java.math.BigDecimal;

import com.nali.spreader.data.User;

public interface IUserFactorScoreEvaluater {
	BigDecimal evaluate(BigDecimal value, User user);
	
	BigDecimal getMaxValue();
}
