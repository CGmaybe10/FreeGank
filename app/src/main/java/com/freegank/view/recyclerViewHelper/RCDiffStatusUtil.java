package com.freegank.view.recyclerViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freegank.R;
import com.freegank.constant.DataStatus;

/**
 * Created by moubiao on 2016/10/11.
 * RecyclerView不同状态的工具类
 */

public class RCDiffStatusUtil {
    private final String TAG = "moubiao";

    private Context mContext;

    private View mDiffStatusView;
    private View mErrorView, mEmptyView, mLoadingView;

    public RCDiffStatusUtil(Context context) {
        mContext = context;
    }

    public View getStatusView(ViewGroup parent) {
        mDiffStatusView = LayoutInflater.from(mContext).inflate(R.layout.diff_status_layout, parent, false);
        mErrorView = mDiffStatusView.findViewById(R.id.status_error);
        mEmptyView = mDiffStatusView.findViewById(R.id.status_empty);
        mLoadingView = mDiffStatusView.findViewById(R.id.status_loading);

        return mDiffStatusView;
    }

    /**
     * 设置RecyclerView的状态
     */
    public void setStatus(DataStatus.Status status) {
        switch (status) {
            case LOADING:
                setLoadingDataStatus();
                break;
            case EMPTY:
                setNoDataStatus();
                break;
            case ERROR:
                setErrorStatus();
                break;
            default:
                mDiffStatusView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 正在加载数据状态
     */
    private void setLoadingDataStatus() {
        mLoadingView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    /**
     * 空数据状态
     */
    private void setNoDataStatus() {
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    /**
     * 数据加载出错状态
     */
    private void setErrorStatus() {
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

}
