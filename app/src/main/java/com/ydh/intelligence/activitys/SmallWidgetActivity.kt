package com.ydh.intelligence.activitys

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.ydh.intelligence.NewAppWidget
import com.ydh.intelligence.R
import com.ydh.intelligence.utils.CommonUtil

class SmallWidgetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_small_widget)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun addCommonWidget(view: View?) {
        addSmallWidget(NewAppWidget::class.java)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun addSmallWidget(widgetClass: Class<*>?) {
        val instance = AppWidgetManager.getInstance(mContext)
        val componentName = ComponentName(mContext, widgetClass!!)
        val appWidgetIds = instance.getAppWidgetIds(componentName)
        if (appWidgetIds != null && appWidgetIds.isNotEmpty()) {
            CommonUtil.showToast("组件已经添加过")
            return
        }
        if (instance.isRequestPinAppWidgetSupported) {
            instance.requestPinAppWidget(componentName, null, null)
        }
    }
}