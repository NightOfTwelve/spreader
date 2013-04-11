package com.nali.spreader.spider.utils;

import java.util.HashSet;
import java.util.Set;

import com.nali.spreader.analyzer.usergroup.ReplyWeiboByGroup;
import com.nali.spreader.util.TxtFileUtil;

public class Genre {
	private static final String FILE_APP_GENRE = "txt/app-genre.txt";
	public static final Set<Integer> genres = new HashSet<Integer>();
	static {
		Set<String> datas = TxtFileUtil.read(ReplyWeiboByGroup.class
				.getClassLoader().getResource(FILE_APP_GENRE));
		for (String s : datas) {
			if (s != null) {
				Integer g = Integer.parseInt(s);
				genres.add(g);
			}
		}
	}
}
