package com.ydh.intelligence.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.dialogs.EditeDialog;
import com.ydh.intelligence.services.FloatingService;
import com.ydh.intelligence.services.ScrollService;
import com.ydh.intelligence.utils.ClipboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoClickActivity extends BaseActivity {
    @BindView(R.id.rg_scroll)
    RadioGroup mRgScroll;
    @BindView(R.id.tv_input)
    TextView mTvInput;
    @BindView(R.id.ll_scroll)
    LinearLayout llScroll;
    @BindView(R.id.tv_location)
    TextView mTvLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_click);
        unBind = ButterKnife.bind(this);
        checkPermission();
        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 111);
        initListener();
    }
    private void initListener() {
        if (SPUtils.getCacheInt(SPUtils.FILE_USER, SPUtils.ORIENTATION) == 0) {
            mRgScroll.check(R.id.rb_up_down);
        } else {
            mRgScroll.check(R.id.rb_left_right);
        }
        mTvInput.setText(SPUtils.getCacheL(SPUtils.FILE_USER, SPUtils.DURATION) + "");
        mRgScroll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_up_down) {
                    SPUtils.setCacheInt(SPUtils.FILE_USER, SPUtils.ORIENTATION, 0);
                } else if (checkedId == R.id.rb_left_right) {
                    SPUtils.setCacheInt(SPUtils.FILE_USER, SPUtils.ORIENTATION, 1);
                }
            }
        });
        mTvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditeDialog(mContext, getString(R.string.hint_input_frequency), 1, new EditeDialog.EditInterface() {
                    @Override
                    public void onClick(String s) {
                        if (TextUtils.isEmpty(s)) return;
                        SPUtils.setCacheL(SPUtils.FILE_USER, SPUtils.DURATION, Long.parseLong(s));
                        mTvInput.setText(s);
                    }
                });
            }
        });
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName())), 0);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, R.string.hint_permission_fail, Toast.LENGTH_SHORT).show();

            } else {

            }
        }
    }

    public void buttonStart(View view) {
        Intent serviceFloat = new Intent(this, FloatingService.class);
        startService(serviceFloat);
    }

    public void buttonScroll(View view) {
        Intent serviceScroll = new Intent(this, ScrollService.class);
        startService(serviceScroll);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                mTvLocation.setText(x + "," + y);
                break;
            case MotionEvent.ACTION_UP:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                ClipboardUtils.setClipboard(nowX + "," + nowY);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}