package com.ydh.intelligence.activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ydh.intelligence.common.permission.PermissionListener;
import com.ydh.intelligence.common.permission.PermissionSetting;
import com.ydh.intelligence.common.permission.PermissionUtils;
import com.ydh.intelligence.common.permission.YuAlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2023/2/16
 * Time:15:53
 * author:ydh
 */
public class PermissionActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //申请权限之后需要执行的方法。需要的时候再重写
    public void permissonExcute(int requestCode) {
    }

    //申请权限没有全部允许，让去设置界面自己去设置，点击了不
    public void permissonCancel() {

    }

    private PermissionListener mlistener;

    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    private void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        mlistener = listener;
        Activity topActivity = this;
        if (topActivity == null) {
            return;
        }
        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), perRequestCode);
        } else {  //为空，则已经全部授权
            listener.onGranted();
        }
    }

    public int perRequestCode;


    /**
     * 权限申请结果
     *
     * @param requestCode  请求码
     * @param permissions  所有的权限集合
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == perRequestCode) {
            if (grantResults.length > 0) {
                //被用户拒绝的权限集合
                List<String> deniedPermissions = new ArrayList<>();
                //用户通过的权限集合
                List<String> grantedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    //获取授权结果，这是一个int类型的值
                    int grantResult = grantResults[i];

                    if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                        String permission = permissions[i];
                        deniedPermissions.add(permission);
                    } else {  //用户同意的权限
                        String permission = permissions[i];
                        grantedPermissions.add(permission);
                    }
                }

                if (deniedPermissions.isEmpty()) {  //全部申请成功
                    mlistener.onGranted();
                } else {  //部分权限申请成功
                    //回调授权成功的接口
                    mlistener.onDenied(deniedPermissions);
                    //回调授权失败的接口
                    mlistener.onGranted(grantedPermissions);
                }
            }
        }
    }


    public void requestPermission(final String[] persssions, int requestCode) {
        this.perRequestCode = requestCode;
        final PermissionSetting ps = new PermissionSetting(this);
        requestRunTimePermission(persssions
                , new PermissionListener() {
                    @Override
                    public void onGranted() {//全部申请成功的回调
                        permissonExcute(perRequestCode);
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) {//部分成功权限

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {//部分未成功权限
                        String message = "我们需要以下权限，请在设置中为我们开启：" + "\n" + PermissionUtils.getName(deniedPermission);
                        YuAlertDialog.newBuilder(PermissionActivity.this)
                                .setCancelable(false)
                                .setTitle("提示")
                                .setMessage(message)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.execute(6667);
                                    }
                                })
                                .setNegativeButton("不", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.cancel();
                                        permissonCancel();
                                    }
                                })
                                .show();
                    }
                });
    }
}
