package com.kingnet.KYVideo.okhttp.builder;

import java.util.Map;

/**
 * Created by Charles on 16/05/25.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
