package com.kingnet.KYVideo.okhttp.utils;

import android.util.Log;

/**
 * Created by Charles on 16/05/25.
 */
public class L
{
    private static boolean debug = true;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

