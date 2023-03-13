package com.ydh.intelligence.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ydh.intelligence.R;
import com.ydh.intelligence.dialogs.PhotoDialog;
import com.ydh.intelligence.entitys.UserEntity;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.BitmapUtils;
import com.ydh.intelligence.utils.DeviceUtils;
import com.ydh.intelligence.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends PermissionActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_editor)
    ImageView ivEditor;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_device)
    LinearLayout llDevice;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    private Uri photoUri;
    private List<LocalMedia> selectList = new ArrayList<>();
    private final static String TAG = UserActivity.class.getSimpleName();
    private AlertDialog.Builder builder1;
    private AlertDialog alertDialog1;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        unBind = ButterKnife.bind(this);
        requestPermission(new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        initData();
    }

    @OnClick({R.id.iv_head, R.id.iv_editor, R.id.ll_device, R.id.ll_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.iv_editor:
                PhotoDialog photoDialog = new PhotoDialog(mContext);
                photoDialog.addListener(new PhotoDialog.OnPhotoInterface() {
                    @Override
                    public void camera() {
                        cameraPhoto();
                    }

                    @Override
                    public void selectPhoto() {
                        PictureSelector.create(UserActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .compress(true)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                    }
                });
                photoDialog.showDialog();
                break;
            case R.id.ll_device:
                startActivity(DeviceInfoActivity.class);
                break;
            case R.id.ll_password:
                break;
        }
    }

    private void initData() {
        showLoadingDialog();
        Call<List<UserEntity>> call = HttpClient.getHttpSupabase().getUserInfo("eq." + DeviceUtils.getDeviceId(mContext));
        mNetWorkList.add(call);
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                cancelLoadingDialog();
                if (response != null && response.body() != null && response.body().size() > 0) {
                    List<UserEntity> body = response.body();
                    userEntity = body.get(0);
                    if (!TextUtils.isEmpty(userEntity.getHeadUrl())) {
                        Bitmap bitmap = BitmapUtils.handleBase64ToBitmap(userEntity.getHeadUrl());
                        ivHead.setImageBitmap(bitmap);
                    }
                } else {
                    toast(getString(R.string.hint_use_info));
                }
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                cancelLoadingDialog();
            }
        });
    }

    private void uploadUserInfo(String stringFile) {
        showLoadingDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("deviceId", userEntity.getDeviceId());
        map.put("name", userEntity.getName());
        map.put("headUrl", stringFile);
        map.put("account", userEntity.getAccount());
        map.put("password", userEntity.getPassword());
        map.put("age", userEntity.getAge());
        Call<ResponseBody> call = HttpClient.getHttpSupabase().patchData("eq." + userEntity.getId(), HttpClient.getRequestBody(map));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                cancelLoadingDialog();
                Bitmap bitmap = BitmapUtils.handleBase64ToBitmap(stringFile);
                ivHead.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cancelLoadingDialog();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void cameraPhoto() {
        if (builder1 == null) {
            builder1 = new AlertDialog.Builder(UserActivity.this)
                    .setCancelable(true)
                    .setTitle(getString(R.string.permissions))
                    .setMessage(getString(R.string.permission_text))
                    .setNegativeButton(getString(R.string.setting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", UserActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            UserActivity.this.startActivity(intent);
                        }
                    });
        }
        if (alertDialog1 == null) {
            alertDialog1 = builder1.create();
        }
        //获取权限以及判断
        PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        //该权限已被拒绝
                        alertDialog1.show();
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        //该权限已授权
                        PictureSelector.create(UserActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .compress(true)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        //该权限未授权
                        alertDialog1.show();
                    }
                }).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        /**
                         *上传
                         */
                        String path = selectList.get(0).getCompressPath();
                        byte[] bytes = FileIOUtils.readFile2BytesByStream(path);
                        String stringFile = EncodeUtils.base64Encode2String(bytes);
                        uploadUserInfo("data:image/jpeg;base64," + stringFile);
                    }
                    break;
            }
        }
    }

}