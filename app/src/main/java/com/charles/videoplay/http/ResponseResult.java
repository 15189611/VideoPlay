package com.charles.videoplay.http;

import com.charles.videoplay.util.JsonParser;

import java.lang.reflect.Type;

public class ResponseResult<T> {
    private static final long serialVersionUID = 1L;
    public int errcode;
    public long time;
    public T data;

    public Object getResult(Class<?> clazz) throws AppException {
        Object object;
        try {
            object = clazz.cast(data);
        } catch (Exception e) {
            throw new AppException(AppException.ExceptionStatus.ParseException, AppException.PARSE_ERROR);
        }
        return object;
    }

    public T getResult() {
        if (data != null) {
            return data;
        }
        return null;
    }
    public void setResult(String resultStr, Type type) throws AppException{
        data = JsonParser.deserializeByJson(resultStr, type);
    }
}
