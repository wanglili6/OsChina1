package com.bestteam.oschina.net.okhttp.interceptor;

import android.net.Uri;
import android.os.Handler;


import com.bestteam.oschina.activity.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by lxj on 2016/12/18.
 */

public class OKHttp3Helper {

    private static OKHttp3Helper mInstance = new OKHttp3Helper();
    private final OkHttpClient okhttp;
    private Handler handler = new Handler();


    private OKHttp3Helper() {
        //1.创建OkHttpClient对象
        okhttp = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)//
                .writeTimeout(10, TimeUnit.SECONDS)//
                .retryOnConnectionFailure(true)//重试的操作
                //数据缓存的相关设置
//                .cache(new Cache(MyApplication.getContext().getCacheDir(),10 * 1024 * 1024))//设置数据缓存的
                //公共的Cookie的设置
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.context)))
                .addInterceptor(new CacheIntercept())
                .addInterceptor(new CommonHeaderInterceptor())
                .addInterceptor(new LoggingInterceptor())//处理请求前后的打印内容
                .build();
    }

    public static OKHttp3Helper create() {
        return mInstance;
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, HttpCallback callback) {
        get(url, null, null, callback);
    }

    /***
     * 执行get请求
     *url:请求地址
     * headers: 添加头信息
     * params: 请求参数
     * HttpCallback: 响应结果
     * @param url
     * @param callback
     */
    public void get(String url, Map<String, String> headers, Map<String, String> params, final HttpCallback callback) {
        //创建请求对象Request
        Request.Builder builder = new Request.Builder();

        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //请求参数: http://www.xxx.com?username=ywf&pwd=123
         Uri.Builder uriBuilder = null;
        if (url != null) {
            uriBuilder = Uri.parse(url).buildUpon();
            if( params != null){
                Set keys = params.keySet();
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    uriBuilder.appendQueryParameter(key, params.get(key));
                }
            }

        }

        builder.url(uriBuilder.toString());
        builder.get();//设置请求方式是get
        Request request = builder.build();

        //执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //将响应体的数据转为string
                final String result = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }
                });
            }
        });
    }

    /**
     * 提交表单发送请求
     *
     * @param url
     * @param headers
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> headers, Map<String, String> params, final HttpCallback callback) {
        //创建请求体,formBody表单
        FormBody.Builder formBoadybuilder = new FormBody.Builder();
        if (params != null) {
            Iterator i$ = params.keySet().iterator();

            while (i$.hasNext()) {
                String key = (String) i$.next();
                formBoadybuilder.add(key, params.get(key));
            }
        }
        FormBody formBody = formBoadybuilder.build();

        //创建请求对象Request
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(formBody);//设置请求方式是get
        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        //执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取http相应体数据
                ResponseBody body = response.body();
                //将响应体的数据转为string
                final String string = body.string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    /**
     * 发送文件请求
     *
     * @param url
     * @param headers
     * @param params
     * @param callback
     */
    public void upload(String url, Map<String, String> headers, Map<String, Object> params, final HttpCallback callback) {
        //创建请求体
        MultipartBody.Builder multiPartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        ;
        if (params != null && !params.isEmpty()) {
            Iterator i$ = params.keySet().iterator();
            while (i$.hasNext()) {
                String key = (String) i$.next();
                Object o = params.get(key);
                if (o instanceof File) {
                    File  file = (File)o;
                    RequestBody fileBody = RequestBody.create(null, file);//MediaType.parse("application/octet-stream")
                    multiPartBodyBuilder.addFormDataPart(key, file.getName(), fileBody);
                } else {
                    multiPartBodyBuilder.addFormDataPart(key, (String) o);
//                    multiPartBodyBuilder.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"" + key + "\""}), RequestBody.create((MediaType) null, (String) params.get(key)));
                }
            }
        }
        //创建请求对象Request
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(multiPartBodyBuilder.build());//设置请求方式是post
        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        //3.执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取http相应体数据
                ResponseBody body = response.body();
                //将响应体的数据转为string
                final String string = body.string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    public interface HttpCallback {
        void onSuccess(String data);

        void onFail(Exception e);
    }

}
