package com.charles.videoplay.util;

import android.util.Log;

/**
 * Created by Charles on 2016/10/9.
 */

public class Logger {

    public static void i(String tag , String msg){
        Log.i(tag,msg);
    }

    public static void i(String msg){
        Log.i("Charles",msg);
    }
}
