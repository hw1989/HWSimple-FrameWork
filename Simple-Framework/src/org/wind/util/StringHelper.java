package org.wind.util;

public class StringHelper {
	@Deprecated
	public static boolean isNullorEmpty(String s) {
		if (s == null || "".equals(s)) {
			return true;
		}
		return false;
	}
	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s)) {
			return true;
		}
		return false;
	}
}
