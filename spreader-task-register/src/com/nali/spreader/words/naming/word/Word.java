package com.nali.spreader.words.naming.word;


public class Word implements Snippet {
	private String name;
	
	public Word(String name) {
		super();
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

}
