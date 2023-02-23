package com.ydh.intelligence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ydh.intelligence.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class MiddleDialog {
    private final Dialog mDialog;
    private ListView mListView;
    private TextView noData;
    private TextView tvTitle;
    private MiddleInterface mListener;

    public MiddleDialog(Context mContext, final List<String> mDatas, final MiddleInterface mListener) {
        mDialog = new Dialog(mContext, R.style.HintDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View view = View.inflate(mContext, R.layout.dialog_middle, null);
        mListView = view.findViewById(R.id.list_view);
        noData = view.findViewById(R.id.no_data);
        tvTitle = view.findViewById(R.id.tv_title);
        mDialog.setContentView(view);
        mDialog.show();
        if (mDatas == null || mDatas.size() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
        final CommonAdapter<String> mCommonAdapter = new CommonAdapter<String>(mContext, R.layout.item_middle_dialog, mDatas) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.text, item);
            }
        };
        mListView.setAdapter(mCommonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDialog.dismiss();
                mListener.onClick(position);
            }
        });
    }

    public interface MiddleInterface {
        void onClick(int position);
    }
}
