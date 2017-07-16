package com.valygard.utils;

public class StringUtils {

	private StringUtils() {
		throw new AssertionError("Cannot instantiate utility class");
	}
	
	public static String reverse(String input) {
		String result = "";
		int index = input.length() - 1;
		while (result.length() != input.length()) {
			result += input.substring(index, index + 1);
			index--;
		}
		return result;
	}
}
