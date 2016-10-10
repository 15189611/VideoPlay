package com.kingnet.KYVideo.okhttp.builder;


import com.kingnet.KYVideo.okhttp.request.PostStringRequest;
import com.kingnet.KYVideo.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by Charles on 16/05/25.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType).build();
    }


}
