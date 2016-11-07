package com.charles.videoplay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Charles on 2016/11/7.
 */

public class DataUtil {
    public static final String VIDEO_TIME = "MM-dd HH:mm";

    public static String getTodayTime(Date date, String pattern) {
        String str = "";

        try {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            str = sf.format(date);
        } catch (Exception e) {
        }

        return str;
    }

}
