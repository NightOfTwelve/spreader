package com.nali.spreader.workshop.weibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Words;
import com.nali.spreader.data.Constellation;
import com.nali.spreader.data.RealMan;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.service.IRealManService;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.RangeChoice;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.words.Area;
import com.nali.spreader.words.CnCities;
import com.nali.spreader.words.CnPersons;
import com.nali.spreader.words.IDGenerator;
import com.nali.spreader.words.PwdGenerator;
import com.nali.spreader.words.StudentIDGenerator;
import com.nali.spreader.words.Txt;

@Component
public class RobotUserInfoGenerator {
	private static Logger logger = Logger.getLogger(RobotUserInfoGenerator.class);
	private static final String FILE_YEAR = "txt/year.txt";
	@Autowired
	private IRealManService realManService;
	// Randomers
	private Randomer<Integer> genderRandomer;
	private Randomer<Randomer<Integer>> yearRandomer;
	private Randomer<Integer> monthRandomer;
	private Randomer<Integer> dayRandomer;
	private static Randomer<Integer> wordLengthRandomer = new NumberRandomer(4, 9);
	private static Randomer<Integer> numberLengthRandomer = new NumberRandomer(3, 7);
	
	private RangeChoice<Integer, Constellation> constellations;
	private Randomer<Area> directAreas = new AvgRandomer<Area>(CnCities.getDirectAreas());
	private Randomer<Area> provinceAreas = new AvgRandomer<Area>(CnCities.getProvinceAreas());
	private double directRate = 0.25;
	
	public RobotUserInfoGenerator() throws IOException {
		genderRandomer = rangeRandomer(1, 2);
		initYearRandomer();
		monthRandomer = rangeRandomer(1, 12);
		dayRandomer = rangeRandomer(1, 28);
		initConstellation();
	}

	private Randomer<Integer> rangeRandomer(int start, int end) {
		List<Integer> rlt = new ArrayList<Integer>(end-start+1);
		for (int i = start; i <= end; i++) {
			rlt.add(i);
		}
		return new AvgRandomer<Integer>(rlt);
	}

	private void initYearRandomer() throws IOException {
		WeightRandomer<Randomer<Integer>> tmpRandomer=new WeightRandomer<Randomer<Integer>>();
		List<Entry<String, List<String>>> properties = TxtFileUtil.readKeyList(Txt.getUrl(FILE_YEAR));
		for (Entry<String, List<String>> entry : properties) {
			Integer weight = Integer.valueOf(entry.getKey());
			List<String> yearStrs = entry.getValue();
			Collection<Integer> years = new ArrayList<Integer>(yearStrs.size());
			for (String yearStr : yearStrs) {
				years.add(Integer.valueOf(yearStr));
			}
			tmpRandomer.add(new AvgRandomer<Integer>(years), weight);
		}
		yearRandomer = tmpRandomer;
	}
	
