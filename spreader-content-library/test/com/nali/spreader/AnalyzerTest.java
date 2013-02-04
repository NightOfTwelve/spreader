package com.nali.spreader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.nali.spreader.lucene.service.SegmenAnalyzerService;
import com.nali.spreader.remote.ISegmenAnalyzerService;

public class AnalyzerTest {

	@Test
	public void test1() {
		Analyzer c = new IKAnalyzer(true);
		// Analyzer c = new MMSegAnalyzer("./resource/lucene");
		Reader sr = new StringReader("this a an 我是来自中国的上海市的浦东新区的呀");
		TokenStream ts = c.tokenStream("xx", sr);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		try {
			while (ts.incrementToken()) {
				System.out.print(term.toString() + "|");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		ISegmenAnalyzerService k = new SegmenAnalyzerService();
		System.out.println(k.getContentSegmen("this a an 我是来自中国的上海市的浦东新区的呀吨克斤两"));
	}
}
