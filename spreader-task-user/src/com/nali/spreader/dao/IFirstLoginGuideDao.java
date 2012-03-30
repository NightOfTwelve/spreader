package com.nali.spreader.dao;

public interface IFirstLoginGuideDao {

	void init(Long uid);

	void guide(Long uid);

	boolean isUserGuide(Long uid);

	void setInitFlagOn();

	boolean isInitFlagOn();

}
