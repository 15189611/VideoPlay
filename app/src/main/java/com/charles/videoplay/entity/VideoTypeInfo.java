package com.charles.videoplay.entity;

import java.io.Serializable;

/**
 * Created by Charles on 2016/10/10.
 */

public class VideoTypeInfo implements Serializable {

    private static final long serialVersionUID = -4814882527808205985L;

    private String des; //视频描述
    private String favorCount; //喜欢(点赞)数
    private int favorFlag; //是否粉丝
    private String imgUrl; //视频缩列图URL地址
    private String playCount; //视频播放次数
    private String title; //视频title
    private String uid; //视频用户ID
    private String userHead; //视频用户头像
    private String userNick; //视频用户昵称
    private int vid; //视频主键ID

    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public String getFavorCount() {
        return favorCount;
    }
    public void setFavorCount(String favorCount) {
        this.favorCount = favorCount;
    }
    public int getFavorFlag() {
        return favorFlag;
    }
    public void setFavorFlag(int favorFlag) {
        this.favorFlag = favorFlag;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getPlayCount() {
        return playCount;
    }
    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUserHead() {
        return userHead;
    }
    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
    public String getUserNick() {
        return userNick;
    }
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    public int getVid() {
        return vid;
    }
    public void setVid(int vid) {
        this.vid = vid;
    }
}

