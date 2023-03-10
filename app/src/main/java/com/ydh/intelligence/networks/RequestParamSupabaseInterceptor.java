package com.ydh.intelligence.networks;


import com.ydh.intelligence.common.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对于请求头的定制化。
 * 这里需要将token作为一个头添加进来
 * 在http请求时，我们一般会在request header 或 response header 中看到”Connection:Keep-Alive”或 “Connection:close”，
 * 这里具体的含义是有关http 请求的是否保持长连接，即链接是否复用，每次请求是复用已建立好的请求，还是重新建立一个新的请求。
 * 而在实际生产环境中，可能会受到ECS/VM 的连接数限制而会对该配置项进行选择调配。例如VM规格只能支持65535个链接，如果链接不复用，
 * 都是短连接的话，并发过高的情况下，会直接把VM的连接数打满导致出现问题等，下面来详细说明下这个配置项。
 */
public class RequestParamSupabaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorised = originalRequest.newBuilder()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("apikey", Constant.API_KEY)
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .build();

        Response response = chain.proceed(authorised);//执行此次请求
        return response;
    }
}
