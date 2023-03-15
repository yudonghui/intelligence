package com.ydh.intelligence.activitys;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.TestActivity;
import com.ydh.intelligence.common.SpaceItemDecoration;
import com.ydh.intelligence.dialogs.AgreementDialog;
import com.ydh.intelligence.entitys.HomeEntity;
import com.ydh.intelligence.entitys.UserEntity;
import com.ydh.intelligence.interfaces.ViewInterface;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.CommonUtil;
import com.ydh.intelligence.utils.DeviceUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    private UserEntity userInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        tvTitle = findViewById(R.id.tv_title);
        recyclerView = findViewById(R.id.recycler_view);
        unBind = ButterKnife.bind(this);
        initData();
        int displayWidth = CommonUtil.getDisplayWidth(this);
        int dp40 = CommonUtil.dp2px(40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((displayWidth - dp40) / 3, (displayWidth - dp40) / 3);
        ArrayList<HomeEntity> homeEntities = new ArrayList<>();
        homeEntities.add(new HomeEntity(R.drawable.shape_theme_10, getString(R.string.home_ChatGPT), 1));
        homeEntities.add(new HomeEntity(R.drawable.shape_red_10, getString(R.string.home_picture), 2));
        homeEntities.add(new HomeEntity(R.drawable.shape_blue_10, getString(R.string.home_small_widget), 3));
        homeEntities.add(new HomeEntity(R.drawable.shape_orange_10, getString(R.string.home_auto), 4));
        homeEntities.add(new HomeEntity(R.drawable.shape_blue_10, getString(R.string.home_text_voice), 5));
        homeEntities.add(new HomeEntity(R.drawable.shape_theme_10, getString(R.string.home_coupons), 6));
        homeEntities.add(new HomeEntity(R.drawable.shape_orange_10, getString(R.string.home_picture_text), 7));
        homeEntities.add(new HomeEntity(R.drawable.shape_red_10, getString(R.string.home_mpac), 8));
        homeEntities.add(new HomeEntity(R.drawable.shape_gray_10, getString(R.string.home_tool), 100));
        CommonAdapter<HomeEntity> mAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_main, homeEntities) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                TextView mTvMain = holder.getView(R.id.tv_main);
                mTvMain.setLayoutParams(layoutParams);
                mTvMain.setText(homeEntity.getTitle());
                mTvMain.setBackgroundResource(homeEntity.getImgRes());
                mTvMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (homeEntity.getType() == 1) {//ChatGpt
                            startActivity(ChatGptActivity.class);
                        } else if (homeEntity.getType() == 2) {//百度生成图片
                            startActivity(BdAiActivity.class);
                        } else if (homeEntity.getType() == 3) {//小组件
                            startActivity(SmallWidgetActivity.class);
                        } else if (homeEntity.getType() == 4) {//自动化操作
                            startActivity(AutoClickActivity.class);
                        } else if (homeEntity.getType() == 5) {//文本转语音
                            startActivity(VoiceActivity.class);
                        } else if (homeEntity.getType() == 6) {//优惠券
                            startActivity(CouponsActivity.class);
                        } else if (homeEntity.getType() == 7) {//图片识别
                            startActivity(MachineLearningActivity.class);
                        } else if (homeEntity.getType() == 8) {//MPAndroidChart
                            startActivity(MpAndroidChartActivity.class);
                        } else if (homeEntity.getType() == 100) {//实用工具
                            startActivity(TestActivity.class);
                        }
                    }
                });
            }
        };
        GridLayoutManager layout = new GridLayoutManager(mContext, 3);
        recyclerView.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.GRIDLAYOUT));
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mAdapter);
        //检查版本更新
        checkUpdate();
        if (!"1".equals(SPUtils.getCache(SPUtils.FILE_DATA, SPUtils.IS_FIRST))) {
            new AgreementDialog(mContext, new ViewInterface() {
                @Override
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private void initData() {
        Call<List<UserEntity>> call = HttpClient.getHttpSupabase().getUserInfo("eq." + DeviceUtils.getDeviceId(mContext));
        mNetWorkList.add(call);
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                if (response != null && response.body() != null && response.body().size() > 0) {
                    userInfoEntity = response.body().get(0);
                } else {
                    insertData();
                }
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {

            }
        });

    }

    private void insertData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "清風_" + words[(int) (Math.random() * 26)] + (int) (Math.random() * 10) + words[(int) (Math.random() * 26)] + (int) (Math.random() * 10) + words[(int) (Math.random() * 26)] + (int) (Math.random() * 10));
        map.put("deviceId", DeviceUtils.getDeviceId(mContext));
        Call<ResponseBody> call = HttpClient.getHttpSupabase().insertUse(HttpClient.getRequestBody(map));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void checkUpdate() {
        /*XUpdate.newBuild(this)
                .updateUrl(Constant.UPDATE_URL)
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter(this))
                .update();*/
    }

    //对返回键进行监听
    //退出时的时间
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                CommonUtil.showToast(getString(R.string.hint_quite) + getString(R.string.app_name));
                mExitTime = System.currentTimeMillis();
            } else {
               /* finish();
                System.exit(0);*/
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.tv_device)
    public void onViewClicked() {
        //startActivity(DeviceInfoActivity.class);
        startActivity(UserActivity.class);
    }
}