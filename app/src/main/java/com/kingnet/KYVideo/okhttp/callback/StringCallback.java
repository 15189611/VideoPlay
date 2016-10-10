package com.kingnet.KYVideo.okhttp.callback;


import java.io.IOException;

import okhttp3.Response;

/**
 * Created by charles on 2016/05/25
 */
public abstract class StringCallback extends Callback<String>
{
    public StringCallback(){

    }

    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }

}


