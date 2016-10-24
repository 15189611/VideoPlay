package com.charles.videoplay.entity;

import java.io.Serializable;

/**
 * Created by Charles on 2016/10/10.
 */

public class LoginUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int uid; //用户id
    private String nick; //用户昵称
    private String head; //用户头像url
    private String token; //用户的新的token
    private long time; //服务器Unix时间戳
    private int vipFlag; //1大V ; 0：普通用户
    private String sign; //个性签名
    private String type; //用户类型
    private int gender;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getVipFlag() {
        return vipFlag;
    }

    public void setVipFlag(int vipFlag) {
        this.vipFlag = vipFlag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
