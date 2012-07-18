package com.nali.spreader.words;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.nali.spreader.util.random.AvgRandomer;

public class UsCityRandomer {
	private static final String US_TXT = "txt/us.txt";
	private static AvgRandomer<UsState> statesRandomer;
	private static List<String> cities;

	static {
		try {
			initStates();
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	public static AvgRandomer<UsState> getStatesRandomer() {
		return statesRandomer;
	}

	public static List<String> getCities() {
		return cities;
	}
	
	private static void initStates() throws IOException {
		List<UsState> states = new ObjectMapper().readValue(new InputStreamReader(Txt.getUrl(US_TXT).openStream(), "utf-8"),
				TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, UsState.class));
		List<UsState> checkedStates = new ArrayList<UsState>(states.size());
		cities = new ArrayList<String>();
		for (UsState state : states) {
			if(state.getCities().size()>0) {
				checkedStates.add(state);
				for (UsCity city : state.getCities()) {
					cities.add(city.getName());
				}
			}
		}
		statesRandomer = new AvgRandomer<UsState>(checkedStates);
	}

}
