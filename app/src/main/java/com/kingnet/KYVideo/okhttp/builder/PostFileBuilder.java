package com.kingnet.KYVideo.okhttp.builder;

import com.kingnet.KYVideo.okhttp.request.PostFileRequest;
import com.kingnet.KYVideo.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by Charles on 16/05/25.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType).build();
    }


}
