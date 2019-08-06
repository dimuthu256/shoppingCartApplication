package com.org.shoppingcart.rest.config;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonUtil {
	private static Gson gson = new Gson();

	private GsonUtil() {

	}

	//convert given object to string
	public static String getToString(Object src, Type typeOfSrc) {
		try {
			if (src != null)
				return gson.toJson(src, typeOfSrc);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return "";
	}

	//convert given json string to object
	public static <T> T getFromObject(String json, Class<T> classOfT) {
		try {
			if (json != null)
				return gson.fromJson(json, classOfT);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
}
