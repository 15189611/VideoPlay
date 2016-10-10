package com.kingnet.KYVideo.okhttp;

import com.kingnet.KYVideo.okhttp.builder.GetBuilder;
import com.kingnet.KYVideo.okhttp.builder.HeadBuilder;
import com.kingnet.KYVideo.okhttp.builder.OtherRequestBuilder;
import com.kingnet.KYVideo.okhttp.builder.PostFileBuilder;
import com.kingnet.KYVideo.okhttp.builder.PostFormBuilder;
import com.kingnet.KYVideo.okhttp.builder.PostStringBuilder;
import com.kingnet.KYVideo.okhttp.callback.Callback;
import com.kingnet.KYVideo.okhttp.request.RequestCall;
import com.kingnet.KYVideo.okhttp.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by charles on 2016/5/25.
 */
public class OkHttpUtils
{
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        return initClient(null);
    }


    public Executor getDelivery()
    {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }
     
    public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post()
    {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put()
    {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head()
    {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete()
    {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch()
    {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    @SuppressWarnings("unchecked")
	public void execute(final RequestCall requestCall, Callback<Object> callback)
    {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback<Object> finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                sendFailResultCallback(call, e, finalCallback);
            }
            @Override
            public void onResponse(final Call call, final Response response)
            {
                if (call.isCanceled())
                {
                    sendFailResultCallback(call, new IOException("Canceled!"), finalCallback);
                    return;
                }

                if (!finalCallback.validateReponse(response))
                {
                    sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback);
                    return;
                }

                try
                {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalCallback);

                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback<Object> callback)
    {
        if (callback == null) return;

        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback<Object> callback)
    {
        if (callback == null) return;
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

