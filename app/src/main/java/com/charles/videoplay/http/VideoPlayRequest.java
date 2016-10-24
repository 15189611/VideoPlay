package com.charles.videoplay.http;

import android.app.Activity;


import com.charles.videoplay.http.apiservice.ApiService;
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
public class VideoPlayRequest {

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
    @SuppressWarnings("unchecked")
    public <T> void requestPostWithActivity(Activity activity ,String requestMethod,Map<String, Object> map,Type type ,ResponseListener<T> listener){
        if (activity != null && BussinessUtil.isValid(requestMethod)) {
            cancelRequest(activity, requestMethod);
        }
        Subscriber<T> subscriber = new BaseSubscriber<>(activity,requestMethod,listener);

        ApiService apiService = RetrofitManager.getInstance().getApiService();
        apiService
                .getPostData(requestMethod,map)
                .map(new ResponseResultFunc(type))
                .compose(requestScheduler())
                .subscribe(subscriber);

        addSubscribe(activity, requestMethod, subscriber);
    }

    @SuppressWarnings("unchecked")
    public <T> void requestGetWithActivity(Activity activity ,String requestMethod,Map<String, Object> map,Type type ,ResponseListener<T> listener){
        if (activity != null && BussinessUtil.isValid(requestMethod)) {
            cancelRequest(activity, requestMethod);
        }
        Subscriber<T> subscriber = new BaseSubscriber<>(activity,requestMethod,listener);

        ApiService apiService = RetrofitManager.getInstance().getApiService();
        apiService
                .getWithGetData(requestMethod,map)
                .map(new ResponseResultFunc(type))
                .compose(requestScheduler())
                .subscribe(subscriber);

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



    public class ResponseResultFunc<T> implements Func1<ResponseResult<T>, T> {
        private Type type;

        public ResponseResultFunc(Type type) {
            this.type = type;
        }

        @Override
        public T call(ResponseResult responseResult) {
            if(responseResult == null){
                throw new AppException(AppException.ExceptionStatus.ResultException, AppException.RESULT_ERROR);
            }else{
                if (responseResult.data != null) {
                    return JsonParser.deserializeByJson(JsonParser.serializeToJson(responseResult.data), type); //将json字符串转成实体类
                }else{
                    return null;
                }
            }

        }
    }

    public  <T> Observable.Transformer<T, T> requestScheduler() {
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
