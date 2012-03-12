package com.nali.spreader.score;

import java.math.BigDecimal;

import com.nali.spreader.data.User;

public interface IUserScoreEvaluater {
	BigDecimal evalueate(User user);
}
