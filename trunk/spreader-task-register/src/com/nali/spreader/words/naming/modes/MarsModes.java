package com.nali.spreader.words.naming.modes;

import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.WapperIterator;
import com.nali.spreader.words.Mars;
import com.nali.spreader.words.naming.Modes;

public class MarsModes implements Modes {
	private Modes modes;

	public MarsModes(Modes modes) {
		this.modes = modes;
	}

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		Iterator<String> iterator = modes.iterator(robot, name);
		return new WapperIterator<String>(iterator) {
			@Override
			public String next() {
				return Mars.tran(super.next());
			}
		};
	}

}
