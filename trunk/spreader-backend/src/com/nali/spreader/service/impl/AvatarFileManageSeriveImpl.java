package com.nali.spreader.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.sardine.DavResource;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.IPhotoDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.service.IAvatarFileManageService;
import com.nali.spreader.utils.PhotoHelper;
import com.nali.spreader.utils.TimeHelper;

@Service
public class AvatarFileManageSeriveImpl implements IAvatarFileManageService {
	private static final Logger LOGGER = Logger
			.getLogger(AvatarFileManageSeriveImpl.class);
	// 性别分类
	private final String GENDER_MALE = "男";
	private final String GENDER_FEMALE = "女";
	private final String GENDER_GENERAL = "通用";
	@Autowired
	private IPhotoDao photoDao;
	@Autowired
	private ICrudPhotoDao crudPhotoDao;

	/**
	 * 创建一套空目录结构
	 * 
	 * @param serviceUrl
	 */
	private void createTypeFileDir(String serviceUrl) {
		// 获取头像分类的配置文件信息
		Map<Object, Object> maleTypeMap = PhotoHelper
				.getPropertiesMap("/avatarconfig/malePhotoType.properties");
		Map<Object, Object> femaleTypeMap = PhotoHelper
				.getPropertiesMap("/avatarconfig/femalePhotoType.properties");
		Map<Object, Object> generalTypeMap = PhotoHelper
				.getPropertiesMap("/avatarconfig/generalPhotoType.properties");
		try {
			Sardine sardine = SardineFactory.begin();
			if (!sardine.exists(serviceUrl)) {
				sardine.createDirectory(serviceUrl);
				// 男性
				StringBuffer maleBuff = new StringBuffer(serviceUrl);
				// 女性
				StringBuffer femaleBuff = new StringBuffer(serviceUrl);
				// 通用
				StringBuffer generalBuff = new StringBuffer(serviceUrl);

				maleBuff.append(GENDER_MALE).append("/");
				if (!sardine.exists(maleBuff.toString())) {
					sardine.createDirectory(maleBuff.toString());
				}
				createPhotoTypeDir(sardine, maleTypeMap, maleBuff);

				femaleBuff.append(GENDER_FEMALE).append("/");
				if (!sardine.exists(femaleBuff.toString())) {
					sardine.createDirectory(femaleBuff.toString());
				}
				createPhotoTypeDir(sardine, femaleTypeMap, femaleBuff);

				generalBuff.append(GENDER_GENERAL).append("/");
				if (!sardine.exists(generalBuff.toString())) {
					sardine.createDirectory(generalBuff.toString());
				}
				createPhotoTypeDir(sardine, generalTypeMap, generalBuff);
			} else {
				LOGGER.info(serviceUrl + "已经创建,继续检查下一级目录");
				// 男性
				StringBuffer maleBuff = new StringBuffer(serviceUrl);
				// 女性
				StringBuffer femaleBuff = new StringBuffer(serviceUrl);
				// 通用
				StringBuffer generalBuff = new StringBuffer(serviceUrl);

				maleBuff.append(GENDER_MALE).append("/");
				if (!sardine.exists(maleBuff.toString())) {
					sardine.createDirectory(maleBuff.toString());
				}
				createPhotoTypeDir(sardine, maleTypeMap, maleBuff);

				femaleBuff.append(GENDER_FEMALE).append("/");
				if (!sardine.exists(femaleBuff.toString())) {
					sardine.createDirectory(femaleBuff.toString());
				}
				createPhotoTypeDir(sardine, femaleTypeMap, femaleBuff);

				generalBuff.append(GENDER_GENERAL).append("/");
				if (!sardine.exists(generalBuff.toString())) {
					sardine.createDirectory(generalBuff.toString());
				}
				createPhotoTypeDir(sardine, generalTypeMap, generalBuff);
			}
		} catch (Exception e) {
			LOGGER.info("创建失败", e);
		}
	}

