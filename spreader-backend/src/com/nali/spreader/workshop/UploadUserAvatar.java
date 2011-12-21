package com.nali.spreader.workshop;

import java.util.Date;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;

public class UploadUserAvatar<D, R> extends SingleTaskMachineImpl implements
		PassiveWorkshop<D, R> {

	public UploadUserAvatar() {
		super(SimpleActionConfig.uploadAvatar, Website.weibo, Channel.normal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void work(D data, SingleTaskExporter exporter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleResult(Date updateTime, R resultObject) {
		// TODO Auto-generated method stub
		
	}

}
