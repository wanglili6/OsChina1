package com.bestteam.oschina.net.okhttp.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ywf on 2017/1/4.
 */
public class CacheIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
            CacheControl FORCE_CACHE1 = new CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)//设置缓存数据的有效期, 超出有效期,框架自动清空缓存
                    .maxStale(1, TimeUnit.DAYS)//设置缓存数据过时时间,超出有效期,框架自动清空缓存数据
                    .build();
            request = request.newBuilder()
                    .cacheControl(FORCE_CACHE1)
                    .build();

            Response response = chain.proceed(request);

            return response;
//        }
    }


}