	/**
	 * 创建类型子路径
	 * 
	 * @param sardine
	 * @param tmap
	 * @param dir
	 */
	private void createPhotoTypeDir(Sardine sardine, Map<Object, Object> tmap,
			StringBuffer dir) {
		Set<Object> set = tmap.keySet();
		String tmpdir = dir.toString();
		try {
			for (Object o : set) {
				String type = tmap.get(o.toString()).toString();
				String dirStr = new StringBuffer(tmpdir).append(type)
						.append("/").toString();
				if (!sardine.exists(dirStr)) {
					sardine.createDirectory(dirStr);
					LOGGER.info("创建路径:" + dirStr);
				}
			}
		} catch (SardineException e) {
			LOGGER.error("创建头像类型失败", e);
		}

	}

	@Override
	public void initCreatePhotoFileDirectory(String webDav, Date date, Integer k) {
		// 获取初始化日期集合
		List<String> dateList = TimeHelper.findAfterNDateList(date, k);
		long start = System.currentTimeMillis();
		LOGGER.info("开始创建头像库目录--------->");
		if (dateList.size() > 0) {
			for (String sdt : dateList) {
				StringBuffer buff = new StringBuffer(webDav);
				buff.append(sdt).append("/");
				createTypeFileDir(buff.toString());
			}
		} else {
			LOGGER.info("获取日期的集合为空，初始化失败");
		}
		long end = (System.currentTimeMillis() - start) / 1000;
		LOGGER.info("头像库目录创建结束,耗时:" + end + "s");
	}

	/**
	 * 创建一个头像文件参数集合用于批量保存数据
	 * 
	 * @param url
	 * @return
	 * @throws SardineException
	 */
	private List<Map<String, Object>> createAvatarFileUrlData(String url)
			throws SardineException {
		Sardine sardine = SardineFactory.begin();
		// 待保存的参数集合
		List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();

		// List<DavResource> resDate = sardine.getResources(url + "/");
		// // 日期
		// for (DavResource davDate : resDate) {
		// if (!davDate.isDirectory()) {
		// String genUrl = davDate.getAbsoluteUrl();
		List<DavResource> resGender = sardine.getResources(url + "/");
		// 性别
		for (DavResource davGender : resGender) {
			// 获取性别
			String paramGender = davGender.getName();
			if (!davGender.isDirectory()) {
				String typeUrl = davGender.getAbsoluteUrl();
				List<DavResource> resType = sardine.getResources(typeUrl + "/");
				for (DavResource davType : resType) {
					// 获取类别
					String paramType = davType.getName();
					if (!davType.isDirectory()) {
						String imgUrl = davType.getAbsoluteUrl();
						List<DavResource> resImage = sardine
								.getResources(imgUrl + "/");
						for (DavResource davImg : resImage) {
							if (!davImg.isDirectory()) {
								String fileName = davImg.getName();
								// 只有图片文件才做处理
								if (isImageFile(fileName)) {
									String fileUri = davImg.getAbsoluteUrl();
									Map<String, Object> params = new HashMap<String, Object>();
									// 设置性别
									params.put("GENDER", paramGender);
									// 设置类型
									params.put("TYPE", paramType);
									// 设置URL
									params.put("URI", interceptUrl(fileUri));
									// 设置创建日期
									params.put("CREATETIME",
											davImg.getCreation());
									paramsList.add(params);
									LOGGER.info(fileUri);
								} else {
									LOGGER.info("不是图片文件，跳过处理");
								}
							}
						}
					}
				}
			}
		}
		// }
		// }
		return paramsList;
	}

