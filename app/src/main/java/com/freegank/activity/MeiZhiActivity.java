package com.freegank.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.bean.IntentConstant;
import com.freegank.util.DisplayUtil;
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
        mToolbar.setTranslationY(-DisplayUtil.getActionBarHeight(this) * 2);
        isToolBarHiding = true;
        mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
