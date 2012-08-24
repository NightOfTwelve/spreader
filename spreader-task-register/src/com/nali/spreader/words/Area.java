package com.nali.spreader.words;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.TxtFileUtil.LineHandler;

public class Area {
	private static Logger logger = Logger.getLogger(Area.class);
	private static final Pattern linePattern=Pattern.compile("(\\d+)(\t+)(.*)");
	private String name;
	private int code;
	private Area parentArea;
	private List<Area> subAreas;

	private Area(String name, int code) {
		super();
		this.name = name;
		this.code = code;
	}
	
	public static List<Area> load(URL src) throws IOException {
		final Area root = new Area(null, 0);
		LineHandler handler = new LineHandler() {
			private Area parent = null;
			private Area last = root;
			private int currentLevel = 0;
			private LinkedList<Area> stack=new LinkedList<Area>();
			@Override
			public void handle(String line) {
				Matcher matcher = linePattern.matcher(line);
				if(matcher.matches()) {
					int code = Integer.parseInt(matcher.group(1));
					int level = matcher.group(2).length();
					String name = matcher.group(3);
					Area area = new Area(name, code);
					if(level==currentLevel) {
						parent.addSubAreas(area);
					} else if(level>currentLevel) {
						last.subAreas=new ArrayList<Area>();
						last.addSubAreas(area);
						stack.push(parent);
						parent=last;
					} else {//level<currentLevel
						while(level<currentLevel) {
							currentLevel--;
							parent=stack.pop();
						}
						parent.addSubAreas(area);
					}
					currentLevel=level;
					last=area;
				} else {
					logger.error("cannot parse line:" + line);
				}
			}
		};
		TxtFileUtil.read(src, handler);
		return root.subAreas;
	}
	
	protected void addSubAreas(Area area) {
		subAreas.add(area);
		area.parentArea = this;
	}

	public String getName() {
		return name;
	}
	public int getCode() {
		return code;
	}
	public List<Area> getSubAreas() {
		return subAreas;
	}
	public static void print(List<Area> al) {//for test
		for (Area area : al) {
			print(area, 1);
		}
	}
	private static void print(Area a, int i) {
		System.out.print(a.code);
		for (int j = 0; j < i; j++) {
			System.out.print('\t');
		}
		System.out.println(a.name);
		if(a.subAreas!=null) {
			for (Area s : a.subAreas) {
				print(s, i+1);
			}
		}
	}

	public Area getParentArea() {
		return parentArea;
	}
}
