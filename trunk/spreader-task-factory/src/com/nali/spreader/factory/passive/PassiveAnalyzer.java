package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.base.Analyzer;

public interface PassiveAnalyzer<D> extends PassiveObject, Analyzer {
	void work(D data);
}
