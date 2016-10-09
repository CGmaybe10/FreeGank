package com.freegank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.freegank.R;
import com.freegank.bean.IntentConstant;

/**
 * Created by moubiao on 2016/10/9.
 */

public class ContentDetailActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private String contentUrl;

    private WebView mContentWV;

    @Override
    public int getLayoutResId() {
        return R.layout.content_detail_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        Intent data = getIntent();
        contentUrl = data.getStringExtra(IntentConstant.CONTENT_URL);
    }

    private void initView() {
        mContentWV = (WebView) findViewById(R.id.content_wv);
        WebSettings settings = mContentWV.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mContentWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mContentWV.loadUrl(contentUrl);
                return true;
            }
        });
        mContentWV.loadUrl(contentUrl);
    }
}
