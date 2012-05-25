package com.nali.spreader.group.exp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lang.StringUtils;
import com.nali.spreader.config.Range;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.service.IKeywordService;

@Component
public class IBatisPropertyExpParser implements PropertyExpParser{
	
	@Autowired
	private IKeywordService keywordService;
	
	@Override
	public Map<String, Object> parseQuery(PropertyExpression expression) {
		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		
		Long articlesGte = expression.getArticlesGte();
		if(articlesGte != null) {
			propertiesMap.put("articlesGte", articlesGte);
		}
		
		Long articlesLte = expression.getArticlesLte();
		if(articlesLte != null) {
			propertiesMap.put("articlesLte", articlesLte);
		}
		
		Long attentionsGte = expression.getAttentionsGte();
		if(attentionsGte != null) {
			propertiesMap.put("attentionsGte", attentionsGte);
		}
		
		Long attentionsLte = expression.getAttentionsLte();
		if(attentionsLte != null) {
			propertiesMap.put("attentionsLte", attentionsLte);
		}
		
		Date birthDayGte = expression.getBirthDayGte();
		if(birthDayGte != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birthDayGte);
			propertiesMap.put("birthDayYearGte", calendar.get(Calendar.YEAR));
			propertiesMap.put("birthDayMonthGte", calendar.get(Calendar.MONTH));
			propertiesMap.put("birthDayDayGte", calendar.get(Calendar.DAY_OF_MONTH));
		}
		
		Date birthDayLte = expression.getBirthDayLte();
		if(birthDayLte != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birthDayLte);
			propertiesMap.put("birthDayYearLte", calendar.get(Calendar.YEAR));
			propertiesMap.put("birthDayMonthLte", calendar.get(Calendar.MONTH));
			propertiesMap.put("birthDayDayLte", calendar.get(Calendar.DAY_OF_MONTH));
		}
		
		
		String keywordStr = expression.getCategory();
		if(StringUtils.isNotEmptyNoOffset(keywordStr)) {
			Keyword keyword = keywordService.findKeywordByKeywordName(keywordStr);
			if(keyword != null) {
				propertiesMap.put("tagId", keyword.getId());
			}else{
				throw new GroupUserQueryException("Not exist tag name: " + keywordStr);
			}
		}
		
		String city = expression.getCity();
		if(StringUtils.isNotEmptyNoOffset(city)) {
			propertiesMap.put("city", city);
		}
		
		Integer constellation = expression.getConstellation();
		if(constellation != null) {
			propertiesMap.put("constellation", constellation);
		}
		
		Long fansGte = expression.getFansGte();
		if(fansGte != null) {
			propertiesMap.put("fansGte", fansGte);
		}
		
		Long fansLte = expression.getFansLte();
		if(fansLte != null) {
			propertiesMap.put("fansLte", fansLte);
		}
		
		Integer gender = expression.getGender();
		if(gender != null) {
			propertiesMap.put("gender", gender);
		}
		
//		String introduction = expression.getIntroduction();
//		if(StringUtils.isNotEmptyNoOffset(introduction)) {
//			propertiesMap.put("introduction", introduction);
//		}
		
		Boolean isRobot = expression.getIsRobot();
		if(isRobot != null) {
			propertiesMap.put("isRobot", isRobot);
		}
		
		String nationality = expression.getNationality();
		if(StringUtils.isNotEmptyNoOffset(nationality)) {
			propertiesMap.put("nationality", nationality);
		}
		
		String nickName = expression.getNickName();
		if(StringUtils.isNotEmptyNoOffset(nickName)) {
			propertiesMap.put("nickName", nickName);
		}
		
		String province = expression.getProvince();
		if(StringUtils.isNotEmptyNoOffset(province)) {
			propertiesMap.put("province", province);
		}
		
		Long robotFansGte = expression.getRobotFansGte();
		if(robotFansGte != null) {
			propertiesMap.put("robotFansGte", robotFansGte);
		}
		
		Long robotFansLte = expression.getRobotFansLte();
		if(robotFansLte != null) {
			propertiesMap.put("robotFansLte", robotFansLte);
		}
		
		Float scoreGte = expression.getScoreGte();
		if(scoreGte != null) {
			propertiesMap.put("scoreGte", scoreGte);
		}
		
		Float scoreLte = expression.getScoreLte();
		if(scoreLte != null) {
			propertiesMap.put("scoreLte", scoreGte);
		}
		
		Boolean vType = expression.getVType();
		if(vType != null) {
			propertiesMap.put("vType", vType);
		}
		return propertiesMap;
	}

	@Override
	public int parsePropVal(PropertyExpressionDTO expression) {
		int propVal = 0;

		Range<Long> articles = expression.getArticles();
		if(articles != null) {
			propVal += Properties.articles.getPropVal();
		}
		
		Range<Long> attentions = expression.getAttentions();
		if(attentions != null) {
			propVal += Properties.attentions.getPropVal();
		}
		
		Range<Date> birthDay = expression.getBirthDay();
		if(birthDay != null) {
			propVal += Properties.birthDay.getPropVal();
		}
		
		String constellation = expression.getConstellation();
		if(StringUtils.isNotEmptyNoOffset(constellation)) {
			propVal += Properties.constellation.getPropVal();
		}
		
		Range<Long> fans = expression.getFans();
		if(fans != null) {
			propVal += Properties.fans.getPropVal();
		}
		
		String gender = expression.getGender();
		if(StringUtils.isNotEmptyNoOffset(gender)) {
			propVal += Properties.gender.getPropVal();
		}
		
//		String introduction = expression.getIntroduction();
//		if(StringUtils.isNotEmptyNoOffset(introduction)) {
//			propVal += Properties.introduction.getPropVal();
//		}
		
		Boolean isRobot = expression.getIsRobot();
		if(isRobot != null) {
			propVal += Properties.isRobot.getPropVal();
		}
		
		String nationality = expression.getNationality();
		if(StringUtils.isNotEmptyNoOffset(nationality)) {
			propVal += Properties.nationality.getPropVal();
		}
		
		String nickName = expression.getNickName();
		if(StringUtils.isNotEmptyNoOffset(nickName)) {
			propVal += Properties.nickName.getPropVal();
		}
		
		String province = expression.getProvince();
		if(StringUtils.isNotEmptyNoOffset(province)) {
			propVal += Properties.province.getPropVal();
		}
		
		Range<Long> robotFans = expression.getRobotFans();
		if(robotFans != null) {
			propVal += Properties.robotFans.getPropVal();
		}
		
		Range<Float> score = expression.getScore();
		if(score != null) {
			propVal += Properties.score.getPropVal();
		}
		
		Boolean vType = expression.getVType();
		if(vType != null) {
			propVal += Properties.vType.getPropVal();
		}
		
		String category = expression.getCategory();
		if(!StringUtils.isEmpty(category)) {
			propVal += Properties.category.getPropVal();
		}
		
//		String webSite = expression.getWebsite();
//		if(StringUtils.isNotEmptyNoOffset(webSite)) {
//			propVal += Properties.website.getPropVal();
//		}
		
		return propVal;
	}
}
