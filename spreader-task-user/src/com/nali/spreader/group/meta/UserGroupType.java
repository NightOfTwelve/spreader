package com.nali.spreader.group.meta;

import java.util.Map;

import com.nali.common.util.CollectionUtils;

public enum UserGroupType {
	fixed(0), dynamic(1), manual(2);
	private int typeVal;
	private static Map<Integer, UserGroupType> types;
	
	static{
		UserGroupType[] values = UserGroupType.values();
		types = CollectionUtils.newHashMap(values.length);
		
		for(UserGroupType type : values) {
			types.put(type.getTypeVal(), type);
		}
	}

	public static UserGroupType valueOf(int typeVal) {
		return types.get(typeVal);
	}
	
	private UserGroupType(int typeVal) {
		this.typeVal = typeVal;
	}
	
	public int getTypeVal() {
		return typeVal;
	}
}