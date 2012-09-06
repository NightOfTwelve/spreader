package com.nali.spreader.words;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

public class AppleNickname {
	private static Randomer<Integer> nicknameModes = new NumberRandomer(1, 8);
	private static Randomer<Boolean> booleanRandomer = new AvgRandomer<Boolean>(true, false);
	private static Randomer<Integer> numberRanges = new NumberRandomer(1, 100000);
	private static Randomer<String> sps = new AvgRandomer<String>("", " ", "_", "-", "~");
	public static String genNickname(RobotRegister robotRegister) {
		String firstNamePinyin = robotRegister.getFirstNamePinyin();
		String lastNamePinyin = robotRegister.getLastNamePinyin();
		String enName = robotRegister.getEnName();
		
		String nickname;
		switch (nicknameModes.get()) {
		case 1:
			nickname = firstNamePinyin+lastNamePinyin;
			break;
		case 2:
			nickname = firstNamePinyin+lastNamePinyin.charAt(0);
			break;
		case 3:
			nickname = firstNamePinyin.charAt(0)+lastNamePinyin;
			break;
		case 4:
			nickname = enName;
			break;
		case 5:
			nickname = lastNamePinyin + enName;
			break;
		case 6:
			nickname = enName+firstNamePinyin;
			break;
		case 7:
			nickname = firstNamePinyin.charAt(0)+ "" +lastNamePinyin.charAt(0);
			break;
		default:
			throw new IllegalArgumentException("unspported type");
		}
		if(booleanRandomer.get()) {
			nickname = nickname.toLowerCase();
		}
		if(nickname.length()<6 || booleanRandomer.get()) {
			String sp = sps.get();
			Integer num;
			if(booleanRandomer.get()) {
				num = numberRanges.get();
			} else {
				num = SpecialDateUtil.getCachedThisYear();
			}
			if(booleanRandomer.get()) {
				nickname = num + sp + nickname;
			} else {
				nickname = nickname + sp + num;
			}
		}
		return nickname;
	}

}

