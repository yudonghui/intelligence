package com.ydh.intelligence.networks;

import android.util.Log;

import com.google.gson.Gson;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.ServersApi;
import com.ydh.intelligence.common.Constant;
import com.ydh.intelligence.interfaces.YdhInterface;
import com.ydh.intelligence.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date:2023/2/6
 * Time:13:52
 * author:ydh
 */
public class HttpClient {
    private static ServersApi serversApi = null;
    public static String URL = "https://api.openai.com";
    public static String BD_URL = "https://wenxin.baidu.com";
    private static String BASE_URL_TB = "https://eco.taobao.com/";//
    private static String BASE_URL_JD = "https://api.jd.com/";//
    private static String BASE_URL_SUPABASE = "https://iaoqbthyohbschdxnvya.supabase.co/";//
    private static ServersApi serversApiTb = null;
    private static ServersApi serversApiJd = null;
    private static ServersApi serversApiSupabase = null;
    public static long timeOut = 30000;//连接超时,30秒
    public static long timeOutLong = 60000;//连接超时,60秒
    public static String JD_MATERIAL_FORM = "jd.union.open.goods.jingfen.query";//京粉精选商品查询接口
    public static String JD_COMMON_GET = "jd.union.open.promotion.common.get";//网站/APP获取推广链接
    public static String JD_MATERIAL_SEARCH = "jd.union.open.goods.query";//关键词商品查询接口
    public static String JD_MATERIAL_DETAIL = "jd.union.open.goods.bigfield.query";//商品详情
    public static String JD_MATERIAL_QUERY = "jd.union.open.goods.material.query";//猜你喜欢商品推荐
    private static HttpClient instance;

    public static HttpClient getInstantce() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public static ServersApi getHttpApi() {
        if (serversApi == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("接口请求", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //对所有请求添加请求头
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            OkHttpClient.Builder builder = httpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOut, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamInterceptor());//请求头添加上token
            builder.addInterceptor(loggingInterceptor);

            serversApi = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build().create(ServersApi.class);
        }
        return serversApi;
    }

    public static ServersApi getHttpApiBd() {
        if (serversApi == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("接口请求", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //对所有请求添加请求头
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            OkHttpClient.Builder builder = httpClientBuilder.connectTimeout(timeOutLong, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOutLong, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamBdInterceptor());//请求头添加上token
            builder.addInterceptor(loggingInterceptor);

            serversApi = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BD_URL)
                    .build().create(ServersApi.class);
        }
        return serversApi;
    }
    public static ServersApi getHttpApiTb() {
        return getHttpApiTb(timeOut);
    }

    public static ServersApi getHttpApiJd() {
        return getHttpApiJd(timeOut);
    }

    public static ServersApi getHttpApiTb(long timeOut) {

        if (serversApiTb == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.startsWith("{")) {
                        // LogUtils.e("请求结果" + message);
                        LogUtils.e("请求结果" + message);
                    } else if (message.contains("-->") && message.contains("https://")) {
                        LogUtils.e("请求接口" + message);
                    } else if (message.contains("app_key")) {
                        LogUtils.e("请求参数" + message.replace("&", "\n").replace("=", ":"));
                    }
                }
            });

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            //对所有请求添加请求头
            OkHttpClient.Builder builder = httpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOut, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamInterceptor());//请求头添加上token
            if (Constant.ISSUE) {
                builder.addInterceptor(loggingInterceptor);
            }

            serversApiTb = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL_TB)
                    .build().create(ServersApi.class);
        }
        return serversApiTb;
    }

    public static ServersApi getHttpApiJd(long timeOut) {

        if (serversApiJd == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.contains("trace-id")) {
                        LogUtils.e("返回结果: " + message);
                    }
                    if (message.startsWith("{")) {
                        // LogUtils.e("请求结果" + message);
                        LogUtils.e("请求结果" + message);
                    } else if (message.contains("-->") && message.contains("https://")) {
                        LogUtils.e("请求接口" + message);
                    } else if (message.contains("app_key")) {
                        LogUtils.e("请求参数" + message.replace("&", "\n").replace("=", ":"));
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            //对所有请求添加请求头
            OkHttpClient.Builder builder = httpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOut, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamInterceptor());//请求头添加上token
            if (Constant.ISSUE) {
                builder.addInterceptor(loggingInterceptor);
            }
            serversApiJd = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL_JD)
                    .build().create(ServersApi.class);
        }
        return serversApiJd;
    }
    public static ServersApi getHttpSupabase() {

        if (serversApiSupabase == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.contains("trace-id")) {
                        LogUtils.e("返回结果: " + message);
                    }
                    if (message.startsWith("{")) {
                        // LogUtils.e("请求结果" + message);
                        LogUtils.e("请求结果" + message);
                    } else if (message.contains("-->") && message.contains("https://")) {
                        LogUtils.e("请求接口" + message);
                    } else if (message.contains("app_key")) {
                        LogUtils.e("请求参数" + message.replace("&", "\n").replace("=", ":"));
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            //对所有请求添加请求头
            OkHttpClient.Builder builder = httpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOut, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamSupabaseInterceptor());//请求头添加上token
            if (Constant.ISSUE) {
                builder.addInterceptor(loggingInterceptor);
            }
            serversApiSupabase = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL_SUPABASE)
                    .build().create(ServersApi.class);
        }
        return serversApiSupabase;
    }
    public void post(String url, FormBody body, final YdhInterface mListener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("referer", " https://uutool.cn/text2voice/")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mListener != null) {
                    mListener.onSuccess(call.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                    //处理UI需要切换到UI线程处理
                }
            }
        });
    }

    public FormBody getFormBody(HashMap<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    //map参数请求数据
    public static RequestBody getRequestBody(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        Log.e("Authorization", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.AUTHORIZATION));//打印map数据日志
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }
}
