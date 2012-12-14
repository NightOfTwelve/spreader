package com.nali.spreader.client.ximalaya.config;

import java.util.Map;

import com.nali.common.util.CollectionUtils;

/**
 * 注册返回状态
 * 
 * @author xiefei
 * 
 */
public enum RegisterStatus {
	uncheckError(0, "其他未检查异常"), success(1, "成功"), nickNameDuplication(2, "昵称重复"), nickNameTooLong(
			3, "昵称太长"), nickNameTooShort(4, "昵称太短"), nickNameInvalidFormat(5, "昵称中有非法字符"), emailTooLong(
			6, "邮箱太长"), emailInvalidFormat(7, "邮箱格式不正确"), emailBeUsed(8, "邮箱已使用"), passwordNotEnter(
			9, "密码为空"), passwordTooLong(10, "密码太长"), passwordTooShort(11, "密码太短");
	private int id;
	private String name;
	private static Map<Integer, RegisterStatus> enumsMap;

	private RegisterStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	static {
		RegisterStatus[] rs = RegisterStatus.values();
		enumsMap = CollectionUtils.newHashMap(rs.length);
		for (RegisterStatus r : rs) {
			enumsMap.put(r.getId(), r);
		}
	}

	public static Map<Integer, RegisterStatus> getEnumsMap() {
		return enumsMap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
