package com.nali.spreader.lucene.config;

public enum DefaultConfigEnum {
	mmseg4jDicPath("mmseg4jDicPath", "/lucene", "mmseg4j分词器指定的词库地址"), stopwordsPath(
			"stopwordsPath", "/lucene/stopwords.properties", "默认的停用词库地址"), tokenStreamFiledName(
			"tokenStreamFiledName", "defaultFiled", "分词器tokenStream方法中默认的入参");
	private DefaultConfigEnum(String key, String value, String directions) {
		this.key = key;
		this.value = value;
		this.directions = directions;
	}

	private String key;
	private String value;
	private String directions;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}
}
