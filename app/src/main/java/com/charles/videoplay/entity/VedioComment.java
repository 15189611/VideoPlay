package com.charles.videoplay.entity;

import java.io.Serializable;

/**
 * Created by Charles on 2016/10/12.
 */

public class VedioComment implements Serializable {
    private static final long serialVersionUID = 3342346079463204108L;
    private int cid; //评论id
    private int uid; //评论人员id
    private String nick; //评论人员姓名
    private String head; //评论人员头像
    private String content; //评论内容
    private long time; //评论时间

    /**
     * 评论id
     */
    public int getCid() {
        return cid;
    }

    /**
     * 评论id
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * 评论人员id
     */
    public int getUid() {
        return uid;
    }

    /**
     * 评论人员id
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * 评论人员姓名
     */
    public String getNick() {
        return nick;
    }

    /**
     * 评论人员姓名
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * 评论人员头像
     */
    public String getHead() {
        return head;
    }

    /**
     * 评论人员头像
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 评论时间
     */
    public long getTime() {
        return time;
    }

    /**
     * 评论时间
     */
    public void setTime(long time) {
        this.time = time;
    }
}