package com.nali.spreader.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.PhotoExample;
import com.nali.spreader.data.PhotoExample.Criteria;
import com.nali.spreader.service.IUploadAvatarService;
import com.nali.spreader.util.random.WeightRandomer;

@Service
public class UploadAvatarServiceImpl implements IUploadAvatarService {
	private static final Logger logger = Logger
			.getLogger(UploadAvatarServiceImpl.class);
	@Autowired
	private ICrudPhotoDao crudPhotoDao;

	@Override
	public List<Photo> findPhotoListByWeight(
			List<Map<List<Photo>, Integer>> list) {
		WeightRandomer<List<Photo>> weightList = new WeightRandomer<List<Photo>>();
		if (list.size() > 0) {
			for (Map<List<Photo>, Integer> weightMap : list) {
				Set<Entry<List<Photo>, Integer>> set = weightMap.entrySet();
				if (set != null) {
					Iterator<Entry<List<Photo>, Integer>> it = set.iterator();
					while (it.hasNext()) {
						Map.Entry<List<Photo>, Integer> entry = it.next();
						List<Photo> wList = entry.getKey();
						Integer weight = entry.getValue();
						weightList.add(wList, weight);
					}
				}
			}
		}
		List<Photo> result = weightList.get();
		return result;
	}

	@Override
	public String randomAvatarUrl(List<Photo> list, String http) {
		String uri = null;
		int size = list.size();
		StringBuffer buff = new StringBuffer(http);
		if (size > 0) {
			int index = getRandomNum(size);
			Photo p = list.get(index);
			String purl = p.getPicUrl();
			if (StringUtils.isNotEmpty(purl)) {
				buff.append(purl);
			}
			uri = buff.toString();
		}
		return uri;
	}

	/**
	 * 获取随机整数
	 * 
	 * @param size
	 * @return
	 */
	private int getRandomNum(int size) {
		Random rd = new Random();
		int t = rd.nextInt(size);
		return t;
	}

	@Override
	public List<String> findAllPhotoTypeByGender(Integer gender) {
		// TODO Auto-generated method stub
		return null;
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

}
