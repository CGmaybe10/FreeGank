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
import com.freegank.activity.DailyDetailActivity;
import com.freegank.activity.MeiZhiActivity;
import com.freegank.adapter.DailyAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.bean.IntentConstant;
import com.freegank.constant.DataStatus;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;
import com.freegank.interfaces.OnItemClickListener;
import com.freegank.util.DateUtil;
import com.freegank.view.recyclerViewHelper.CommonItemDecoration;
import com.freegank.view.recyclerViewHelper.RCDiffStatusUtil;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/14.
 * 每日推荐的fragment
 */
public class DailyRecommendFragment extends LazyFragment<DailyOverviewData> implements OnRefreshListener, OnLoadMoreListener,
        OnItemClickListener, View.OnClickListener {
    private final String TAG = "moubiao";
    private final String REGEX = "\\b((https|http|ftp|rtsp|mms):\\/\\/)[^\\s]+.(jpg|jpeg|png)\\b";

    private Context mContext;
    private int mQuantity = 10;
    private int mPage = 1;

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mContentRY;
    private RCDiffStatusUtil mDiffStatusUtil;
    private View mDiffStatusView;
    private TextView mNoDataTV;
    private ImageButton mRefreshBT;

    private DailyAdapter mDailyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mContext = getActivity();
        mDailyAdapter = new DailyAdapter(mContext, mData);
        mDailyAdapter.setOnItemClickListener(this);

        mDiffStatusUtil = new RCDiffStatusUtil(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        mContentRY = (RecyclerView) view.findViewById(R.id.swipe_target);
        mContentRY.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mContentRY.addItemDecoration(new CommonItemDecoration(getResources().getDimensionPixelSize(R.dimen.daily_divider_height)));

        mDiffStatusView = mDiffStatusUtil.getStatusView(mContentRY);
        mNoDataTV = (TextView) mDiffStatusView.findViewById(R.id.no_data_tv);
        mRefreshBT = (ImageButton) mDiffStatusView.findViewById(R.id.refresh_bt);

        mDailyAdapter.setDiffStatusView(mDiffStatusView);
        mContentRY.setAdapter(mDailyAdapter);

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
        GankApiService gankService = RetrofitHelper.getRetrofitService(mContext, GankApiService.class);
        Call<BaseData<DailyOverviewData>> call = gankService.getDailyOverview(String.valueOf(mQuantity), String.valueOf(mPage));
        call.enqueue(new Callback<BaseData<DailyOverviewData>>() {
            @Override
            public void onResponse(Call<BaseData<DailyOverviewData>> call, Response<BaseData<DailyOverviewData>> response) {
                hideProgressBar(refresh);
                BaseData<DailyOverviewData> result = response.body();
                List<DailyOverviewData> dailyList = result.getResults();

                for (DailyOverviewData daily : dailyList) {
                    //转化标题
                    String title = daily.getContent();
                    Pattern pattern = Pattern.compile(REGEX);
                    Matcher matcher = pattern.matcher(title);
                    if (matcher.find()) {
                        daily.setContent(matcher.group());
                    } else {
                        daily.setContent(null);
                    }

                    //转化日期
                    Date date = DateUtil.string2Date(daily.getPublishedAt(), "yyyy-MM-dd");
                    String dateStr = DateUtil.date2String(date, "yyyy/MM/dd");
                    daily.setPublishedAt(dateStr);
                }
                mData.addAll(dailyList);
                if (mData.size() == 0) {
                    mDiffStatusUtil.setStatus(DataStatus.Status.EMPTY);
                } else {
                    mDiffStatusUtil.setStatus(DataStatus.Status.HAS_DATA);
                }
            }

            @Override
            public void onFailure(Call<BaseData<DailyOverviewData>> call, Throwable t) {
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
            swipeToLoadLayout.setRefreshing(false);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.overview_img:
                intent.setClass(mContext, MeiZhiActivity.class);
                intent.putExtra(IntentConstant.MEI_ZHI_URL, mData.get(position).getContent());
                break;
            case R.id.overview_title_tx:
                intent.setClass(mContext, DailyDetailActivity.class);
                intent.putExtra(IntentConstant.MEI_ZHI_URL, mData.get(position).getContent());
                intent.putExtra(IntentConstant.DAILY_DATE, mData.get(position).getPublishedAt());
                break;
            default:
                break;
        }
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
