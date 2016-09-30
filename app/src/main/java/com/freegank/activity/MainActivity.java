package com.freegank.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.freegank.R;
import com.freegank.adapter.MainAdapter;
import com.freegank.fragment.CategoryFragment;
import com.freegank.fragment.DailyRecommendFragment;
import com.freegank.fragment.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private MainAdapter mMainAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        DailyRecommendFragment dailyFG = new DailyRecommendFragment();

        CategoryFragment androidFG = new CategoryFragment();
        Bundle androidData = new Bundle();
        androidData.putString(CategoryFragment.CATEGORY, getString(R.string.category_android));
        androidFG.setArguments(androidData);

        CategoryFragment resourceFG = new CategoryFragment();
        Bundle resourceData = new Bundle();
        resourceData.putString(CategoryFragment.CATEGORY, getString(R.string.category_expend));
        resourceFG.setArguments(resourceData);

        CategoryFragment webFG = new CategoryFragment();
        Bundle webData = new Bundle();
        webData.putString(CategoryFragment.CATEGORY, getString(R.string.category_web));
        webFG.setArguments(webData);

        CategoryFragment videoFG = new CategoryFragment();
        Bundle videoData = new Bundle();
        videoData.putString(CategoryFragment.CATEGORY, getString(R.string.category_video));
        videoFG.setArguments(videoData);

        CategoryFragment randomFG = new CategoryFragment();
        Bundle randomData = new Bundle();
        randomData.putString(CategoryFragment.CATEGORY, getString(R.string.category_random));
        randomFG.setArguments(randomData);

        CategoryFragment iosFG = new CategoryFragment();
        Bundle iosData = new Bundle();
        iosData.putString(CategoryFragment.CATEGORY, getString(R.string.category_ios));
        iosFG.setArguments(iosData);

        WelfareFragment welfareFG = new WelfareFragment();

        List<Fragment> data = new ArrayList<>();
        data.add(dailyFG);
        data.add(androidFG);
        data.add(resourceFG);
        data.add(webFG);
        data.add(videoFG);
        data.add(randomFG);
        data.add(iosFG);
        data.add(welfareFG);

        List<String> tabTitle = new ArrayList<>();
        tabTitle.add(getString(R.string.category_daily));
        tabTitle.add(getString(R.string.category_android));
        tabTitle.add(getString(R.string.category_expend));
        tabTitle.add(getString(R.string.category_web));
        tabTitle.add(getString(R.string.category_video));
        tabTitle.add(getString(R.string.category_random));
        tabTitle.add(getString(R.string.category_ios));
        tabTitle.add(getString(R.string.category_welfare));
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