	/**
	 * 循环保存图片属性到数据库
	 * 
	 * @param uriList
	 */
	private void savePhotoInfo(List<Map<String, Object>> uriList) {
		Date tDate = photoDao.selectMaxCreateTime();
		// Date tDate = null;
		// if (maxTime != null) {
		// tDate = maxTime.get("maxtime");
		// }
		if (uriList.size() > 0) {
			for (Map<String, Object> param : uriList) {
				String sgender = param.get("GENDER").toString();
				String stype = param.get("TYPE").toString();
				String suri = param.get("URI").toString();
				Date stime = (Date) param.get("CREATETIME");
				if (tDate != null) {
					if (stime.after(tDate)) {
						Photo pt = new Photo();
						pt.setGender(converGender(sgender));
						pt.setPicType(stype);
						pt.setPicUrl(suri);
						pt.setCreatetime(stime);
						crudPhotoDao.insert(pt);
					}
				} else {
					Photo pt = new Photo();
					pt.setGender(converGender(sgender));
					pt.setPicType(stype);
					pt.setPicUrl(suri);
					pt.setCreatetime(stime);
					crudPhotoDao.insert(pt);
				}
			}
		} else {
			LOGGER.info("没有需要同步的数据");
		}
	}

	/**
	 * 转换性别代码
	 * 
	 * @param g
	 * @return
	 */
	private Integer converGender(String g) {
		if (g.equals(GENDER_MALE)) {
			return 1;
		} else if (g.equals(GENDER_FEMALE)) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * 判断是否是图片文件
	 * 
	 * @param filename
	 * @return
	 */
	private boolean isImageFile(String filename) {
		boolean isImage = false;
		// 部分图片文件的扩展名
		String[] extName = { "jpg", "jpeg", "png", "bmp", "gif", "pic", "ico" };
		if (StringUtils.isNotEmpty(filename)) {
			String[] tarray = filename.split("[.]");
			String ext = tarray[1];
			for (String tmp : extName) {
				if (ext.equalsIgnoreCase(tmp)) {
					isImage = true;
					break;
				}
			}
		}
		return isImage;
	}

	/**
	 * 转换URL保存固定的图片地址
	 * 
	 * @param url
	 * @return
	 */
	private String interceptUrl(String url) {
		StringBuffer buff = new StringBuffer();
		if (StringUtils.isNotEmpty(url)) {
			String[] array = url.split("[/]");
			for (int i = 4; i > 0; i--) {
				buff.append("/");
				buff.append(array[array.length - i]);
			}
		}
		return buff.toString();
	}

	@Override
	public void synAvatarFileDataBase(String serviceUrl, Date lastTime,
			Date specTime) {
		// 根据上次执行的日期开始本次的扫描任务
		List<String> jobDate = TimeHelper.findStart2EndDateList(lastTime,
				specTime);
		if (jobDate.size() > 0) {
			for (String sdate : jobDate) {
				StringBuffer webBuff = new StringBuffer(serviceUrl);
				webBuff.append(sdate);
				// webBuff.append("/");
				try {
					LOGGER.info("开始执行" + sdate + "任务");
					List<Map<String, Object>> list = createAvatarFileUrlData(webBuff
							.toString());
					savePhotoInfo(list);
					LOGGER.info("执行结束" + sdate);
				} catch (SardineException e) {
					LOGGER.error("获取同步数据出错", e);
				}
			}
		} else {
			LOGGER.info("执行出错，没有执行任务的时间集合");
		}
	}

	@Override
	public void createLastTimeToCurrTimeDir(String serviceUri, Date last,
			Date curr) {
		// TODO Auto-generated method stub
		List<String> dateList = TimeHelper.findStart2EndDateList(last, curr);
		if (dateList.size() > 0) {
			for (String date : dateList) {
				StringBuffer buff = new StringBuffer(serviceUri);
				buff.append(date);
				buff.append("/");
				createTypeFileDir(buff.toString());
			}
		}
	}

	public static void main(String arge[]) throws IOException {
		AvatarFileManageSeriveImpl afs = new AvatarFileManageSeriveImpl();
		Sardine sardine = SardineFactory.begin();
		String webDav = "http://192.168.3.61:8080/slide/files/20111124/男/其他/34.jpg";
		InputStream is = new FileInputStream(new File(
				"D:\\nali\\doc\\营销系统\\头像库\\头像库\\女\\可爱\\16.jpg"));
		sardine.put("http://192.168.3.61:8080/slide/files/16.txt", is);

	}
}
