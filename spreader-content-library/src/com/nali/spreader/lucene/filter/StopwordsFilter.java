package com.nali.spreader.lucene.filter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;

import com.nali.spreader.lucene.config.DefaultConfigEnum;

@Component
public class StopwordsFilter extends FilteringTokenFilter {
	private static Logger logger = Logger.getLogger(StopwordsFilter.class);
	private static final String DEFAULT_STOPWORD_FILE_KEY = "content.library.stopwords";
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	public static Set<String> stopwords = getStopwords();

	private static Set<String> getStopwords() {
		try {
			Configuration cfg = new PropertiesConfiguration(
					StopwordsFilter.class
							.getResource(DefaultConfigEnum.stopwordsPath
									.getValue()));
			String[] words = cfg.getStringArray(DEFAULT_STOPWORD_FILE_KEY);
			return new HashSet<String>(Arrays.asList(words));
		} catch (Exception e) {
			logger.error(e);
		}
		return new HashSet<String>();
	}

	private static final Set<String> ignoreStop = new HashSet<String>(
			Arrays.asList("a", "an", "and", "are", "as", "at", "be", "but",
					"by", "for", "if", "in", "into", "is", "it", "no", "not",
					"of", "on", "or", "such", "that", "the", "their", "then",
					"there", "these", "they", "this", "to", "was", "will",
					"with"));

	private StopwordsFilter(boolean enablePositionIncrements,
			TokenStream input, String fileName, String key, Set<String> stop,
			boolean ignoreCase) {
		super(enablePositionIncrements, input);
		getStopwords(fileName, key);
		if (stop != null) {
			stopwords.addAll(stop);
		}
		if (!ignoreCase) {
			stopwords.addAll(ignoreStop);
		}
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input,
			Set<String> stop) {
		this(enablePositionIncrements, input, null, null, stop, false);
	}

	public StopwordsFilter(TokenStream input, String fileName, String key,
			Set<String> stop, boolean ignoreCase) {
		this(false, input, fileName, key, stop, ignoreCase);
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input,
			Set<String> stop, boolean ignoreCase) {
		this(enablePositionIncrements, input, null, null, stop, ignoreCase);
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input,
			String fileName, String key) {
		this(enablePositionIncrements, input, fileName, key, false);
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input,
			String fileName, String key, boolean ignoreCase) {
		this(enablePositionIncrements, input, fileName, key, null, ignoreCase);
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input,
			boolean ignoreCase) {
		this(enablePositionIncrements, input, null, null, null, ignoreCase);
	}

	public StopwordsFilter(boolean enablePositionIncrements, TokenStream input) {
		this(enablePositionIncrements, input, null, null, null, false);
	}

	private void getStopwords(String fileName, String keyName) {
		if (StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(keyName)) {
			try {
				URL url = StopwordsFilter.class.getResource(fileName);
				Configuration cfg = new PropertiesConfiguration(url);
				String[] words = cfg.getStringArray(keyName);
				stopwords = new HashSet<String>(Arrays.asList(words));
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	protected boolean accept() throws IOException {
		return !stopwords.contains(termAtt.toString());
	}

	public static void main(String[] args) {
		System.out.println(StopwordsFilter.stopwords);
	}
}
