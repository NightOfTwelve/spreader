package com.nali.spreader.content;

public interface IContentAssembler {
	String assembleContent(int taskType, int webSiteId, long...ids);
	
	boolean hasSubTask(int taskType, int webSiteId, long...ids);
}
