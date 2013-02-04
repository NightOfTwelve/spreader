package com.nali.spreader.spider.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class WeiboMsg {
	public static String decode(String encodeText) {
		StringBuilder decodeText = new StringBuilder();
		for (int i = 0; i < encodeText.length(); i++) {
			char ch = encodeText.charAt(i);
			if (ch == '\\') {
				char next = encodeText.charAt(i + 1);
				if (next == 'u') {
					int charInt = Integer.valueOf(encodeText.substring(i + 2, i + 6), 16)
							.intValue();
					decodeText.append(Character.valueOf((char) charInt));
					i += 5;
				} else {
					if (next == 't') {
						decodeText.append('\t');
					} else if (next == 'n') {
						decodeText.append('\n');
					} else if (next == '/') {
						decodeText.append('/');
					} else if (next == '"') {
						decodeText.append('"');
					} else {
						throw new IllegalArgumentException("Invalid next:\\" + next);
					}
					i++;
				}
			} else {
				decodeText.append(Character.valueOf(ch));
			}
		}
		return decodeText.toString();
	}

	public static String decodeUseJS(String encodeText) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		Object rlt;
		try {
			rlt = engine.eval("\"" + encodeText.replace("\"", "\\\"") + "\"");
		} catch (ScriptException e) {
			throw new IllegalArgumentException("Invalid encodeText:" + encodeText, e);
		}
		return rlt.toString();
	}

	public static String encode(String text) {
		StringBuilder encodeText = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			encodeText.append("\\u").append(Integer.toString(ch, 16));
		}
		return encodeText.toString();
	}
}
