package com.ydh.intelligence.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ydh.intelligence.dialogs.CheckCopyDialog;
import com.ydh.intelligence.utils.ClipboardUtils;


/**
 * @author zhengluping
 * @date 2018/1/16
 */
public class BaseTaoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String clipboard = ClipboardUtils.getClipboard();
                if (!TextUtils.isEmpty(clipboard)) {
                    new CheckCopyDialog(mContext).show(clipboard);
                }
            }
        }, 1000);
    }
}
