package com.ydh.intelligence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ydh.intelligence.BaseApplication;
import com.ydh.intelligence.R;
import com.ydh.intelligence.interfaces.HisDialogInterface;
import com.ydh.intelligence.utils.CommonUtil;


public class HisDialog {
    /**
     * 正常情况
     */
    public final static int DIALOG_NORMAL = 1;
    /**
     * 内容是个编辑框
     */
    public final static int DIALOG_EDITE = 2;
    /**
     * 内容是个编辑框.只允许输入数字和小数点
     */
    public final static int DIALOG_EDITE_NUM = 3;

    public Dialog mHintDialog;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private TextView mMessage;
    private EditText mEditeText;
    private EditText mEditeNumber;
    private TextView mTitle;
    private LinearLayout mLlBtn;

    public HisDialog(Context mContext, Builder builder) {
        mHintDialog = new Dialog(mContext, R.style.HintDialog);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_his, null);
        mTitle = (TextView) view.findViewById(R.id.title);
        mMessage = (TextView) view.findViewById(R.id.message);
        mEditeText = view.findViewById(R.id.ettext);
        mEditeNumber = view.findViewById(R.id.etnumber);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        setView(builder);
        mHintDialog.setContentView(view);
        mHintDialog.show();
        dissmissListener(builder);
    }

    private void dissmissListener(final Builder builder) {
        mHintDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (builder.mDismissListener != null) {
                    builder.mDismissListener.callBack(null);
                }
            }
        });
    }

    /**
     * type 0  只创建不显示(原来界面)
     * type 1 新界面显示
     * type 2 message是html文本
     */
    public HisDialog(Context mContext, Builder builder, int type) {
        if (type == 0) {
            mHintDialog = new Dialog(mContext, R.style.HintDialog);
            mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = View.inflate(mContext, R.layout.dialog_his, null);
            mTitle = (TextView) view.findViewById(R.id.title);
            mMessage = (TextView) view.findViewById(R.id.message);
            mEditeText = view.findViewById(R.id.ettext);
            mEditeNumber = view.findViewById(R.id.etnumber);
            mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
            mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
            setView(builder);
            mHintDialog.setContentView(view);
        } else {
            mHintDialog = new Dialog(mContext, R.style.HintDialog);
            mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = View.inflate(mContext, R.layout.dialog_his_two, null);
            mTitle = (TextView) view.findViewById(R.id.title);
            mMessage = (TextView) view.findViewById(R.id.message);
            mLlBtn = (LinearLayout) view.findViewById(R.id.ll_btn);
            setViewTwo(builder, type, mContext);
            mHintDialog.setContentView(view);
        }
    }


    public void show() {
        if (mHintDialog != null) {
            mHintDialog.show();
        }
    }

    private void setViewTwo(Builder builder, int type, Context mContext) {
        mHintDialog.setCancelable(builder.isCancel);
        /**
         *标题
         */
        if (TextUtils.isEmpty(builder.title)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(builder.title);
        }
        mMessage.setGravity(builder.gravity);
        if (type == 2) {//显示的文本 可能比较长，宽度增加
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dp2px(330), ViewGroup.LayoutParams.WRAP_CONTENT);
            mTitle.setLayoutParams(layoutParams);
            mMessage.setLayoutParams(layoutParams);
            mLlBtn.setLayoutParams(layoutParams);
            mMessage.setText(Html.fromHtml(builder.message));
        } else {
            mMessage.setText(builder.message);
        }
        if (!TextUtils.isEmpty(builder.rightBtnText) && TextUtils.isEmpty(builder.leftBtnText) && TextUtils.isEmpty(builder.middleBtnText)) {
            mLlBtn.addView(getRightBtn(mContext, builder));
        } else if (!TextUtils.isEmpty(builder.rightBtnText) && !TextUtils.isEmpty(builder.leftBtnText) && TextUtils.isEmpty(builder.middleBtnText)) {
            mLlBtn.addView(getLeftBtn(mContext, builder));
            mLlBtn.addView(getRightBtn(mContext, builder));
        } else if (TextUtils.isEmpty(builder.rightBtnText) && !TextUtils.isEmpty(builder.leftBtnText) && !TextUtils.isEmpty(builder.middleBtnText)) {
            mLlBtn.addView(getLeftBtn(mContext, builder));
            mLlBtn.addView(getMiddleBtn(mContext, builder));
        } else if (!TextUtils.isEmpty(builder.rightBtnText) && TextUtils.isEmpty(builder.leftBtnText) && !TextUtils.isEmpty(builder.middleBtnText)) {
            mLlBtn.addView(getLeftBtn(mContext, builder));
            mLlBtn.addView(getMiddleBtn(mContext, builder));
        } else {
            mLlBtn.addView(getLeftBtn(mContext, builder));
            mLlBtn.addView(getMiddleBtn(mContext, builder));
            mLlBtn.addView(getRightBtn(mContext, builder));
        }
    }

    private void setView(final Builder builder) {
        mHintDialog.setCancelable(builder.isCancel);
        /**
         *标题
         */
        if (TextUtils.isEmpty(builder.title)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(builder.title);
        }
        mMessage.setGravity(builder.gravity);
        /**
         *根据不中间显示的内容进行划分
         * 正常显示文本
         * 编辑框
         * 编辑框只能输入数字和小数点
         */
        if (builder.type == DIALOG_NORMAL) {
            if (TextUtils.isEmpty(builder.messageSpan)) {
                if (TextUtils.isEmpty(builder.message)) {
                    mMessage.setVisibility(View.GONE);
                } else {
                    mMessage.setVisibility(View.VISIBLE);
                    mMessage.setText(builder.message);
                }
            } else {
                mMessage.setVisibility(View.VISIBLE);
                mMessage.setText(builder.messageSpan);
            }
        } else if (builder.type == DIALOG_EDITE) {
            mEditeText.setVisibility(View.VISIBLE);
            mEditeText.setHint(builder.hint);
        } else if (builder.type == DIALOG_EDITE_NUM) {
            mEditeNumber.setVisibility(View.VISIBLE);
            mEditeNumber.setHint(builder.hint);
        }
        /**
         *取消按钮
         */
        if (TextUtils.isEmpty(builder.cancel)) {
            mTvCancel.setVisibility(View.GONE);
        } else {
            mTvCancel.setVisibility(View.VISIBLE);
            mTvCancel.setBackgroundResource(R.drawable.shape_gray_b_10);
            mTvCancel.setText(builder.cancel);
        }
        /**
         *中间显示的信息是否可复制
         */
        mMessage.setTextIsSelectable(builder.textIsSelectable);
        /**
         *确定按钮
         */
        if (TextUtils.isEmpty(builder.confirm)) {
            mTvConfirm.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(builder.cancel)) {
                mTvConfirm.setBackgroundResource(R.drawable.shape_theme_r_l_10);
            } else {
                mTvConfirm.setBackgroundResource(R.drawable.shape_theme_b_10);
            }
            mTvConfirm.setVisibility(View.VISIBLE);
            mTvConfirm.setText(builder.confirm);
        }

        /**
         *相关点击事件
         */
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mCancelListener != null)
                    builder.mCancelListener.callBack(v);
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mConfirmListener != null) {
                    if (builder.type == DIALOG_EDITE) {
                        builder.mConfirmListener.callBack(mEditeText);
                    } else if (builder.type == DIALOG_EDITE_NUM) {
                        builder.mConfirmListener.callBack(mEditeNumber);
                    } else {
                        builder.mConfirmListener.callBack(v);
                    }

                }
            }
        });
    }

    private TextView getRightBtn(Context mContext, final Builder builder) {
        TextView textView = new TextView(mContext);
        textView.setText(builder.rightBtnText);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dp2px(75), CommonUtil.dp2px(38));
        layoutParams.leftMargin = CommonUtil.dp2px(10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        textView.setBackgroundResource(R.drawable.shape_theme_5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mRightBtnListener != null)
                    builder.mRightBtnListener.callBack(v);
            }
        });
        return textView;
    }

    private TextView getLeftBtn(Context mContext, final Builder builder) {
        TextView textView = new TextView(mContext);
        textView.setText(builder.leftBtnText);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dp2px(75), CommonUtil.dp2px(38));
        layoutParams.leftMargin = CommonUtil.dp2px(10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        textView.setBackgroundResource(R.drawable.shape_fenred_5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mLeftBtnListener != null)
                    builder.mLeftBtnListener.callBack(v);
            }
        });
        return textView;
    }

    private TextView getMiddleBtn(Context mContext, final Builder builder) {
        TextView textView = new TextView(mContext);
        textView.setText(builder.middleBtnText);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dp2px(75), CommonUtil.dp2px(38));
        layoutParams.leftMargin = CommonUtil.dp2px(10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        textView.setBackgroundResource(R.drawable.shape_orange_5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mMiddleBtnListener != null)
                    builder.mMiddleBtnListener.callBack(v);
            }
        });
        return textView;
    }

    public static class Builder {
        private int type = DIALOG_NORMAL;//内容的类型
        private String title;
        /**
         * 正常中间是显示文本
         */
        private String message;
        private SpannableString messageSpan;
        private int messageSize;
        /**
         * type是DIALOG_EDITE的时候中间是个编辑框，
         * 这个时候可以设置一个提示语
         */
        private String hint = BaseApplication.getContext().getResources().getString(R.string.hint_input_content);
        /**
         * 是否可以取消dialog。false是不可取消.默认可以取消
         */
        private boolean isCancel = true;
        private String confirm;
        private String cancel;
        private HisDialogInterface mConfirmListener;
        private HisDialogInterface mCancelListener;
        private HisDialogInterface mDismissListener;
        private String leftBtnText;
        private String middleBtnText;
        private String rightBtnText;
        private HisDialogInterface mLeftBtnListener;
        private HisDialogInterface mRightBtnListener;
        private HisDialogInterface mMiddleBtnListener;

        private int gravity = Gravity.CENTER_VERTICAL;//文本显示的时候文字的位置
        private boolean textIsSelectable = false;

        public Builder textIsSelectable(boolean textIsSelectable) {
            this.textIsSelectable = textIsSelectable;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder message(SpannableString messageSpan) {
            this.messageSpan = messageSpan;
            return this;
        }

        public Builder messageSize(int messageSize) {
            this.messageSize = messageSize;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder isCancel(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        public Builder confirm(String confirm, HisDialogInterface mListener) {
            this.confirm = confirm;
            this.mConfirmListener = mListener;
            return this;
        }

        public Builder cancel(String cancel, HisDialogInterface mListener) {
            this.cancel = cancel;
            this.mCancelListener = mListener;
            return this;
        }

        public Builder rightBtn(String confirm, HisDialogInterface mListener) {
            this.rightBtnText = confirm;
            this.mRightBtnListener = mListener;
            return this;
        }

        public Builder rightBtn(String confirm) {
            this.rightBtnText = confirm;
            return this;
        }

        public Builder middleBtn(String confirm, HisDialogInterface mListener) {
            this.middleBtnText = confirm;
            this.mMiddleBtnListener = mListener;
            return this;
        }

        public Builder dissmiss(HisDialogInterface mListener) {
            this.mDismissListener = mListener;
            return this;
        }

        public Builder leftBtn(String confirm, HisDialogInterface mListener) {
            this.leftBtnText = confirm;
            this.mLeftBtnListener = mListener;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder cancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public HisDialog build(Context mContext) {
            return new HisDialog(mContext, this);
        }


        public HisDialog build(Context mContext, int type) {
            return new HisDialog(mContext, this, type);
        }
    }
}
