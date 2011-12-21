package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.spreader.data.Photo;

public interface IPhotoDao {
	/**
	 * 获取系统中最新时间
	 * 
	 * @return
	 */
	Date selectMaxCreateTime();

	/**
	 * 按性别获取所有图片类型
	 * 
	 * @param p
	 * @return
	 */
	List<String> getPicTypeByGender(Photo p);
}
