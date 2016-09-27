package com.freegank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.freegank.R;
import com.freegank.adapter.WelfareAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DetailData;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/18.
 * 福利界面的fragment
 */
public class WelfareFragment extends LazyFragment implements OnRefreshListener, OnLoadMoreListener {
    private final String TAG = "moubiao";

    private Context mContext;
    private int mQuantity = 24;
    private int mPage = 1;

    private SwipeToLoadLayout mLoadLayout;
    private RecyclerView mRecyclerView;

    private List<DetailData> mData;
    private WelfareAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        mContext = getContext();
        mData = new ArrayList<>();
        mAdapter = new WelfareAdapter(mContext, mData);
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
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {
        mLoadLayout.setRefreshing(true);
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
            }

            @Override
            public void onFailure(Call<BaseData<DetailData>> call, Throwable t) {
                hideProgressBar(refresh);
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
}
