package com.nali.spreader.service;

import java.util.List;
import java.util.Map;

import com.nali.spreader.data.Photo;

/**
 * 头像上传相关服务
 * 
 * @author xiefei
 * 
 */
public interface IUploadAvatarService {
	/**
	 * 根据权重筛选头像库 Map key:头像库，value:权重
	 * 
	 * @param list
	 * @return
	 */
	List<Photo> findPhotoListByWeight(List<Map<List<Photo>, Integer>> list);

	/**
	 * 在筛选后的Photo集合中随机取出一个URL
	 * 
	 * @param list
	 * @return
	 */
	String randomAvatarUrl(List<Photo> list, String http);

	/**
	 * 根据性别和类型获取头像的集合
	 * @param gender
	 * @param type
	 * @return
	 */
	List<Photo> findPhotoListByGenderType(Integer gender,String type);

	/**
	 * 根据性别获取所有头像的类别，包括通用类别
	 * 
	 * @param gender
	 * @return
	 */
	List<String> findAllPhotoTypeByGender(Integer gender);

}
