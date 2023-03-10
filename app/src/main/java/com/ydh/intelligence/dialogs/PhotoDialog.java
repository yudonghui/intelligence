package com.ydh.intelligence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ydh.intelligence.R;


public class PhotoDialog {
    private Context mContext;
    private TextView mCamera;
    private TextView mPhoto;
    private Button mCancel;
    private View inflate;
    private Dialog mDialog;
    private Window window;

    public PhotoDialog(Context mContext) {
        //有关相机调用
        inflate = View.inflate(mContext, R.layout.photo_dialog, null);
        mCamera = (TextView) inflate.findViewById(R.id.buttonCamera);
        mPhoto = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancel = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);
    }

    public void showDialog() {
        mDialog.show();
        window.setGravity(Gravity.BOTTOM);
    }

    public void addListener(final OnPhotoInterface mListener) {
        //拍照
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.camera();
                mDialog.dismiss();
            }
        });
        //从相册中选取
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectPhoto();
                mDialog.dismiss();
            }
        });
        //取消
        mCancel.setOnClickListener(CancelListener);
    }

    //取消
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    public interface OnPhotoInterface {
        void camera();

        void selectPhoto();
    }
}
