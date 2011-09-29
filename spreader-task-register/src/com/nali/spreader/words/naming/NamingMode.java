package com.nali.spreader.words.naming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.words.naming.RobotMatcher.AgeRobotMatcher;
import com.nali.spreader.words.naming.modes.ConstellationModes;
import com.nali.spreader.words.naming.modes.ConstellationModes2;
import com.nali.spreader.words.naming.modes.DateModes;
import com.nali.spreader.words.naming.modes.FreeNameModes;
import com.nali.spreader.words.naming.modes.HomeModes;
import com.nali.spreader.words.naming.modes.MarsModes;
import com.nali.spreader.words.naming.modes.ModifierModes;
import com.nali.spreader.words.naming.modes.RandomNumberModes;

public enum NamingMode { //TODO 可选分隔符
	/**星座+名/全名/英文名*/
	constellationNaming(Names.NAMES_FULL_LAST_EN, new ConstellationModes()),
	
	/**任意组合姓名，英文，拼音等 */
	FreeNameNaming2(Names.NAMES_NONE, new FreeNameModes()),
	
	/**前缀+星座 || 星座+后缀*/
	constellationNaming2(Names.NAMES_NONE, new ConstellationModes2()),
	
	/**省/市+名/全名/英文名 || 名/全名/英文名 + '在' + 省/市*/
	homeNaming(Names.NAMES_FULL_LAST_EN, new HomeModes()),
	
	/**前缀+名/全名 || 名/全名+后缀*/
	modifierNaming(Names.NAMES_FULL_LAST, new ModifierModes()),
	
	/**名/全名/英文名 + '_' + 生日年/注册年/出生月日*/
	dateNaming(Names.NAMES_FULL_LAST_EN, new DateModes()),
	
	/**名/全名/英文名 + 3-4位随机数/伪qq号随机数*/
	numberNaming(Names.NAMES_FULL_LAST_EN, new RandomNumberModes()),
	
	/**火星文, 针对:前缀+名/全名 || 名/全名+后缀 */
	marsModifierNaming(Names.NAMES_FULL_LAST, new MarsModes(new ModifierModes()), new AgeRobotMatcher(10, 18)),
	;
	private Names names;
	private Modes modes;
	private RobotMatcher matcher;
	private NamingMode(Names names, Modes modes) {
		this(names, modes, Constants.TRUE_MATCHER);
	}
	private NamingMode(Names names, Modes modes, RobotMatcher matcher) {
		//test size
		this.names = names;
		this.modes = modes;
		this.matcher = matcher;
	}
	public List<String> gets(RobotRegister robot) {
		if(matcher.match(robot)) {
			List<String> nameList = names.gets(robot);
			List<String> rlt= new ArrayList<String>();
			Iterator<String> nameIter = nameList.iterator();
			while (nameIter.hasNext()) {
				String name = nameIter.next();
				Iterator<String> modeIter = modes.iterator(robot, name);
				while (modeIter.hasNext()) {
					rlt.add(modeIter.next());
				}
			}
			return rlt;
		} else {
			return Collections.emptyList();
		}
	}
}