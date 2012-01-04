package com.nali.spreader.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.IPhotoDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.PhotoExample;
import com.nali.spreader.data.PhotoExample.Criteria;
import com.nali.spreader.data.User;
import com.nali.spreader.service.IUploadAvatarService;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.utils.PhotoHelper;

/**
 * 上传头像服务类
 * 
 * @author xiefei
 * 
 */
@Service
public class UploadAvatarServiceImpl implements IUploadAvatarService {
	private static final Logger logger = Logger
			.getLogger(UploadAvatarServiceImpl.class);
	@Autowired
	private ICrudPhotoDao crudPhotoDao;
	@Autowired
	private IPhotoDao photoDao;
	@Autowired
	private ICrudUserDao crudUserDao;

	@Override
	public List<Photo> findPhotoListByWeight(Map<List<Photo>, Integer> m) {
		WeightRandomer<List<Photo>> weightList = new WeightRandomer<List<Photo>>();
		Set<Entry<List<Photo>, Integer>> set = m.entrySet();
		if (set != null) {
			Iterator<Entry<List<Photo>, Integer>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<List<Photo>, Integer> entry = it.next();
				List<Photo> wList = entry.getKey();
				Integer weight = entry.getValue();
				weightList.add(wList, weight);
			}
		}
		List<Photo> result = weightList.get();
		return result;
	}

	@Override
	public Photo randomAvatarUrl(List<Photo> list) {
		String uri = null;
		int size = list.size();
		Photo p = null;
		if (size > 0) {
			int index = PhotoHelper.getRandomNum(size);
			p = list.get(index);
			String purl = p.getPicUrl();
			uri = PhotoHelper.formatPhotoUrl(purl);
			p.setPicUrl(uri.toString());
		}
		return p;
	}

	@Override
	public List<String> findAllPhotoTypeByGender(Integer gender) {
		Photo p = new Photo();
		p.setGender(2);
		List<String> list = photoDao.getPicTypeByGender(p);
		return list;
	}

	@Override
	public List<Photo> findPhotoListByGenderType(Integer gender, String type) {
		PhotoExample pe = new PhotoExample();
		Criteria cr = pe.createCriteria();
		if (gender != null) {
			cr.andGenderEqualTo(gender);
		}
		if (StringUtils.isNotEmpty(type)) {
			cr.andPicTypeEqualTo(type);
		}
		cr.andAvatarflgEqualTo(true);
		List<Photo> list = crudPhotoDao.selectByExampleWithoutBLOBs(pe);
		return list;
	}

	@Override
	public Map<String, Integer> findTypeWeightByProperties(String url) {
		Map<String, Integer> map = null;
		if (StringUtils.isNotEmpty(url)) {
			Map<Object, Object> propMap = PhotoHelper.getPropertiesMap(url);
			if (propMap != null) {
				Integer gender = Integer.parseInt(propMap.get("gender")
						.toString());
				Integer general = Integer.parseInt(propMap.get("general")
						.toString());
				map = CollectionUtils.newHashMap(5);
				map.put("gender", gender);
				map.put("general", general);
			}
		}
		return map;
	}

	@SuppressWarnings("unused")
	public static void main(String argep[]) {
		@SuppressWarnings("rawtypes")
		Map m = PhotoHelper
				.getPropertiesMap("/avatarconfig/webDavService.properties");
		String s = m.get("url").toString();
		System.out.println(m);
	}

	@Override
	public List<Photo> findPhotoListByGender(Integer gender) {
		PhotoExample pe = new PhotoExample();
		Criteria cr = pe.createCriteria();
		if (gender != null) {
			cr.andGenderEqualTo(gender);
		}
		cr.andAvatarflgEqualTo(true);
		List<Photo> list = crudPhotoDao.selectByExampleWithoutBLOBs(pe);
		return list;
	}

	public Map<List<Photo>, Integer> createWeightMap(Integer gender) {
		List<Photo> genderList = findPhotoListByGender(gender);
		List<Photo> generalList = findPhotoListByGender(3);
		// 默认权重配置
		Integer genderWeight = 60;
		Integer generalWeight = 40;
		// 获取权重配置
		Map<String, Integer> weightMap = findTypeWeightByProperties(PhotoHelper.TYPEWEIGHT_FILE);
		if (weightMap != null) {
			genderWeight = weightMap.get("gender");
			generalWeight = weightMap.get("general");
		}
		Map<List<Photo>, Integer> m = CollectionUtils.newHashMap(5);
		// 这里做判断SIZE=0的List不进入随机筛选，否则会有异常
		if (genderList.size() > 0) {
			m.put(genderList, genderWeight);
			logger.info("genderList不为空进入筛选");
		}
		if (generalList.size() > 0) {
			m.put(generalList, generalWeight);
			logger.info("generalList不为空进入筛选");
		}
		return m;
	}

	@Override
	public int updateUserAvatarUrl(Long uid, Long pid) {
		int cnt = 0;
		User u = crudUserDao.selectByPrimaryKey(uid);
		if (u != null) {
			u.setPid(pid);
			cnt = crudUserDao.updateByPrimaryKey(u);
		} else {
			logger.warn("找不到用户,uid:" + uid);
		}
		return cnt;
	}
}
