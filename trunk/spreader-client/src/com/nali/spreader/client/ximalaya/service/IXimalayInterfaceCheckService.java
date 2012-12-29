package com.nali.spreader.client.ximalaya.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.nali.spreader.data.User;

/**
 * 喜马拉雅接口检验工具
 * 
 * @author xiefei
 * 
 */
public interface IXimalayInterfaceCheckService {

	byte[] getParamsMD5(Object[] params) throws IOException, NoSuchAlgorithmException;

	List<User> getUsers(List<Map<String, Object>> maps);

}
