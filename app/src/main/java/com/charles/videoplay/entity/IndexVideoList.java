package com.charles.videoplay.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Charles on 2016/10/10.
 */

public class IndexVideoList implements Serializable {
    private static final long serialVersionUID = -1353850465936313952L;

    private int errcode;

    private List<VideoType> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }


    public List<VideoType> getData() {
        return data;
    }

    public void setData(List<VideoType> data) {
        this.data = data;
    }

}
