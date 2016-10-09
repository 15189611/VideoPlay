package com.charles.videoplay.http;

import com.charles.videoplay.util.JsonParser;

import java.lang.reflect.Type;

public class ResponseResult<T> {

    public String code;
    public String msg;
    public T result;

    public Object getResult(Class<?> clazz) throws AppException {
        Object object;
        try {
            object = clazz.cast(result);
        } catch (Exception e) {
            throw new AppException(AppException.ExceptionStatus.ParseException, AppException.PARSE_ERROR);
        }
        return object;
    }

    public T getResult() {
        if (result != null) {
            return result;
        }
        return null;
    }
    public void setResult(String resultStr, Type type) throws AppException{
        result = JsonParser.deserializeByJson(resultStr, type);
    }
}
