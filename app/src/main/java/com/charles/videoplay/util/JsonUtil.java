package com.charles.videoplay.util;

import android.text.Html;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonUtil {

	/**
	 * 将对象转为json
	 */
	public static String toJson(Object o) {
		Gson gson = new Gson();
		String str = "";
		if (o != null) {
			str = gson.toJson(o);
		}
		return str;
	}

	/**
	 * 将对象转为json
	 */
	public static Object toObject(String jsonStr, Type type) {
		Gson gson = new Gson();
		Object o = null;
		try {
			if (jsonStr != null && !"".equals(jsonStr)) {
				o = gson.fromJson(jsonStr, type);
			}
		} catch (Exception e) {
		}
		return o;
	}

	public static <T> T toObjectT(String jsonStr, Type type) {
		Gson gson = new Gson();
		T o = null;
		try {
			if (jsonStr != null && !"".equals(jsonStr)) {
				o = gson.fromJson(jsonStr, type);
			}
		} catch (Exception e) {
		}
		return o;
	}

	public static <T> T rechangeObject(JSONObject jsonObject, Type type) {
		Gson gson = new Gson();
		T o = null;
		try {
			if (jsonObject != null) {
				String json = jsonObject.toString();
				if (BussinessUtil.isValid(json)) {
					o = gson.fromJson(json, type);
				}
			}
		} catch (Exception e) {
		}

		return o;
	}

	/**
	 * 将对象转为json
	 */
	public static Object toArrayObject(String jsonStr, Type type) {
		Gson gson = new Gson();
		Object o = null;
		try {
			if (jsonStr != null && !"".equals(jsonStr)) {
				o = gson.fromJson(jsonStr, type);
			}
		} catch (Exception e) {
		}
		return o;
	}

	public static <T> T toArrayObjectByT(String jsonStr, Type type) {
		Gson gson = new Gson();
		T o = null;
		try {
			if (jsonStr != null && !"".equals(jsonStr)) {
				o = gson.fromJson(jsonStr, type);
			}
		} catch (Exception e) {
		}
		return o;
	}

	public static int getInt(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return -1;
			} else {
				return root.getInt(key);
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public static String getString(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return "";
			} else {
				return Html.fromHtml(root.getString(key)).toString();
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static double getDouble(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return -1;
			} else {
				return root.getDouble(key);
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public static double getLong(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return -1;
			} else {
				return root.getLong(key);
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public static JSONArray getJsonArray(JSONObject root, String key) {
		try {
			return root.getJSONArray(key);
		} catch (Exception e) {
			return new JSONArray();
		}
	}

	public static boolean getBoolean(JSONObject root, String key) {
		try {
			String str = getString(root, key);
			if (str.equals("true"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static JSONObject getJsonObject(String jsonStr) {
		try {
			return new JSONObject(jsonStr);
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	public static JSONObject getJsonObject(JSONObject js, String key) {
		try {
			return js.getJSONObject(key);
		} catch (Exception e) {
			return new JSONObject();
		}
	}
}
