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
 * Created by moubiao on 2016/9/18.
 * android，ios，前端等分类的adapter
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mDetailData;

    public CategoryAdapter(Context context, List<DetailData> detailData) {
        mContext = context;
        mDetailData = detailData;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        DetailData data = mDetailData.get(position);
        holder.mCategoryTitleTV.setText(data.getDesc());
        holder.mCategoryAuthorTV.setText(data.getWho());
    }

    @Override
    public int getItemCount() {
        return mDetailData.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mCategoryTitleTV, mCategoryAuthorTV;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryTitleTV = (TextView) itemView.findViewById(R.id.category_title_tv);
            mCategoryAuthorTV = (TextView) itemView.findViewById(R.id.category_author_tv);
        }
    }
}
