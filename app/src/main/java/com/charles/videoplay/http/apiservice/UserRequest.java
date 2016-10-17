package com.charles.videoplay.http.apiservice;

import android.app.Activity;

import com.charles.videoplay.http.responselistener.ResponseListener;

/**
 * Created by Charles on 2016/10/17.
 */

public class UserRequest {

    private static final UserRequest userRequest = new UserRequest();

    public static UserRequest newInstance() {
        return userRequest;
    }

    public void getUserToken(Activity activity ,ResponseListener<String> listener){

    }
}
