package com.freegank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.bean.IntentConstant;
import com.squareup.picasso.Picasso;

/**
 * Created by moubiao on 2016/9/27.
 * 查看图片的界面
 */

public class MeiZhiActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private String IMG_URL;

    private ImageView mMeiZhiIMG;

    @Override
    public int getLayoutResId() {
        return R.layout.mei_zhi_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        Intent data = getIntent();
        IMG_URL = data.getStringExtra(IntentConstant.MEI_ZHI_URL);
    }

    private void initView() {
        hideToolbar();
        mMeiZhiIMG = (ImageView) findViewById(R.id.mei_zhi_img);
        Picasso.with(this)
                .load(IMG_URL)
                .placeholder(R.drawable.default_ic)
                .error(R.drawable.error_ic)
                .into(mMeiZhiIMG);

        mMeiZhiIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideToolbar();
            }
        });
    }

    /**
     * 隐藏toolbar
     */
    private void hideToolbar() {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            int height = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
            mToolbar.setTranslationY(-height);
            isToolBarHiding = true;
        }
    }
}
