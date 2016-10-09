package com.charles.videoplay.util;

import com.charles.videoplay.http.AppException;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Charles on 2016/10/10.
 */

public class JsonParser  {
    public static Gson gson = new Gson();

    public static <T> T deserializeByJson(String data, Type type) throws AppException {
        try {
            if (BussinessUtil.isValid(data)) {
                return gson.fromJson(data, type);
            }
            return null;
        } catch (Exception e) {
            throw new AppException(AppException.ExceptionStatus.ParseException, AppException.PARSE_ERROR);
        }
    }

    public static <T> T deserializeByJson(String data, Class<T> clz) throws AppException {
        try {
            if (BussinessUtil.isValid(data)) {
                return gson.fromJson(data, clz);
            }
            return null;
        } catch (Exception e) {
            throw new AppException(AppException.ExceptionStatus.ParseException, AppException.PARSE_ERROR);
        }
    }

    public static <T> String serializeToJson(T t) throws AppException {
        try {
            if (t == null) {
                return "";
            }
            return gson.toJson(t);
        } catch (Exception e) {
            throw new AppException(AppException.ExceptionStatus.ParseException, AppException.PARSE_ERROR);
        }
    }
}
