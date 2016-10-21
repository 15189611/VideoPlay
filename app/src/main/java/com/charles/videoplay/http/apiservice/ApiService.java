package com.charles.videoplay.http.apiservice;



import com.charles.videoplay.http.ResponseResult;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Charles
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("api/index")
    Observable<ResponseResult>  requestData(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("LoginByToken")
    Observable<ResponseResult> getToken(@FieldMap Map<String, Object> body);

}
