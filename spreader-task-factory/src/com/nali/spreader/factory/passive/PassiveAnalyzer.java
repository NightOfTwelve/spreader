package com.nali.spreader.factory.passive;

public interface PassiveAnalyzer<D> extends PassiveObject {
	void work(D data);
}
