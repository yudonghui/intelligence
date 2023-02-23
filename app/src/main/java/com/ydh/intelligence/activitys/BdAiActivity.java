package com.ydh.intelligence.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.common.Constant;
import com.ydh.intelligence.db.DbInterface;
import com.ydh.intelligence.db.DbManager;
import com.ydh.intelligence.db.HistoryEntity;
import com.ydh.intelligence.entitys.BaseEntity;
import com.ydh.intelligence.entitys.ImgEntity;
import com.ydh.intelligence.entitys.ImgUrlsEntity;
import com.ydh.intelligence.entitys.TagEntity;
import com.ydh.intelligence.entitys.TaskIdEntity;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.SecretUtils;
import com.ydh.intelligence.utils.Strings;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh
 * taskId: 13829184
 * taskId: 13826797
 * taskId: 13823361
 */
public class BdAiActivity extends BaseActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tfl_prompt)
    TagFlowLayout tflPrompt;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.tv_resolution)
    TextView tvResolution;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tfl_resolution)
    TagFlowLayout tflResolution;
    @BindView(R.id.tfl_num)
    TagFlowLayout tflNum;
    @BindView(R.id.et_search)
    EditText etSearch;
    private List<TagEntity> styleList = new ArrayList<>();//修饰词 style
    private List<TagEntity> resolutionList = new ArrayList<>();//分辨率 resolution
    private List<TagEntity> numList = new ArrayList<>();//数量 num
    private String num = "1";//数量


    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Long taskId = msg.obj instanceof Long ? (Long) msg.obj : 0;
                getImg(taskId, false);
            }
        }
    };

    static class HisHandler extends Handler {
        WeakReference mWeakReference;

        public HisHandler(ChatGptActivity mActivity) {
            this.mWeakReference = new WeakReference(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            ChatGptActivity mainActivity = (ChatGptActivity) (mWeakReference.get());
            if (msg.what == 1) {
                String authorization = SecretUtils.decode((String) msg.obj);
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.AUTHORIZATION, "Bearer " + authorization.substring(2));
                mainActivity.initData();
            } else if (msg.what == 200) {
                mainActivity.cancelLoadingDialog();
                Toast.makeText(mainActivity, "获取配置信息异常！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd_ai);
        unBind = ButterKnife.bind(this);
        initBdData();
        initData();
        initAdapter();
    }


    @OnClick({R.id.iv_return, R.id.tv_search, R.id.tv_history, R.id.tv_style, R.id.tv_resolution, R.id.tv_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_search:
                showLoadingDialog();
                searchData();
                break;
            case R.id.tv_history:
                startActivity(HistoryActivity.class);
                break;
            case R.id.tv_style:
                break;
            case R.id.tv_resolution:
                break;
            case R.id.tv_num:
                break;
        }
    }

    private void searchData() {
        String text = etSearch.getText().toString();
        Call<BaseEntity<TaskIdEntity>> call = HttpClient.getHttpApiBd().getTaskId(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.BD_TOKEN), text,
                tvStyle.getText().toString(), tvResolution.getText().toString(), num);
        call.enqueue(new Callback<BaseEntity<TaskIdEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<TaskIdEntity>> call, Response<BaseEntity<TaskIdEntity>> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    if ("0".equals(response.body().getCode())) {
                        TaskIdEntity data = response.body().getData();
                        Long taskId = data.getTaskId();
                        getImg(taskId, true);
                    } else {
                        cancelLoadingDialog();
                        toast(response.body().getMsg());
                    }
                } else {
                    cancelLoadingDialog();
                    toast("未获取到数据");
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<TaskIdEntity>> call, Throwable t) {
                cancelLoadingDialog();
            }
        });

    }

    private void getImg(Long taskId, boolean isFirst) {
        Call<BaseEntity<ImgEntity>> call = HttpClient.getHttpApiBd().getImg(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.BD_TOKEN), taskId);
        call.enqueue(new Callback<BaseEntity<ImgEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<ImgEntity>> call, Response<BaseEntity<ImgEntity>> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    ImgEntity imgEntity = response.body().getData();
                    if (isFirst) {//同一个taskId只保存一次
                        HistoryEntity historyEntity = new HistoryEntity(imgEntity.getText(), imgEntity.getStyle(), imgEntity.getResolution(), imgEntity.getTaskId(), imgEntity.getCreateTime());
                        DbManager.getInstance().insertHistory(historyEntity, new DbInterface() {
                            @Override
                            public void success(Object result) {

                            }

                            @Override
                            public void fail() {

                            }
                        });
                    }
                    if (imgEntity.getStatus() == 0) {//"0"表示任务排队中或正在处理。
                        String waiting = imgEntity.getWaiting();
                        long delayMillis = 2000;
                        if (TextUtils.isEmpty(waiting) && waiting.length() > 0) {
                            String substring = waiting.substring(0, waiting.length() - 1);
                            delayMillis = Strings.getInt(substring)*1000;
                        }
                        Message obtain = Message.obtain();
                        obtain.what = 1;
                        obtain.obj = taskId;
                        handler.sendMessageDelayed(obtain, delayMillis);
                        return;
                    }
                    cancelLoadingDialog();
                    List<ImgUrlsEntity> mImgList = imgEntity.getImgUrls();
                    if (mImgList != null && mImgList.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mImgList", (Serializable) mImgList);
                        startActivity(PictureActivity.class, bundle);
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

    private void initBdData() {
        Call<BaseEntity<Object>> call = HttpClient.getHttpApiBd().bdToken("client_credentials", Constant.BD_API_KEY, Constant.BD_SECRET_KEY);
        call.enqueue(new Callback<BaseEntity<Object>>() {
            @Override
            public void onResponse(Call<BaseEntity<Object>> call, Response<BaseEntity<Object>> response) {
                if (response != null && response.body() != null) {
                    Object data = response.body().getData();
                    Log.e("结果：", Strings.getString(data));
                    SPUtils.setCache(SPUtils.FILE_USER, SPUtils.BD_TOKEN, Strings.getString(data));
                }

            }

            @Override
            public void onFailure(Call<BaseEntity<Object>> call, Throwable t) {
                toast("网络失败");
            }
        });
    }

    /**
     * 古风、二次元、写实风格、浮世绘、low poly 、未来主义、像素风格、概念艺术、赛博朋克、洛丽塔风格、巴洛克风格、超现实主义、水彩画、蒸汽波艺术、油画、卡通画
     */
    private void initData() {
        styleList.clear();
        styleList.add(new TagEntity(true, "古风"));
        styleList.add(new TagEntity("油画"));
        styleList.add(new TagEntity("卡通画"));
        styleList.add(new TagEntity("二次元"));
        styleList.add(new TagEntity("写实风格"));
        styleList.add(new TagEntity("浮世绘"));
        styleList.add(new TagEntity("low poly"));
        styleList.add(new TagEntity("未来主义"));
        styleList.add(new TagEntity("像素风格"));
        styleList.add(new TagEntity("概念艺术"));
        styleList.add(new TagEntity("赛博朋克"));
        styleList.add(new TagEntity("洛丽塔风格"));
        styleList.add(new TagEntity("巴洛克风格"));
        styleList.add(new TagEntity("超现实主义"));
        styleList.add(new TagEntity("水彩画"));
        styleList.add(new TagEntity("蒸汽波艺术"));
        resolutionList.clear();
        resolutionList.add(new TagEntity(true, "1024*1024"));
        resolutionList.add(new TagEntity("1024*1536"));
        resolutionList.add(new TagEntity("1536*1024"));
        numList.clear();
        numList.add(new TagEntity(true, "1张", "1"));
        numList.add(new TagEntity("2张", "2"));
        numList.add(new TagEntity("3张", "3"));
        numList.add(new TagEntity("4张", "4"));
    }

    private void initAdapter() {
        initAdapter(tflPrompt, styleList);
        initAdapter(tflResolution, resolutionList);
        initAdapter(tflNum, numList);

    }


    private void initAdapter(TagFlowLayout tfl, List<TagEntity> mList) {
        TagAdapter<TagEntity> mTagAdapter = new TagAdapter<TagEntity>(mList) {

            @Override
            public View getView(FlowLayout parent, int position, TagEntity tagEntity) {
                TextView tv = (TextView) (((Activity) mContext).getLayoutInflater()).inflate(R.layout.tag_tv,
                        tfl, false);
                tv.setText(tagEntity.getName());
                if (tagEntity.isCheck()) {
                    tv.setBackgroundResource(R.drawable.shape_theme_20);
                    tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_white));
                } else {
                    tv.setBackgroundResource(R.drawable.shape_gray_light_20);
                    tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_85));
                }
                return tv;
            }
        };
        tfl.setAdapter(mTagAdapter);
        tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setCheck(false);
                }
                mList.get(position).setCheck(mList.get(position).isCheck() ? false : true);
                switch (tfl.getId()) {
                    case R.id.tfl_prompt:
                        tvStyle.setText(mList.get(position).getName());
                        break;
                    case R.id.tfl_resolution:
                        tvResolution.setText(mList.get(position).getName());
                        break;
                    case R.id.tfl_num:
                        tvNum.setText(mList.get(position).getName());
                        num = mList.get(position).getValue();
                        break;
                }

                mTagAdapter.notifyDataChanged();
                return true;
            }
        });
    }
}