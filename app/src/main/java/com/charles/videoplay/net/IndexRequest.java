package com.charles.videoplay.net;

import android.util.Log;

import com.charles.videoplay.http.AppException;
import com.charles.videoplay.listener.ResponseListener;
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
        map.put(Constant.UID,"0");
        for (String key : map.keySet()) {
            Log.i("doGet","key= "+ key + " and value= " + map.get(key));
        }
         Logger.i("url==" +url);
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
            url = sb.substring(0,sb.length()-1).toString();
        }

        OkHttpUtils
                .get()
                .url(url)
                .addHeader(VideoNetWork.COOKIE, VideoNetWork.getRequestKey(url))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        if(listener != null ){
                            listener.onFailure((AppException) e);
                        }
                        Logger.i("失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        if(listener != null){

                            Logger.i("成功=="+response);
                            listener.onSuccess(response);
                        }
                    }
                });
    }

}
