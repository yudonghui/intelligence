package com.ydh.intelligence;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ydh on 2022/7/20
 */
public class SPUtils {

    //用户信息文件
    public static final String FILE_USER = "cache_user";
    public static final String AUTHORIZATION = "authorization";
    public static final String MODELS = "models";
    public static final String CURRENT_TOKENS = "currentTokens";//当前最大请求max_tokens
    public static final String USE_TOKENS = "useTokens";//当前总共使用量
    public static final String BD_TOKEN = "bdToken";//百度获取token接口
    public static final String DURATION = "duration";
    public static final String ORIENTATION = "orientation";
    //存储信息，除非卸载否则一直存在
    public static final String FILE_DATA = "file_data";
    public static final String IS_FIRST = "is_first"; //是否第一次打开
    public static final String BD_HIS_SEARCH = "bd_his_search"; //历史搜索text-~style-~resolution-~taskId&&&

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String spNaem, String spValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(spNaem, spValue);
        editor.commit();
    }

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCacheL(String fileNaem, String spNaem, long spValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(spNaem, spValue);
        editor.commit();
    }

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCacheInt(String fileNaem, String spNaem, int spValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(spNaem, spValue);
        editor.commit();
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static String getCache(String fileNaem, String spNaem) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        String spValue = sp.getString(spNaem, "");
        return spValue;
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static long getCacheL(String fileNaem, String spNaem) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        long spValue = sp.getLong(spNaem, 0);
        return spValue;
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static int getCacheInt(String fileNaem, String spNaem) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        int spValue = sp.getInt(spNaem, 0);
        return spValue;
    }

    public static void clearCache(String fileName) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
