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

import okhttp3.Headers;
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
    private static MediaType MEDIA_TYPE_PLAIN ;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request mRequest = chain.request();
        Request copy = mRequest.newBuilder().build();

        String url = mRequest.url().toString();
        String method = copy.method();

        Request request = null;
        if("GET".equals(method)){
            request = chain.request().newBuilder()
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader(COOKIE, getRequestKey(url))
                    .build();
            Log.i("Charles2", "Get=" +url);
        }else{
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            String header = buffer.readUtf8();

            MediaType mediaType = copy.body().contentType();
            if (mediaType != null) {
                if (!isText(mediaType)) {
                    MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
                }
            }

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
            RequestBody  body = RequestBody.create(MEDIA_TYPE_PLAIN, JsonUtil.toJson(parms));
             request = chain.request().newBuilder()
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader(COOKIE, getRequestKey(url, JsonUtil.toJson(parms)))
                    .post(body)
                    .build();
            Log.i("Charles2","Post="+JsonUtil.toJson(parms) + "--url==" + url);
        }


        if (BuildConfig.DEBUG) {
            Response response = chain.proceed(request);
            return interceptLogWithResponse(response);
        }

        return chain.proceed(request);
    }

    private boolean isText(MediaType mediaType)
    {
        if (mediaType.type() != null && mediaType.type().equals("text"))
        {
            return true;
        }
        if (mediaType.subtype() != null)
        {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    public static String getRequestKey(String url){
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
        rc.setNet("wifi");
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
