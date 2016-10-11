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
import com.freegank.constant.DataStatus;
import com.freegank.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moubiao on 2016/9/13.
 * 每日推荐的adapter
 */
public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DailyOverviewData> mDailyData;
    private OnItemClickListener mClickListener;

    private View mDiffStatusView;

    public DailyAdapter(Context context, List<DailyOverviewData> dailyData) {
        mContext = context;
        mDailyData = dailyData;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDailyData.size() == 0) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return DataStatus.VIEW_TYPE_HAS_DATE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == DataStatus.VIEW_TYPE_HAS_DATE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.overview_item, parent, false);
            viewHolder = new DailyViewHolder(view);
        } else {
            if (mDiffStatusView == null) {
                mDiffStatusView = new View(mContext);
            }
            viewHolder = new DiffStatusViewHolder(mDiffStatusView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType != DataStatus.VIEW_TYPE_DIFF_STATUS) {
            DailyViewHolder vh = (DailyViewHolder) holder;
            DailyOverviewData data = mDailyData.get(position);
            Picasso.with(mContext)
                    .load(data.getContent())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_error)
                    .into(vh.mImageView);
            vh.mTitleTV.setText(data.getTitle());
            vh.mDateTV.setText(data.getPublishedAt());

            setListener(vh, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mDailyData.size() == 0) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return mDailyData.size();
        }
    }

    public void setDiffStatusView(View diffStatusView) {
        mDiffStatusView = diffStatusView;
    }

    private void setListener(final DailyViewHolder holder, final int position) {
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.OnItemClick(holder.mImageView, position);
            }
        });

        holder.mTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.OnItemClick(holder.mTitleTV, position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private class DailyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTitleTV;
        private TextView mDateTV;

        DailyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.overview_img);
            mTitleTV = (TextView) itemView.findViewById(R.id.overview_title_tx);
            mDateTV = (TextView) itemView.findViewById(R.id.overview_date_tx);
        }
    }

    private class DiffStatusViewHolder extends RecyclerView.ViewHolder {

        DiffStatusViewHolder(View itemView) {
            super(itemView);
        }
    }
}
