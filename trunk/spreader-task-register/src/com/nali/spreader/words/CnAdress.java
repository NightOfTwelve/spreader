package com.nali.spreader.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nali.spreader.util.CommonPattern;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.words.naming.word.Phrase;
import com.nali.spreader.words.naming.word.RandomNumbers;
import com.nali.spreader.words.naming.word.RandomSnippet;
import com.nali.spreader.words.naming.word.RandomWords;
import com.nali.spreader.words.naming.word.Snippet;
import com.nali.spreader.words.naming.word.Word;

public class CnAdress {
	private static Snippet streets;
	private static Snippet suites;

	public static String street() {
		return streets.name();
	}
	
	public static String suite() {
		return suites.name();
	}
	
	static {
		try {
			loadStreets("txt/street-cn.txt");
		} catch (IOException e) {
			throw new Error(e);
		}
		initSuites();
	}
	
	private static void loadStreets(String src) throws IOException {
		Map<String, List<String>> categoryStreets = TxtFileUtil.readKeyListMap(Txt.getUrl(src));
		RandomWords topStreets = tran(categoryStreets.get("top"));
		RandomWords normalStreetPrefixs = tran(categoryStreets.get("normal"));
		RandomWords cityStreetStreetPrefixs = tran(getCities());
		Snippet streetSuffix = new RandomSnippet(new WeightRandomer<Snippet>()
				.a(new Word("街"), 1)
				.a(new Word("路"), 2));
		
		Snippet streetNames = new RandomSnippet(new WeightRandomer<Snippet>()
				.a(topStreets, 50)
				.a(new Phrase("", normalStreetPrefixs, streetSuffix), 30)
				.a(new Phrase("", cityStreetStreetPrefixs, streetSuffix), 20)
				);
		Snippet randomNo=new RandomNumbers(1, 1000);
		Snippet noWord = tran("弄,号");
		streets = new Phrase("", streetNames, randomNo, noWord);
	}
	
	private static void initSuites() {
		Snippet randomUnit=new RandomNumbers(1, 50);
		Snippet unitNames = tran("栋,座,幢");
		Snippet RoomWord = new Word("室");
		Snippet RoomNo = new Phrase("", new RandomNumbers(1, 10), new RandomNumbers(0, 2), new RandomNumbers(1, 10));
		suites = new RandomSnippet(new WeightRandomer<Snippet>()
											.a(new Phrase("", RoomNo, RoomWord), 30)
											.a(new Phrase("", randomUnit, unitNames, RoomNo, RoomWord), 80));
	}
	
	private static List<String> getCities() {
		List<String> cityStreets = new ArrayList<String>();
		for (Area city : CnCities.getDirectAreas()) {
			cityStreets.add(city.getName());
		}
		for (Area province : CnCities.getProvinceAreas()) {
			cityStreets.add(province.getName());
			List<Area> subAreas = province.getSubAreas();
			if(subAreas!=null) {
				for (Area subArea : subAreas) {
					cityStreets.add(subArea.getName());
				}
			}
		}
		return cityStreets;
	}
	
	private static RandomWords tran(List<String> words) {
		return new RandomWords(new AvgRandomer<String>(words));
	}
	
	private static RandomWords tran(String words) {
		return tran(Arrays.asList(CommonPattern.comma.split(words)));
	}
}
