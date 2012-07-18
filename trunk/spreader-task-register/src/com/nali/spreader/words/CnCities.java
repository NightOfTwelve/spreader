package com.nali.spreader.words;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CnCities {
	private static Logger logger = Logger.getLogger(CnCities.class);
	private static final String DIRECT_WORD = "直辖";
	private static List<Area> directAreas;
	private static List<Area> provinceAreas;
	
	static {
		try {
			initCities("txt/city.txt");
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	private static void initCities(String src) throws IOException {
		List<Area> list = Area.load(Txt.getUrl(src));
		for (Iterator<Area> iterator = list.iterator(); iterator.hasNext();) {
			Area area = iterator.next();
			if(DIRECT_WORD.equals(area.getName())) {
				directAreas = area.getSubAreas();
				iterator.remove();
				provinceAreas = list;
				return;
			}
		}
		//when not found direct cities
		provinceAreas = list;
		logger.error("not found direct cities.");
	}

	public static List<Area> getDirectAreas() {
		return directAreas;
	}

	public static List<Area> getProvinceAreas() {
		return provinceAreas;
	}

}
