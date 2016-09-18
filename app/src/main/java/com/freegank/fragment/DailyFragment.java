package com.freegank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.freegank.R;
import com.freegank.adapter.DailyAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/14.
 * 每日推荐的fragment
 */
public class DailyFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    private final String TAG = "moubiao";
    private final String REGEX = "\\b((https|http|ftp|rtsp|mms):\\/\\/)[^\\s]+.(jpg|jpeg|png)\\b";

    private Context mContext;
    private int mQuantity = 10;
    private int mPage = 1;

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mContentRY;

    private DailyAdapter mDailyAdapter;
    private List<DailyOverviewData> mDailyData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        mContentRY = (RecyclerView) view.findViewById(R.id.swipe_target);
        mContentRY.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mContentRY.setAdapter(mDailyAdapter);
    }

    private void initData() {
        mContext = getActivity();
        mDailyData = new ArrayList<>();
        mDailyAdapter = new DailyAdapter(mContext, mDailyData);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mDailyData.clear();
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
        GankApiService gankService = RetrofitHelper.getRetrofitService(getContext(), GankApiService.class);
        Call<BaseData<DailyOverviewData>> call = gankService.getDailyOverview(String.valueOf(mQuantity), String.valueOf(mPage));
        call.enqueue(new Callback<BaseData<DailyOverviewData>>() {
            @Override
            public void onResponse(Call<BaseData<DailyOverviewData>> call, Response<BaseData<DailyOverviewData>> response) {
                hideProgressBar(refresh);
                BaseData<DailyOverviewData> result = response.body();
                List<DailyOverviewData> dailyList = result.getResults();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
                    try {
                        Date date = format.parse(daily.getPublishedAt());
                        String transDate = format.format(date);
                        daily.setPublishedAt(transDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                mDailyData.addAll(dailyList);
                mDailyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseData<DailyOverviewData>> call, Throwable t) {
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
            swipeToLoadLayout.setRefreshing(false);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
