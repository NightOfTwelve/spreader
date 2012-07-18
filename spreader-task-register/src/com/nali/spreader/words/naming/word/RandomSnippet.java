package com.nali.spreader.words.naming.word;

import com.nali.spreader.util.random.Randomer;

public class RandomSnippet implements Snippet {
	private Randomer<Snippet> snippets;

	public RandomSnippet(Randomer<Snippet> snippets) {
		super();
		this.snippets = snippets;
	}

	@Override
	public String name() {
		return snippets.get().name();
	}

}