	public static String randomEmail() {
		int wordLength = wordLengthRandomer.get();
		int numberLength = numberLengthRandomer.get();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordLength; i++) {
			sb.append(RandomUtil.randomArrayItem(Words.LOW_CASE));
		}
		for (int i = 0; i < numberLength; i++) {
			sb.append(RandomUtil.randomArrayItem(Words.NUMBER));
		}
		return sb.toString();
	}
	
	public static List<String> makeEmailAccounts(RobotRegister robot) {
		List<String> rlt = new LinkedList<String>();
		add(rlt, robot.getFirstNamePinyinLower()+"_"+robot.getBirthdayYear());
		add(rlt, robot.getFirstNamePinyinLower()+robot.getBirthdayFull());
		add(rlt, robot.getFullNamePinyinLower()+robot.getBirthdayFull());
		add(rlt, robot.getFullNamePinyinLower()+robot.getBirthdayYear());
		add(rlt, robot.getEnNameLower()+robot.getBirthdayFull());
		add(rlt, robot.getEnNameLower()+robot.getBirthdayYear());
		add(rlt, robot.getEnNameLower()+"_"+robot.getFirstNamePinyinLower());
		add(rlt, robot.getLastNamePinyinLower()+"_"+(robot.getBirthdayMonth()*100+robot.getBirthdayDay()));
		add(rlt, robot.getFirstNamePinyinLower()+"_"+robot.getLastNamePinyinLower());
		rlt.remove(robot.getPwd().toLowerCase());
		rlt = new ArrayList<String>(rlt);
		Collections.shuffle(rlt);
		return rlt;
	}
	
	private static void add(List<String> names, String roughName) {
		if(roughName.length()>18) {
			roughName = roughName.substring(0, 18);
		}
		names.add(roughName);
	}
	
	public RobotRegister createRobot(RealMan realMan) {
		RobotRegister robot = new RobotRegister();
		//注意：robotRegister中first_name和last_name颠倒了
		boolean isMale;
		Integer gender;
		String lastName;//姓
		String firstName;//名
		Integer year;
		Integer month;
		Integer date;
		if(realMan!=null) {
			String idCode;
			try {
				idCode = IDGenerator.checkedId(realMan.getRealId());
				robot.setRealManId(realMan.getId());
			} catch (IllegalArgumentException e) {
				logger.error("illegal realman personId, id:" + realMan.getId() + ", personId:" + realMan.getRealId(), e);
				realManService.updateIsReal(realMan.getId(), false);
				return null;
			}
			isMale = IDGenerator.isMale(idCode);
			gender = isMale?User.GENDER_MALE:User.GENDER_FEMALE;
			KeyValuePair<String, String> parsedName = CnPersons.parseName(realMan.getRealName());
			lastName = parsedName.getKey();
			firstName = parsedName.getValue();
			year = IDGenerator.getYear(idCode);
			month = IDGenerator.getMonth(idCode);
			date = IDGenerator.getDate(idCode);
			Integer areaCode = IDGenerator.getAreaCode(idCode);
			Area area = CnCities.findNearestAreaByCode(areaCode);
			if(area==null) {
				setArea(robot, isMale);
			} else {
				Area county = area;
				Area city = getParentArea(county);
				Area province = getParentArea(city);
				robot.setProvince(province.getName());
				robot.setCity(city.getName());
				robot.setCounty(county.getName());
			}
			robot.setPersonId(idCode);
		} else {
			gender = genderRandomer.get();
			isMale = User.GENDER_MALE.equals(gender);
			lastName = CnPersons.getLastName();
			firstName = CnPersons.getFirstName(isMale);
			year = yearRandomer.get().get();
			month = monthRandomer.get();
			date = dayRandomer.get();
			int areaCode = setArea(robot, isMale);
			String idCode = IDGenerator.generate(areaCode, year, month, date, isMale);
			robot.setPersonId(idCode);
		}
		robot.setGender(gender);
		robot.setLastName(firstName);//颠倒设置
		robot.setFirstName(lastName);//颠倒设置
		robot.setEnName(CnPersons.getEnName(isMale));
		
		robot.setBirthdayYear(year);
		robot.setBirthdayMonth(month);
		robot.setBirthdayDay(date);

		String lastNamePinyin = CnPersons.getNamePinyin(lastName, true);
		String firstNamePinyin = CnPersons.getNamePinyin(firstName, false);
		robot.setFirstNamePinyin(lastNamePinyin);//颠倒设置
		robot.setLastNamePinyin(firstNamePinyin);//颠倒设置
		Constellation constellation = getConstellation(month, date);
		robot.setConstellation(constellation.ordinal());
		robot.setStudentId(StudentIDGenerator.generate(robot));

		String pwd = PwdGenerator.makePwd(firstNamePinyin, lastNamePinyin, robot.getEnName(), year*10000+month*100+date);
		robot.setPwd(pwd);

		// robot.setCareer(career);
		// robot.setSchool(school);
		// robot.setIntroduction(introduction);
		// robot.setEmail(email);

		 robot.setNickName(robot.getFullName());//temp
		return robot;
	}

	private Area getParentArea(Area area) {
		Area parent = area.getParentArea();
		if(parent!=null) {
			if(parent.getCode()!=CnCities.DIRECT_CODE) {
				return parent;
			}
		}
		return area;
	}

	private int setArea(RobotRegister robot, boolean isMale) {
		Area province;
		if(Math.random()<directRate) {
			province = directAreas.get();
		} else {
			province = provinceAreas.get();
		}
		Area city = RandomUtil.randomItem(province.getSubAreas(), RandomUtil.random);
		robot.setProvince(province.getName());
		robot.setCity(city.getName());
		Area county = city.getSubAreas()==null? city : RandomUtil.randomItem(city.getSubAreas(), RandomUtil.random);
		robot.setCounty(county.getName());
		return county.getCode();
	}

	private Constellation getConstellation(Integer month, Integer date) {
		return constellations.getCeiling(month * 100 + date);
	}

	private void initConstellation() {
		constellations = new RangeChoice<Integer, Constellation>();
		for (Constellation constellation : Constellation.values()) {
			int month = constellation.getEndMonth();
			int date = constellation.getEndDate();
			constellations.setBorder(month * 100 + date, constellation);
			if (month == 1) {
				constellations.setBorder(12 * 100 + 31, constellation);
			}
		}
	}

}
