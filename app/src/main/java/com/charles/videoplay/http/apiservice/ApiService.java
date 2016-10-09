package com.charles.videoplay.http.apiservice;


import com.charles.videoplay.http.ResponseResult;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Charles
 * Created by Charles
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("api/index")
    Observable<ResponseResult> requestData(@FieldMap Map<String, String> body);
}
