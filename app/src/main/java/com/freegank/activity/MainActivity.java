package com.freegank.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.freegank.R;
import com.freegank.adapter.DailyAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.http.GankApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {
    private final String TAG = "moubiao";
    private final String REGEX = "\\b((https|http|ftp|rtsp|mms):\\/\\/)[^\\s]+.(jpg|jpeg|png)\\b";

    private TabLayout mTabLayout;
    private RecyclerView mContentRY;
    private DailyAdapter mDailyAdapter;
    private List<DailyOverviewData> mDailyData;

    @Override
    public int onLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        mDailyData = new ArrayList<>();
        mDailyAdapter = new DailyAdapter(this, mDailyData);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.category_tab);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_daily)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_android)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_app)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_expend)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_web)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_video)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_random)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_welfare)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.category_ios)));

        mContentRY = (RecyclerView) findViewById(R.id.category_ry);
        mContentRY.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mContentRY.setAdapter(mDailyAdapter);

        Button button = (Button) findViewById(R.id.request_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GankApiService gankService = retrofit.create(GankApiService.class);
                Call<BaseData<DailyOverviewData>> call = gankService.getDailyOverview(String.valueOf(10), String.valueOf(1));
                call.enqueue(new Callback<BaseData<DailyOverviewData>>() {
                    @Override
                    public void onResponse(Call<BaseData<DailyOverviewData>> call, Response<BaseData<DailyOverviewData>> response) {
                        BaseData<DailyOverviewData> data = response.body();
                        List<DailyOverviewData> daily = data.getResults();

                        for (DailyOverviewData da : daily) {
                            String s = da.getContent();

                            Pattern pattern = Pattern.compile(REGEX);
                            Matcher matcher = pattern.matcher(s);

                            if (matcher.find()) {
                                da.setContent(matcher.group());
//                                Log.d(TAG, "onClick: Found value0: " + matcher.group());
                            } else {
                                da.setContent(null);
                            }
                        }
                        DailyOverviewData d = daily.get(0);
                        String content = d.getContent();
                        mDailyData.addAll(daily);
                        mDailyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseData<DailyOverviewData>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
