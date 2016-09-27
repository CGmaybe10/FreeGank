package com.freegank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by moubiao on 2016/9/27.
 * 懒加载的fragment
 */

public abstract class LazyFragment extends Fragment {
    private final String TAG = "moubiao";

    private boolean isFirst = true;//是否是第一次进入fragment
    private boolean isVisible = false;//fragment是否可见
    private boolean isPrepared = false;//view是否加载完成

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisible = isVisibleToUser;
        if (isVisible) {
            onVisible();
        } else {
            onInvisible();
        }

        Log.d(TAG, "setUserVisibleHint: is visible = " + isVisible);
    }

    /**
     * 子类在初始化完view后使用super调用该方法
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        onVisible();
    }

    /**
     * fragment可见时
     */
    private void onVisible() {
        if (isFirst && isVisible && isPrepared) {
            loadData();
            isFirst = false;
        }
    }

    /**
     * fragment不可见时
     */
    protected void onInvisible() {

    }

    /**
     * 加载数据
     */
    protected abstract void loadData();
}
