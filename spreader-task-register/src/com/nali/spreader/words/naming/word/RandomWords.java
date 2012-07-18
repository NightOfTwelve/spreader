package com.nali.spreader.words.naming.word;

import com.nali.spreader.util.random.Randomer;

public class RandomWords implements Snippet {
	private Randomer<String> words;

	public RandomWords(Randomer<String> words) {
		super();
		this.words = words;
	}

	@Override
	public String name() {
		return words.get();
	}

}
