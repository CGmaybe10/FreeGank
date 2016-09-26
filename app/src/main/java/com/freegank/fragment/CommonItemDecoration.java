package com.freegank.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by moubiao on 2016/9/26.
 * 通用的 recyclerView的分隔符
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private String TAG = "moubiao";

    private int mDivHeight;

    public CommonItemDecoration(int divHeight) {
        mDivHeight = divHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (GridLayoutManager.VERTICAL == ((GridLayoutManager) layoutManager).getOrientation()) {
                setStaggeredGridVerticalDivider(outRect, view, parent);
            } else {
                setStaggeredGridHorizontalDivider(outRect, view, parent, state);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (StaggeredGridLayoutManager.VERTICAL == ((StaggeredGridLayoutManager) layoutManager).getOrientation()) {
                setStaggeredGridVerticalDivider(outRect, view, parent);
            } else {
                setStaggeredGridHorizontalDivider(outRect, view, parent, state);
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (LinearLayoutManager.VERTICAL == ((LinearLayoutManager) layoutManager).getOrientation()) {
                setVerticalDivider(outRect, view, parent);
            } else {
                setHorizontalDivider(outRect, view, parent, state);
            }
        }
    }

    /**
     * 设置LinearLayout垂直布局的divider
     */
    private void setVerticalDivider(Rect outRect, View view, RecyclerView parent) {
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.set(mDivHeight, mDivHeight, mDivHeight, mDivHeight);
        } else {
            outRect.set(mDivHeight, 0, mDivHeight, mDivHeight);
        }
    }

    /**
     * 设置LinearLayout水平布局的divider
     */
    private void setHorizontalDivider(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == state.getItemCount() - 1) {
            outRect.set(mDivHeight, mDivHeight, mDivHeight, mDivHeight);
        } else {
            outRect.set(mDivHeight, mDivHeight, 0, mDivHeight);
        }
    }

    /**
     * 设置GridLayout和StaggeredLayout垂直布局的divider
     */
    private void setStaggeredGridVerticalDivider(Rect outRect, View view, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int position = parent.getChildLayoutPosition(view);
        if (position < spanCount) {
            outRect.set(mDivHeight, mDivHeight, 0, mDivHeight);
        } else {
            outRect.set(mDivHeight, 0, 0, mDivHeight);
        }
    }

    /**
     * 设置GridLayout和StaggeredLayout水平布局的divider
     */
    private void setStaggeredGridHorizontalDivider(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == state.getItemCount()
                || parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.set(mDivHeight, 0, mDivHeight, mDivHeight);
        } else {
            outRect.set(mDivHeight, 0, 0, mDivHeight);
        }
    }


    /**
     * 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}
