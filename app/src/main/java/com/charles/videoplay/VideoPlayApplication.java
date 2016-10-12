package com.charles.videoplay;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.charles.videoplay.util.Screen;
import com.kingnet.KYVideo.okhttp.OkHttpUtils;
import com.kingnet.KYVideo.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Charles on 2016/10/10.
 */

public class VideoPlayApplication extends Application {

    public static Application application;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = application.getApplicationContext();
        initOkhttp();
        Screen.initScreen(getApplicationContext());
    }

    private  void initOkhttp(){
        File cacheFile = new File(getApplicationContext().getCacheDir(), "cache");
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient clien=new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("Charles"))
                .cache(cache)
                .build();
        OkHttpUtils.initClient(clien);
    }

    /**
     * 获取app当前版本
     */
    public static String getAppVersion(){
        PackageManager packageManager =application.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(application.getPackageName(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    /**
     * 获取用户设备 ID
     */
    public static String getDeviceId(){
        TelephonyManager tm = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
