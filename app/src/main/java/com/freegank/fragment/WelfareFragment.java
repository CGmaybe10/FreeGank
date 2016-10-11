package com.freegank.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.freegank.R;
import com.freegank.activity.MeiZhiActivity;
import com.freegank.adapter.WelfareAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DetailData;
import com.freegank.bean.IntentConstant;
import com.freegank.constant.DataStatus;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;
import com.freegank.interfaces.OnItemClickListener;
import com.freegank.view.recyclerViewHelper.CommonItemDecoration;
import com.freegank.view.recyclerViewHelper.RCDiffStatusUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/18.
 * 福利界面的fragment
 */
public class WelfareFragment extends LazyFragment<DetailData> implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener,
        View.OnClickListener {
    private final String TAG = "moubiao";

    private Context mContext;
    private int mQuantity = 24;
    private int mPage = 1;

    private SwipeToLoadLayout mLoadLayout;
    private RecyclerView mRecyclerView;
    private View mWelfareDiffView;
    private TextView mNoDataTV;
    private ImageButton mRefreshBT;

    private WelfareAdapter mAdapter;

    private RCDiffStatusUtil mDiffStatusUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        mContext = getContext();
        mAdapter = new WelfareAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(this);

        mDiffStatusUtil = new RCDiffStatusUtil(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welfare_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.welfare_refresh);
        mLoadLayout.setOnRefreshListener(this);
        mLoadLayout.setOnLoadMoreListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new CommonItemDecoration(getResources().getDimensionPixelSize(R.dimen.welfare_divider_height)));

        mWelfareDiffView = mDiffStatusUtil.getStatusView(mLoadLayout);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = mWelfareDiffView.getLayoutParams();
                layoutParams.width = mRecyclerView.getWidth();
                mWelfareDiffView.setLayoutParams(layoutParams);
            }
        });
        mNoDataTV = (TextView) mWelfareDiffView.findViewById(R.id.no_data_tv);
        mRefreshBT = (ImageButton) mWelfareDiffView.findViewById(R.id.refresh_bt);
        mAdapter.setWelfareDiffView(mWelfareDiffView);
        mRecyclerView.setAdapter(mAdapter);

        setListener();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setListener() {
        mNoDataTV.setOnClickListener(this);
        mRefreshBT.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        getRemoteData(true);
    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent intent = new Intent(mContext, MeiZhiActivity.class);
        intent.putExtra(IntentConstant.MEI_ZHI_URL, mData.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mQuantity = 24;
        mPage = 1;
        mData.clear();
        getRemoteData(true);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        getRemoteData(false);
    }

    private void getRemoteData(final boolean refresh) {
        GankApiService gankApiService = RetrofitHelper.getRetrofitService(mContext, GankApiService.class);
        Call<BaseData<DetailData>> call = gankApiService.getCategoryData(mContext.getString(R.string.category_welfare),
                String.valueOf(mQuantity), String.valueOf(mPage));
        call.enqueue(new Callback<BaseData<DetailData>>() {
            @Override
            public void onResponse(Call<BaseData<DetailData>> call, Response<BaseData<DetailData>> response) {
                hideProgressBar(refresh);
                BaseData<DetailData> result = response.body();
                List<DetailData> data = result.getResults();
                mData.addAll(data);
                if (mData.size() == 0) {
                    mDiffStatusUtil.setStatus(DataStatus.Status.EMPTY);
                } else {
                    mDiffStatusUtil.setStatus(DataStatus.Status.HAS_DATA);
                }
            }

            @Override
            public void onFailure(Call<BaseData<DetailData>> call, Throwable t) {
                hideProgressBar(refresh);
                mDiffStatusUtil.setStatus(DataStatus.Status.ERROR);
                t.printStackTrace();
            }
        });
    }

    private void hideProgressBar(boolean refresh) {
        if (refresh) {
            mLoadLayout.setRefreshing(false);
        } else {
            mLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_data_tv:
                mDiffStatusUtil.setStatus(DataStatus.Status.LOADING);
                break;
            case R.id.refresh_bt:
                mDiffStatusUtil.setStatus(DataStatus.Status.LOADING);
                break;
            default:
                break;
        }
        getRemoteData(true);
    }
}
