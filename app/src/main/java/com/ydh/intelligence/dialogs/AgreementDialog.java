package com.ydh.intelligence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.activitys.H5Activity;
import com.ydh.intelligence.interfaces.ViewInterface;


/**
 * Created by ydh on 2022/8/26
 */
public class AgreementDialog {
    private Context mContext;
    private Dialog mDialog;
    private ImageView mIvDelete;
    private TextView mTvMessage;
    private TextView mTvAgree;
    private TextView mTvNoAgree;

    public AgreementDialog(Context mContext, ViewInterface mListener) {
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.HintDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_agreement, null);
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvAgree = (TextView) view.findViewById(R.id.tv_agree);
        mTvNoAgree = (TextView) view.findViewById(R.id.tv_no_agree);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        SpannableString ssb = new SpannableString(mContext.getString(R.string.agreement));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(mContext, H5Activity.class);
                intent.putExtra("from", 1);
                intent.putExtra("url", "https://yudonghui.github.io/index.html");
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(mContext, R.color.color_theme));
            }
        };
        ssb.setSpan(clickableSpan, 8, 25, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_red)), 8, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvMessage.setMovementMethod(LinkMovementMethod.getInstance());
        mTvMessage.setText(ssb);
        mTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.setCache(SPUtils.FILE_DATA, SPUtils.IS_FIRST, "1");
                mDialog.dismiss();
            }
        });
        mTvNoAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v);
            }
        });
        mDialog.show();
    }

}
