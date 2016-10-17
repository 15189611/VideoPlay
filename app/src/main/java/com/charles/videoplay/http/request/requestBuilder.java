package com.charles.videoplay.http.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charles on 2016/10/17.
 */

public class requestBuilder {

    Map<String, Object> methodParams = new HashMap<>();

    public static requestBuilder newInstance(String method) {
        return new requestBuilder(method);
    }

    public requestBuilder(String method){
        init(method);
    }

    private void init(String method) {

    }

}
