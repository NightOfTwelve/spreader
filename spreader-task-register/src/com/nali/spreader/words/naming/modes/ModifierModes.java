package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.Txt;
import com.nali.spreader.words.naming.Modes;

public class ModifierModes implements Modes {
	private Randomer<String> suffix = new AvgRandomer<String>(Txt.suffix);
	private Randomer<String> prefix = new AvgRandomer<String>(Txt.prefix);

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		return Arrays.asList(
				name + suffix.get(),
				prefix.get() + name
			).iterator();
	}

}
