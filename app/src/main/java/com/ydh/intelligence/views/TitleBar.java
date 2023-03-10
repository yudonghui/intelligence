package com.ydh.intelligence.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ydh.intelligence.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date:2023/3/10
 * Time:9:24
 * author:ydh
 */
public class TitleBar extends LinearLayout {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_second)
    TextView tvRightSecond;
    @BindView(R.id.tv_right_first)
    TextView tvRightFirst;
    @BindView(R.id.iv_right_second)
    ImageView ivRightSecond;
    @BindView(R.id.iv_right_first)
    ImageView ivRightFirst;
    @BindView(R.id.rl_container)
    ConstraintLayout rlContainer;
    private View mInflate;
    public Context mContext;
    private int default_bg = getResources().getColor(R.color.color_theme);
    private int default_title_color = getResources().getColor(R.color.color_white);
    private int default_right_color = getResources().getColor(R.color.color_white);
    private int backgroundColor = default_bg;//标题颜色
    private int titleColor = default_title_color;//标题颜色
    private int rightColor = default_right_color;//右边文字的颜色
    private String title;//标题
    private String rightText;//右边的文字
    private String rightTextTwo;//右边的文字
    private int rightImage = 0;//右边数第一个图片
    private int rightImageTwo = 0;//右边数第二个图片

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.titlebar, this);
        ButterKnife.bind(mInflate);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.TitleBar_tb_background_color, default_bg);
            title = typedArray.getString(R.styleable.TitleBar_tb_title);
            titleColor = typedArray.getColor(R.styleable.TitleBar_tb_title_color, default_title_color);
            rightColor = typedArray.getColor(R.styleable.TitleBar_tb_right_color, default_right_color);

            rightText = typedArray.getString(R.styleable.TitleBar_tb_right_text);
            rightTextTwo = typedArray.getString(R.styleable.TitleBar_tb_right_text_two);
            rightImage = typedArray.getResourceId(R.styleable.TitleBar_tb_right_image, 0);
            rightImageTwo = typedArray.getResourceId(R.styleable.TitleBar_tb_right_image_two, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackground(backgroundColor);
        setTitle(title, titleColor);
        setTvRight(rightText, rightColor);
        setTvRightSecond(rightText, rightColor);
        setRightImage(rightImage);
        setRightImageSecond(rightImageTwo);
        addListener();
    }

    private void addListener() {
        if (ivReturn != null) {
            ivReturn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
        }
    }

    public void setBackground(int backgroundColor) {
        rlContainer.setBackgroundColor(backgroundColor);
    }

    public void setTitle(String title) {
        setTitle(title, default_title_color);
    }

    public void setTitle(String title, int titleColor) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setTextColor(titleColor);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(GONE);
        }
    }

    public void setTvRight(String rightText) {
        setTvRight(rightText, rightColor);
    }

    public void setTvRight(String rightText, int rightTextColor) {
        if (!TextUtils.isEmpty(rightText)) {
            tvRightFirst.setVisibility(VISIBLE);
            tvRightFirst.setTextColor(rightTextColor);
            tvRightFirst.setText(rightText);
        } else {
            tvRightFirst.setVisibility(GONE);
        }
    }

    public void setTvRightSecond(String rightText) {
        setTvRight(rightText, rightColor);
    }

    public void setTvRightSecond(String rightText, int rightTextColor) {
        if (!TextUtils.isEmpty(rightText)) {
            tvRightSecond.setVisibility(VISIBLE);
            tvRightSecond.setTextColor(rightTextColor);
            tvRightSecond.setText(rightText);
        } else {
            tvRightSecond.setVisibility(GONE);
        }
    }

    public void setRightImage(int rightImage) {
        if (rightImage == 0) {
            ivRightFirst.setVisibility(GONE);
        } else {
            ivRightFirst.setVisibility(VISIBLE);
            ivRightFirst.setImageResource(rightImage);
        }
    }

    public void setRightImageSecond(int rightImageSecond) {
        if (rightImageTwo == 0) {
            ivRightSecond.setVisibility(GONE);
        } else {
            ivRightSecond.setVisibility(VISIBLE);
            ivRightSecond.setImageResource(rightImageTwo);
        }
    }

    public void setLeftVisible(int leftVisible) {
        ivReturn.setVisibility(leftVisible);
    }
}
