package com.nali.spreader.group.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.serialization.json.IJsonSerializer;
import com.nali.common.serialization.json.JackSonSerializer;
import com.nali.common.serialization.json.JsonGenerationException;
import com.nali.common.serialization.json.JsonParseException;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpParser;
import com.nali.spreader.group.exp.PropertyExpression;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;

@Component
public class UserGroupAssembler {
	private IJsonSerializer jsonSerializer = new JackSonSerializer();
	
	@Autowired
	private PropertyExpParser propertyExpParser;
	
	public UserGroup assembleUserGroup(Website webSite, String name,
			String description, UserGroupType gtype, PropertyExpressionDTO expressionDTO) throws AssembleException {
		checkUserGroupType(gtype, expressionDTO);
		UserGroup userGroup = new UserGroup();
		userGroup.setWebsiteId(webSite.getId());
		userGroup.setDescription(description);
		userGroup.setGname(name);
		
		try {
			PropertyExpression expression = new PropertyExpression(expressionDTO);
			userGroup.setPropExp(jsonSerializer.toString(expression));
		    userGroup.setGtype(gtype.getTypeVal());
			userGroup.setDescription(description);
			userGroup.setPropVal(this.propertyExpParser.parsePropVal(expressionDTO));
			return userGroup;
		} catch (JsonGenerationException e) {
			throw new AssembleException("Json assemble user group error." , e);
		} catch(IllegalArgumentException ex) {
			throw new AssembleException("Conatins illegal input arguments, " + ex.getMessage(), ex);
		}
	}
	
	public String toJson(PropertyExpression expression) throws AssembleException {
		try {
			return this.jsonSerializer.toString(expression);
		} catch (JsonGenerationException e) {
			throw new AssembleException("Parse property expression to json string fails" + expression, e);
		}
	}
	
	public PropertyExpression toExpression(String expression) throws AssembleException {
		try {
			return this.jsonSerializer.toBean(expression, PropertyExpression.class);
		} catch (JsonParseException e) {
			throw new AssembleException("Can't parse string to property expression: " + expression, e);
		}
	}
	
	private void checkUserGroupType(UserGroupType gtype, PropertyExpressionDTO expressionDTO)throws AssembleException {
		if(gtype == UserGroupType.fixed) {
//			if(expressionDTO.getScore() == null) {
//				throw new AssembleException("静态分组必须按用户评分来划分，而评分为空");
//			}
		}
	}
}
