package com.org.shoppingcart.rest.config;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static ResourceBundle resConfig;

	private ResourceUtil() {
		super();
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
