package com.nali.spreader.content;

import java.util.List;

public interface IContentAssembler {
	String assembleContent(int taskType, int webSiteId, long fromId, long toId);
	
	String assembleContent(int taskType, int webSiteId, List<String> slices);
}
