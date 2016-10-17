package com.charles.videoplay.http;


import com.charles.videoplay.http.apiservice.ApiService;
import com.charles.videoplay.http.interceptor.ClientInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Charles
 */
public class RetrofitManager {

    private static final int DEFAULT_TIMEOUT_MS = 30000;

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;

    private ApiService mApiService;

    private RetrofitManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT_MS / 2, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_MS / 2, TimeUnit.MILLISECONDS)
                .addInterceptor(new ClientInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("www.52doushi.com")
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
