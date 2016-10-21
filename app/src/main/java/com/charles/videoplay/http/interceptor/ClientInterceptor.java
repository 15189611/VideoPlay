package com.charles.videoplay.http.interceptor;


import android.text.TextUtils;
import android.util.Log;

import com.charles.videoplay.BuildConfig;
import com.charles.videoplay.VideoPlayApplication;
import com.charles.videoplay.entity.RequestCookie;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonUtil;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.util.MD5Util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class ClientInterceptor implements Interceptor {

    public static String REQ_KAY = "reqkey";
    public static String COOKIE = "Cookie";
    public static String KEY = "dsva@2016";
    public static String APPNAME = "dsapi";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request2 = chain.request();
        Request copy = chain.request().newBuilder().build();
        final Buffer buffer = new Buffer();
        copy.body().writeTo(buffer);
        String header = buffer.readUtf8();
        String url = request2.url().toString();
        Log.i("Charles2", "url=" +url) ;
        String[] strings = header.split("&");
        Map<String,Object> parms = new HashMap<>();

        for (int i = 0; i < strings.length ; i++) {
            String item = strings[i];
            String[] item2 = item.split("=");
            if(item2.length == 1){
                parms.put(item2[0],"");
            }else{
                if(item2[0].equals("platform")){
                    parms.put(item2[0],Integer.valueOf(item2[1]));
                    continue;
                }else if(item2[0].equals("myuid")){
                    parms.put(item2[0],Integer.valueOf(item2[1]));
                    continue;
                }else{
                    parms.put(item2[0],item2[1]);
                }
            }
        }
        Log.i("Charles2","自己的="+JsonUtil.toJson(parms));
        final Request request = chain.request().newBuilder()
                .addHeader("Connection", "Keep-Alive")
                .addHeader("charset", "utf-8")
                .addHeader(COOKIE, getRequestKey(url,JsonUtil.toJson(parms)))
                .build();

        if (BuildConfig.DEBUG) {
            Response response = chain.proceed(request);
            return interceptLogWithResponse(response);
        }

        return chain.proceed(request);
    }

    public static String getRequestKey(String url) throws Exception{
        return getRequestKey(url,null);
    }

    private static String getRequestKey(String url,String postdata){
        Logger.i("url = " + url + "\n postData = " + postdata);
        long currentTime  = new Date().getTime();
        long aLong = currentTime / 1000 - Constant.serverDifference;
        RequestCookie rc = new RequestCookie();
        rc.setTs(aLong);
        rc.setCid(1);
        rc.setVer(VideoPlayApplication.getAppVersion());
        rc.setMid(VideoPlayApplication.getDeviceId());
        rc.setOsver(android.os.Build.VERSION.RELEASE);
        rc.setSum(getSum(url, rc, postdata));
        Logger.i("REQ_KAY = " + REQ_KAY + '=' + JsonUtil.toJson(rc));
        return REQ_KAY+'='+ JsonUtil.toJson(rc);
    }
    private static String getSum(String url,RequestCookie rc,String postdata){
        String result = url.substring(url.indexOf(APPNAME) - 1, url.length());
        StringBuilder sbReq = new StringBuilder();
        sbReq.append(result)
                .append(KEY)
                .append(rc.getCid())
                .append(rc.getVer())
                .append(rc.getOsver())
                .append(rc.getMid())
                .append(rc.getTs() + "");
        if (!TextUtils.isEmpty(postdata)) {
            sbReq.append(postdata);
        }
        Logger.i(sbReq.toString());
        return MD5Util.getMD5String(sbReq.toString());
    }

    private Response interceptLogWithResponse(Response response) {
        if (BuildConfig.DEBUG) {
            try {
                Response.Builder builder = response.newBuilder();
                Response clone = builder.build();
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        String resp = body.string();
                        Logger.i(resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return response;
    }
}
