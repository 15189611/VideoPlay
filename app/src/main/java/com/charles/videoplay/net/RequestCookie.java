package com.charles.videoplay.net;

import java.io.Serializable;

/**
 * Created by Charles on 2016/10/10.
 */

public class RequestCookie implements Serializable {

    private static final long serialVersionUID = -1353850465936313952L;
    private long ts; //	int	客户端unix时间戳，需要跟服务器时间做同步
    /**
     * 0：iphone
     * 1：android phone
     * 2：ipad
     * 3：android pad
     * 4：其他
     */
    private int cid; //	int	客户端id
    private String ver; //	string	客户端版本号，如1.0.3 ,1.1.0
    private String osver; //	string	操作系统版本，如8.0.0
    private String mid; //	string	设备id
    private String sum; //	string	MD5校验值

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getOsver() {
        return osver;
    }

    public void setOsver(String osver) {
        this.osver = osver;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}

