package com.org.shoppingcart.controller.util;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static ResourceBundle resConfig;

	public ResourceUtil() {

	}

	public static String getAppConfigValue(String key) {
		if (resConfig == null)
			resConfig = ResourceBundle.getBundle("app-config");
		try {
			return resConfig.getString(key);
		} catch (Exception e) {
			return "";
		}
	}

}
