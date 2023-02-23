package com.ydh.intelligence.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ydh.intelligence.R;
import com.ydh.intelligence.entitys.ImgUrlsEntity;
import com.ydh.intelligence.utils.ImageNetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ydh on 2020/12/3
 * 展示大图
 */
public class PictureActivity extends PermissionActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private List<ImageView> mLists = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    int curPosition;
    private List<ImgUrlsEntity> mImgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        unBind = ButterKnife.bind(this);
        curPosition = getIntent().getIntExtra("position", 0);
        mImgList = (List<ImgUrlsEntity>) getIntent().getSerializableExtra("mImgList");
        tvNum.setText((curPosition + 1) + "/" + mImgList.size());
        initAdapter();
        initData();
        initListener();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                curPosition = i;
                tvNum.setText((curPosition + 1) + "/" + mImgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private void initAdapter() {
        mPagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mLists == null ? 0 : mLists.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mLists.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(mLists.get(position));
                return mLists.get(position);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    private void initData() {
        mLists.clear();
        for (int i = 0; i < mImgList.size(); i++) {
            ImgUrlsEntity imgUrlsEntity = mImgList.get(i);
            ImageView imageView = new ImageView(mContext);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            /**
             * 先用url,如果url为空那么取content的值
             */
            if (!TextUtils.isEmpty(imgUrlsEntity.getImage())) {
                ImageNetUtils.setImageUrl(mContext, imgUrlsEntity.getImage(), imageView);
            }
            mLists.add(imageView);
        }
        mPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(curPosition);
    }


    @OnClick({R.id.iv_return, R.id.iv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_download:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    break;
                }
                ImageView imageView = mLists.get(curPosition);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ImageNetUtils.saveBitmap(this, bitmap);
                toast("保存成功！");
                break;
        }
    }
}
