package com.nali.spreader.client.ximalaya.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;

@Service
public class XimalayInterfaceCheckService implements IXimalayInterfaceCheckService {

	@Value("${ximalaya.md5Key}")
	private String md5Key;

	@Override
	public byte[] getParamsMD5(Object[] params) throws IOException, NoSuchAlgorithmException {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bao);
		oo.writeObject(params);
		oo.writeObject(md5Key);
		oo.close();
		byte[] bs = bao.toByteArray();
		byte[] md5 = MessageDigest.getInstance("MD5").digest(bs);
		return md5;
	}

	@Override
	public List<User> getUsers(List<Map<String, Object>> maps) {
		List<User> users = new ArrayList<User>();
		for (Map<String, Object> map : maps) {
			if (!maps.isEmpty()) {
				Long websiteUid = (Long) map.get("websiteUid");
				String nickName = (String) map.get("nickName");
				Integer gender = (Integer) map.get("gender");
				String realName = (String) map.get("realName");
				String email = (String) map.get("email");
				String nationality = (String) map.get("nationality");
				String province = (String) map.get("province");
				String city = (String) map.get("city");
				String introduction = (String) map.get("introduction");
				Integer vType = (Integer) map.get("vType");
				Long attentions = (Long) map.get("attentions");
				Long fans = (Long) map.get("fans");
				User user = new User();
				user.setWebsiteId(Website.ximalaya.getId());
				user.setIsRobot(false);
				user.setWebsiteUid(websiteUid);
				user.setNickName(nickName);
				user.setGender(gender);
				user.setRealName(realName);
				user.setEmail(email);
				user.setNationality(nationality);
				user.setProvince(province);
				user.setCity(city);
				user.setIntroduction(introduction);
				user.setAttentions(attentions);
				user.setvType(vType);
				user.setFans(fans);
				user.setCreateTime(new Date());
				users.add(user);
			}
		}
		return users;
	}
}
