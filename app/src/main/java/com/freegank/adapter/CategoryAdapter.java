package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freegank.R;
import com.freegank.bean.DetailData;
import com.freegank.constant.DataStatus;
import com.freegank.interfaces.OnItemClickListener;

import java.util.List;

/**
 * Created by moubiao on 2016/9/18.
 * android，ios，前端等分类的adapter
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mDetailData;
    private OnItemClickListener mItemClickListener;

    private View mDiffView;

    public CategoryAdapter(Context context, List<DetailData> detailData) {
        mContext = context;
        mDetailData = detailData;
    }

    public void setDiffView(View diffView) {
        mDiffView = diffView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDetailData.size() == 0 && mDiffView != null) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return DataStatus.VIEW_TYPE_HAS_DATE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DataStatus.VIEW_TYPE_DIFF_STATUS) {
            return new DiffStatusViewHolder(mDiffView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
            return new CategoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType != DataStatus.VIEW_TYPE_DIFF_STATUS) {
            DetailData data = mDetailData.get(position);
            ((CategoryViewHolder) holder).mCategoryTitleTV.setText(data.getDesc());
            ((CategoryViewHolder) holder).mCategoryAuthorTV.setText(data.getWho());

            if (mItemClickListener == null) {
                return;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(((CategoryViewHolder) holder).mCategoryView, holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mDetailData.size() == 0 && mDiffView != null) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return mDetailData.size();
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        private View mCategoryView;
        private TextView mCategoryTitleTV, mCategoryAuthorTV;

        CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryView = itemView.findViewById(R.id.category_content);
            mCategoryTitleTV = (TextView) itemView.findViewById(R.id.category_title_tv);
            mCategoryAuthorTV = (TextView) itemView.findViewById(R.id.category_author_tv);
        }
    }

    private class DiffStatusViewHolder extends RecyclerView.ViewHolder {

        private DiffStatusViewHolder(View itemView) {
            super(itemView);
        }
    }
}
