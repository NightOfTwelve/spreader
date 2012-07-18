package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.RegAddress;

public interface IAppRegisterService {
	List<Long> assignRegisterIds(int count);
	void saveAppUdid(AppUdid appUdid);
	void saveRegAddress(RegAddress address);
	RegAddress getRegAddress(Long registerId);
	AppUdid getAppUdid(Long registerId);
}
