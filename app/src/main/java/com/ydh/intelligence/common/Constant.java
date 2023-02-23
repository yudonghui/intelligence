package com.ydh.intelligence.common;

/**
 * Date:2023/2/16
 * Time:15:51
 * author:ydh
 */
public class Constant {
    /**
     * 测试标识
     */
    public final static boolean ISSUE = true;//正式发布时请改成false
    /**
     * 检测app是否需要更新
     */
    public final static String UPDATE_URL = "http://upgrade.app.mylikesh.cn/coupons/android/check";
    public final static String DOWNLOAD_URL = "http://d.maps9.com/52f6";
    /**
     * 百度key
     */
    public final static String BD_API_KEY = "y37Uuzxi2V8Kg8ENEAL1gDMC1Gi1Q8Zl";
    public final static String BD_SECRET_KEY = "naaFc2lQubvKno2OLVbjHEja8Gz1zzH4";

    public final static String ACTION_CLICK = "action_click";
    public final static String ACTION_SCROLL = "action_scroll";
    public final static String ACTION_BACK = "action_back";

    /**
     * 淘宝
     * 淘宝客id
     * mm_529810053_2700800303_114443250154
     * 529810053 淘宝联盟账号的ID
     * 2700800303 备案推广类型的ID
     * 114443250154 推广位的ID
     */
    public final static String APP_KEY_TB = "33976106";//淘宝
    public final static String APP_SECRET_TB = "a617a035736f95becd2a9c8db14962cf";//密钥
    public final static String ADZONE_ID = "114443250154";//推广位  mm_529810053_2700800303_114443250154
   /*public final static String APP_SECRET_TB = "73aea5ebcbeedec91aa6ff10b3a8c416";//密钥(顾)
    public final static String APP_KEY_TB = "28252696";//淘宝(顾)
    public final static String ADZONE_ID = "109915700451";//推广位(顾)*/
    /**
     * 京东
     */
    public final static String APP_KEY_JD = "8724dd097e248b402993f9550b287e0d";//京东
    public final static String APP_SECRET_JD = "c7b290141c2145ba9d052a563b5a03b6";//密钥
    public final static String APP_ID = "4100876757";//APP ID

    /**
     * 跳转
     */
    public final static int REQUEST_CODE1 = 1001;
    public final static int REQUEST_CODE2 = 1002;
    public final static int REQUEST_CODE3 = 1003;

    public final static int RESULT_CODE1 = 2001;
    public final static int RESULT_CODE2 = 2002;
    public final static int RESULT_CODE3 = 2003;
}
