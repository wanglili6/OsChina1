package com.bestteam.oschina.net.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ywf on 2016/8/4.
 */
public class CommonHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest = request.newBuilder()
                .addHeader("Version", "")
                .addHeader("os", "Android")
                .build();
        Response response = chain.proceed(compressedRequest);
        return response;
    }
}
