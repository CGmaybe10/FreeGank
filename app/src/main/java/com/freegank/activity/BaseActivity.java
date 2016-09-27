package com.freegank.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.freegank.R;

/**
 * Created by moubiao on 2016/9/13.
 * 自定义的activity基类
 * 这是适配状态栏透明
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = "moubiao";

    protected Toolbar mToolbar;
    protected boolean isToolBarHiding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setStatusBarTranslucent();
    }

    private void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected void showOrHideToolbar() {
        mToolbar.animate()
                .translationY(isToolBarHiding ? 0 : -mToolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

    public abstract int getLayoutResId();
}
