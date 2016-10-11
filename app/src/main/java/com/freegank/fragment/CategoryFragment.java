package com.freegank.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.freegank.R;
import com.freegank.activity.ContentDetailActivity;
import com.freegank.adapter.CategoryAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DetailData;
import com.freegank.bean.IntentConstant;
import com.freegank.constant.DataStatus;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;
import com.freegank.interfaces.OnItemClickListener;
import com.freegank.view.recyclerViewHelper.CommonItemDecoration;
import com.freegank.view.recyclerViewHelper.RCDiffStatusUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/14.
 * android,ios等分类页面的fragment
 */
public class CategoryFragment extends LazyFragment<DetailData> implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener,
        View.OnClickListener {
    private final String TAG = "moubiao";
    public static final String CATEGORY = "category";


    private Context mContext;
    private String mCategory;
    private int mQuantity = 10;
    private int mPage = 1;

    private SwipeToLoadLayout mLoadLayout;
    private RecyclerView mRecyclerView;
    private View mCategoryDiffView;
    private TextView mNoDataTV;
    private ImageButton mRefreshBT;

    RCDiffStatusUtil mDiffStatusUtil;
    private CategoryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle date = getArguments();
        mCategory = date.getString(CATEGORY);

        mContext = getActivity();
        mAdapter = new CategoryAdapter(getContext(), mData);
        mAdapter.setOnItemClickListener(this);

        mDiffStatusUtil = new RCDiffStatusUtil(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.category_refresh);
        mLoadLayout.setOnRefreshListener(this);
        mLoadLayout.setOnLoadMoreListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new CommonItemDecoration(getResources().getDimensionPixelSize(R.dimen.category_divider_height)));

        mCategoryDiffView = mDiffStatusUtil.getStatusView(mRecyclerView);
        mNoDataTV = (TextView) mCategoryDiffView.findViewById(R.id.no_data_tv);
        mRefreshBT = (ImageButton) mCategoryDiffView.findViewById(R.id.refresh_bt);
        mAdapter.setDiffView(mCategoryDiffView);
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
    public void onRefresh() {
        mQuantity = 10;
        mPage = 1;
        mData.clear();
        getRemoteData(true);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        getRemoteData(false);
    }

    /**
     * 获取服务器数据
     */
    private void getRemoteData(final boolean refresh) {
        GankApiService gankApiService = RetrofitHelper.getRetrofitService(mContext, GankApiService.class);
        Call<BaseData<DetailData>> call = gankApiService.getCategoryData(mCategory, String.valueOf(mQuantity), String.valueOf(mPage));
        call.enqueue(new Callback<BaseData<DetailData>>() {
            @Override
            public void onResponse(Call<BaseData<DetailData>> call, Response<BaseData<DetailData>> response) {
                hideProgressBar(refresh);
                BaseData<DetailData> result = response.body();
                mData.addAll(result.getResults());
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

    /**
     * 隐藏刷新或者加载更多的进度条
     */
    private void hideProgressBar(boolean refresh) {
        if (refresh) {
            mLoadLayout.setRefreshing(false);
        } else {
            mLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra(IntentConstant.CONTENT_URL, mData.get(position).getUrl());
        startActivity(intent);
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
