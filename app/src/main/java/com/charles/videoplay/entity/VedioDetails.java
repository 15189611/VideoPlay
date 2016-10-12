package com.charles.videoplay.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Charles on 2016/10/12.
 */

public class VedioDetails implements Serializable {
    private static final long serialVersionUID = -6985456526932154246L;

    private int commentCount; //视频评论数
    private List<VedioComment> commentList; //视频评论列表
    private String des; //视频描述
    private int favorCount; //视频喜欢数
    private int favorFlag; //视频是否喜欢
    private int userRelation; //是否关注
    private String imgUrl; //视频缩略图url
    private String mp4Url; //视频url
    private int playCount; //视频播放数
    private long time; //视频发布时间
    private int timelen; //时长
    private String title; //视频标题
    private int uid; //uid
    private String userHead; //人员头像url
    private String userNick; //人员名称
    private int vid; //视频id
    private String descriptionOP;
    private String sign;
    public String getDescriptionOP() {
        return descriptionOP;
    }
    public void setDescriptionOP(String descriptionOP) {
        this.descriptionOP = descriptionOP;
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
    public int getFavorFlag() {
        return favorFlag;
    }
    private int vipFlag;

    /** uid */
    public int getUid() {
        return uid;
    }
    /** uid */
    public void setUid(int uid) {
        this.uid = uid;
    }
    /** 人员头像url */
    public String getUserHead() {
        return userHead;
    }
    /** 人员头像url */
    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
    /** 人员名称 */
    public String getUserNick() {
        return userNick;
    }
    /** 人员名称 */
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    /** 视频id */
    public int getVid() {
        return vid;
    }
    /** 视频id */
    public void setVid(int vid) {
        this.vid = vid;
    }
    /** 视频喜欢数 */
    public int getFavorCount() {
        return favorCount;
    }
    /** 视频喜欢数 */
    public void setFavorCount(int favorCount) {
        this.favorCount = favorCount;
    }
    /** 视频喜欢数+1 */
    public void setFavorCountAdd() {
        this.favorCount++;
    }
    /** 视频喜欢数-1 */
    public void setFavorCountReduce() {
        this.favorCount--;
    }
    /** 视频播放数 */
    public int getPlayCount() {
        return playCount;
    }
    /** 视频播放数 */
    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }	/** 视频播放数增加 */
    public void setPlayCountAdd() {
        this.playCount=this.playCount++;
    }
    /** 视频缩略图url */
    public String getImgUrl() {
        return imgUrl;
    }
    /** 视频缩略图url */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    /** 视频标题 */
    public String getTitle() {
        return title;
    }
    /** 视频标题 */
    public void setTitle(String title) {
        this.title = title;
    }
    /** 视频描述 */
    public String getDes() {
        return des;
    }
    /** 视频描述 */
    public void setDes(String des) {
        this.des = des;
    }
    /** 视频是否喜欢 1 = 喜欢; 0 = 不喜欢*/
    public int isFavorFlag() {
        return favorFlag;
    }
    /** 视频是否喜欢 1 = 喜欢; 0 = 不喜欢 */
    public void setFavorFlag(int favorFlag) {
        this.favorFlag = favorFlag;
    }
    /** 视频url */
    public String getMp4Url() {
        return mp4Url;
    }
    /** 视频url */
    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }
    /** 视频评论数 */
    public int getCommentCount() {
        return commentCount;
    }
    /** 视频评论数 */
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    /** 视频发布时间 */
    public long getTime() {
        return time;
    }
    /** 视频发布时间 */
    public void setTime(long time) {
        this.time = time;
    }
    /** 视频时长 */
    public int getTimelen() {
        return timelen;
    }
    /** 视频时长 */
    public void setTimelen(int timelen) {
        this.timelen = timelen;
    }
    /** 是否关注1 = 关注; 0 = 不关注 */
    public int getUserRelation() {
        return userRelation;
    }
    /** 是否关注 1 = 关注; 0 = 不关注*/
    public void setUserRelation(int userRelation) {
        this.userRelation = userRelation;
    }
    /** 视频评论列表 */
    public List<VedioComment> getCommentList() {
        return commentList;
    }
    /** 视频评论列表 */
    public void setCommentList(List<VedioComment> commentList) {
        this.commentList = commentList;
    }

}
