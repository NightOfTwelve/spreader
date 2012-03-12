package com.nali.spreader.score;

import java.math.BigDecimal;
import java.util.Map;

import com.nali.spreader.data.User;

public class UserScoreEvaluater implements IUserScoreEvaluater {
	private Map<String, BigDecimal> fatorGenes;
	
	public Map<String, BigDecimal> getFatorGenes() {
		return fatorGenes;
	}

	public void setFatorGenes(Map<String, BigDecimal> fatorGenes) {
		this.fatorGenes = fatorGenes;
	}

	@Override
	public BigDecimal evalueate(User user) {
		return null;
	}
}