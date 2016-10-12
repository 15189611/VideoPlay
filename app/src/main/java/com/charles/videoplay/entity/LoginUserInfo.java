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
    /**
     * 	0：手机注册用户
     * 	1：QQ登录用户
     * 	2：微信登录用户
     * 	3：新浪微博登录用户
     */
    private String type; //用户类型
    private String AndroidNewestVerUrl; //android最新版本地址
    private String AndroidNewest; //android最新版本
    private int UpType; //0:不用升级；1:提示升级 2:强制升级
    private String UpTips; //升级提示描述(UpdateType为12时返回)
    private int TipUpCount; //建议升级的每天提示次数(UpdateType为1时返回)
    private String UpWord; //升级/立即升级
    /** 用户ID */
    public int getUid() {
        return uid;
    }
    /** 用户ID */
    public void setUid(int uid) {
        this.uid = uid;
    }
    /** 用户昵称 */
    public String getNick() {
        return nick;
    }
    /** 用户昵称 */
    public void setNick(String nick) {
        this.nick = nick;
    }
    /** 用户头像图片链接 */
    public String getHead() {
        return head;
    }
    /** 用户头像图片链接 */
    public void setHead(String head) {
        this.head = head;
    }
    /** 用户唯一识别Token */
    public String getToken() {
        return token;
    }
    /** 用户唯一识别Token */
    public void setToken(String token) {
        this.token = token;
    }
    /** 服务器Unix时间戳 */
    public long getTime() {
        return time;
    }
    /** 服务器Unix时间戳 */
    public void setTime(long time) {
        this.time = time;
    }
    /** 服务器Unix时间戳 */
    public int getVipFlag() {
        return vipFlag;
    }
    /** 服务器Unix时间戳 */
    public void setVipFlag(int vipFlag) {
        this.vipFlag = vipFlag;
    }
    /** 个性签名 */
    public String getSign() {
        return sign;
    }
    /** 个性签名 */
    public void setSign(String sign) {
        this.sign = sign;
    }
    /**
     * 用户类型
     * 	0：手机注册用户
     * 	1：QQ登录用户
     * 	2：微信登录用户
     * 	3：新浪微博登录用户
     */
    public String getType() {
        return type;
    }
    /**
     * 用户类型
     * 	0：手机注册用户
     * 	1：QQ登录用户
     * 	2：微信登录用户
     * 	3：新浪微博登录用户
     */
    public void setType(String type) {
        this.type = type;
    }
    /** 或Aandroid最新版本地址*/
    public String getAndroidNewestVerUrl() {
        return AndroidNewestVerUrl;
    }
    /** Android最新版本地址 */
    public void setAndroidNewestVerUrl(String AndroidNewestVerUrl) {
        this.AndroidNewestVerUrl = AndroidNewestVerUrl;
    }
    /** android最新版本 */
    public String getAndroidNewest() {
        return AndroidNewest;
    }
    /** android最新版本 */
    public void setAndroidNewest(String AndroidNewest) {
        this.AndroidNewest = AndroidNewest;
    }
    /** 0:不用升级；1:提示升级 2:强制升级 */
    public int getUpType() {
        return UpType;
    }
    /** 0:不用升级；1:提示升级 2:强制升级 */
    public void setUpType(int upType) {
        UpType = upType;
    }
    /** 升级提示描述(UpdateType为12时返回) */
    public String getUpTips() {
        return UpTips;
    }
    /** 升级提示描述(UpdateType为12时返回) */
    public void setUpTips(String upTips) {
        UpTips = upTips;
    }
    /** 建议升级的每天提示次数(UpdateType为1时返回) */
    public int getTipUpCount() {
        return TipUpCount;
    }
    /** 建议升级的每天提示次数(UpdateType为1时返回) */
    public void setTipUpCount(int tipUpCount) {
        TipUpCount = tipUpCount;
    }
    /** 升级/立即升级 */
    public String getUpWord() {
        return UpWord;
    }
    /** 升级/立即升级 */
    public void setUpWord(String upWord) {
        UpWord = upWord;
    }
}
