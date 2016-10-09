package com.charles.videoplay.http.subscribers;


import com.charles.videoplay.http.responselistener.ResponseListener;

public class ResponseSubscriber<T> extends BaseSubscriber<T> {

    public ResponseSubscriber(ResponseListener<T> responseListener) {
        super(responseListener);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
    }
}