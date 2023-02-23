package com.ydh.intelligence.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ydh.intelligence.R;
import com.ydh.intelligence.dialogs.MiddleDialog;
import com.ydh.intelligence.interfaces.YdhInterface;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.Base64Utils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceActivity extends PermissionActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_content)
    EditText mEtContent;//输入内容
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;//语速
    @BindView(R.id.tv_intonation)
    TextView mTvIntonation;//音调
    @BindView(R.id.tv_voice)
    TextView mTvVoice;//音量
    @BindView(R.id.tv_speaker)
    TextView mTvSpeaker;//发音人
    @BindView(R.id.tv_change)
    TextView mTvChange;//转换并播放
    @BindView(R.id.tv_download)
    TextView mTvDownload;//下载
    @BindView(R.id.adView)
    AdView adView;
    /**
     * speaker
     * 0女声
     * 1男声音
     * 3度逍遥
     * 4度丫丫
     */
    private String url = "https://api.uutool.cn/speech/text2voice/";

    private ArrayList<String> mSpeedList;
    private ArrayList<String> mIntonationList;
    private ArrayList<String> mVoiceList;
    private ArrayList<String> mSpeakerList;
    private HashMap<String, String> mSpeakMap;
    private String voiceResult;
    private AdView mAdView;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        mTvSpeed = findViewById(R.id.tv_speed);
        mTvIntonation = findViewById(R.id.tv_intonation);
        mTvVoice = findViewById(R.id.tv_voice);
        mTvSpeaker = findViewById(R.id.tv_speaker);
        mTvChange = findViewById(R.id.tv_change);
        mTvDownload = findViewById(R.id.tv_download);
        mEtContent = findViewById(R.id.et_content);
        requestPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        initData();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onPause() {
        if (mAdView != null) mAdView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void initData() {
        mSpeedList = new ArrayList<>();
        mSpeedList.add("0");
        mSpeedList.add("1");
        mSpeedList.add("2");
        mSpeedList.add("3");
        mSpeedList.add("4");
        mSpeedList.add("5");
        mSpeedList.add("6");
        mSpeedList.add("7");
        mSpeedList.add("8");
        mSpeedList.add("9");
        mIntonationList = new ArrayList<>();
        mIntonationList.addAll(mSpeedList);
        mVoiceList = new ArrayList<>();
        mVoiceList.addAll(mSpeedList);
        mVoiceList.add("10");
        mVoiceList.add("11");
        mVoiceList.add("12");
        mVoiceList.add("13");
        mVoiceList.add("14");
        mSpeakerList = new ArrayList<>();
        mSpeakerList.add("女声");
        mSpeakerList.add("男声");
        mSpeakerList.add("情感男声");
        mSpeakerList.add("情感女声");
        mSpeakMap = new HashMap<>();
        mSpeakMap.put("女声", "0");
        mSpeakMap.put("男声", "1");
        mSpeakMap.put("情感男声", "3");
        mSpeakMap.put("情感女声", "4");
    }

    @OnClick({R.id.iv_return, R.id.tv_speed, R.id.tv_intonation, R.id.tv_voice, R.id.tv_speaker, R.id.tv_change, R.id.tv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_speed:
                new MiddleDialog(mContext, mSpeedList, new MiddleDialog.MiddleInterface() {
                    @Override
                    public void onClick(int position) {
                        mTvSpeed.setText(mSpeedList.get(position));
                    }
                });
                break;
            case R.id.tv_intonation:
                new MiddleDialog(mContext, mIntonationList, new MiddleDialog.MiddleInterface() {
                    @Override
                    public void onClick(int position) {
                        mTvIntonation.setText(mIntonationList.get(position));
                    }
                });
                break;
            case R.id.tv_voice:
                new MiddleDialog(mContext, mVoiceList, new MiddleDialog.MiddleInterface() {
                    @Override
                    public void onClick(int position) {
                        mTvVoice.setText(mVoiceList.get(position));
                    }
                });
                break;
            case R.id.tv_speaker:
                new MiddleDialog(mContext, mSpeakerList, new MiddleDialog.MiddleInterface() {
                    @Override
                    public void onClick(int position) {
                        mTvSpeaker.setText(mSpeakerList.get(position));
                    }
                });
                break;
            case R.id.tv_change:
                voiceResult = "";
                change();
                break;
            case R.id.tv_download:
                if (TextUtils.isEmpty(voiceResult)) {
                    Toast.makeText(mContext, "请先转换！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Base64Utils.decoderCachBase64File(voiceResult, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp3");
                Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void change() {
        String text = mEtContent.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("text", text);
        map.put("speed", mTvSpeed.getText().toString());
        map.put("intonation", mTvIntonation.getText().toString());
        map.put("voice", mTvVoice.getText().toString());
        map.put("speaker", mSpeakMap.get(mTvSpeaker.getText().toString()));
        showLoadingDialog();
        HttpClient.getInstantce().post(url, HttpClient.getInstantce().getFormBody(map), new YdhInterface() {
            @Override
            public void onSuccess(String result) {
                cancelLoadingDialog();
                Log.e("结果：", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String voice = data.getString("voice");
                    voiceResult = voice.replace("\\", "").replace("data:audio/mp3;base64,", "");
                    Base64Utils.decoderCachBase64File(voiceResult, getCacheDir().getAbsolutePath() + "/1.mp3");
                    Uri parse = Uri.parse(getCacheDir().getAbsolutePath() + "/1.mp3");
                    MediaPlayer.create(mContext, parse).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                cancelLoadingDialog();
            }
        });
    }

    private HisHandler mHandler = new HisHandler(this);


    static class HisHandler extends Handler {
        WeakReference mWeakReference;

        public HisHandler(VoiceActivity mActivity) {
            this.mWeakReference = new WeakReference(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    String string = (String) msg.obj;

                } catch (Exception e) {

                }

            } else if (msg.what == 2) {

            }
        }
    }
}