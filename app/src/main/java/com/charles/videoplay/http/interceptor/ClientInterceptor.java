package com.charles.videoplay.http.interceptor;


import com.charles.videoplay.BuildConfig;
import com.charles.videoplay.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ClientInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Charset", "UTF-8")
                .build();

        if (BuildConfig.DEBUG) {
            Response response = chain.proceed(request);
            return interceptLogWithResponse(response);
        }

        return chain.proceed(request);
    }

    private Response interceptLogWithResponse(Response response) {
        if (BuildConfig.DEBUG) {
            try {
                Response.Builder builder = response.newBuilder();
                Response clone = builder.build();
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        String resp = body.string();
                        Logger.i(resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return response;
    }
}
