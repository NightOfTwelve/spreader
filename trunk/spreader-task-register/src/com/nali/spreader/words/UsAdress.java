package com.nali.spreader.words;

import java.util.Arrays;

import com.nali.spreader.util.CommonPattern;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.words.naming.word.Phrase;
import com.nali.spreader.words.naming.word.RandomNumbers;
import com.nali.spreader.words.naming.word.RandomSnippet;
import com.nali.spreader.words.naming.word.RandomWords;
import com.nali.spreader.words.naming.word.Snippet;
import com.nali.spreader.words.naming.word.Word;

public class UsAdress {
	
	public static String address() {
		return streetAddress.name();
	}
	
	public static String suite() {
		return suite.name();
	}

	private final static Word empty = new Word("");
	//adj
	private final static Snippet orders = tran(
			"1st,2nd,3rd,4th,5th,6th,7th,8th,9th,First,Second,Third,Fourth,Fifth,Sixth,Eighth,Ninth,Main");
	private final static Snippet directions = tran(
			"East,West,North,South,NE,SE,NW,SW");
	private final static Snippet trees = tran(
			"Elm,Cedar,Maple,Pine,Oak,Willow,Laurel,Walnut,Chestnut,Linden,Cherry Tree,Rose");
	private final static Snippet peoples = tran(
			"Jefferson,Adam,Madison,Virginia,Washington,Wilson,Lincoln,Jackson");
	private final static Snippet popularWords = tran(
			"Park,Highland,Woods,Sunset,Salem,Stone,Lake,Hill,Garden");
	private final static Snippet normalWords = tran(
			"Easy,Dusty,Tiny,Short");
	private final static Snippet lessWords = tran(
			"Lovers,Darling,Kiss,Happy Hours,Why Worry,Pretty Good,Never Blue,Endless,Happy Valley Cut Off,Down The Hill,Our Hills");
	//address suffix
	private final static Snippet streets = new RandomSnippet(new WeightRandomer<Snippet>()
												.a(new Word("Street"), 120)
												.a(new Word("St"), 120)
												.a(new Word("Avenue"), 60)
												.a(new Word("Ave"), 60)
												.a(new Word("Road"), 30)
												.a(new Word("Rd"), 30)
												);
		//	Lane,Garden,Residential,Yard,Dormitory
		//	Vallage,Hill,Lake,Town,District
	//suite
	private final static Snippet randomUnit=new RandomNumbers(1, 30);
	private final static Snippet RoomWord = new Word("Room");
	private final static Snippet RoomNo = new Phrase("", new RandomNumbers(1, 10), new RandomNumbers(0, 2), new RandomNumbers(1, 10));
	private final static Snippet unit = tran("Unit,Building");
	private final static Snippet suite= new RandomSnippet(new WeightRandomer<Snippet>()
										.a(empty, 80)
										.a(new Phrase(RoomWord, RoomNo), 90)
										.a(new Phrase(RoomWord, RoomNo, unit, randomUnit), 100));

	//phrase
	private final static Snippet orderedDecorative=new Phrase(
					new RandomSnippet(new WeightRandomer<Snippet>()
								.a(directions, 100)
								.a(peoples, 40)
								.a(popularWords, 60))
					, orders
				);
	//address
	private final static Snippet NoWord = new Word("No");//TODO 网页注册不支持.
	private final static Snippet randomNo=new RandomNumbers(1, 2000);
	private final static Snippet No = new RandomSnippet(new WeightRandomer<Snippet>()
			.a(new Phrase(NoWord, randomNo), 70)
			.a(randomNo, 100));
	private final static Snippet streetAddress=new Phrase(
					No
					,new RandomSnippet(new WeightRandomer<Snippet>()
							.a(orderedDecorative, 90)
							.a(trees, 60)
							.a(peoples, 50)
							.a(orders, 20)
							.a(normalWords, 20)
							.a(lessWords, 10)
							)
					, streets
				);
	
//	private static RandomSnippet emptyable(Snippet snippet, int emptyWeight) {
//		return new RandomSnippet(new WeightRandomer<Snippet>()
//				.a(snippet, 100)
//				.a(empty, emptyWeight));
//	}
	
	private static RandomWords tran(String words) {
		return new RandomWords(
				new AvgRandomer<String>(
						Arrays.asList(
								CommonPattern.comma.split(words))));
	}
	
	public static void main(String[] args) {
		System.out.println(suite() + ", " + address());
	}
}
