package com.charles.videoplay.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.charles.videoplay.entity.LoginUserInfo;
import com.charles.videoplay.util.JsonParser;
import com.charles.videoplay.util.JsonUtil;
import com.google.gson.Gson;


/**
 * Created by Charles on 2016/10/12.
 */

public class ShareUtils {
    public static boolean save(Context context,String key, String value) {
        return edit(context).putString(key, value).commit();
    }

    public static boolean save(Context context,String key, int value) {
        return edit(context).putInt(key, value).commit();
    }

    public static boolean save(Context context,String key, boolean value) {
        return edit(context).putBoolean(key, value).commit();
    }

    public static boolean save(Context context,String key, float value) {
        return edit(context).putFloat(key, value).commit();
    }

    public static boolean remove(Context context,String key) {
        return edit(context).remove(key).commit();
    }

    public static SharedPreferences mPreference;
    public static SharedPreferences getPreference(Context context) {
        if (mPreference == null)
            mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreference;
    }

    private static SharedPreferences.Editor edit(Context context) {
        return getPreference(context).edit();
    }

    // 通过键删除值
    public static void isValue(Context context,String name) {
        getPreference(context).edit().remove(name).commit();
    }

    public static void saveObjecToString(Context context,String key, Object value) {
        String strJson = "";
        Gson gson = new Gson();
        strJson = gson.toJson(value);
        save(context,key, strJson);
    }

    public static Object get(Context context,String key) {
        return getPreference(context).getAll().get(key);
    }

    public static void saveUser(Context context,LoginUserInfo user) {
        String result = null;
        if (user != null) {
            result = JsonUtil.toJson(user);
        }
        save(context,"User", result);
    }

    public static LoginUserInfo getUser(Context context) {
        if (get(context,"User") != null) {
            LoginUserInfo user = JsonParser.deserializeByJson(ShareUtils.get(context,"User").toString(), LoginUserInfo.class);
            return user.getUid()>0?user:null;
        } else {
            return null;
        }
    }
}
