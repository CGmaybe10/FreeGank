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

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.freegank.R;
import com.freegank.activity.ContentDetailActivity;
import com.freegank.adapter.CategoryAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DetailData;
import com.freegank.bean.IntentConstant;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;
import com.freegank.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/14.
 * android,ios等分类页面的fragment
 */
public class CategoryFragment extends LazyFragment implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener {
    private final String TAG = "moubiao";
    public static final String CATEGORY = "category";


    private Context mContext;
    private String mCategory;
    private int mQuantity = 10;
    private int mPage = 1;

    private SwipeToLoadLayout mLoadLayout;
    private RecyclerView mRecyclerView;

    private List<DetailData> mCategoryData;
    private CategoryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle date = getArguments();
        mCategory = date.getString(CATEGORY);

        mContext = getContext();
        mCategoryData = new ArrayList<>();
        mAdapter = new CategoryAdapter(getContext(), mCategoryData);
        mAdapter.setOnItemClickListener(this);
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
        mRecyclerView.setAdapter(mAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {
        mLoadLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        mQuantity = 10;
        mPage = 1;
        mCategoryData.clear();
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
                mCategoryData.addAll(result.getResults());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseData<DetailData>> call, Throwable t) {
                hideProgressBar(refresh);
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
        intent.putExtra(IntentConstant.CONTENT_URL, mCategoryData.get(position).getUrl());
        startActivity(intent);
    }
}
