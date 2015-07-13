package org.wind.util;

import java.io.File;

public class FileUtils {
	/**
	 * 判断路径是否存在
	 * 
	 * @param path验证路径
	 * @param create创建路径
	 */
	public static boolean createorexistsPath(String path, boolean create) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			return false;
		} else {
			return true;
		}
	}
}
