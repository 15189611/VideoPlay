package com.charles.videoplay.net;

import android.text.TextUtils;
import android.util.Log;

import com.charles.videoplay.VideoPlayApplication;
import com.charles.videoplay.entity.RequestCookie;
import com.charles.videoplay.entity.UserToken;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonUtil;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.util.MD5Util;
import com.google.gson.Gson;
import com.kingnet.KYVideo.okhttp.OkHttpUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Charles on 2016/10/10.
 */

public class VideoNetWork {

    public static String SERVERANDPORT_OUT = "www.52doushi.com";
    public static String APPNAME = "dsapi";

    public static String REQ_KAY = "reqkey";
    public static String COOKIE = "Cookie";
    public static String KEY = "dsva@2016";

    public static void getToken(){
        String uploadCarch = getOutRootUrl() + "LoginByToken";
        Map<String, Object> map = new HashMap<>();
        map.put("version", "1.0.1");
        map.put("platform", 1);
        map.put("myuid", 0);
        map.put("token","");
        String params = JsonUtil.toJson(map);
        Log.i("Charles2","好的=="+params  +" url==" +uploadCarch);

        OkHttpUtils
                .postString()
                .url(uploadCarch)
                .content(params)
                .addHeader(COOKIE, getRequestKey(uploadCarch, params))
                .build()
                .execute(new com.kingnet.KYVideo.okhttp.callback.StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        Logger.i("User=="+response);
                        Gson gson = new Gson();
                        UserToken userToken = gson.fromJson(response, UserToken.class);
                        if(userToken.getErrcode() == 0){
                            if(userToken.getData()== null) return;
                            ShareUtils.saveUser(VideoPlayApplication.getMyApplicationContext(),userToken.getData());
                        }

                        long currentTime  = new Date().getTime();
                        Constant.serverDifference = (currentTime/1000)-userToken.getTime();
                        Log.i("Charles", "相差时间=" +Constant.serverDifference  +"服务器时间=" +userToken.getTime() +"当前时间="+currentTime);
                    }
                });
    }

    public static String getOutRootUrl(){
        StringBuilder url = new StringBuilder("http://");
        url.append(SERVERANDPORT_OUT).append('/')
                .append(APPNAME).append('/');
        return url.toString();
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
}
