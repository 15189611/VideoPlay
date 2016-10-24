package com.charles.videoplay.http.apiservice;

import android.app.Activity;

import com.charles.videoplay.entity.BandanList;
import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.entity.VedioDetails;
import com.charles.videoplay.entity.VideoType;
import com.charles.videoplay.http.VideoPlayRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.sp.ShareUtils;
import com.charles.videoplay.util.Constant;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Charles on 2016/10/17.
 */

public class UserRequest extends VideoPlayRequest {

    private static final UserRequest userRequest = new UserRequest();

    public static UserRequest newInstance() {
        return userRequest;
    }

    public void getUserToken(Activity activity,String method ,ResponseListener<LoginUserInfo> listener){
        Map<String, Object> map = new HashMap<>();
        map.put("myuid", 0);
        map.put("platform", 1);
        map.put("token","");
        map.put("version", "1.0.1");

        Type type = new TypeToken<LoginUserInfo>() {
        }.getType();
        requestPostWithActivity(activity,method,map,type,listener);
    }

    public void getIndexData(Activity activity,String method ,ResponseListener<List<VideoType>> listener){
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.UID, "0");

        Type type = new TypeToken<List<VideoType>>() {
        }.getType();
        requestGetWithActivity(activity,method,map,type,listener);
    }

    public void getVideoListOfType(Activity activity,String method,int page, int vTid,  ResponseListener<List<VedioDetails>> listener ){
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

        Type type = new TypeToken<List<VedioDetails>>() {
        }.getType();

        requestGetWithActivity(activity,method,map,type,listener);
    }

    public void GetBangList(Activity activity,String method,ResponseListener<List<BandanList>> listener ){
        Map<String, Object> map = new HashMap<>();
        LoginUserInfo userInfo = ShareUtils.getUser(activity);
        map.put("myuid",userInfo.getUid());

        Type type = new TypeToken<List<VedioDetails>>() {
        }.getType();

        requestGetWithActivity(activity,method,map,type,listener);
    }

    public void GetBangVideos(Activity activity,String method, int page,ResponseListener<List<BandanList>> listener ){
        Map<String, Object> map = new HashMap<>();
        LoginUserInfo userInfo = ShareUtils.getUser(activity);

        int cnt = 10;
        int fromCount;
        if (page != 0) {
            fromCount = (page * cnt) + 1;
        } else {
            fromCount = 0;
        }
        map.put("myuid",userInfo.getUid());
        map.put("from",fromCount);
        map.put("bid",1);
        map.put("cnt",cnt);
        Type type = new TypeToken<List<VedioDetails>>() {
        }.getType();

        requestGetWithActivity(activity,method,map,type,listener);
    }

}
