package com.nali.spreader.test.group;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.Range;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.assembler.UserGroupAssembler;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.Properties;
import com.nali.spreader.group.exp.PropertyExpression;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.test.util.RandomUtils;

@Component
public class UserGroupCreater {
	
	@Autowired
	private UserGroupAssembler userGroupAssembler;
	
	
	
	public UserGroup createArticlesUserGroup() throws AssembleException {
		PropertyExpressionDTO propertyExpressionDTO = new PropertyExpressionDTO();
		
		Range<Long> articles = new Range<Long>();
		articles.setGte(1L);
		propertyExpressionDTO.setArticles(articles);
		
		UserGroup userGroup = this.userGroupAssembler.assembleUserGroup(Website.weibo, "文章分组", "文章用户分组", UserGroupType.dynamic, propertyExpressionDTO);
		return userGroup;
	}
	
	public UserGroup createFansUserGroup() throws AssembleException {
		PropertyExpressionDTO propertyExpressionDTO = new PropertyExpressionDTO();
		Range<Long> fans = new Range<Long>();
		fans.setLte(1000l);
		propertyExpressionDTO.setFans(fans);
		UserGroup userGroup = this.userGroupAssembler.assembleUserGroup(Website.weibo, "粉丝分组", "粉丝数用户分组", UserGroupType.dynamic, propertyExpressionDTO);
		return userGroup;
	}
	
	public UserGroup createCategoryUserGroup() throws AssembleException {
		PropertyExpressionDTO propertyExpressionDTO = new PropertyExpressionDTO();
		propertyExpressionDTO.setCategory("音乐");
		UserGroup userGroup = this.userGroupAssembler.assembleUserGroup(Website.weibo, "音乐分的类分组", "音乐分类分组", UserGroupType.dynamic, propertyExpressionDTO);
		return userGroup;
	}
	
	public UserGroup createCtrlUserGroup() throws AssembleException {
		PropertyExpressionDTO dto = new PropertyExpressionDTO();
		Range<Long> fansRange = new Range<Long>();
		fansRange.setGte(10L);
		dto.setFans(fansRange);
		UserGroup userGroup = this.userGroupAssembler.assembleUserGroup(Website.weibo, "ctrl group test", "ctrl group test", UserGroupType.fixed, dto);
		return userGroup;
	}
	
	public UserGroup[] createRandomUserGroups(int count) throws AssembleException {
		UserGroup[] userGroups = new UserGroup[count];
		for(int i = 0; i < count; i++) {
			UserGroup userGroup = new UserGroup();
			userGroup.setDescription("测试分组");
			userGroup.setGname("测试分组" + i);
			
			userGroup.setGtype(UserGroupType.dynamic.getTypeVal());
			
			PropertyExpressionDTO propertyExpressionDTO = new PropertyExpressionDTO();
			PropertyExpression propExpression = new PropertyExpression(propertyExpressionDTO);
			userGroup.setPropExp(this.userGroupAssembler.toJson(propExpression));
			
			Properties[] properties = Properties.values();
			properties = RandomUtils.getRandomCountFromArr(1, properties.length, properties);
			
			int propVal = 0;
			for(Properties  property : properties) {
				propVal += property.getPropVal();
			}
			
			userGroup.setPropVal(propVal);
			
			userGroup.setWebsiteId(Website.weibo.getId());
			
			Calendar fromCal = Calendar.getInstance();
			fromCal.set(2012, 0, 1);
			
			Calendar toCal = Calendar.getInstance();
			toCal.set(2012, 11, 1);
			
			Date lastModifiedTime = RandomUtils.getRandomDate(fromCal, toCal).getTime();
			
			userGroup.setLastModifiedTime(lastModifiedTime);
			
			userGroups[i] = userGroup;
		}
		return userGroups;
	}
}
