package com.charles.videoplay.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Charles on 2016/10/10.
 */

public class DataHomeVideoType  implements Serializable {

    private static final long serialVersionUID = 7687816343337235782L;

    private String imgUrl; //视频类型图片url链接
    private String name; //视频类型名称
    private List<VideoTypeInfo> vinfoList; //视频类型列表
    private int vtid; //视频类型ID

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<VideoTypeInfo> getVinfoList() {
        return vinfoList;
    }
    public void setVinfoList(List<VideoTypeInfo> vinfoList) {
        this.vinfoList = vinfoList;
    }
    public int getVtid() {
        return vtid;
    }
    public void setVtid(int vtid) {
        this.vtid = vtid;
    }
}
