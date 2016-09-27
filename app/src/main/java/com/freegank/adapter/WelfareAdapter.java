package com.freegank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.bean.DetailData;
import com.freegank.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moubiao on 2016/9/18.
 * 福利界面的adapter
 */
public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.WelfareViewHolder> {
    private final String TAG = "moubiao";

    private Context mContext;
    private List<DetailData> mData;
    private OnItemClickListener mClickListener;

    public WelfareAdapter(Context context, List<DetailData> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public WelfareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.welfare_item, parent, false);
        return new WelfareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WelfareViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mData.get(position).getUrl())
                .placeholder(R.drawable.default_ic)
                .error(R.drawable.error_ic)
                .into(holder.mWelfareImg);

        holder.mWelfareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.OnClick(v, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
}
