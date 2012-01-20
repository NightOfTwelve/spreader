package com.nali.spreader.common;

import java.io.IOException;
import java.net.URL;

import com.nali.spreader.words.Txt;


public class TestUri {
	public static void main(String[] args) throws IOException {
		URL file = Txt.getUrl("com/nali/cms/util/RoomUtil.class");
		System.out.println(file);
	}
}
