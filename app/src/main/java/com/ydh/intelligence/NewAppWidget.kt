package com.ydh.intelligence

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 * 支付宝常用 URL Scheme
 * #支付宝付款（跳转支付宝转账向商家付款界面）
 * <p>
 * alipay://platformapi/startapp?appId=20000056
 * <p>
 * 支付宝记账（跳转支付宝记账界面）
 * <p>
 * alipay://platformapi/startapp?appId=20000168
 * <p>
 * 支付宝滴滴
 * <p>
 * alipay://platformapi/startapp?appId=20000778
 * <p>
 * 支付宝蚂蚁森林
 * <p>
 * alipay://platformapi/startapp?appId=60000002
 * <p>
 * 支付宝转账（跳转支付宝转账界面）
 * <p>
 * alipayqr://platformapi/startapp?saId=20000116
 * <p>
 * 支付宝手机充值（跳转支付宝手机充值页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=10000003
 * <p>
 * 支付宝卡包（跳转支付宝卡包页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=20000021
 * <p>
 * 支付宝吱口令（跳转支付宝吱口令页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=20000085
 * <p>
 * 支付宝芝麻信用（跳转支付宝芝麻信用页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=20000118
 * <p>
 * 支付宝红包（跳转支付宝红包页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=88886666
 * <p>
 * 支付宝爱心（跳转支付宝献爱心页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=1000009
 * <p>
 * 支付宝升级页面（跳转支付宝升级页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000066
 * <p>
 * 支付宝滴滴打的（跳转支付宝滴滴打的页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000130
 * <p>
 * 支付宝客服（跳转支付宝客服页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000691
 * <p>
 * 支付宝生活（跳转支付宝生活页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000193
 * <p>
 * 支付宝生活号（跳转支付宝生活号页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000101
 * <p>
 * 支付宝记账（跳转支付宝记账页面）
 * <p>
 * alipayqr://platformapi/startapp?saId=2000168
 * <p>
 * 支付宝收款码
 * <p>
 * alipayqr://platformapi/startapp?saId=20000123
 * <p>
 * 支付宝扫一扫（跳转支付宝扫一扫）
 * <p>
 * alipayqr://platformapi/startapp?saId=10000007
 * <p>
 * 支付宝内功能页面 URL Scheme
 * alipays://platformapi/startapp?appId=20000003  #支付宝账单
 * <p>
 * alipays://platformapi/startapp?appId=20000008  #支付宝登陆界面
 * <p>
 * alipays://platformapi/startapp?appId=20000014  #支付宝我的银行卡
 * <p>
 * alipays://platformapi/startapp?appId=20000019  #支付宝余额
 * <p>
 * alipays://platformapi/startapp?appId=20000032  #支付宝余额宝
 * <p>
 * alipays://platformapi/startapp?appId=20000120  #支付宝饿了么
 * <p>
 * alipays://platformapi/startapp?appId=20000134  #支付宝自选股
 * <p>
 * alipays://platformapi/startapp?appId=20000160  #支付宝会员
 * <p>
 * alipays://platformapi/startapp?appId=20000165  #支付宝定期
 * <p>
 * alipays://platformapi/startapp?appId=20000166  #支付宝通讯录
 * <p>
 * alipays://platformapi/startapp?appId=20000193  #支付宝生活缴费
 * <p>
 * alipays://platformapi/startapp?appId=20000199  #支付宝花呗
 * <p>
 * alipays://platformapi/startapp?appId=20000218  #支付宝黄金
 * <p>
 * alipays://platformapi/startapp?appId=20000243  #支付宝总资产
 * <p>
 * alipays://platformapi/startapp?appId=20000754  #支付宝我的快递
 * <p>
 * alipays://platformapi/startapp?appId=20000793  #支付宝基金
 * <p>
 * alipays://platformapi/startapp?appId=20000835  #支付宝语音助手
 * <p>
 * alipays://platformapi/startapp?appId=20000987  #支付宝充值中心
 * <p>
 * alipays://platformapi/startapp?appId=20001003  #支付宝搜索
 * <p>
 * alipays://platformapi/startapp?appId=60000002  #支付宝蚂蚁森林
 * <p>
 * alipays://platformapi/startapp?appId=60000057  #支付宝流量钱包
 * <p>
 * alipays://platformapi/startapp?appId=60000081  #支付宝商家服务
 * <p>
 * alipays://platformapi/startapp?appId=60000155  #支付宝共享单车
 * <p>
 * alipays://platformapi/startapp?appId=66666674  #支付宝蚂蚁庄园
 * <p>
 * alipays://platformapi/startapp?appId=66666708  #支付宝余利宝
 * <p>
 * alipays://platformapi/startapp?appId=68687009  #支付宝惠支付
 * <p>
 * alipays://platformapi/startapp?appId=68687131  #支付宝养老
 * <p>
 * alipays://platformapi/startapp?appId=77700124  #支付宝余额宝
 * <p>
 * alipays://platformapi/startapp?appId=2019072665939857  #支付宝随申办
 * <p>
 * 参考资料：https://zhuanlan.zhihu.com/p/410800014
 */
