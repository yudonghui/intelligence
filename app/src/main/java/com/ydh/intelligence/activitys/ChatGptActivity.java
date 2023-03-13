package com.ydh.intelligence.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydh.intelligence.CompletionEntity;
import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.db.ClickEntity;
import com.ydh.intelligence.db.DbInterface;
import com.ydh.intelligence.db.DbManager;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.DateFormtUtils;
import com.ydh.intelligence.utils.DeviceUtils;
import com.ydh.intelligence.utils.SecretUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatGptActivity extends BaseActivity {

    private RecyclerView mRvContent;
    private EditText mEtInput;
    private TextView mTvSubmit;
    private TextView mTvSetting;
    private ArrayList<ClickEntity> mDataList = new ArrayList<>();
    private CommonAdapter<ClickEntity> mCommonAdapter;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvSetting = findViewById(R.id.tv_setting);
        mRvContent = findViewById(R.id.rv_content);
        mEtInput = findViewById(R.id.et_input);
        mTvSubmit = findViewById(R.id.tv_submit);
        uuid = DeviceUtils.getDeviceId(ChatGptActivity.this);
        if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.MODELS))) {//默认精准查询
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.MODELS, "text-davinci-003");
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS, "1000");
        }
        initListener();
        initAdapter();
        initConfigData();
        initAdMob();
    }

    private void initAdMob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e("初始化：", initializationStatus.toString());
            }
        });
    }

    private void initListener() {
        mTvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatGptActivity.this, SettingActivity.class));
            }
        });
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEtInput.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    toast(getString(R.string.hint_input_content));
                    return;
                }
                completions();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        });
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<ClickEntity>(this, R.layout.item_completion, mDataList) {

            @Override
            protected void convert(ViewHolder holder, ClickEntity clickEntity, int position) {
                holder.setText(R.id.tv_creat_time, DateFormtUtils.dateByLong(clickEntity.getCreateTime(), DateFormtUtils.YMD_HMS));
                holder.setText(R.id.tv_input, clickEntity.getInputContent());
                holder.setText(R.id.tv_result, clickEntity.getResultContent());
            }
        };
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(layout);
        mRvContent.setAdapter(mCommonAdapter);
    }

    public void initData() {
        Log.e("Authorization", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.AUTHORIZATION));
        DbManager.getInstance().queryAll(new DbInterface<List<ClickEntity>>() {
            @Override
            public void success(List<ClickEntity> result) {
                cancelLoadingDialog();
                mDataList.clear();
                mDataList.addAll(result);
                long useTokens = 0;
                for (int i = 0; i < mDataList.size(); i++) {
                    useTokens += mDataList.get(i).getUseTokens();
                }
                SPUtils.setCacheL(SPUtils.FILE_USER, SPUtils.USE_TOKENS, useTokens);
                mCommonAdapter.notifyDataSetChanged();
                mRvContent.scrollToPosition(mCommonAdapter.getDatas().size() - 1);
            }

            @Override
            public void fail() {
                cancelLoadingDialog();
            }
        });
    }

    private void completions() {
        long useTokens = SPUtils.getCacheL(SPUtils.FILE_USER, SPUtils.USE_TOKENS);
        if (useTokens > 1000) {
            toast(getString(R.string.hint_quota_over));
            return;
        }
        showLoadingDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("model", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.MODELS));
        final String prompt = mEtInput.getText().toString();
        map.put("prompt", prompt);
        map.put("max_tokens", Integer.parseInt(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS)));
        map.put("temperature", 0);
        Call<CompletionEntity> call = HttpClient.getHttpApi().completions(HttpClient.getRequestBody(map));
        call.enqueue(new Callback<CompletionEntity>() {
            @Override
            public void onResponse(Call<CompletionEntity> call, Response<CompletionEntity> response) {
                cancelLoadingDialog();
                if (response != null && response.body() != null) {
                    CompletionEntity body = response.body();
                    if (body.getError() != null) {
                        toast(body.getError().getMessage());
                        return;
                    }
                    List<CompletionEntity.ChoicesBean> choices = body.getChoices();
                    StringBuilder resultContent = new StringBuilder();
                    for (int i = 0; i < choices.size(); i++) {
                        String text = choices.get(i).getText();
                        if (text.startsWith("\n\n")) {
                            text = text.substring(2);
                        }
                        resultContent.append(text + "\n");
                    }
                    if (resultContent.length() > 0) {
                        resultContent.deleteCharAt(resultContent.length() - 2);
                    }
                    long totalTokens = Long.parseLong(body.getUsage().getTotal_tokens());
                    SPUtils.setCacheL(SPUtils.FILE_USER, SPUtils.USE_TOKENS, SPUtils.getCacheL(SPUtils.FILE_USER, SPUtils.USE_TOKENS) + totalTokens);
                    final ClickEntity clickEntity = new ClickEntity(uuid, System.currentTimeMillis(), prompt, resultContent.toString(), totalTokens);
                    DbManager.getInstance().insert(clickEntity, new DbInterface() {
                        @Override
                        public void success(Object result) {
                            mDataList.add(clickEntity);
                            mCommonAdapter.notifyDataSetChanged();
                            mRvContent.scrollToPosition(mCommonAdapter.getDatas().size() - 1);
                        }

                        @Override
                        public void fail() {

                        }
                    });
                } else {
                    toast(getString(R.string.hint_no_search));
                }

            }

            @Override
            public void onFailure(Call<CompletionEntity> call, Throwable t) {
                cancelLoadingDialog();
                toast(getString(R.string.hint_submit_fail));
            }
        });
    }

    /**
     * 自己定义的配置文件
     */
    public void initConfigData() {
        showLoadingDialog();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://yudonghui.github.io/files/config.json").build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message obtain = Message.obtain();
                obtain.what = 200;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    Map<String, String> res = new Gson().fromJson(string, new TypeToken<Map<String, String>>() {
                    }.getType());
                    if (res != null) {
                        Message obtain = Message.obtain();
                        obtain.what = 1;
                        obtain.obj = res.get(SPUtils.AUTHORIZATION);
                        mHandler.sendMessage(obtain);
                        return;
                    }
                }
                Message obtain = Message.obtain();
                obtain.what = 200;
                mHandler.sendMessage(obtain);
            }
        });
    }

    private HisHandler mHandler = new HisHandler(this);

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
                mainActivity.toast(mainActivity.getString(R.string.hint_config_fail));
            }
        }
    }

}