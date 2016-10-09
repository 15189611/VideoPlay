package com.charles.videoplay.http.responselistener;


import com.charles.videoplay.http.AppException;

public interface ResponseListener<T> {

    void onSuccess(T t);

    void onFailure(AppException e);

}
