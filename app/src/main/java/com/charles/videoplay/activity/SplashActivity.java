package com.charles.videoplay.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.charles.videoplay.R;
import com.charles.videoplay.base.BaseActivity;
import com.charles.videoplay.entity.IndexVideoList;
import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.ResponseResult;
import com.charles.videoplay.http.RetrofitManager;
import com.charles.videoplay.http.VideoPlayRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.http.subscribers.BaseSubscriber;
import com.charles.videoplay.net.IndexRequest;
import com.charles.videoplay.net.VideoNetWork;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SplashActivity extends BaseActivity {

    private Subscription subscribe;
    private Subscription subscribe1;

    @Override
    protected int getLayoutResId() {
        return R.layout.splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        Type type = new TypeToken<LoginUserInfo>() {
        }.getType();
        ResponseListener<LoginUserInfo> listener = new ResponseListener<LoginUserInfo>() {
            @Override
            public void onSuccess(LoginUserInfo loginUserInfo) {
                Log.i("Charles2", "成功==="+loginUserInfo);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i("Charles2", "失败==="+e.errorCode);
            }
        };

        getDataToken(this,listener,type);
        VideoNetWork.getToken();
        getIndexData();
    }

    @SuppressWarnings("unchecked")
    private  <T> void getDataToken(Activity activity, ResponseListener<T> listener , Type type) {
        Map<String, Object> map = new HashMap<>();
        map.put("version", "1.0.1");
        map.put("platform", 1);
        map.put("myuid", 0);
        map.put("token","");

        Subscriber<T> subscriber = new BaseSubscriber<>(activity, "LoginByToken", listener);

          RetrofitManager.getInstance()
                .getApiService().getToken(map)
                .map(new ResponseResultFunc(type))
                .compose(requestScheduler())
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

    private void getIndexData() {
        subscribe1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                try {
                    IndexRequest.getHomeVideoTypes(new ResponseListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            subscriber.onNext(s);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onFailure(AppException e) {
                            subscriber.onError(e);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String response) {
                IndexVideoList videoList = JsonParser.deserializeByJson(response, IndexVideoList.class);
                ShareUtils.saveObjecToString(SplashActivity.this, Constant.SAVE_INDEX_DATA_KEY, videoList);
            }
        });
    }

    @Override
    protected void getRemoteData() {
        delayJump();
    }

    private void delayJump() {
        subscribe = Observable.timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected boolean enableToolbar() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe.isUnsubscribed() ) {
            subscribe.unsubscribe();
        }
        if(subscribe1.isUnsubscribed()){
            subscribe1.unsubscribe();
        }
    }

}
