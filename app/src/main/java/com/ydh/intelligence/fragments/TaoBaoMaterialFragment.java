package com.ydh.intelligence.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ydh.intelligence.R;
import com.ydh.intelligence.activitys.WebWiewActivity;
import com.ydh.intelligence.adapters.MaterialFormAdapter;
import com.ydh.intelligence.common.Constant;
import com.ydh.intelligence.common.SpaceItemDecoration;
import com.ydh.intelligence.common.bases.BaseFragment;
import com.ydh.intelligence.common.bases.ErrorEntity;
import com.ydh.intelligence.dialogs.CommonDialog;
import com.ydh.intelligence.entitys.MaterialContentEntity;
import com.ydh.intelligence.entitys.MaterialEntity;
import com.ydh.intelligence.entitys.TbCodeEntity;
import com.ydh.intelligence.interfaces.ViewInterface;
import com.ydh.intelligence.networks.HttpClient;
import com.ydh.intelligence.utils.ClipboardUtils;
import com.ydh.intelligence.utils.CommonUtil;
import com.ydh.intelligence.utils.DateFormtUtils;
import com.ydh.intelligence.utils.HttpMd5;
import com.ydh.intelligence.utils.MsgCode;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh on 2022/8/16
 */
public class TaoBaoMaterialFragment extends BaseFragment {

    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String material_id;
    private CommonAdapter<MaterialEntity> mMaterialAdapter;
    private ArrayList<MaterialEntity> mMaterialList = new ArrayList<>();

    public static TaoBaoMaterialFragment newInstance(String material_id) {
        Bundle args = new Bundle();
        args.putString("material_id", material_id);
        TaoBaoMaterialFragment fragment = new TaoBaoMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tao_bao_material, container, false);
        unbinder = ButterKnife.bind(this, view);
        material_id = getArguments().getString("material_id");
        initAdapter();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page_no = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page_no++;
                initData();
            }
        });
    }

    private int page_no = 1;
    private int page_size = 20;

    private void initData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.dg.optimus.material");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("adzone_id", Constant.ADZONE_ID);
        map.put("material_id", material_id);
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("page_no", page_no);
        map.put("page_size", page_size);
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<MaterialContentEntity> call = HttpClient.getHttpApiTb().getMaterailTb(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<MaterialContentEntity>() {
            @Override
            public void onResponse(Call<MaterialContentEntity> call, Response<MaterialContentEntity> response) {
                stopOver(refreshLayout);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        List<MaterialEntity> map_data = response.body().getResult_list();
                        if (map_data != null && map_data.size() > 0) {
                            if (page_no == 1) {
                                mMaterialList.clear();
                            }
                            mMaterialList.addAll(map_data);
                            mMaterialAdapter.notifyDataSetChanged();
                        } else {
                            CommonUtil.showToast("暂无数据");
                        }
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MaterialContentEntity> call, Throwable t) {
                stopOver(refreshLayout);
            }
        });

    }

    /**
     *
     */
    private void initAdapter() {
        mMaterialAdapter = new MaterialFormAdapter(mContext, R.layout.item_tao_bao, mMaterialList);
        rvMaterial.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        rvMaterial.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvMaterial.setAdapter(mMaterialAdapter);
    }

    /**
     * 淘宝客-公用-淘口令生成
     *
     * @param materialEntity
     */
    private void tpwdCreate(MaterialEntity materialEntity, String price, String originPrice) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.tpwd.create");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("url", "https:" + materialEntity.getClick_url());
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbCodeEntity> call = HttpClient.getHttpApiTb().getMaterailTbCode(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbCodeEntity>() {
            @Override
            public void onResponse(Call<TbCodeEntity> call, Response<TbCodeEntity> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        TbCodeEntity.DataBean data = response.body().getData();
                        if (data != null) {
                            new CommonDialog.Builder()
                                    .message("【" + materialEntity.getTitle() + "】\n【价格】" + originPrice + "\n【券后价】" + price)
                                    .message2(data.getPassword_simple())
                                    .leftBtn("复制文案", new ViewInterface() {
                                        @Override
                                        public void onClick(View view) {
                                            ClipboardUtils.setClipboard(data.getModel());
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            intent.setClassName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    })
                                    .rightBtn("领优惠券", new ViewInterface() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("url", "https:" + materialEntity.getCoupon_share_url());
                                            startActivity(WebWiewActivity.class, bundle);
                                        }
                                    }).build(mContext);
                        } else {
                            CommonUtil.showToast("暂无数据");
                        }
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<TbCodeEntity> call, Throwable t) {
            }
        });
    }
}
