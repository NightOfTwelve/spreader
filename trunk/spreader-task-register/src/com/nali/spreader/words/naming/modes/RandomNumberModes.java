package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.words.naming.Modes;

public class RandomNumberModes implements Modes {
	private final NumberRandomer smallNumberRandomer = new NumberRandomer(1300, 10000);
	private final NumberRandomer qqNumberRandomer = new NumberRandomer(1000000, 100000000);

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		return Arrays.asList(
				name + smallNumberRandomer.get(),
				name + smallNumberRandomer.get(),
				name + qqNumberRandomer.get(),
				name + qqNumberRandomer.get()
			).iterator();
	}

}
