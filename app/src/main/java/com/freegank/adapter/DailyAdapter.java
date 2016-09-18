package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freegank.R;
import com.freegank.bean.DailyOverviewData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moubiao on 2016/9/13.
 * 每日推荐的adapter
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DailyOverviewData> mDailyData;

    public DailyAdapter(Context context, List<DailyOverviewData> dailyData) {
        mContext = context;
        mDailyData = dailyData;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.overview_item, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        DailyOverviewData data = mDailyData.get(position);
        Picasso.with(mContext)
                .load(data.getContent())
                .into(holder.mImageView);
        holder.mTitleTV.setText(data.getTitle());
        holder.mDateTV.setText(data.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return mDailyData.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTitleTV;
        private TextView mDateTV;

        public DailyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.overview_img);
            mTitleTV = (TextView) itemView.findViewById(R.id.overview_title_tx);
            mDateTV = (TextView) itemView.findViewById(R.id.overview_date_tx);
        }
    }
}
