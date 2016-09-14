package com.freegank.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.freegank.R;
import com.freegank.adapter.DailyAdapter;
import com.freegank.adapter.MainAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.fragment.CategoryFragment;
import com.freegank.fragment.DailyFragment;
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

    private MainAdapter mMainAdapter;

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
        DailyFragment dailyFG = new DailyFragment();
        CategoryFragment oneFG = new CategoryFragment();
        CategoryFragment twoFG = new CategoryFragment();
        CategoryFragment threeFG = new CategoryFragment();
        CategoryFragment fiveFG = new CategoryFragment();
        CategoryFragment sixFG = new CategoryFragment();
        CategoryFragment sevenFG = new CategoryFragment();
        CategoryFragment eightFG = new CategoryFragment();
        CategoryFragment nineFG = new CategoryFragment();
        List<Fragment> data = new ArrayList<>();
        data.add(dailyFG);
        data.add(oneFG);
        data.add(twoFG);
        data.add(threeFG);
        data.add(fiveFG);
        data.add(sixFG);
        data.add(sevenFG);
        data.add(eightFG);
        data.add(nineFG);

        List<String> tabTitle = new ArrayList<>();
        tabTitle.add(getString(R.string.category_daily));
        tabTitle.add(getString(R.string.category_android));
        tabTitle.add(getString(R.string.category_app));
        tabTitle.add(getString(R.string.category_expend));
        tabTitle.add(getString(R.string.category_web));
        tabTitle.add(getString(R.string.category_video));
        tabTitle.add(getString(R.string.category_random));
        tabTitle.add(getString(R.string.category_welfare));
        tabTitle.add(getString(R.string.category_ios));
        mMainAdapter = new MainAdapter(getSupportFragmentManager(), data, tabTitle);
    }

    private void initView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.category_tab);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        ViewPager contentVP = (ViewPager) findViewById(R.id.tab_content_vp);
        contentVP.setAdapter(mMainAdapter);
        tabLayout.setupWithViewPager(contentVP);
    }
}
