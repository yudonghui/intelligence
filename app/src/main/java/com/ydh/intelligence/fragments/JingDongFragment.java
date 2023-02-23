package com.ydh.intelligence.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ydh.intelligence.R;
import com.ydh.intelligence.adapters.FragmentAdapter;
import com.ydh.intelligence.common.Constant;
import com.ydh.intelligence.common.MaterialIds;
import com.ydh.intelligence.common.bases.BaseFragment;
import com.ydh.intelligence.entitys.TitleEntity;
import com.ydh.intelligence.utils.ClipboardUtils;
import com.ydh.intelligence.utils.CommonUtil;
import com.ydh.intelligence.views.TablayoutTabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/8/16
 */
public class JingDongFragment extends BaseFragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<TitleEntity> mTitleList;
    private FragmentAdapter mVpAdapter;

    public static JingDongFragment newInstance() {
        Bundle args = new Bundle();
        JingDongFragment fragment = new JingDongFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jing_dong, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTitleList = MaterialIds.getMaterial();
        for (int i = 0; i < mTitleList.size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(mTitleList.get(i).getMaterial_name()));
            mFragmentList.add(JingDongMaterialFragment.newInstance(mTitleList.get(i).getMaterial_id()));
        }
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(tablayout, CommonUtil.dp2px(10), CommonUtil.dp2px(10));
            }
        });
        mVpAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        viewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        tablayout.setupWithViewPager(viewPager);
        return view;
    }

    @OnClick({R.id.iv_share, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                ClipboardUtils.setClipboardNo(Constant.DOWNLOAD_URL);
                Uri uriWx = Uri.parse("weixin://");
                Intent intentWx = new Intent(Intent.ACTION_VIEW, uriWx);
                startActivity(intentWx);
                break;
            case R.id.iv_search:
                break;
        }
    }
}
