package com.nali.spreader.words;

import java.util.ArrayList;
import java.util.List;

import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

public class UsCity {
	String name;
	String cnName;
	List<String> zips = new ArrayList<String>();
	private Randomer<String> randZips;
	
	public String getRandomZip() {
		if(randZips==null) {
			synchronized (this) {
				if(randZips==null) {
					randZips=new AvgRandomer<String>(zips);
				}
			}
		}
		return randZips.get();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public List<String> getZips() {
		return zips;
	}
	public void setZips(List<String> zips) {
		this.zips = zips;
	}
}
