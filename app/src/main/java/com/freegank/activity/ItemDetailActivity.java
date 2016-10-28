package com.freegank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.freegank.R;
import com.freegank.bean.IntentConstant;

/**
 * Created by moubiao on 2016/10/9.
 */

public class ItemDetailActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private String contentUrl;
    private boolean mLoadingFlag = true;

    private FrameLayout mContainerFL;
    private WebView mContentWV;
    private View mStatusView;
    private View mLoadingPro;
    private View mErrorView;
    private ImageButton mRetryBT;

    @Override
    public int getLayoutResId() {
        return R.layout.item_detail_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
        setListener();
    }

    private void initData() {
        Intent data = getIntent();
        contentUrl = data.getStringExtra(IntentConstant.CONTENT_URL);
    }

    private void initView() {
        mContainerFL = (FrameLayout) findViewById(R.id.item_container);

        mContentWV = (WebView) findViewById(R.id.content_wv);
        WebSettings settings = mContentWV.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mContentWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingFlag = false;
                displayLoadingStatus(false, false);
            }
        });
        mContentWV.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                displayLoadingStatus(false, true);
            }
        });
        mContentWV.loadUrl(contentUrl);

        mStatusView = findViewById(R.id.item_loading_status);
        mLoadingPro = findViewById(R.id.status_loading);
        mErrorView = findViewById(R.id.status_error);
        mRetryBT = (ImageButton) findViewById(R.id.refresh_bt);
    }

    private void setListener() {
        mRetryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingFlag = true;
                displayLoadingStatus(true, false);
                mContentWV.loadUrl(contentUrl);
            }
        });
    }

    /**
     * 根据加载数据的结果显示不同的view
     */
    private void displayLoadingStatus(boolean refresh, boolean success) {
        if (refresh) {
            mContentWV.setVisibility(View.GONE);

            mStatusView.setVisibility(View.VISIBLE);
            mLoadingPro.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.GONE);
            return;
        }

        if (success && mLoadingFlag) {
            mStatusView.setVisibility(View.GONE);

            mContentWV.setVisibility(View.VISIBLE);
        } else {
            mContentWV.setVisibility(View.GONE);

            mStatusView.setVisibility(View.VISIBLE);
            mLoadingPro.setVisibility(View.GONE);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mContentWV.canGoBack()) {
            mContentWV.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mContentWV != null) {
            mContainerFL.removeView(mContentWV);
            mContentWV.setWebViewClient(null);
            mContentWV.setWebChromeClient(null);
            mContentWV.removeAllViews();
            mContentWV.destroy();
            mContentWV = null;
        }
    }
}
