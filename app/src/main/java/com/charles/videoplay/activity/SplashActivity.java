package com.charles.videoplay.activity;

import android.content.Intent;
import com.charles.videoplay.R;
import com.charles.videoplay.base.BaseActivity;
import com.charles.videoplay.entity.IndexVideoList;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.listener.ResponseListener;
import com.charles.videoplay.net.IndexRequest;
import com.charles.videoplay.net.VideoNetWork;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonParser;
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
        VideoNetWork.getToken();
        getIndexData();
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
