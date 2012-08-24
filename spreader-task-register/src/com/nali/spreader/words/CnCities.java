package com.nali.spreader.words;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CnCities {
	private static Logger logger = Logger.getLogger(CnCities.class);
	public static final String DIRECT_WORD = "直辖";
	public static final Integer DIRECT_CODE = 0;
	private static List<Area> directAreas;
	private static List<Area> provinceAreas;
	private static TreeMap<Integer, Area> areas = new TreeMap<Integer, Area>();
	
	static {
		try {
			initCities("txt/city.txt");
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	private static void initCities(String src) throws IOException {
		List<Area> list = Area.load(Txt.getUrl(src));
		addToAreasMap(list);
		areas.remove(DIRECT_CODE);
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

	private static void addToAreasMap(List<Area> list) {
		if(list==null||list.isEmpty()) {
			return;
		}
		for (Area area : list) {
			areas.put(area.getCode(), area);
			addToAreasMap(area.getSubAreas());
		}
	}

	public static List<Area> getDirectAreas() {
		return directAreas;
	}

	public static List<Area> getProvinceAreas() {
		return provinceAreas;
	}
	
	public static Area findAreaByCode(Integer code) {
		return areas.get(code);
	}
	
	public static Area findNearestAreaByCode(Integer code) {
		Entry<Integer, Area> ceilingEntry = areas.ceilingEntry(code);
		Entry<Integer, Area> floorEntry = areas.floorEntry(code);
		Entry<Integer, Area> pickEntry;
		if(ceilingEntry==null) {
			pickEntry = floorEntry;
		} else if(floorEntry==null) {
			pickEntry = ceilingEntry;
		} else {
			pickEntry = (ceilingEntry.getKey()-code)>(code-floorEntry.getKey())?floorEntry:ceilingEntry;
		}
		if(pickEntry==null) {
			return null;
		} else {
			return pickEntry.getValue();
		}
	}

}
