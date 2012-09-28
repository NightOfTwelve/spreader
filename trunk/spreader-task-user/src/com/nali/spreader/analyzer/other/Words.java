package com.nali.spreader.analyzer.other;

import java.io.IOException;
import java.util.Set;

import com.nali.spreader.analyzer.usergroup.ReplyWeiboByGroup;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

public class Words {
	private static final String FILE_REPLY_WORDS = "txt/reply.txt";
	public static final Randomer<String> defaultReplyWords;
	static {
		try {
			Set<String> datas = TxtFileUtil.read(ReplyWeiboByGroup.class.getClassLoader().getResource(FILE_REPLY_WORDS));
			defaultReplyWords = new AvgRandomer<String>(datas);
		} catch (IOException e) {
			throw new Error(e);
		}
	}
}
