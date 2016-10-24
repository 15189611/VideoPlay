package com.charles.videoplay.activity;

import android.content.Intent;
import android.util.Log;
import com.charles.videoplay.R;
import com.charles.videoplay.VideoPlayApplication;
import com.charles.videoplay.base.BaseActivity;
import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.entity.VideoType;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
        UserRequest.newInstance().getUserToken(this, "LoginByToken", new ResponseListener<LoginUserInfo>() {
            @Override
            public void onSuccess(LoginUserInfo loginUserInfo) {
                if(loginUserInfo == null){
                    return;
                }

                ShareUtils.saveUser(SplashActivity.this,loginUserInfo);
                long currentTime  = new Date().getTime();
                Constant.serverDifference = (currentTime/1000)-loginUserInfo.getTime();
            }

            @Override
            public void onFailure(AppException e) {
            }
        });
        getIndexData();

    }

    private void getIndexData() {
        getData();
    }

    private void getData() {
        subscribe1 = Observable.create(new Observable.OnSubscribe<List<VideoType>>() {
            @Override
            public void call(final Subscriber<? super List<VideoType>> subscriber) {
                    UserRequest.newInstance().getIndexData(SplashActivity.this, "GetHomeVideoTypes", new ResponseListener<List<VideoType>>() {
                        @Override
                        public void onSuccess(List<VideoType> videoTypes) {
                            subscriber.onNext(videoTypes);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onFailure(AppException e) {
                            subscriber.onError(e);
                        }
                    });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<VideoType>>() {
            @Override
            public void call(List<VideoType> response) {
                String json = JsonUtil.toJson(response);
                ShareUtils.save(SplashActivity.this, Constant.SAVE_INDEX_DATA_KEY, json);
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
