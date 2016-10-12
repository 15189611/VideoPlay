package com.charles.videoplay.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Charles on 2016/10/12.
 */

public class VideoDetailsList implements Serializable {

    private static final long serialVersionUID = 4742068133139025586L;

    private int errcode;

    private String errmsg;

    private List<VedioDetails> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrcode(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<VedioDetails> getData() {
        return data;
    }

    public void setData(List<VedioDetails> data) {
        this.data = data;
    }
}
