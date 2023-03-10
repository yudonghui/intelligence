package com.ydh.intelligence.activitys;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.ydh.intelligence.R;
import com.ydh.intelligence.fragments.JingDongFragment;
import com.ydh.intelligence.fragments.TaoBaoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponsActivity extends BaseTaoActivity{
    @BindView(R.id.content_fragment)
    FrameLayout contentFragment;
    @BindView(R.id.iv_tao_bao)
    ImageView ivTaoBao;
    @BindView(R.id.tv_tao_bao)
    TextView tvTaoBao;
    @BindView(R.id.ll_tao_bao)
    LinearLayout llTaoBao;
    @BindView(R.id.iv_jing_dong)
    ImageView ivJingDong;
    @BindView(R.id.tv_jing_dong)
    TextView tvJingDong;
    @BindView(R.id.ll_jing_dong)
    LinearLayout llJingDong;
    private Fragment[] mFragments = new Fragment[2];
    private int curIndex;
    private int colorTheme;
    private int colorGray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        unBind = ButterKnife.bind(this);
        colorTheme = ContextCompat.getColor(mContext, R.color.color_theme);
        colorGray = ContextCompat.getColor(mContext, R.color.color_85);
        setFragment();
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.content_fragment, curIndex);
    }
    private void setFragment() {
        mFragments[0] = TaoBaoFragment.newInstance();//淘宝fragment
        mFragments[1] = JingDongFragment.newInstance();//京东fragment
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @OnClick({R.id.ll_tao_bao, R.id.ll_jing_dong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tao_bao:
                showCurrentFragment(0);
                break;
            case R.id.ll_jing_dong:
                showCurrentFragment(1);
                break;
        }
    }

    private void showCurrentFragment(int index) {
        if (index == 0) {
            tvTaoBao.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvTaoBao.setTextColor(colorTheme);
            ivTaoBao.setImageResource(R.mipmap.tao_bao_true);

            tvJingDong.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvJingDong.setTextColor(colorGray);
            ivJingDong.setImageResource(R.mipmap.jing_dong_false);

        } else if (index == 1) {
            tvTaoBao.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvTaoBao.setTextColor(colorGray);
            ivTaoBao.setImageResource(R.mipmap.tao_bao_false);

            tvJingDong.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvJingDong.setTextColor(colorTheme);
            ivJingDong.setImageResource(R.mipmap.jing_dong_true);

        }
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

}