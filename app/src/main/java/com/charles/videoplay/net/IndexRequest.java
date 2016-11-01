package com.charles.videoplay.net;


import android.content.Context;
import android.util.Log;

import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.entity.UpdateData;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.charles.videoplay.util.JsonUtil;
import com.charles.videoplay.util.Logger;
import com.kingnet.KYVideo.okhttp.OkHttpUtils;
import com.kingnet.KYVideo.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Charles on 2016/10/10.
 */

public class IndexRequest {
    private static final MediaType MEDIA_TYPE_JSON  = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致

    public static void pushLogs(){
           OkHttpClient  mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();

/*
        UpdateData dataRecord = new UpdateData();
        dataRecord.setAction("click");
        dataRecord.setGod_id("1");
        dataRecord.setCat_id("2");
        dataRecord.setPos_type("ll");
        dataRecord.setUser_id("22222");
        dataRecord.setPosition(35.23+":"+123);
        List<UpdateData> list = new ArrayList<>();
        for (int i = 0 ; i < 3 ; i++){
            list.add(dataRecord);
        }
*/
        Map map = new HashMap();
        map.put("behavior",list);
        map.put("token","d3c38abf78a9b7d36713cccd3e6ccc3e");
        String mRequest = JsonUtil.toJson(map);
        Log.i("Charles2" , "jsonObject==" + mRequest);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON,  mRequest);

        String baseUrl = "http://120.55.90.70:8004/index.php/data/uplogs";
        Request request = new Request.Builder().url(baseUrl).post(body).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Charles2", "错误=="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.i("Charles2" , "成功==" + response.body().string());
                }
            }
        });
    }



















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
                    }

                    @Override
                    public void onResponse(String response) {
                        if (listener != null) {
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
                    }
                    @Override
                    public void onResponse(String response) {
                        if (listener != null) {
                            listener.onSuccess(response);
                        }
                    }
                });
    }


}
