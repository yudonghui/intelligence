package com.ydh.intelligence;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.revenuecat.purchases.Purchases;
import com.revenuecat.purchases.PurchasesConfiguration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Date:2023/2/6
 * Time:14:07
 * author:ydh
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Purchases.setDebugLogsEnabled(true);
        Purchases.configure(new PurchasesConfiguration.Builder(this, "goog_PMYDolMYkMHEmiYwgSyzbLiCGhi").build());
        initUpdate();
    }


    private void initUpdate() {
       /* XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置true只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                // .param("versionCode", UpdateUtils.getVersionCode(this))  //设置默认公共请求参数
                //  .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() { //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {    //对不同错误进行处理
                            if (Constant.ISSUE == true)
                                Toast.makeText(getContext(), "版本" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);                                          //这个必须初始化*/
    }
    public static Context getContext() {
        return context;
    }
    //初始化列表上下拉刷新加载控件样式
    static {
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setEnableLastTime(false);
                classicsHeader.setPrimaryColor(Color.TRANSPARENT);
//                classicsHeader.setPrimaryColor(context.getResources().getColor(R.color.green_50));
                classicsHeader.setAccentColor(context.getResources().getColor(R.color.color_theme));
                classicsHeader.setDrawableMarginRight(10);
                classicsHeader.setSpinnerStyle(SpinnerStyle.Scale);

                layout.setRefreshHeader(classicsHeader);
                layout.setPrimaryColorsId(R.color.color_theme, R.color.color_white);//全局设置主题颜色
                layout.setHeaderHeight(30);
                layout.setFooterHeight(40);
            }
        });
    }
}
