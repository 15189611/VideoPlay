package com.charles.videoplay.entity;

import java.io.Serializable;

/**
 * Created by Charles on 2016/10/10.
 */

public class UserToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private int errcode;
    private long time;
    private LoginUserInfo data;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public LoginUserInfo getData() {
        return data;
    }

    public void setData(LoginUserInfo data) {
        this.data = data;
    }
}
