package com.charles.videoplay.http.subscribers;

import android.app.Activity;

import com.charles.videoplay.BuildConfig;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.VideoPlayRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.util.JsonUtil;
import com.charles.videoplay.util.Logger;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import rx.Subscriber;

public class BaseSubscriber<T> extends Subscriber<T> {

    protected Activity mActivity;
    protected String mRequestMethod;
    protected ResponseListener<T> mResponseListener;

    public BaseSubscriber(ResponseListener<T> responseListener) {
        mResponseListener = responseListener;
    }

    public BaseSubscriber(String requestMethod, ResponseListener<T> responseListener) {
        mRequestMethod = requestMethod;
        mResponseListener = responseListener;
    }

    public BaseSubscriber(Activity activity, String requestMethod, ResponseListener<T> responseListener) {
        mActivity = activity;
        mRequestMethod = requestMethod;
        mResponseListener = responseListener;
    }

    @Override
    public void onCompleted() {
        removeRequest();
    }

    @Override
    public void onError(Throwable e) {
        removeRequest();
        if (mResponseListener != null) {
            AppException appException;

            if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketException) {
                appException = new AppException(AppException.ExceptionStatus.NetWorkException, AppException.NETWORK_ERROR);
            } else if (e instanceof SocketTimeoutException) {
                appException = new AppException(AppException.ExceptionStatus.TimeoutException, AppException.TIMEOUT_ERROR);
            } else if (e instanceof TimeoutException) {
                appException = new AppException(AppException.ExceptionStatus.TimeoutException, AppException.TIMEOUT_ERROR);
            } else if (e instanceof AppException) {
                appException = (AppException) e;
            } else {
                appException = new AppException(AppException.ExceptionStatus.ResultException, AppException.RESULT_ERROR);
            }

            mResponseListener.onFailure(appException);
        }

        if (BuildConfig.DEBUG && e != null){
            Logger.i("request" + " >>>> " + mRequestMethod);
            Logger.i(JsonUtil.toJson(e));
        }
    }

    @Override
    public void onNext(T t) {
        if (mResponseListener != null) {
            mResponseListener.onSuccess(t);
        }

        if (BuildConfig.DEBUG){
            Logger.i("request"+ " >>>> " + mRequestMethod);
            Logger.i(JsonUtil.toJson(t));
        }
    }

    protected void removeRequest() {
        if (mActivity != null && mRequestMethod != null && mRequestMethod.length() > 0) {
            VideoPlayRequest.removeRequest(mActivity, mRequestMethod);
        }
    }
}