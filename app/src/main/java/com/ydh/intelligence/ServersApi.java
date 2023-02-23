package com.ydh.intelligence;

import com.ydh.intelligence.entitys.BaseEntity;
import com.ydh.intelligence.entitys.ImgEntity;
import com.ydh.intelligence.entitys.MaterialContentEntity;
import com.ydh.intelligence.entitys.TaskIdEntity;
import com.ydh.intelligence.entitys.TbCodeEntity;
import com.ydh.intelligence.entitys.TbDetailEntity;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Date:2023/2/6
 * Time:13:54
 * author:ydh
 */
public interface ServersApi {
    //医生状态查询
    @POST("v1/completions")
    Call<CompletionEntity> completions(@Body RequestBody body);

    //获取token
    @POST("moduleApi/portal/api/oauth/token")
    Call<BaseEntity<Object>> bdToken(@Query("grant_type") String grantType, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    //获取taskId
    @POST("moduleApi/portal/api/rest/1.0/ernievilg/v1/txt2img")
    Call<BaseEntity<TaskIdEntity>> getTaskId(@Query("access_token") String accessToken, @Query("text") String text,
                                             @Query("style") String style, @Query("resolution") String resolution, @Query("num") String num);

    //获取图片链接
    @POST("moduleApi/portal/api/rest/1.0/ernievilg/v1/getImg")
    Call<BaseEntity<ImgEntity>> getImg(@Query("access_token") String accessToken, @Query("taskId") Long taskId);

    //
    @FormUrlEncoded
    @POST("router/rest")
    Call<MaterialContentEntity> getMaterailTb(@FieldMap Map<String, Object> paramsMap);

    //公用-淘口令生成
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbCodeEntity> getMaterailTbCode(@FieldMap Map<String, Object> paramsMap);

    //公用-淘宝客商品详情查询(简版)
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbDetailEntity> getMaterailTbDetail(@FieldMap Map<String, Object> paramsMap);

    //推广者-物料搜索 taobao.tbk.dg.material.optional
    @FormUrlEncoded
    @POST("router/rest")
    Call<MaterialContentEntity> getMaterailOptional(@FieldMap Map<String, Object> paramsMap);

    //公用-长链转短链
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbDetailEntity> getSpreadGet(@FieldMap Map<String, Object> paramsMap);

    //京粉精选商品查询接口
    @FormUrlEncoded
    @POST("routerjson")
    Call<ResponseBody> getMaterailJd(@FieldMap Map<String, Object> paramsMap);
}
