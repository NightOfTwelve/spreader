package com.nali.spreader.words.naming.word;

public class Phrase implements Snippet {
	private static final String DEFAULT_SEP = " ";
	private Snippet[] snippets;
	private String sep;

	public Phrase(String sep, Snippet... snippets) {
		super();
		this.snippets = snippets;
		this.sep = sep;
	}

	public Phrase(Snippet... snippets) {
		this(DEFAULT_SEP, snippets);
	}

	@Override
	public String name() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < snippets.length; i++) {
			sb.append(snippets[i].name());
			if(i!=snippets.length-1) {
				sb.append(sep);
			}
		}
		return sb.toString();
	}

}
