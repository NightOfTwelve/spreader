package com.nali.spreader.service;

public interface IFirstLoginGuideService {

	void guide(Long uid);

	boolean isUserGuide(Long uid);
}
