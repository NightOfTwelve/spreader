package com.nali.spreader.words;

import java.util.ArrayList;
import java.util.List;

import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

public class UsState {
	String name;
	String shortName;
	String cnName;
	List<String> areaCodes = new ArrayList<String>();
	List<UsCity> cities = new ArrayList<UsCity>();
	private Randomer<UsCity> randCities;
	private Randomer<String> randAreaCodes;
	
	public String getRandomAreaCode() {
		if(randAreaCodes==null) {
			synchronized (this) {
				if(randAreaCodes==null) {
					randAreaCodes=new AvgRandomer<String>(areaCodes);
				}
			}
		}
		return randAreaCodes.get();
	}

	public UsCity getRandomCity() {
		if(randCities==null) {
			synchronized (this) {
				if(randCities==null) {
					randCities=new AvgRandomer<UsCity>(cities);
				}
			}
		}
		return randCities.get();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public List<UsCity> getCities() {
		return cities;
	}
	public void setCities(List<UsCity> cities) {
		this.cities = cities;
	}
	public List<String> getAreaCodes() {
		return areaCodes;
	}
	public void setAreaCodes(List<String> areaCodes) {
		this.areaCodes = areaCodes;
	}
}