class NewAppWidget : AppWidgetProvider() {
    val WECHAT_APP_PACKAGE = "com.tencent.mm" // 微信

    val WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI" // 微信

    val WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut" // 微信
    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                        appWidgetId: Int) {
        var views = RemoteViews(context.packageName, R.layout.new_app_widget)
      /*  if (!isAliPayInstalled(context)) {
            views.setTextViewText(R.id.tv_alipay, "未安装")
        }
        if (!isWxInstall(context)) {
            views.setTextViewText(R.id.tv_wx, "未安装")
        }*/
        val uriAliScan = Uri.parse("alipayqr://platformapi/startapp?saId=10000007") //支付宝扫一扫
        val intentAliScan = Intent(Intent.ACTION_VIEW, uriAliScan)
        val pendingIntentAliScan = PendingIntent.getActivity(context, 0, intentAliScan, 0)
        views.setOnClickPendingIntent(R.id.ll_alipay_scan, pendingIntentAliScan)

        val uriAliPay = Uri.parse("alipayqr://platformapi/startapp?saId=20000056") //支付宝付款码
        val intentAliPay = Intent(Intent.ACTION_VIEW, uriAliPay)
        val pendingIntentAliPay = PendingIntent.getActivity(context, 0, intentAliPay, 0)
        views.setOnClickPendingIntent(R.id.ll_alipay_pay, pendingIntentAliPay)

        val uriAliCode = Uri.parse("alipayqr://platformapi/startapp?saId=2019072665939857&page=pages%2Fside-code%2Fside-code") //支付宝随身码
        val intentAliCode = Intent(Intent.ACTION_VIEW, uriAliCode)
        val pendingIntentAliCode = PendingIntent.getActivity(context, 0, intentAliCode, 0)
        views.setOnClickPendingIntent(R.id.ll_alipay_code, pendingIntentAliCode)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.putExtra(WECHAT_OPEN_SCANER_NAME, true)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val componentName = ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS)
        intent.component = componentName
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        views.setOnClickPendingIntent(R.id.ll_wx_scan, pendingIntent)

        val intentWx = Intent(Intent.ACTION_VIEW)
        intentWx.putExtra(WECHAT_OPEN_SCANER_NAME, true)
        intentWx.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val componentNameWx = ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS)
        intentWx.component = componentNameWx
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        @SuppressLint("WrongConstant") val pendingIntentWx = PendingIntent.getActivity(context, 0, intentWx, 1)
        views.setOnClickPendingIntent(R.id.ll_wx_pay, pendingIntentWx)

        val uriWxCode = Uri.parse("weixin://app/wx58164a91f1821369/jumpWxa/?userName=gh_d4acc9de8978&page=pages%2Fside-code%2Fside-code") //微信随身码
        val intentWxCode = Intent(Intent.ACTION_VIEW, uriWxCode)
        //intentWxCode.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentWxCode.component = componentNameWx
        val pendingIntentWxCode = PendingIntent.getActivity(context, 0, intentWxCode, 0)
        views.setOnClickPendingIntent(R.id.ll_wx_code, pendingIntentWxCode)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        Log.e("CommonWidget：", "onUpdate")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action
        Log.e("CommonWidget：", "onReceive-------$action")
    }

    override fun onEnabled(context: Context?) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context?) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * 是否安装了微信
     */
    fun isWxInstall(context: Context): Boolean {
        val packageManager = context.packageManager
        val installedPackages = packageManager.getInstalledPackages(0)
        if (installedPackages != null) {
            for (i in installedPackages.indices) {
                val packageName = installedPackages[i].packageName
                if (packageName == WECHAT_APP_PACKAGE) { //微信
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    fun isQQClientAvailable(context: Context): Boolean {
        val packageManager = context.packageManager
        val installedPackages = packageManager.getInstalledPackages(0)
        if (installedPackages != null) {
            for (i in installedPackages.indices) {
                val pn = installedPackages[i].packageName
                if (pn.equals("com.tencent.qqlite", ignoreCase = true) || pn.equals("com.tencent.mobileqq", ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 是否安装了支付宝
     */
    fun isAliPayInstalled(context: Context): Boolean {
        val uri = Uri.parse("alipays://platformapi/startApp")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val componentName = intent.resolveActivity(context.packageManager)
        return componentName != null
    }

    /**
     * sina
     *
     *
     * 判断是否安装新浪微博
     */
    fun isSinaInstalled(context: Context): Boolean {
        val packageManager = context.packageManager // 获取packagemanager
        val installedPackages = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (installedPackages != null) {
            for (i in installedPackages.indices) {
                val pn = installedPackages[i].packageName
                if (pn == "com.sina.weibo") {
                    return true
                }
            }
        }
        return false
    }

    fun getCurrentDate(): String? {
        val date = Date()
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        return dateFormat.format(date)
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}