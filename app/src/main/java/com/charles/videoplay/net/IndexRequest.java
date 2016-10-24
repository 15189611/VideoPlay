package com.charles.videoplay.net;


import android.content.Context;
import android.util.Log;

import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.Logger;
import com.kingnet.KYVideo.okhttp.OkHttpUtils;
import com.kingnet.KYVideo.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;

/**
 * Created by Charles on 2016/10/10.
 */

public class IndexRequest {

    public static void getHomeVideoTypes(final ResponseListener<String> listener) throws Exception {
        String url = VideoNetWork.getOutRootUrl() + "GetHomeVideoTypes";

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.UID, "0");

        StringBuilder sb = null;
        if (map != null) {
            sb = new StringBuilder(url);
            Set<String> keys = map.keySet();
            Iterator<String> iterator = keys.iterator();
            sb.append('?');
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = java.net.URLEncoder.encode(String.valueOf(map.get(key)), "utf-8");
                sb.append(key).append('=').append(value).append('&');
            }
            url = sb.substring(0, sb.length() - 1).toString();
        }

        OkHttpUtils
                .get()
                .url(url)
                .addHeader(VideoNetWork.COOKIE, VideoNetWork.getRequestKey(url))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        if (listener != null) {
                            listener.onFailure((AppException) e);
                        }
                        Logger.i("失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        if (listener != null) {
                            Logger.i("成功==" + response);
                            listener.onSuccess(response);
                        }
                    }
                });
    }

    public static void getVideoListOfType(Context context, int page, int vTid, final ResponseListener<String> listener) throws Exception {
        String url = VideoNetWork.getOutRootUrl() + "GetVideoListOfType";
        LoginUserInfo user = ShareUtils.getUser(context);
        int cnt = 20;
        Map<String, Object> map = new HashMap<>();
        int fromCount;
        if (page != 0) {
            fromCount = (page * cnt) + 1;
        } else {
            fromCount = 0;
        }
        map.put("from", fromCount);
        map.put("cnt", cnt);
        map.put("vtid", vTid);

        StringBuilder sb = null;
        if (map != null) {
            sb = new StringBuilder(url);
            Set<String> keys = map.keySet();
            Iterator<String> iterator = keys.iterator();
            sb.append('?');
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = java.net.URLEncoder.encode(String.valueOf(map.get(key)), "utf-8");
                sb.append(key).append('=').append(value).append('&');
            }
            url = sb.substring(0, sb.length() - 1).toString();
        }
        Log.i("Charles2", "正式的url==" +url);
        OkHttpUtils
                .get()
                .url(url)
                .addHeader(VideoNetWork.COOKIE, VideoNetWork.getRequestKey(url))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        if (listener != null) {
                            listener.onFailure((AppException) e);
                        }
                        Logger.i("失败==" + e.getMessage());
                    }
                    @Override
                    public void onResponse(String response) {
                        if (listener != null) {
                            Logger.i("成功==" + response);
                            listener.onSuccess(response);
                        }
                    }
                });
    }


}
