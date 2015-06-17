package org.wind.util;

public class StringHelper {
	public static boolean isNullorEmpty(String s) {
		if (s == null || "".equals(s)) {
			return true;
		}
		return false;
	}
}
