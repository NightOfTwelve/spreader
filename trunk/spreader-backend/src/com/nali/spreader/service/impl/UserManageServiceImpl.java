package com.nali.spreader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.ContentKeywordsConfig;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.RobotRegisterExample.Criteria;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserManageService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.utils.PhotoHelper;

@Service
public class UserManageServiceImpl implements IUserManageService {
	private static final Logger LOGGER = Logger.getLogger(UserManageServiceImpl.class);
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudRobotRegisterDao regDao;
	@Autowired
	private ICrudPhotoDao photoDao;
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private IGlobalUserService globaUserService;
	// 导入帐号的默认ROBOT_REGISTER_ID
	private static final Long IMPORT_ROBOT_REGISTER_ID = 0L;

	@Override
	public PageResult<User> findUserInfo(UserTagParamsDto utp, Limit lit) {
		utp.setLit(lit);
		List<User> uList = userDao.findUserAndTagInfoList(utp);
		for (User u : uList) {
			StringBuffer buff = new StringBuffer();
			List<UserTag> utList = u.getTags();
			Long pid = u.getPid();
			if (pid != null) {
				Photo p = photoDao.selectByPrimaryKey(pid);
				if (p != null) {
					String pUri = p.getPicUrl();
					u.setAvatarUrl(PhotoHelper.formatPhotoUrl(pUri));
				} else {
					LOGGER.info("未找到对应头像信息");
				}
			}
			if (utList.size() > 0) {
				for (UserTag ut : utList) {
					buff.append(ut.getTag());
					buff.append(",");
				}
			}
			u.setTag(buff.toString());
		}
		int cnt = userDao.countUserAndTagNumer(utp);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

	@Override
	public PageResult<User> findUserFansInfo(UserTagParamsDto utp, Limit lit) {
		utp.setLit(lit);
		List<User> uList = userDao.findUserFansInfoList(utp);
		for (User u : uList) {
			StringBuffer buff = new StringBuffer();
			List<UserTag> utList = u.getTags();
			Long pid = u.getPid();
			if (pid != null) {
				Photo p = photoDao.selectByPrimaryKey(pid);
				if (p != null) {
					String pUri = p.getPicUrl();
					u.setAvatarUrl(PhotoHelper.formatPhotoUrl(pUri));
				} else {
					LOGGER.info("未找到对应头像信息");
				}
			}
			if (utList.size() > 0) {
				for (UserTag ut : utList) {
					buff.append(ut.getTag());
					buff.append(",");
				}
			}
			u.setTag(buff.toString());
		}
		int cnt = userDao.countUserFansNumer(utp);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

	@Override
	public PageResult<RobotRegister> findRobotRegisterInfo(String nickName, String province,
			Limit lit) {
		RobotRegisterExample exp = new RobotRegisterExample();
		Criteria cr = exp.createCriteria();
		if (StringUtils.isNotEmpty(nickName)) {
			cr.andNickNameEqualTo(nickName);
		}
		if (StringUtils.isNotEmpty(province)) {
			cr.andProvinceEqualTo(province);
		}
		exp.setLimit(lit);
		List<RobotRegister> list = regDao.selectByExample(exp);
		int count = regDao.countByExample(exp);
		return new PageResult<RobotRegister>(list, lit, count);
	}

	@Override
	public int updateUserProprietor(User user) {
		int rows = this.crudUserDao.updateByPrimaryKeySelective(user);
		return rows;
	}

	@Override
	public String findUserRegisterPassword(Long uid) {
		String pwd = "";
		if (uid == null) {
			throw new IllegalArgumentException("参数为空，无法获取用户密码");
		} else {
			User u = new User();
			u.setId(uid);
			pwd = this.userDao.getUserPassword(u);
		}
		return pwd;
	}

	@Override
	public <T> List<T> getRandomList(List<T> list, Range<Integer> range) {
		Randomer<Integer> randomer;
		if (range.checkNotNull()) {
			randomer = new NumberRandomer(range.getGte(), range.getLte() + 1);
		} else {
			randomer = new NumberRandomer(ContentKeywordsConfig.DEFAULT_RANDOM_GTE,
					ContentKeywordsConfig.DEFAULT_RANDOM_LTE + 1);
		}
		List<T> result;
		if (!CollectionUtils.isEmpty(list)) {
			result = RandomUtil.randomItems(list, randomer.get());
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<UserTag> createUserTags(List<String> keywordList) {
		List<UserTag> tagList = new ArrayList<UserTag>();
		if (!CollectionUtils.isEmpty(keywordList)) {
			for (String tag : keywordList) {
				UserTag ut = new UserTag();
				ut.setTag(tag);
				tagList.add(ut);
			}
		}
		return tagList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nali.spreader.service.IUserManageService#importWeiboAccount(org.
	 * springframework.web.multipart.commons.CommonsMultipartFile)
	 */
	@Override
	public List<KeyValue<RobotUser, User>> importWeiboAccount(CommonsMultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("import file is empty");
		}
		try {
			List<KeyValue<RobotUser, User>> data = new ArrayList<KeyValue<RobotUser, User>>();
			XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
			// sheet 总数
			int sheetNumber = book.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++) {
				XSSFSheet sheet = book.getSheetAt(i);
				int sheetRows = sheet.getPhysicalNumberOfRows();
				for (int r = 1; r < sheetRows; r++) {
					XSSFRow row = sheet.getRow(r);
					if (row != null) {
						// 微博昵称
						XSSFCell nickNameCell = row.getCell(0);
						// websiteUid
						XSSFCell websiteUidCell = row.getCell(1);
						// email
						XSSFCell emailCell = row.getCell(2);
						// password
						XSSFCell pwdCell = row.getCell(3);
						if (websiteUidCell != null && websiteUidCell != null && emailCell != null
								&& pwdCell != null) {
							Long websiteUid = null;
							// 该单元格如果为数字型直接转为Long
							if (websiteUidCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								websiteUid = ((Double) websiteUidCell.getNumericCellValue())
										.longValue();
							} else {
								String websiteUidStr = websiteUidCell.getRichStringCellValue()
										.getString();
								if (StringUtils.isNotBlank(websiteUidStr)) {
									websiteUid = Long.parseLong(websiteUidStr);
								}
							}
							String emailAccount = emailCell.getRichStringCellValue().getString();
							String pwd = pwdCell.getRichStringCellValue().getString();
							// 有些空行也会读出来，这里做过滤
							if (websiteUid != null && StringUtils.isNotBlank(emailAccount)
									&& StringUtils.isNotBlank(pwd)) {
								String nickName = null;
								if (nickNameCell != null) {
									nickName = nickNameCell.getRichStringCellValue().getString();
								}
								KeyValue<RobotUser, User> kv = new KeyValue<RobotUser, User>();
								RobotUser robotUser = new RobotUser();
								robotUser.setRobotRegisterId(IMPORT_ROBOT_REGISTER_ID);
								robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
								robotUser.setWebsiteId(Website.weibo.getId());
								robotUser.setWebsiteUid(websiteUid);
								robotUser.setLoginName(emailAccount);
								robotUser.setLoginPwd(pwd);
								kv.setKey(robotUser);
								User existsUser = globaUserService.findByUniqueKey(
										Website.weibo.getId(), websiteUid);
								// 已存在，设置isrobot true
								if (existsUser != null) {
									existsUser.setEmail(emailAccount);
									existsUser.setNickName(nickName);
									existsUser.setIsRobot(true);
									kv.setValue(existsUser);
								} else {
									User user = new User();
									user.setEmail(emailAccount);
									user.setNickName(nickName);
									kv.setValue(user);
								}
								data.add(kv);
							}
						}
					}
				}
			}
			return data;
		} catch (IOException e) {
			LOGGER.error("file read error", e);
			return Collections.emptyList();
		}
	}
}