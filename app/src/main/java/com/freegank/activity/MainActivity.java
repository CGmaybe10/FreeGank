package com.freegank.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.freegank.R;

public class MainActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private TabLayout mTabLayout;
    private RecyclerView mContentRY;

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
    }
}
