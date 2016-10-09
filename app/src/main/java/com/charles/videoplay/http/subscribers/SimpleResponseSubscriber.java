package com.charles.videoplay.http.subscribers;

import com.charles.videoplay.http.responselistener.SimpleResponseListener;

import rx.Subscriber;

public class SimpleResponseSubscriber<T> extends Subscriber<T> {

    protected SimpleResponseListener<T> mSimpleResponseListener;

    public SimpleResponseSubscriber(SimpleResponseListener<T> simpleResponseListener) {
        mSimpleResponseListener = simpleResponseListener;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
        if (mSimpleResponseListener != null) {
            mSimpleResponseListener.onSuccess(t);
        }
    }
}