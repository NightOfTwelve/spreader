package com.nali.spreader.lucene.analysis;

import java.io.Reader;
import java.net.URL;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;
import com.nali.spreader.lucene.config.DefaultConfigEnum;
import com.nali.spreader.lucene.filter.StopwordsFilter;

/**
 * 继承MMSegAnalyzer 增加自定义的停词功能
 * 
 * @author xiefei
 * 
 */
public final class ContentAnalyzer extends MMSegAnalyzer {
	private Dictionary dic = null;
	private String fileName = null;
	private String key = null;
	private Set<String> stop = null;
	private boolean ignoreCase = false;
	private URL url = ContentAnalyzer.class.getResource(DefaultConfigEnum.mmseg4jDicPath.getValue());

	public ContentAnalyzer(String dictionaryPath, String fileName, String key,
			Set<String> stop, boolean ignoreCase) {
		this.fileName = fileName;
		this.key = key;
		this.stop = stop;
		this.ignoreCase = ignoreCase;
	}

	public ContentAnalyzer(String dictionaryPath) {
		if (StringUtils.isNotBlank(dictionaryPath)) {
			dic = Dictionary.getInstance(dictionaryPath);
		} else {
			dic = Dictionary.getInstance(url.toString());
		}
	}

	public ContentAnalyzer() {
		dic = Dictionary.getInstance(url.toString());
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new StopwordsFilter(new MMSegTokenizer(new MaxWordSeg(dic),
				reader), DefaultConfigEnum.stopwordsPath.getValue(), "content.library.stopwords", stop,
				ignoreCase);
	}
}
