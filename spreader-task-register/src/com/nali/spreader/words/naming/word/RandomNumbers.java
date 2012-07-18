package com.nali.spreader.words.naming.word;

import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

public class RandomNumbers implements Snippet {
	private Randomer<Integer> numbers;

	public RandomNumbers(int start, int end) {
		numbers = new NumberRandomer(start, end);
	}

	@Override
	public String name() {
		return numbers.get().toString();
	}

}
