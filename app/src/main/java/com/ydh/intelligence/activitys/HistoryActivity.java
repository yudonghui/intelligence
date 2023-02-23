package com.ydh.intelligence.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.db.DbInterface;
import com.ydh.intelligence.db.DbManager;
import com.ydh.intelligence.db.HistoryEntity;
import com.ydh.intelligence.entitys.BaseEntity;
import com.ydh.intelligence.entitys.ImgEntity;
import com.ydh.intelligence.entitys.ImgUrlsEntity;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.Strings;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tfl)
    TagFlowLayout tfl;
    private List<HistoryEntity> mList = new ArrayList<>();
    private TagAdapter<HistoryEntity> mTagAdapter;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Long taskId = msg.obj instanceof Long ? (Long) msg.obj : 0;
                getImg(taskId);
            }
        }
    };
    private HistoryEntity historyEntity;//点击的历史数据

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        unBind = ButterKnife.bind(this);
        initAdapter();
        DbManager.getInstance().queryAllHis(new DbInterface<List<HistoryEntity>>() {

            @Override
            public void success(List<HistoryEntity> result) {
                mList.clear();
                if (result != null) {
                    mList.addAll(result);
                    mTagAdapter.notifyDataChanged();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initAdapter() {
        mTagAdapter = new TagAdapter<HistoryEntity>(mList) {

            @Override
            public View getView(FlowLayout parent, int position, HistoryEntity historyEntity) {
                TextView tv = (TextView) (((Activity) mContext).getLayoutInflater()).inflate(R.layout.tag_tv,
                        tfl, false);
                tv.setText(historyEntity.getText() + "，" + historyEntity.getStyle() + "，" + historyEntity.getResolution());
                return tv;
            }
        };
        tfl.setAdapter(mTagAdapter);
        tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                historyEntity = mList.get(position);
                getImg(Strings.getLong(historyEntity.getTaskId()));
                return true;
            }
        });
    }

    private void getImg(Long taskId) {
        Call<BaseEntity<ImgEntity>> call = HttpClient.getHttpApiBd().getImg(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.BD_TOKEN), taskId);
        call.enqueue(new Callback<BaseEntity<ImgEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<ImgEntity>> call, Response<BaseEntity<ImgEntity>> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    ImgEntity imgEntity = response.body().getData();
                    if (imgEntity.getStatus() == 0) {//"0"表示任务排队中或正在处理。
                        String waiting = imgEntity.getWaiting().substring(0, imgEntity.getWaiting().length() - 1);
                        Message obtain = Message.obtain();
                        obtain.what = 1;
                        obtain.obj = taskId;
                        handler.sendMessageDelayed(obtain, Strings.getInt(waiting) * 1000);
                        return;
                    }
                    cancelLoadingDialog();
                    List<ImgUrlsEntity> mImgList = imgEntity.getImgUrls();
                    if (mImgList != null && mImgList.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mImgList", (Serializable) mImgList);
                        startActivity(PictureActivity.class, bundle);
                        if (historyEntity!=null){
                            historyEntity.setNum(mImgList.size());
                            DbManager.getInstance().update(historyEntity, new DbInterface() {
                                @Override
                                public void success(Object result) {

                                }

                                @Override
                                public void fail() {

                                }
                            });
                        }
                    } else {
                        toast("获取数据为空");
                    }

                } else {
                    cancelLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<ImgEntity>> call, Throwable t) {
                cancelLoadingDialog();
            }
        });
    }

    @OnClick(R.id.iv_return)
    public void onViewClicked() {
        finish();
    }
}