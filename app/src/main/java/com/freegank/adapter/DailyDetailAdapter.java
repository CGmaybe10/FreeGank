package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freegank.R;
import com.freegank.bean.DetailData;
import com.freegank.interfaces.OnItemClickListener;
import com.freegank.util.DisplayUtil;

import java.util.List;

/**
 * Created by moubiao on 2016/10/8.
 * 每日推荐详情的adapter
 */

public class DailyDetailAdapter extends RecyclerView.Adapter<DailyDetailAdapter.DailyDetailViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mDetailData;

    private OnItemClickListener mItemClickListener;

    public DailyDetailAdapter(Context context, List<DetailData> detailData) {
        mContext = context;
        mDetailData = detailData;
    }

    @Override
    public DailyDetailAdapter.DailyDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.daily_detail_item, parent, false);
        return new DailyDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DailyDetailAdapter.DailyDetailViewHolder holder, int position) {
        if (position == 0) {
            holder.mCategoryTV.setPadding(DisplayUtil.dp2px(mContext, 10f), DisplayUtil.dp2px(mContext, 16f),
                    DisplayUtil.dp2px(mContext, 10f), DisplayUtil.dp2px(mContext, 5f));
        }

        DetailData data = mDetailData.get(position);
        if (position == 0) {
            showCategoryTitle(holder.mCategoryTV, true);
        } else {
            if (data.getType().equals(mDetailData.get(position - 1).getType())) {
                showCategoryTitle(holder.mCategoryTV, false);
            } else {
                showCategoryTitle(holder.mCategoryTV, true);
            }
        }
        holder.mCategoryTV.setText(data.getType());
        holder.mDescribeTV.setText(data.getDesc());

        if (mItemClickListener == null) {
            return;
        }
        holder.mDescribeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.OnItemClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDetailData.size();
    }

    /**
     * 是否显示分类标题
     */
    private void showCategoryTitle(View view, boolean display) {
        if (display) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class DailyDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView mCategoryTV;
        private TextView mDescribeTV;

        DailyDetailViewHolder(View itemView) {
            super(itemView);
            mCategoryTV = (TextView) itemView.findViewById(R.id.daily_detail_category_tv);
            mDescribeTV = (TextView) itemView.findViewById(R.id.daily_detail_describe_tv);
        }
    }
}
