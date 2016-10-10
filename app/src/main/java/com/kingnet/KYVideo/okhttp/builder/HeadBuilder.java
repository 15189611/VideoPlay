package com.kingnet.KYVideo.okhttp.builder;

import com.kingnet.KYVideo.okhttp.OkHttpUtils;
import com.kingnet.KYVideo.okhttp.request.OtherRequest;
import com.kingnet.KYVideo.okhttp.request.RequestCall;

/*
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;
*/

/**
 * Created by charles on 16/05/25.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
