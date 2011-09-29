package com.nali.spreader.data;

import com.nali.spreader.util.Randomer;

public class Province {
	private String name;
	private Randomer<String> cities;
	
	public Province(String name, Randomer<String> cities) {
		this.name = name;
		this.cities = cities;
	}

	public String getRandomCity() {
		return cities.get();
	}

	public String getName() {
		return name;
	}
	
}
