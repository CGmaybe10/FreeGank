package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freegank.R;
import com.freegank.bean.DetailData;

import java.util.List;

/**
 * Created by moubiao on 2016/10/8.
 * 每日推荐详情的adapter
 */

public class DailyDetailAdapter extends RecyclerView.Adapter<DailyDetailAdapter.DailyDetailViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mDetailData;
    private String mType = "";

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
    public void onBindViewHolder(DailyDetailAdapter.DailyDetailViewHolder holder, int position) {
        DetailData data = mDetailData.get(position);
        if (!mType.equals(data.getType())) {
            showCategoryTitle(holder.mCategoryTV, true);
            holder.mCategoryTV.setText(data.getType());
        } else {
            showCategoryTitle(holder.mCategoryTV, false);
        }
        mType = data.getType();
        holder.mDescribeTV.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return mDetailData.size();
    }

    /**
     * 是否显示分类标题
     */
    private void showCategoryTitle(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
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
