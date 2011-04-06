package com.nali.spreader.content;

import java.util.List;

public class ContentAsembler implements IContentAssembler{
	
	@Override
	public String assembleContent(int taskType, int webSiteId, long fromId, long toId) {
		if(toId > fromId) {
			return new StringBuilder().append(fromId).append("-").append(toId).toString();
		}else {
			return String.valueOf(fromId);
		}
	}

	@Override
	public String assembleContent(int taskType, int webSiteId,
			List<String> slices) {
		int size = slices.size();
		StringBuilder sb = new StringBuilder();
		if(size > 1) {
			int i = 0;
			while(i++ < size -1) {
				sb.append(slices.get(i)).append(",");
			}
			sb.append(slices.get(size - 1));
		}
		return sb.toString();
	}
}
