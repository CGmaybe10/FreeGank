package com.freegank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by moubiao on 2016/9/14.
 * 主界面中viewpager的adapter
 */
public class MainAdapter extends FragmentPagerAdapter {
    private final String TAG = "moubiao";

    private List<Fragment> mPageData;
    private List<String> mTitle;

    public MainAdapter(FragmentManager fm, List<Fragment> pageData, List<String> title) {
        super(fm);
        mPageData = pageData;
        mTitle = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mPageData.get(position);
    }

    @Override
    public int getCount() {
        return mPageData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
