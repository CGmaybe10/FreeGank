package com.freegank.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by moubiao on 2016/9/13.
 * 每日推荐的adapter
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {
        public DailyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
