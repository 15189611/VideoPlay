package com.charles.videoplay.http;

import android.app.Activity;

import com.charles.videoplay.http.responselistener.ResponseListener;

import java.lang.reflect.Type;
import java.util.Map;


/**
 * Created by Charles
 */
public interface IHttpRequest {

    <T> void requestDataWithActivity(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener);

    <T> void requestDataWithDialog(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener);

    <T> void requestData(String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener);
}
