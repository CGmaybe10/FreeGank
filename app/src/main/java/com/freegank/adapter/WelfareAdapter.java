package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.bean.DetailData;
import com.freegank.constant.DataStatus;
import com.freegank.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moubiao on 2016/9/18.
 * 福利界面的adapter
 */
public class WelfareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mWelfareData;
    private OnItemClickListener mClickListener;

    private View mWelfareDiffView;

    public WelfareAdapter(Context context, List<DetailData> data) {
        mContext = context;
        mWelfareData = data;
    }

    public void setWelfareDiffView(View welfareDiffView) {
        mWelfareDiffView = welfareDiffView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWelfareData.size() == 0 && mWelfareDiffView != null) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return DataStatus.VIEW_TYPE_HAS_DATE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DataStatus.VIEW_TYPE_DIFF_STATUS) {
            return new DiffViewHolder(mWelfareDiffView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.welfare_item, parent, false);
            return new WelfareViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType != DataStatus.VIEW_TYPE_DIFF_STATUS) {
            Picasso.with(mContext)
                    .load(mWelfareData.get(position).getUrl())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_error)
                    .into(((WelfareViewHolder) holder).mWelfareImg);

            ((WelfareViewHolder) holder).mWelfareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.OnItemClick(v, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mWelfareData.size() == 0 && mWelfareDiffView != null) {
            return DataStatus.VIEW_TYPE_DIFF_STATUS;
        } else {
            return mWelfareData.size();
        }
    }

    /**
     * 设置点击图片的监听器
     */
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    class WelfareViewHolder extends RecyclerView.ViewHolder {
        private ImageView mWelfareImg;

        WelfareViewHolder(View itemView) {
            super(itemView);
            mWelfareImg = (ImageView) itemView.findViewById(R.id.welfare_img);
        }
    }

    class DiffViewHolder extends RecyclerView.ViewHolder {

        public DiffViewHolder(View itemView) {
            super(itemView);
        }
    }
}
