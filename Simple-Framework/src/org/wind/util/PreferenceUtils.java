package org.wind.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
	private static PreferenceUtils utils = null;
	private static SharedPreferences preferences = null;

	private PreferenceUtils(Context context) {
		preferences = context.getSharedPreferences("setting",
				Context.MODE_PRIVATE);
	}

	public static PreferenceUtils init(Application context) {
		if (utils == null || preferences == null) {
			utils = new PreferenceUtils(context);
		}
		return utils;
	}

	public static String getString(String key, String def) {
		return preferences.getString(key, def);
	}

	public static int getInt(String key, int def) {
		return preferences.getInt(key, def);
	}

	public static boolean getBool(String key, Boolean def) {
		return preferences.getBoolean(key, def);
	}
}
