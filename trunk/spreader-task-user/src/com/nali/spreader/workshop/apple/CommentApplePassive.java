package com.nali.spreader.workshop.apple;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.workshop.apple.CommentApple.CommentDto;

@Component
@ClassDescription("app下载完评论几率")
public class CommentApplePassive implements PassiveAnalyzer<KeyValue<Long, CommentDto>>, Configable<Integer> {
	private static WeightRandomer<Integer> starRandomer;
	private static WeightRandomer<KeyValue<Integer, Randomer<String>>> wordsRandomer;
	private static Randomer<Boolean> booleanRandomer = new AvgRandomer<Boolean>(true, false);
	private static Randomer<Boolean> lessTrueBooleanRandomer = new AvgRandomer<Boolean>(true, false, false);
	private static WeightRandomer<String> wordEndRandomer;
	
	private WeightRandomer<Boolean> doCommentRandomer;

	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, CommentDto>> commentApple;
	
	static {
		starRandomer = new WeightRandomer<Integer>();
		starRandomer.add(5, 80);
		starRandomer.add(4, 20);
		Map<String, List<String>> keyListMap = TxtFileUtil.readKeyListMap(CommentApplePassive.class.getClassLoader().getResource("txt/app-comment.txt"));
		wordsRandomer = new WeightRandomer<KeyValue<Integer, Randomer<String>>>();
		for (Entry<String, List<String>> entry : keyListMap.entrySet()) {
			Integer key = Integer.valueOf(entry.getKey());
			List<String> baseWords = entry.getValue();
			Randomer<String> baseWordsRandomer = new AvgRandomer<String>(baseWords);
			int weight = baseWords.size()*(keyListMap.size()*2-key);
			wordsRandomer.add(new KeyValue<Integer, Randomer<String>>(key, baseWordsRandomer), weight);
		}
		wordEndRandomer = new WeightRandomer<String>();
		wordEndRandomer.add("", 50);
		wordEndRandomer.add("!", 50);
		wordEndRandomer.add("!!", 10);
		wordEndRandomer.add("!!!", 20);
		wordEndRandomer.add("!!!!", 5);
	}

	private static String getRandomWords() {
		KeyValue<Integer, Randomer<String>> entry = wordsRandomer.get();
		Integer module = entry.getKey();
		String baseWord = entry.getValue().get();
		StringBuilder word = new StringBuilder();
//		1=(it's )?word(!)?
//		2=word(!)?
//		3=(i )?word(!)?
		if(module==1) {
			if(booleanRandomer.get()) {
				word.append("it's ");
			}
		} else if(module==2) {
		} else if(module==3) {
			if(booleanRandomer.get()) {
				word.append("i ");
			}
		} else {
			throw new IllegalArgumentException("unsupported word module:" + module);
		}
		if(lessTrueBooleanRandomer.get()) {
			word.append(baseWord.toUpperCase());
		} else {
			word.append(baseWord);
		}
		word.append(wordEndRandomer.get());
		if(booleanRandomer.get()) {
			upperFirst(word);
		}
		return word.toString();
	}

	private static void upperFirst(StringBuilder word) {
		word.setCharAt(0, Character.toUpperCase(word.charAt(0)));
	}

	@Override
	public void work(KeyValue<Long, CommentDto> data) {
		if(doCommentRandomer.get()) {
			CommentDto commentDto = data.getValue();
			commentDto.setStars(starRandomer.get());
			commentDto.setTitle(getRandomWords());
			commentDto.setContent(getRandomWords());
			commentApple.send(data);
		}
	}

	@Override
	public void init(Integer config) {
		if(config==null||config<0) {
			config = 0;
		}
		if(config>100) {
			config=100;
		}
		doCommentRandomer = new WeightRandomer<Boolean>();
		doCommentRandomer.add(true, config);
		doCommentRandomer.add(false, 100 - config);
	}

}
