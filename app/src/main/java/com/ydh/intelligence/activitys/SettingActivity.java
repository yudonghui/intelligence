package com.ydh.intelligence.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.revenuecat.purchases.Offerings;
import com.revenuecat.purchases.Package;
import com.revenuecat.purchases.Purchases;
import com.revenuecat.purchases.PurchasesError;
import com.revenuecat.purchases.interfaces.ReceiveOfferingsListener;
import com.ydh.intelligence.R;
import com.ydh.intelligence.SPUtils;
import com.ydh.intelligence.dialogs.MiddleDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydh
 * ca-app-pub-7520425448678486~9398465955
 * ca-app-pub-7520425448678486/5245536324
 * 测试ca-app-pub-3940256099942544/5224354917
 */
public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";
    private RewardedAd rewardedAd;
    private static final String AD_UNIT_ID = "ca-app-pub-7520425448678486/5245536324";
    private boolean isRewarded = false;//是否预加载完成
    private TextView mTvUse;
    private LinearLayout mLlAd;
    private TextView mTvModel;
    private ImageView mIvQuestion;
    private SeekBar mSeekBar;
    private TextView mTvRate;
    private List<String> mModelList = new ArrayList<>();
    private int maxTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mTvUse = findViewById(R.id.tv_use);
        mTvModel = findViewById(R.id.tv_model);
        mLlAd = findViewById(R.id.ll_ad);
        mIvQuestion = findViewById(R.id.iv_question);
        mSeekBar = findViewById(R.id.sb_progress);
        mTvRate = findViewById(R.id.tv_rate);
        mTvModel.setText(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.MODELS));
        maxTokens = getMaxTokens();
        mSeekBar.setMax(maxTokens);
        int progress = Integer.parseInt(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS));
        mSeekBar.setProgress(progress);
        mTvRate.setText(progress + "/" + maxTokens);
        mTvUse.setText(SPUtils.getCacheL(SPUtils.FILE_USER, SPUtils.USE_TOKENS) + "/" + 1000);
        loadRewardedAd();
        initDown();
        initListener();
    }


    private void initDown() {
        mModelList.add("text-davinci-003");
        mModelList.add("text-curie-001");
        mModelList.add("text-babbage-001");
        mModelList.add("text-ada-001");
    }


    private void initListener() {
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SettingActivity.this, AdActivity.class));
                startActivity(new Intent(SettingActivity.this, AdGoogleActivity.class));
            }
        });
        mLlAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardedVideo();
            }
        });
        mTvModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MiddleDialog(mContext, mModelList, new MiddleDialog.MiddleInterface() {

                    @Override
                    public void onClick(int position) {
                        SPUtils.setCache(SPUtils.FILE_USER, SPUtils.MODELS, mModelList.get(position));
                        mTvModel.setText(mModelList.get(position));
                        maxTokens = getMaxTokens();
                        String currentTokens = SPUtils.getCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS);
                        if (Integer.parseInt(currentTokens) > maxTokens) {
                            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS, "1000");
                            mTvRate.setText(1000 + "/" + maxTokens);
                        } else {
                            mTvRate.setText(currentTokens + "/" + maxTokens);
                        }
                        mSeekBar.setMax(getMaxTokens());
                    }
                });
            }
        });
        mIvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explainDialog();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvRate.setText(progress + "/" + maxTokens);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.CURRENT_TOKENS, seekBar.getProgress() + "");
            }
        });
    }

    private int getMaxTokens() {
        return "text-davinci-003".equals(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.MODELS)) ? 3999 : 2000;
    }

    private void explainDialog() {
        final Dialog mDialog = new Dialog(mContext, R.style.HintDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View view = View.inflate(mContext, R.layout.dialog_explain, null);
        ImageView ivDelete = view.findViewById(R.id.iv_delete);
        TextView tvDavinci = view.findViewById(R.id.tv_davinci);
        TextView tvCurie = view.findViewById(R.id.tv_curie);
        TextView tvBabbage = view.findViewById(R.id.tv_babbage);
        TextView tvAda = view.findViewById(R.id.tv_ada);
        String modelDavinci = getString(R.string.model_davinci);
        String modelCurie = getString(R.string.model_curie);
        String modelBabbage = getString(R.string.model_babbage);
        String modelAda = getString(R.string.model_ada);
        ForegroundColorSpan span = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_black));
        SpannableString ssDavinci = new SpannableString("text-davinci-003  " + modelDavinci);
        ssDavinci.setSpan(span, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString ssCurie = new SpannableString("text-curie-001  " + modelCurie);
        ssCurie.setSpan(span, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString ssBabbage = new SpannableString("text-babbage-001  " + modelBabbage);
        ssBabbage.setSpan(span, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString ssAda = new SpannableString("text-ada-001  " + modelAda);
        ssAda.setSpan(span, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDavinci.setText(ssDavinci);
        tvCurie.setText(ssCurie);
        tvBabbage.setText(ssBabbage);
        tvAda.setText(ssAda);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(view);
        mDialog.show();
    }

    private void buy() {
        Purchases.getSharedInstance().getOfferings(new ReceiveOfferingsListener() {
            @Override
            public void onReceived(@NonNull Offerings offerings) {
                if (offerings.getCurrent() != null) {
                    List<Package> availablePackages = offerings.getCurrent().getAvailablePackages();
                    // Display packages for sale
                }
            }

            @Override
            public void onError(@NonNull PurchasesError error) {
                // An error occurred
            }
        });
    }


    private void loadRewardedAd() {
        if (rewardedAd == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
                    this,
                    AD_UNIT_ID,
                    adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.e(TAG, loadAdError.getMessage());
                            isRewarded = false;
                            rewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd ad) {
                            isRewarded = true;
                            rewardedAd = ad;
                            Log.e(TAG, "onAdLoaded");
                        }
                    });
        }
    }

    private void showRewardedVideo() {
        if (!isRewarded) return;
        if (rewardedAd == null) {
            Log.e("TAG", "The rewarded ad wasn't ready yet.");
            return;
        }

        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {//全屏展示
                        isRewarded = false;
                        Log.e(TAG, "onAdShowedFullScreenContent");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {//全屏展示的失败
                        Log.e(TAG, "onAdFailedToShowFullScreenContent");
                        rewardedAd = null;
                        isRewarded = false;
                        loadRewardedAd();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {//广告页面消失后的回调
                        rewardedAd = null;
                        Log.e(TAG, "onAdDismissedFullScreenContent");
                        isRewarded = false;
                        loadRewardedAd();
                    }
                });
        rewardedAd.show(
                SettingActivity.this,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.e("TAG", "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                    }
                });
    }
}