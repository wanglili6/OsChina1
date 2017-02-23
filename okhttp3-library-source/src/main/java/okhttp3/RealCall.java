/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

import static okhttp3.internal.platform.Platform.INFO;

final class RealCall implements Call {
  final OkHttpClient client;
  final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

  /** The application's original request unadulterated by redirects or auth headers. */
  final Request originalRequest;
  final boolean forWebSocket;

  // Guarded by this.
  private boolean executed;

  RealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
    this.client = client;
    this.originalRequest = originalRequest;
    this.forWebSocket = forWebSocket;
    this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client, forWebSocket);
  }

  @Override public Request request() {
    return originalRequest;
  }

  @Override public Response execute() throws IOException {
    synchronized (this) {
      if (executed) throw new IllegalStateException("Already Executed");
      executed = true;
    }
    captureCallStackTrace();
    try {
        //获取任务分发控制器，调用任务分发器的executed方法
        //executed该方法把RealCall对象添加到了任务队列中了
      client.dispatcher().executed(this);
      Response result = getResponseWithInterceptorChain();
      if (result == null) throw new IOException("Canceled");
      return result;
    } finally {
      client.dispatcher().finished(this);
    }
  }

  private void captureCallStackTrace() {
    Object callStackTrace = Platform.get().getStackTraceForCloseable("response.body().close()");
    retryAndFollowUpInterceptor.setCallStackTrace(callStackTrace);
  }

  @Override public void enqueue(Callback responseCallback) {
    synchronized (this) {
      if (executed) throw new IllegalStateException("Already Executed");
      executed = true;
    }
    captureCallStackTrace();
      //调用分发器的enqueue方法，将AsyncCall请求任务添加到任务队列中

      //注意：这里面的AsyncCall对象，它是继承与NamedRunnable， NamedRunnable有实现Runnable
      //需要大家注意responseCallback 这个回调接口，该回调接口是，程序员传递过来的回调，用于接收响应结果
    client.dispatcher().enqueue(new AsyncCall(responseCallback));
  }

  @Override public void cancel() {
    retryAndFollowUpInterceptor.cancel();
  }

  @Override public synchronized boolean isExecuted() {
    return executed;
  }

  @Override public boolean isCanceled() {
    return retryAndFollowUpInterceptor.isCanceled();
  }

  @SuppressWarnings("CloneDoesntCallSuperClone") // We are a final type & this saves clearing state.
  @Override public RealCall clone() {
    return new RealCall(client, originalRequest, forWebSocket);
  }

  StreamAllocation streamAllocation() {
    return retryAndFollowUpInterceptor.streamAllocation();
  }

  final class AsyncCall extends NamedRunnable {
    private final Callback responseCallback;

    AsyncCall(Callback responseCallback) {
      super("OkHttp %s", redactedUrl());
      this.responseCallback = responseCallback;
    }

    String host() {
      return originalRequest.url().host();
    }

    Request request() {
      return originalRequest;
    }

    RealCall get() {
      return RealCall.this;
    }

      //在工作线程中，执行任务
      // 注意： 该execute方法是执行在run方法中，请查看NamedRunnable类
    @Override protected void execute() {
      boolean signalledCallback = false;
      try {

          //根据拦截器链，处理网络请求
        Response response = getResponseWithInterceptorChain();
          //如果重试的请求被取消掉，调用 responseCallback.onFailure方法通知客户端，请求失败
        if (retryAndFollowUpInterceptor.isCanceled()) {
          signalledCallback = true;
          responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
        } else {
          signalledCallback = true;
            //请求成功以后，调用 responseCallback.onResponse方法，通知客户端，请求成功
          responseCallback.onResponse(RealCall.this, response);
        }
      } catch (IOException e) {
        if (signalledCallback) {
          // Do not signal the callback twice!
          Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
        } else {
            //请求出现异常，调用onFailure通过客户端，请求失败
          responseCallback.onFailure(RealCall.this, e);
        }
      } finally {
          //这次任务请求完成
        client.dispatcher().finished(this);
      }
    }
  }

  /**
   * Returns a string that describes this call. Doesn't include a full URL as that might contain
   * sensitive information.
   */
  String toLoggableString() {
    return (isCanceled() ? "canceled " : "")
        + (forWebSocket ? "web socket" : "call")
        + " to " + redactedUrl();
  }

  String redactedUrl() {
    return originalRequest.url().redact().toString();
  }

    //该方法用于通过拦截器链，处理网络请求
  Response getResponseWithInterceptorChain() throws IOException {
    // Build a full stack of interceptors.
      //将自定义的拦截器和okhttpClinet提供的拦截器，添加到栈中

      //为什么把封装这么多连接器呢？？？
      //由于 网络请求、缓存、透明压缩等功能每一个都统一了起来，功能都只是一个 Interceptor
    List<Interceptor> interceptors = new ArrayList<>();
      //将程序员自定义的拦截器，添加到集合中
      //也就是在配置 OkHttpClient 时设置的 interceptors添加到栈中
    interceptors.addAll(client.interceptors());

      //负责失败重试以及重定向的 RetryAndFollowUpInterceptor 拦截器，添加到集合中
    interceptors.add(retryAndFollowUpInterceptor);

    // 负责把用户构造的请求转换为发送到服务器的请求、把服务器返回的响应转换为用户友好的响应的 BridgeInterceptor；
    interceptors.add(new BridgeInterceptor(client.cookieJar()));
    //负责读取缓存直接返回、更新缓存的 CacheInterceptor
    interceptors.add(new CacheInterceptor(client.internalCache()));
      //负责和服务器建立连接的 ConnectInterceptor
    interceptors.add(new ConnectInterceptor(client));

    if (!forWebSocket) {
        //配置 OkHttpClient 时设置的 networkInterceptors
      interceptors.addAll(client.networkInterceptors());
    }

      //负责向服务器发送请求数据、从服务器读取响应数据的 CallServerInterceptor。
    interceptors.add(new CallServerInterceptor(forWebSocket));

      //创建一个拦截器链,该拦截器链负责和服务器实际通讯的，重定向、缓存的实现
    Interceptor.Chain chain = new RealInterceptorChain( interceptors, null, null, null, 0, originalRequest);
      //调用拦截器链，处理请求
    return chain.proceed(originalRequest);

      //通过拦截器链来实现和服务交互的实现方式，属于责任链设计模式
      //什么是责任链设计模式呢？
      /**
       * 它包含了一些命令对象和一系列的处理对象，
       * 每一个处理对象决定它能处理哪些命令对象，
       * 它也知道如何将它不能处理的命令对象传递给该链中的下一个处理对象。
       * 该模式还描述了往该处理链的末尾添加新的处理对象的方法。
       */
  }
}
