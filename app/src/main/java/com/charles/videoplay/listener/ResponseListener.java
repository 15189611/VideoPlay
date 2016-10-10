package com.charles.videoplay.listener;

import com.charles.videoplay.http.AppException;

/**
 * Created by Charles on 2016/10/10.
 */

public interface ResponseListener<T> {
    void onSuccess(T t);

    void onFailure(AppException e);
}
