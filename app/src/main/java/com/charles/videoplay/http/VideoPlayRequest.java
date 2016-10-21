package com.charles.videoplay.http;

import android.app.Activity;


import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.http.subscribers.BaseSubscriber;
import com.charles.videoplay.http.subscribers.ProgressResponseSubscriber;
import com.charles.videoplay.util.BussinessUtil;
import com.charles.videoplay.util.JsonParser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Charles on 2016-05-12 22:13.
 */
public class VideoPlayRequest implements IHttpRequest {

    private static final ConcurrentHashMap<Object, Map<String, Subscriber<?>>> mSubscriberMap = new ConcurrentHashMap<>();

    public VideoPlayRequest() {
    }

    public static void removeRequest(Object object, String requestMethod) {
        synchronized (mSubscriberMap) {
            if (object != null && mSubscriberMap.containsKey(object)) {
                Map<String, Subscriber<?>> subscriberMap = mSubscriberMap.get(object);
                if (subscriberMap != null && subscriberMap.containsKey(requestMethod)) {
                    subscriberMap.remove(requestMethod);
                }
            }
        }
    }

    private static void cancelRequest(Object object, String requestMethod) {
        synchronized (mSubscriberMap) {
            if (mSubscriberMap.containsKey(object)) {
                Map<String, Subscriber<?>> subscriberMap = mSubscriberMap.get(object);
                if (subscriberMap != null && subscriberMap.containsKey(requestMethod)) {
                    Subscriber<?> subscriber = subscriberMap.get(requestMethod);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.unsubscribe();
                    }
                    subscriberMap.remove(requestMethod);
                }
            }
        }
    }

    public static void cancelAll(Object object) {
        synchronized (mSubscriberMap) {
            if (mSubscriberMap.containsKey(object)) {
                Map<String, Subscriber<?>> subscriberMap = mSubscriberMap.get(object);
                if (subscriberMap != null) {
                    Iterator<Map.Entry<String, Subscriber<?>>> iterator = subscriberMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Subscriber<?>> entry = iterator.next();
                        Subscriber<?> subscriber = entry.getValue();
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.unsubscribe();
                        }
                        iterator.remove();
                    }
                }

                mSubscriberMap.remove(object);
            }
        }
    }

    /**
     * 外部使用
     */
    @Override
    public <T> void requestDataWithActivity(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        toSubscribeWithActivity(activity, requestMethod, params, type, listener);
    }

    @Override
    public <T> void requestDataWithDialog(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        toSubscribeWithDialog(activity, requestMethod, params, type, listener);
    }

    @Override
    public <T> void requestData(String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        toSubscribe(requestMethod, params, type, listener);
    }

    /**
     * 私有实现
     */

    private <T> void toSubscribeWithActivity(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        if (activity != null && BussinessUtil.isValid(requestMethod)) {
            cancelRequest(activity, requestMethod);
        }

        Subscriber<T> subscriber = new BaseSubscriber<>(activity, requestMethod, listener);

        toSubscribe(params, type, subscriber);

        addSubscribe(activity, requestMethod, subscriber);
    }

    private <T> void toSubscribeWithDialog(Activity activity, String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        if (activity == null || activity.isFinishing()) {
            toSubscribe(requestMethod, params, type, listener);
            return;
        }

        if (BussinessUtil.isValid(requestMethod)) {
            cancelRequest(activity, requestMethod);
        }

        Subscriber<T> subscriber = new ProgressResponseSubscriber<>(activity, requestMethod, listener);

        toSubscribe(params, type, subscriber);

        addSubscribe(activity, requestMethod, subscriber);
    }

    private <T> void addSubscribe(Activity activity, String requestMethod, Subscriber<T> subscriber) {
        if (activity != null && BussinessUtil.isValid(requestMethod)) {
            Map<String, Subscriber<?>> subscriberMap = mSubscriberMap.get(activity);
            if (subscriberMap == null) {
                subscriberMap = new HashMap<>();
                mSubscriberMap.put(activity, subscriberMap);
            }

            subscriberMap.put(requestMethod, subscriber);
        }
    }

    private <T> void toSubscribe(String requestMethod, Map<String, String> params, final Type type, final ResponseListener<T> listener) {
        Subscriber<T> subscriber = new BaseSubscriber<>(requestMethod, listener);

        toSubscribe(params, type, subscriber);
    }

    @SuppressWarnings("unchecked")
    private <T> void toSubscribe(Map<String, String> params, Type type, Subscriber<T> subscriber) {
        RetrofitManager.getInstance().getApiService().requestData(params)
                .map(new ResponseResultFunc(type))
                .compose(requestScheduler())
                .subscribe(subscriber);
    }

    protected <T> void toSubscribe(final Observable<T> observable, final ResponseListener<T> listener) {
        Subscriber<T> subscriber = new BaseSubscriber<>(listener);

        observable.compose(this.<T>requestScheduler())
                .subscribe(subscriber);
    }

    public class ResponseResultFunc<T> implements Func1<ResponseResult<T>, T> {
        private Type type;

        ResponseResultFunc(Type type) {
            this.type = type;
        }

        @Override
        public T call(ResponseResult responseResult) {
            if (responseResult != null) {
                if (responseResult.data != null) {
                    throw new AppException(responseResult.errcode, responseResult.errcode, responseResult.data);
                } else {
                    throw new AppException(responseResult.errcode, responseResult.errcode);
                }
            } else if (responseResult == null) {
                throw new AppException(AppException.ExceptionStatus.ResultException, AppException.RESULT_ERROR);
            }

            return JsonParser.deserializeByJson(JsonParser.serializeToJson(responseResult.data), type); //将json字符串转成实体类
        }
    }

    private <T> Observable.Transformer<T, T> requestScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public class ResponseFunc1<T> implements Func1<T, T> {
        @Override
        public T call(T responseResult) {
            if (responseResult == null) {
                throw new AppException(AppException.ExceptionStatus.ResultException, AppException.RESULT_ERROR);
            }
            return responseResult;
        }
    }

    public class ResponseFunc2<T> implements Func1<ResponseResult<T>, T> {
        @Override
        public T call(ResponseResult<T> responseResult) {
            return responseResult.data;
        }
    }

}
